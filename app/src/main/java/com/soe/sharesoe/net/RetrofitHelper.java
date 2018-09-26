package com.soe.sharesoe.net;


/**
 * Created by wangxiaofa on 16/11/22.
 * Retrofit 网络请求基础类
 */

import com.soe.sharesoe.BuildConfig;
import com.soe.sharesoe.base.App;
import com.soe.sharesoe.entity.BannerModel;
import com.soe.sharesoe.entity.CategoryAllModel;
import com.soe.sharesoe.entity.CategoryRecommendModel;
import com.soe.sharesoe.entity.CategorySubModel;
import com.soe.sharesoe.entity.HotRecommendModel;
import com.soe.sharesoe.entity.HttpBaseResult;
import com.soe.sharesoe.entity.OrderCreateModel;
import com.soe.sharesoe.entity.OrderDetailModel;
import com.soe.sharesoe.entity.ProductDetailModel;
import com.soe.sharesoe.entity.ProductOrderListModel;
import com.soe.sharesoe.entity.ProductReserveCreateModel;
import com.soe.sharesoe.entity.ProductReserveModel;
import com.soe.sharesoe.entity.ProductReturnModel;
import com.soe.sharesoe.entity.ResultModel;
import com.soe.sharesoe.manager.UserDataManager;
import com.soe.sharesoe.module.home.ProductList;
import com.soe.sharesoe.module.home.gridviewpager.CategoryNavigation;
import com.soe.sharesoe.module.member.bean.LoginModel;
import com.soe.sharesoe.module.member.bean.RegisterChechMobile;
import com.soe.sharesoe.module.mycenter.bean.CreditChangeModel;
import com.soe.sharesoe.module.mycenter.bean.CreditInfoModel;
import com.soe.sharesoe.module.mycenter.bean.FavoritesModel;
import com.soe.sharesoe.module.mycenter.bean.UserInfoModel;
import com.soe.sharesoe.module.mycenter.order.PayProductDeposit;
import com.soe.sharesoe.module.mycenter.wallet.AccountMyWallet;
import com.soe.sharesoe.module.mycenter.wallet.AccountRecharge;
import com.soe.sharesoe.module.mycenter.wallet.AccountRechargeFlowList;
import com.soe.sharesoe.module.mycenter.wallet.AccountTransactionDetails;
import com.soe.sharesoe.module.nearby.map.cupboard.Cupboard;
import com.soe.sharesoe.module.nearby.map.cupboard.CupboardInfo;
import com.soe.sharesoe.module.nearby.map.cupboard.CupboardProduct;
import com.soe.sharesoe.net.api.ApiStores;
import com.soe.sharesoe.net.http.ApiException;
import com.soe.sharesoe.net.http.OkHttp3Utils;
import com.soe.sharesoe.utils.CommonUtil;
import com.soe.sharesoe.utils.Q;
import com.soe.sharesoe.utils.S;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrofitHelper {

    public static String BASE_URL = "http://192.168.1.195:9999/";//开发环境
//    public static String BASE_URL = "https://operationapp.coolqi.com";//正式环境
//      public static String BASE_URL ="http://123.56.10.103:9800/";//测试环境
//    public static String BASE_URL ="http://192.168.20.177:9800/";//王占
//    public static String BASE_URL ="http://192.168.20.176:9800/";//立业

    private ApiStores apiStores;

    /**
     * UUID
     **/
    public static String uuId = CommonUtil.getIdentity(App.getInstance());

    //构造方法
    private RetrofitHelper() {

        if (BuildConfig.DEBUG) {
            BASE_URL = (String) S.get(App.getInstance(), "BASE_URL", BASE_URL);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //modify by wang 20160317 for 对http请求结果进行统一的预处理 GosnResponseBodyConvert
//                .addConverterFactory(ResponseConvertFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttp3Utils.getOkHttpClient())
                .build();
        apiStores = retrofit.create(ApiStores.class);

    }


    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    //获取单例
    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }


    ////////////////////获取接口返回数据////////////////////////////////////////////




    /**
     * 收藏夹取消
     *
     * @param subscriber
     */
    public void getCollectionCancel(Subscriber<FavoritesModel> subscriber, HashMap<String,String> map) {

        Observable observable = apiStores.getCollectionCancel(map);

        setSubscribeList(observable, subscriber);
    }


    /**
     * 收藏夹数据
     *
     * @param subscriber
     */
    public void getCollection(Subscriber<FavoritesModel> subscriber, HashMap<String,String> map) {

        Observable observable = apiStores.getCollection(map);

        setSubscribeList(observable, subscriber);
    }


    /**
     * 退出登录
     *
     * @param subscriber
     */
    public void getUserLogout(Subscriber<HttpBaseResult> subscriber) {

        Observable observable = apiStores.getUserLogout();

        setSubscribeList(observable, subscriber);
    }

    /**
     * 实名认证
     *
     * @param subscriber
     */
    public void getUserCenterVerify(Subscriber<HttpBaseResult> subscriber, Map<String, String> map) {

        Observable observable = apiStores.getUserCenterVerify(map);

        setSubscribeList(observable, subscriber);
    }


    /**
     * 修改密码确认接口
     *
     * @param subscriber
     */
    public void getUserCenterVerityCodeConfirm(Subscriber<HttpBaseResult> subscriber, Map<String, String> map) {

        Observable observable = apiStores.getUserCenterVerityCodeConfirm(map);

        setSubscribeList(observable, subscriber);
    }

    /**
     * 发送修改密码验证码校验
     *
     * @param subscriber
     */
    public void getUserCenterVerityCodeCheck(Subscriber<HttpBaseResult> subscriber, Map<String, String> map) {

        Observable observable = apiStores.getUserCenterVerityCodeCheck(map);

        setSubscribeList(observable, subscriber);
    }

    /**
     * 发送修改密码验证码接口
     *
     * @param subscriber
     */
    public void getUserCenterVerityCode(Subscriber<HttpBaseResult> subscriber, Map<String, String> map) {

        Observable observable = apiStores.getUserCenterVerityCode(map);

        setSubscribeList(observable, subscriber);
    }


    /**
     * 全部类型
     *
     * @param subscriber
     */
    public void getCategoryAll(Subscriber<List<CategoryAllModel>> subscriber) {

        Observable observable = apiStores.getCategoryAll()
                .map(new HttpResultFunc<List<CategoryAllModel>>());
        setSubscribeList(observable, subscriber);
    }


    /**
     * 用户详情
     *
     * @param subscriber
     */
    public void getUserInfo(Subscriber<UserInfoModel> subscriber) {

        Observable observable = apiStores.getUserInfo();

        setSubscribeList(observable, subscriber);
    }

    /**
     * Banner页数据
     *
     * @param subscriber
     */
    public void getBanner(Subscriber<BannerModel> subscriber, Map<String, String> map) {

        Observable observable = apiStores.getBanner(map);

        setSubscribeList(observable, subscriber);
    }


    /**
     * 分类-热门推荐
     *
     * @param cid
     * @param subscriber
     */
    public void getCategoryRecommend(long cid, Subscriber<List<CategoryRecommendModel>> subscriber) {

        Observable observable = apiStores.getCategoryRecommend(cid)
                .map(new HttpResultFunc<List<CategoryRecommendModel>>());
        setSubscribeList(observable, subscriber);
    }


    /**
     * 分类-二级分类
     *
     * @param cid
     * @param subscriber
     */
    public void getCategorySub(long cid, Subscriber<List<CategorySubModel>> subscriber) {

        Observable observable = apiStores.getCategorySub(cid)
                .map(new HttpResultFunc<List<CategorySubModel>>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 手机号验证
     */
    public Subscriber getRegisterPhoneData(Subscriber<RegisterChechMobile> subscriber, String phone) {

        Observable observable = apiStores.getRegisterChechMobile(phone);

        return setSubscribeList(observable, subscriber);
    }

    /**
     * 获取注册验证码接口
     */

    public Subscriber getRegisterPhoneCode(Subscriber<RegisterChechMobile> subscriber, String phone) {

        Observable observable = apiStores.getRegisterMobileCode(phone);

        return setSubscribeList(observable, subscriber);
    }


    /**
     * 返回用户积分信息
     */

    public Subscriber getCreditInfo(Subscriber<CreditInfoModel> subscriber, String uid) {

        Observable observable = apiStores.getCreditInfo(uid);

        return setSubscribeList(observable, subscriber);
    }

    /**
     * 分页返回用户积分变化信息
     */


    public Subscriber getCreditChange(Subscriber<CreditChangeModel> subscriber, String uid, String pageSize, String page) {

        Observable observable = apiStores.getCreditChange(uid, pageSize, page);

        return setSubscribeList(observable, subscriber);
    }

    /**
     * 注册验证码校验接口
     */

    public Subscriber getRegisterMobileCodeCheck(Subscriber<RegisterChechMobile> subscriber, String mobile, String verifyCode) {

        Observable observable = apiStores.getRegisterMobileCodeCheck(mobile, verifyCode);

        return setSubscribeList(observable, subscriber);
    }

    /**
     * 确认注册
     */

    public Subscriber getRegisteArffirm(Subscriber<RegisterChechMobile> subscriber, HashMap<String, String> map) {

        Observable observable = apiStores.getRegisteArffirm(map);

        return setSubscribeList(observable, subscriber);
    }

    /**
     * 登录
     */

    public Subscriber getLogin(Subscriber<LoginModel> subscriber, HashMap<String, Object> map) {

        Observable observable = apiStores.getLogin(map);

        return setSubscribeList(observable, subscriber);
    }


    /**
     * 查询导航栏显示的顶级分类
     *
     * @param subscriber
     */
    public void getCategoryNavigation(Subscriber<List<CategoryNavigation>> subscriber) {
        Observable observable = apiStores.getCategoryNavigation()
                .map(new HttpResultFunc<List<CategoryNavigation>>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 查询产品
     *
     * @param subscriber
     */
    public void getProductList(Map<String, Object> map, Subscriber<ProductList> subscriber) {
        Observable observable = apiStores.getProductList(map)
                .map(new HttpResultFunc<ProductList>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 热门推荐-商品列表
     *
     * @param subscriber
     */
    public void getHotRecommendGoods(Map<String, Object> map, Subscriber<ProductList> subscriber) {
        Observable observable = apiStores.getHotRecommendGoods(map)
                .map(new HttpResultFunc<ProductList>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 预约列表
     *
     * @param subscriber
     */
    public void getProductReserveList(Subscriber<List<ProductReserveModel>> subscriber) {
        Observable observable = apiStores.getProductReserve("")
                .map(new HttpResultFunc<List<ProductReserveModel>>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 加入预约
     *
     * @param subscriber
     */
    public void getProductReserveCreate(String productId, Subscriber<ProductReserveCreateModel> subscriber) {
        Observable observable = apiStores.getProductReserveCreate(productId);
        setSubscribeList(observable, subscriber);
    }

    /**
     * 取消预约
     *
     * @param subscriber
     */
    public void getProductReserveCancel(String reserveSn, Subscriber<ResultModel> subscriber) {
        Observable observable = apiStores.getProductReserveCancel(reserveSn);
        setSubscribeList(observable, subscriber);
    }

    /**
     * 商品详情
     *
     * @param subscriber
     */
    public void getProductDetail(String id, Subscriber<ProductDetailModel> subscriber) {
        Observable observable = apiStores.getProductDetail(id)
                .map(new HttpResultFunc<ProductDetailModel>());
        setSubscribeList(observable, subscriber);

    }

    /**
     * 我的钱包,返回账户金额
     *
     * @param subscriber
     */
    public void getAccountMyWallet(Map<String, String> map, Subscriber<AccountMyWallet> subscriber) {
        Observable observable = apiStores.getAccountMyWallet(map)
                .map(new HttpResultFunc<AccountMyWallet>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 我的钱包,充值金额配置
     *
     * @param subscriber
     */
    public void getRechargeAmountConfig(Map<String, String> map, Subscriber<List<Integer>> subscriber) {
        Observable observable = apiStores.getRechargeAmountConfig(map)
                .map(new HttpResultFunc<List<Integer>>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 充值
     *
     * @param subscriber
     */
    public void getaccountRecharge(Map<String, String> map, Subscriber<AccountRecharge> subscriber) {
        Observable observable = apiStores.getaccountRecharge(map)
                .map(new HttpResultFunc<AccountRecharge>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 充值明细
     *
     * @param subscriber
     */
    public void getAccountRechargeFlowList(Map<String, String> map, Subscriber<AccountRechargeFlowList> subscriber) {
        Observable observable = apiStores.getAccountRechargeFlowList(map)
                .map(new HttpResultFunc<AccountRechargeFlowList>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 交易明细
     *
     * @param subscriber
     */
    public void getAccountTransactionDetails(Map<String, String> map, Subscriber<AccountTransactionDetails> subscriber) {
        Observable observable = apiStores.getAccountTransactionDetails(map)
                .map(new HttpResultFunc<AccountTransactionDetails>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 创建订单
     *
     * @param subscriber
     */
    public void getProductOrderCreate(String productId, double startLongitude, double startLatitude, Subscriber<OrderCreateModel> subscriber) {
        Observable observable = apiStores.getProductOrderCreate(productId, startLongitude, startLatitude);
        setSubscribeList(observable, subscriber);
    }


    /**
     * 订单列表
     *
     * @param subscriber
     */
    public void getProductOrderList(int type, int pageNo, int pageSize, Subscriber<ProductOrderListModel> subscriber) {
        Observable observable = apiStores.getProductOrderList(type, pageNo, pageSize)
                .map(new HttpResultFunc<ProductOrderListModel>());
        setSubscribeList(observable, subscriber);
    }


    /**
     * 取消订单
     *
     * @param subscriber
     */
    public void getProductOrderCancel(String sn, Subscriber<ResultModel> subscriber) {
        Observable observable = apiStores.getProductOrderCancel(sn);
        setSubscribeList(observable, subscriber);
    }

    /**
     * 热门推荐
     *
     * @param subscriber
     */
    public void getHotRecommend(int pageSize, int page, Subscriber<List<HotRecommendModel>> subscriber) {
        Observable observable = apiStores.getHotRecommend(pageSize, page)
                .map(new HttpResultFunc<List<HotRecommendModel>>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 获取附近柜子
     *
     * @param subscriber
     */
    public void getCupboardSearchAroundCupboard(Map<String, String> map, Subscriber<List<Cupboard>> subscriber) {
        Observable observable = apiStores.getCupboardSearchAroundCupboard(map)
                .map(new HttpResultFunc<List<Cupboard>>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 根据柜子ID获取产品列表
     *
     * @param subscriber
     */
    public void getProductByCupboardId(Map<String, String> map, Subscriber<List<CupboardProduct>> subscriber) {
        Observable observable = apiStores.getProductByCupboardId(map)
                .map(new HttpResultFunc<List<CupboardProduct>>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 根据柜子ID获取产品列表
     *
     * @param subscriber
     */
    public void getCupboardByCupboardId(Map<String, String> map, Subscriber<CupboardInfo> subscriber) {
        Observable observable = apiStores.getCupboardByCupboardId(map)
                .map(new HttpResultFunc<CupboardInfo>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 支付商品押金或租金
     *
     * @param subscriber
     */
    public void getPayProductDeposit(Map<String, String> map, Subscriber<PayProductDeposit> subscriber) {
        Observable observable = apiStores.getPayProductDeposit(map);
        setSubscribeList(observable, subscriber);
    }

    /**
     * 订单详情
     *
     * @param subscriber
     */
    public void getOrderDetail(String sn, Subscriber<OrderDetailModel> subscriber) {
        Observable observable = apiStores.getOrderDetail(sn)
                .map(new HttpResultFunc<OrderDetailModel>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 开锁成功
     *
     * @param subscriber
     */
    public void getOrderLockOpen(String sn, Subscriber<Object> subscriber) {
        Observable observable = apiStores.getOrderLockOpen(sn)
                .map(new HttpResultFunc<Object>());
        setSubscribeList(observable, subscriber);
    }


    /**
     * 商品归还
     *
     * @param subscriber
     */
    public void getProductReturn(String sn, Subscriber<ProductReturnModel> subscriber) {
        Observable observable = apiStores.getProductReturn(sn);
        setSubscribeList(observable, subscriber);
    }

    /**
     * 添加商品收藏
     *
     * @param subscriber
     */
    public void getCollectionAdd(String productId, Subscriber<Object> subscriber) {
        Observable observable = apiStores.getCollectionAdd(productId)
                .map(new HttpResultFunc<Object>());
        setSubscribeList(observable, subscriber);
    }

    /**
     * 取消商品收藏
     *
     * @param subscriber
     */
    public void getCollectionCancel(String productId, Subscriber<Object> subscriber) {
        Observable observable = apiStores.getCollectionCancel(productId)
                .map(new HttpResultFunc<Object>());
        setSubscribeList(observable, subscriber);
    }

/////////////////////////////////基础RxJava请求//////////////////////////////////////////////////////////

    /**
     * 插入观察者-泛型
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public <T> Subscriber setSubscribeList(Observable<T> observable, Subscriber<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return observer;
    }

    /**
     * 插入观察者-Object
     *
     * @param observable
     * @param observer
     */
    public void setSubscribeObject(Observable<Object> observable, Observer<Object> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

/////////////////////////////HttpBaseResult<T>////////////////////////////////////////////////

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是result部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpBaseResult<T>, T> {

        @Override
        public T call(HttpBaseResult<T> httpResult) {

            if (httpResult.getCode() == 1000) {

                String token = httpResult.getParams().getToken();
                if (!Q.isEmpty(token)) {
                    UserDataManager.setToken(token);
                }
                return httpResult.getResult();

            } else if (httpResult.getCode() == 5001) { // 车锁编号已经关联
                //返回数据
                return httpResult.getResult();
            } else if (httpResult.getCode() == 5002) { // 车尾编号已经关联
                //返回数据
                return httpResult.getResult();
            } else if (httpResult.getCode() == 5003) { // 车尾和车锁已经是绑定状态
                //返回数据
                return httpResult.getResult();
            } else if (httpResult.getCode() == 5004) { // 二维码未关联
                //返回数据
                return httpResult.getResult();
            } else if (httpResult.getCode() == -1) {
                throw new ApiException(httpResult.getCode(), httpResult.getMsg());
            } else if (httpResult.getCode() == 302) { // 手机号或密码错误
                throw new ApiException(httpResult.getCode(), httpResult.getMsg());
            } else if (httpResult.getCode() == 203) { // 登录超时
                throw new ApiException(httpResult.getCode(), httpResult.getMsg());
            } else if (httpResult.getCode() == 204) { // 登录验证失败
                throw new ApiException(httpResult.getCode(), httpResult.getMsg());
            } else if (httpResult.getCode() == 205) { // 用户信息变更
                throw new ApiException(httpResult.getCode(), httpResult.getMsg());
            } else if (httpResult.getCode() == 417) {//获取区域负责人
                throw new ApiException(httpResult.getCode(), httpResult.getMsg());
            } else {
                throw new ApiException(httpResult.getCode(), httpResult.getMsg());
            }

        }
    }


}

