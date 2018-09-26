package com.soe.sharesoe.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.soe.sharesoe.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
 * @version v1.0
 * @project coolqi
 * @Description
 * @encoding UTF-8
 * @date 16/12/31
 * @time 下午4:02
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class UpdateVersionDialog extends Dialog {

    private Context context = null;


    public String content = "";

    public String title = "";

    public int viewFlag = View.GONE;

    private TextView dialog_update_title;

    private TextView dialog_update_content;

    private ImageView dialog_update_close;

    private TextView dialog_update_login;

    //返回的安装包url
    private String apkUrl = "";
    /* 下载包安装路径 */
    // private static final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath(); //"/data/data/com.coolqi.partner.breaf/files/"

    private static final String saveFileName = "Coolqi.apk";

    private boolean interceptFlag = false;

    private int progress;

    private Thread downLoadThread;

    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;
    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;

    //是否存在更新好的安装包
    private boolean isExistNewApk = false;
    private static final String APK_DOWN_PATH = "/coolqi/download";

    public UpdateVersionDialog(Context context) {
        super(context);
        this.context = context;
        initView();

    }

    public UpdateVersionDialog(Context context, int theme, String apkUrl) {
        super(context, theme);
        this.context = context;
        this.apkUrl = apkUrl;

        initView();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:
                    installApk();

                    dialog_update_login.setEnabled(true);
                    isExistNewApk = true;
                    break;
                default:
                    break;
            }
        }
    };

    private void initView() {

        View mView = LayoutInflater.from(this.context).inflate(R.layout.widget_update_dialog, null);

        dialog_update_close = (ImageView) mView.findViewById(R.id.dialog_update_close);
        dialog_update_title = (TextView) mView.findViewById(R.id.dialog_update_title);
        dialog_update_content = (TextView) mView.findViewById(R.id.dialog_update_content);
        dialog_update_login = (TextView) mView.findViewById(R.id.dialog_update_login);
        mProgress = (ProgressBar) mView.findViewById(R.id.widget_dialog_pg_progress);
        mProgress.setVisibility(View.GONE);

        dialog_update_close.setOnClickListener(mClick);
        dialog_update_login.setOnClickListener(mClick);

        setContentView(mView);
    }

    private View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_update_close:
                    UpdateVersionDialog.this.dismiss();
                    interceptFlag = true;
                    break;
                case R.id.dialog_update_login:
                    //存在已下载好的安装包,直接安装
                    if (isExistNewApk) {
                        installApk();
                    } else {
                        File apkfile = createAPKFile(getContext(), saveFileName);
                        if (apkfile.exists()) { //if 存在旧包,则删除旧包,重新下载新包
                            apkfile.delete();
                        }
                        mProgress.setVisibility(View.VISIBLE);
                        dialog_update_login.setEnabled(false);
                        downloadApk();
                    }
                    break;
            }
        }
    };

    /**
     * 查看手机中所有的应用市场：
     */
    private void checkPhoneGoodsAndBrowser() {

        /** 打开浏览器下载**/
        Uri uris = Uri.parse(apkUrl);
        Intent browser = new Intent(Intent.ACTION_VIEW, uris);
        browser.setData(uris);
        browser.addCategory("android.intent.category.BROWSABLE");
        context.startActivity(browser);
    }

    /**
     * 下载apk
     *
     * @param
     */

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

//				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//				conn.connect();
                int length = (int) response.body().contentLength();
                InputStream is = response.body().byteStream();

                File apkFile = createAPKFile(getContext(), saveFileName);
                FileOutputStream fos = new FileOutputStream(apkFile);
//                FileOutputStream fos = context.getApplicationContext().openFileOutput(apkFile, Context.MODE_WORLD_READABLE +Context.MODE_WORLD_WRITEABLE);


                int count = 0;
                int len = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    //更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);

                } while (!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public void setFilePathPermissions(String name, int mode,
                                       int extraPermissions) {
        Class<?> clazz;
        try {
            // 包名加类名
            clazz = Class.forName("android.app.ContextImpl");
            // 方法名
            Method method = clazz.getDeclaredMethod(
                    "setFilePermissionsFromMode", String.class,
                    int.class, int.class);
            method.invoke(null, name, mode, extraPermissions);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安装apk
     *
     * @param
     */
    private void installApk() {
        File apkfile = createAPKFile(this.getContext(), saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        try {
            String command = "chmod 777 " + apkfile.getAbsolutePath();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);

//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri contentUri = FileProvider.getUriForFile(context, getContext().getPackageName() + ".provider", apkfile);
                Log.i("contentUri", contentUri.toString());
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void show() {
        super.show();
        dialog_update_close.setVisibility(viewFlag);
        dialog_update_title.setText(title);
        dialog_update_content.setText(content);
    }

    public File createAPKFile(Context context, String fileName) {
        String state = Environment.getExternalStorageState();
        File rootDir = state.equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : context.getFilesDir();
        File folderDir = new File(rootDir.getAbsolutePath() + APK_DOWN_PATH);
        if (!folderDir.exists()) {
            folderDir.mkdirs();
        }
        try {
            String command = "chmod 777 " + folderDir.getAbsolutePath() + " " + folderDir.getParentFile().getAbsolutePath() + " " + folderDir.getParentFile().getParentFile().getAbsolutePath();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File tmpFile = new File(folderDir, fileName);
        return tmpFile;
    }
}
