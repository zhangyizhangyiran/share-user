package com.soe.sharesoe.net.api;

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

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by wangxiaofa on 16/11/23.
 * 项目所需接口
 */

public interface ApiStores {

    String emptyParams = "";//retrofit2.http.POST请求方式必须包含一个参数,这里我们传入一个空参数
//    /**
//     * 获取验证码
//     **/
//    @GET("/v1/sms/verifyCode")
//    Observable<HttpBaseResult> getPhoneVerify(@Query("phone") String phone);
//
//    /**
//     * 获取注册登录-返回数据
//     **/
//    @FormUrlEncoded
//    @POST("/v1/loginAndRegister")
//    Observable<HttpBaseResult<RegisterModel>> getloginAndRegister(@Field("phone") String phone, @Field("code") String code,
//                                                                  @Field("jpushRegid") String jpushRegid, @Field("businessLoginPwd") String businessLoginPwd);



//

    /**
     * 收藏夹数据
     **/
    @GET("/product/collection/cancel?")
    Observable<HttpBaseResult> getCollectionCancel(@QueryMap Map<String, String> map);


    /**
     * 收藏夹数据
     **/
    @GET("/product/collection/get?")
    Observable<FavoritesModel> getCollection(@QueryMap Map<String, String> map);

    /**
     * 用户退出接口
     **/
    @POST("/logout?")
    Observable<HttpBaseResult> getUserLogout();


    /**
     * 用户实名认证接口
     **/
    @POST("/user/center/verify?")
    Observable<HttpBaseResult> getUserCenterVerify(@QueryMap Map<String, String> map);

    /**
     * 修改密码确认接口接口
     **/
    @POST("/user/center/modifyPwd/confirm?")
    Observable<HttpBaseResult> getUserCenterVerityCodeConfirm(@QueryMap Map<String, String> map);

    /**
     * 修改密码验证码校验接口
     **/
    @POST("/user/center/modifyPwd/verityCode/check?")
    Observable<HttpBaseResult> getUserCenterVerityCodeCheck(@QueryMap Map<String, String> map);

    /**
     * 发送修改密码验证码接口
     **/
    @POST("/user/center/modifyPwd/verityCode?")
    Observable<HttpBaseResult> getUserCenterVerityCode(@QueryMap Map<String, String> map);

    /**
     * Banner页
     **/
    @GET("/common/getAdBanner?")
    Observable<BannerModel> getBanner(@QueryMap Map<String, String> map);

    /**
     * 用户详情
     **/
    @GET("/user/center/info?")
    Observable<UserInfoModel> getUserInfo();

    /**
     * 全部分类
     **/
    @GET("/category/all")
    Observable<HttpBaseResult<List<CategoryAllModel>>> getCategoryAll();

    /**
     * 分类-热门推荐
     **/
    @GET("/category/recommend")
    Observable<HttpBaseResult<List<CategoryRecommendModel>>> getCategoryRecommend(@Query("cid") long cityId);

    /**
     * 分类-二级分类
     **/
    @GET("/category/sub")
    Observable<HttpBaseResult<List<CategorySubModel>>> getCategorySub(@Query("cid") long cityId);

    //注册手机号校验接口
    @FormUrlEncoded
    @POST("/register/check/mobile?")
    Observable<RegisterChechMobile> getRegisterChechMobile(@Field("mobile") String phone);

    //获取注册验证码接口
    @FormUrlEncoded
    @POST("/register/verityCode?")
    Observable<RegisterChechMobile> getRegisterMobileCode(@Field("mobile") String phone);

    //注册验证码校验接口
    @FormUrlEncoded
    @POST("/register/verityCode/check?")
    Observable<RegisterChechMobile> getRegisterMobileCodeCheck(@Field("mobile") String phone, @Field("verifyCode") String verifyCode);

    //确认注册
    @POST("/register/confirm?")
    Observable<RegisterChechMobile> getRegisteArffirm(@QueryMap Map<String, String> map);

    //确认注册
    @POST("/login?")
    Observable<LoginModel> getLogin(@QueryMap Map<String, Object> map);

    //查询导航栏显示的顶级分类
    @GET("/category/navigation?")
    Observable<HttpBaseResult<List<CategoryNavigation>>> getCategoryNavigation();

    //返回用户积分信息
    @GET("/credit/info?")
    Observable<CreditInfoModel> getCreditInfo(@Query("uid") String uid);

    //分页返回用户积分变化信息
    @GET("/credit/change?")
    Observable<CreditChangeModel> getCreditChange(@Query("uid") String uid, @Query("pageSize") String pageSize, @Query("page") String page);

    //查询产品
    @GET("/product/list?")
    Observable<HttpBaseResult<ProductList>> getProductList(@QueryMap Map<String, Object> map);

    //我的钱包,返回账户金额
    @GET("/account/myWallet?")
    Observable<HttpBaseResult<AccountMyWallet>> getAccountMyWallet(@QueryMap Map<String, String> map);

    //我的钱包,充值金额配置
    @GET("/account/getRechargeAmountConfig?")
    Observable<HttpBaseResult<List<Integer>>> getRechargeAmountConfig(@QueryMap Map<String, String> map);

    //我的钱包,充值
    @GET("/account/recharge?")
    Observable<HttpBaseResult<AccountRecharge>> getaccountRecharge(@QueryMap Map<String, String> map);

    //我的钱包,充值明细
    @GET("/account/rechargeFlowList?")
    Observable<HttpBaseResult<AccountRechargeFlowList>> getAccountRechargeFlowList(@QueryMap Map<String, String> map);

    //我的钱包,交易明细
    @GET("/account/transactionDetails?")
    Observable<HttpBaseResult<AccountTransactionDetails>> getAccountTransactionDetails(@QueryMap Map<String, String> map);

    //已预约列表
    @FormUrlEncoded
    @POST("/product/reserve/lists")
    Observable<HttpBaseResult<List<ProductReserveModel>>> getProductReserve(@Field(emptyParams) String data);

    //预约商品
    @FormUrlEncoded
    @POST("/product/reserve/create")
    Observable<ProductReserveCreateModel> getProductReserveCreate(@Field("productId") String productId);

    //取消预约商品
    @FormUrlEncoded
    @POST("/product/reserve/cancel")
    Observable<ResultModel> getProductReserveCancel(@Field("reserveSn") String reserveSn);

    //商品详情
    @GET("/product/detail")
    Observable<HttpBaseResult<ProductDetailModel>> getProductDetail(@Query("id") String id);

    //创建订单
    @FormUrlEncoded
    @POST("box/order/create")
    Observable<OrderCreateModel> getProductOrderCreate(@Field("productId") String productId, @Field("startLongitude") double startLongitude, @Field("startLatitude") double startLatitude);

    //订单列表 订单类型type=1 & page=1&pageSize=20
    @FormUrlEncoded
    @POST("/box/order/lists")
    Observable<HttpBaseResult<ProductOrderListModel>> getProductOrderList(@Field("type") int type, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);

    //取消订单接口
    @FormUrlEncoded
    @POST("/box/order/cancel")
    Observable<ResultModel> getProductOrderCancel(@Field("sn") String sn);

    //热门推荐
    @FormUrlEncoded
    @POST("/topic/topic_list")
    Observable<HttpBaseResult<List<HotRecommendModel>>> getHotRecommend(@Field("pageSize") int pageSize, @Field("page") int page);

    //热门推荐-商品列表
    @GET("/topic/topic_goods")
    Observable<HttpBaseResult<ProductList>> getHotRecommendGoods(@QueryMap Map<String, Object> map);

    //附近,获取附近柜子
    @GET("/cupboard/searchAroundCupboard?")
    Observable<HttpBaseResult<List<Cupboard>>> getCupboardSearchAroundCupboard(@QueryMap Map<String, String> map);

    //附近,根据柜子ID获取产品列表
    @GET("/cupboard/getProductByCupboardId?")
    Observable<HttpBaseResult<List<CupboardProduct>>> getProductByCupboardId(@QueryMap Map<String, String> map);

    //附近,根据柜子ID获取柜子详情
    @GET("/cupboard/getCupboardByCupboardId?")
    Observable<HttpBaseResult<CupboardInfo>> getCupboardByCupboardId(@QueryMap Map<String, String> map);

    //支付商品押金或租金
    @GET("/account/payProductDeposit?")
    Observable<PayProductDeposit> getPayProductDeposit(@QueryMap Map<String, String> map);

    //订单详情
    @GET("/box/order/detail")
    Observable<HttpBaseResult<OrderDetailModel>> getOrderDetail(@Query("sn") String sn);

    //开锁成功
    @FormUrlEncoded
    @POST("/box/order/success/open")
    Observable<HttpBaseResult<Object>> getOrderLockOpen(@Field("sn") String sn);

    //归还商品
    @FormUrlEncoded
    @POST("/box/order/cancel/rent")
    Observable<ProductReturnModel> getProductReturn(@Field("sn") String sn);

    //添加商品
    @GET("/product/collection/add")
    Observable<HttpBaseResult<Object>> getCollectionAdd(@Query("productId") String productId);

    //取消商品收藏
    @GET("/product/collection/cancel")
    Observable<HttpBaseResult<Object>> getCollectionCancel(@Query("productId") String productId);

}




