package com.soe.sharesoe.net.http;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.soe.sharesoe.BuildConfig;
import com.soe.sharesoe.base.App;
import com.soe.sharesoe.base.Constant;
import com.soe.sharesoe.manager.UserDataManager;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.CommonUtil;
import com.soe.sharesoe.utils.S;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 类描述：封装一个OkHttp3的获取类
 * Created by YSL on 2016/8/3 0003.
 */
public class OkHttp3Utils {

    private static OkHttpClient mOkHttpClient;
    private static final int DEFAULT_TIMEOUT = 10;


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     * 可以通过HttpLoggingInterceptor中 setLevel 改变日志级别
     * 共包含四个级别：NONE、BASIC、HEADER、BODY
     * NONE 不记录,
     */
    //Todo 上线版本时,需要将Level 设置为:NONE 不显示
    public static OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(App.getInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())
                            //   .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }

        return mOkHttpClient;
    }

    /**
     * 设置证书
     */
    public static void InitHttpsClent() {
        SSLSocketFactory sslSocketFactory;

        try {
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        }
        Cache cache = new Cache(new File(App.getInstance()
                .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
        mOkHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory)
                .cache(cache)
                .addInterceptor(new CacheInterceptor())
                .addInterceptor(interceptor)
                //   .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private static class CacheInterceptor implements Interceptor {


        public String gzipDecode(InputStream inputStream) {
            GZIPInputStream in = null;
            ByteArrayOutputStream arrayOutputStream = null;
            try {
                in = new GZIPInputStream(inputStream);
                arrayOutputStream = new ByteArrayOutputStream();
                int len;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    arrayOutputStream.write(buffer, 0, len);
                }
                return new String(arrayOutputStream.toByteArray(), "utf-8");
            } catch (Exception e) {
                // e.printStackTrace();
            } finally {
                Util.closeQuietly(in);
                Util.closeQuietly(arrayOutputStream);
            }
            return null;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间60秒
            int maxAge = 0;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request oldRequest = chain.request();
            Response response = null;

            // 新的请求,添加参数 (方案1)
            /*Request newRequest = addParam(oldRequest);
            response = chain.proceed(newRequest);
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Buffer bufferOut = new Buffer();
            buffer.copyTo(bufferOut, 0, buffer.size());
            String json = gzipDecode(bufferOut.inputStream());
            if (json == null) {
                json = buffer.clone().readString(Charset.forName("UTF-8"));
            }*/
            // 新的请求,添加参数(方案2)
            Request newRequest = addParam(oldRequest);
            if (CommonUtil.isNetworkAvailable(App.getInstance())) {
                //有网络时只从网络获取
                newRequest = newRequest.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("X-H", getAAA())
                        .addHeader("Accept", "*/*")
                        .addHeader("uuid", RetrofitHelper.uuId)
                        .addHeader("token", UserDataManager.getToken())
                        .addHeader("invalidate_ts", String.valueOf(System.currentTimeMillis()))
                        .build();
            } else {
                //无网络时只从缓存中读取
                newRequest = newRequest.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .addHeader("uuid", RetrofitHelper.uuId)
                        .addHeader("token", UserDataManager.getToken())
                        .addHeader("invalidate_ts", String.valueOf(System.currentTimeMillis()))
                        .build();
            }
            response = chain.proceed(newRequest);

            ResponseBody value = response.body();

            byte[] resp = value.bytes();

            String json = new String(resp, "UTF-8");

            Logger.i(newRequest.headers().toString() + "\n" + newRequest + "\n" + response + "\n" + json);

//            判断code码,验证登录情况
            if (!TextUtils.isEmpty(json)) {
                if (CheckRepeatLogin(json)) {
                    UserDataManager.clear(App.getInstance());
                }
            }
            // 判断stateCode值,token失效的stateCode为3，新的token在data字段中返回
            try {
                JSONObject jsonObject = new JSONObject(json);
                int stateCode = jsonObject.optInt("code");
                if (stateCode == Constant.CODE_LOGIN_FAIL) {
                    UserDataManager.clear(App.getInstance());
                    JSONObject params = jsonObject.getJSONObject("params");
                    String data = params.optString("token");
                    UserDataManager.setToken(data);
                    // token失效，重新执行请求
                    Request newTokenRequest = addParam(oldRequest);
                    response = chain.proceed(newTokenRequest);
                } else {
                    // 这里值得注意。由于前面value.bytes()把响应流读完并关闭了，所以这里需要重新生成一个response，否则数据就无法正常解析了
                    response = response.newBuilder()
                            .body(ResponseBody.create(null, resp))
                            .build();
                }
            } catch (Exception e) {

            }
            if (CommonUtil.isNetworkAvailable(App.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

            return response;
        }


        /**
         * 检查重复登录
         */
        private boolean CheckRepeatLogin(String content) {
            // TODO Auto-generated method stub
            try {
                JSONObject jsonObject = new JSONObject(content);
                int code = jsonObject.getInt("code");
                if (Constant.CODE_LOGIN_TIMEOUT == code || Constant.CODE_LOGIN_CHANGE == code) {
                    S.put(App.getInstance(), "isLogin", "false");
                    //ActivityManager.startActivity(App.getInstance(), MemberActivity.class);
                    return true;
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }

        /**
         * 添加公共参数
         *
         * @param oldRequest
         * @return
         */
        private Request addParam(Request oldRequest) {
            HttpUrl.Builder builder = oldRequest.url()
                    .newBuilder()                    .setEncodedQueryParameter("uuid", getAAA())
                    .setEncodedQueryParameter("token", UserDataManager.getToken())
                    .setEncodedQueryParameter("invalidate_ts", String.valueOf(System.currentTimeMillis()));

            return oldRequest.newBuilder()
                    .url(builder.build())
                    .build();
        }
    }
    /**
     * 以流的方式添加信任证书
     */
    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     * <p>
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     * <p>
     * <p>
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     * <p>
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private static X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "123456".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * 添加password
     *
     * @param password
     * @return
     * @throws GeneralSecurityException
     */
    private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    //故意乱取名、、 此方法获取手机的IMEI(uuId)
    private static String getAAA() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return RetrofitHelper.uuId;
        }
    }
}
