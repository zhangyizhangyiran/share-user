package com.soe.sharesoe.module.goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxLazyFragment;
import com.soe.sharesoe.entity.ProductDetailModel;
import com.soe.sharesoe.entity.ProductReserveCreateModel;
import com.soe.sharesoe.entity.ResultModel;
import com.soe.sharesoe.module.home.GlideImageLoader;
import com.soe.sharesoe.module.main.OnChangeTabListener;
import com.soe.sharesoe.module.mycenter.activity.AutonymActivity;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.T;
import com.soe.sharesoe.widget.SlideDetailsLayout;
import com.soe.sharesoe.widget.dialog.PromptDialog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static com.soe.sharesoe.utils.S.get;


/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 商品详情页
 * @encoding UTF-8
 * @date 2017/11/10
 * @time 下午3:28
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class GoodsDetailFragment extends RxLazyFragment implements View.OnClickListener, SlideDetailsLayout.OnSlideDetailsListener {

    @BindView(R.id.vp_item_goods_img)
    Banner vp_item_goods_img;
    @BindView(R.id.tv_goods_title)
    TextView tv_goods_title;
    @BindView(R.id.tv_rental_price)
    TextView tv_rental_price;
    @BindView(R.id.tv_deposit_price)
    TextView tv_deposit_price;
    @BindView(R.id.box_name)
    TextView box_name;
    @BindView(R.id.goods_info_collection)
    ImageView goods_info_collection;
    @BindView(R.id.tv_current_goods)
    TextView tv_current_goods;
    @BindView(R.id.tv_reserve_goods)
    TextView tv_reserve_goods;
    @BindView(R.id.ll_current_goods)
    LinearLayout ll_current_goods;
    @BindView(R.id.box_location)
    TextView box_location;
    @BindView(R.id.iv_comment_right)
    ImageView ivCommentRight;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    @BindView(R.id.ll_pull_up)
    LinearLayout ll_pull_up;
    @BindView(R.id.sv_goods_info)
    ScrollView sv_goods_info;
    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.sv_switch)
    SlideDetailsLayout sv_switch;
    @BindView(R.id.fab_up_slide)
    FloatingActionButton fab_up_slide;

    /**
     * 当前商品详情数据页的索引分别是图文详情、规格参数
     */
    public GoodsDetailWebFragment goodsInfoWebFragment;
    private Fragment nowFragment;
    private FragmentManager fragmentManager;
    public Activity activity;

    private String pId;
    private int status;

    private double latestLongitude, latestLatitude;

    private final long INTERVAL = 1000L;
    private long times = 0;
    private String orderSn = "";
    private boolean isReserve = false; //是否被预约
    private boolean isCollection = false; //是否已收藏
    private MyCountDownTimer timer;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_goods_info;
    }

    @Override
    public void finishCreateView(Bundle state) {
        activity = getActivity();
        Intent intent = getActivity().getIntent();
        pId = intent.getStringExtra("pId");
        status = intent.getIntExtra("status", 0);

        //状态 1预约商品 2已借用 3已预约
        if (status == 1) {
            tv_reserve_goods.setText("预约商品");
            tv_reserve_goods.setEnabled(true);
        } else if (status == 2) {
            tv_reserve_goods.setText("已借用");
            tv_reserve_goods.setEnabled(false);

        } else if (status == 3) {
            tv_reserve_goods.setText("已预约");
            tv_reserve_goods.setEnabled(false);
        }
        //加载详情数据
        setDetailData();

        //设置文字中间一条横线
        //tv_deposit_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        fab_up_slide.hide();
    }


    /**
     * 加载完商品详情执行
     */
    public void setDetailData() {

        setProductDetailData();
        goodsInfoWebFragment = new GoodsDetailWebFragment();

        nowFragment = goodsInfoWebFragment;
        fragmentManager = getChildFragmentManager();
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.fl_content, nowFragment).commitAllowingStateLoss();
    }


    @OnClick({R.id.ll_pull_up, R.id.fab_up_slide, R.id.tv_reserve_goods, R.id.ll_comment, R.id.goods_info_collection})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pull_up:
                //上拉查看图文详情
                sv_switch.smoothOpen(true);
                break;

            case R.id.fab_up_slide:
                //点击滑动到顶部
                sv_goods_info.smoothScrollTo(0, 0);
                sv_switch.smoothClose(true);
                break;

            case R.id.tv_reserve_goods:
                if (isLogin()) {
                    if (!"true".equals(get(getActivity(), "attestation", ""))) {
                        PromptDialog.showDialog(getActivity(), "还没有进行实名认证呦！", new PromptDialog.ReserveClickListenr() {
                            @Override
                            public void onClick() {
                                startActivityForResult(new Intent(getActivity(), AutonymActivity.class), 300);

                            }
                        });
                        return;
                    }

                    Log.e("isReserve==", isReserve + "");
                    if (isReserve) {
                        if (!TextUtils.isEmpty(orderSn))
                            productReserveCancel(orderSn);
                    } else {


                        productReserveCreate();
                    }
                }

                break;

            case R.id.ll_comment:
                EventBus.getDefault().post(new OnChangeTabListener(1, latestLatitude, latestLongitude));
                getActivity().finish();
                break;

            case R.id.goods_info_collection:
                if (isLogin()) {
                    //已收藏
                    if (isCollection) {
                        getCollectionCancel();
                    } else {
                        getCollectionAdd();
                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 给商品轮播图设置图片路径
     */
    final ArrayList<String> imgUrls = new ArrayList<>();

    public void setLoopView() {

        //初始化商品图片轮播

        //设置banner样式
        //vp_item_goods_img.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        vp_item_goods_img.setImageLoader(new GlideImageLoader());
        //设置图片集合
        vp_item_goods_img.setImages(imgUrls);
        //设置banner动画效果
        //banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        vp_item_goods_img.isAutoPlay(false);
        //设置轮播时间
        //banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        //banner.setIndicatorGravity(BannerConfig.CENTER);
        vp_item_goods_img.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                startActivity(new Intent(getActivity(), GoodsDetailImageActivity.class)
                        .putExtra("pos", position)
                        .putStringArrayListExtra("list", imgUrls));

            }
        });
        //banner设置方法全部调用完毕时最后调用
        vp_item_goods_img.start();
    }

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            //当前为图文详情页
            fab_up_slide.show();

        } else {
            //当前为商品详情页
            fab_up_slide.hide();
        }
    }

    /**
     * 商品详情页数据
     */
    private void setProductDetailData() {

        RetrofitHelper.getInstance().getProductDetail(pId, new Subscriber<ProductDetailModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ProductDetailModel productDetailModel) {
                imgUrls.addAll(productDetailModel.getImgs());
                tv_goods_title.setText(productDetailModel.getProductName());
                tv_rental_price.setText(productDetailModel.getRental());
                tv_deposit_price.setText("押金：" + productDetailModel.getDeposit() + "元");

                latestLongitude = productDetailModel.getBoxs().get(0).getLatestLongitude();
                latestLatitude = productDetailModel.getBoxs().get(0).getLatestLatitude();

                String boxName = productDetailModel.getBoxs().get(0).getBoxName() == "" ? "???" : productDetailModel.getBoxs().get(0).getBoxName();
                String boxLocation = productDetailModel.getBoxs().get(0).getAddress() == null ? "???" : productDetailModel.getBoxs().get(0).getAddress();
                box_name.setText(boxName);
                box_location.setText(boxLocation);

               /* new GeoCode().ReverseGeoCode(latestLatitude, latestLongitude, new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

                        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                            //没有检索到结果
                        } else {

                        }
                    }
                });*/
                //收藏 ：1是收藏
                int collection = productDetailModel.getCollection();
                if (collection == 1) {
                    goods_info_collection.setImageResource(R.mipmap.icon_like);
                    isCollection = true;
                } else {
                    goods_info_collection.setImageResource(R.mipmap.icon_like_normal);
                    isCollection = false;
                }

                times = productDetailModel.getTimes();
                status = productDetailModel.getStatus();
                int me = productDetailModel.getMe();
                orderSn = productDetailModel.getReserveSn();
                //状态 1预约商品 2已借用 3已预约
                if (status == 1) {
                    tv_reserve_goods.setText("预约商品");
                    tv_reserve_goods.setEnabled(true);
                    isReserve = false;
                } else if (status == 2) {
                    tv_reserve_goods.setText("已借用");
                    tv_reserve_goods.setEnabled(false);
                } else if (status == 3) {
                    //是否是自己预约的：1 是，0否
                    if (me == 0) {
                        tv_reserve_goods.setText("已预约");
                        tv_reserve_goods.setEnabled(false);

                    } else if (me == 1) {
                        tv_reserve_goods.setText("取消预约");
                        tv_reserve_goods.setEnabled(true);
                        isReserve = true;
                    }

                    if (times > 0) {
                        startTimer(times, tv_reserve_goods);

                    }
                }

                setLoopView();
            }
        });
    }

    /**
     * @author wangxiaofa
     * @time 2017/11/10 下午3:30
     * @Description 预约商品
     */
    private void productReserveCreate() {

        RetrofitHelper.getInstance().getProductReserveCreate(pId, new Subscriber<ProductReserveCreateModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ProductReserveCreateModel productReserveModels) {

                times = productReserveModels.getParams().getTimes();
                orderSn = productReserveModels.getParams().getReserveSn();
                //状态 1预约商品 2已借用 3已预约
                startTimer(times, tv_reserve_goods);
                tv_reserve_goods.setEnabled(true);
                isReserve = true;

            }
        });
    }

    /**
     * @author wangxiaofa
     * @time 2017/11/10 下午3:08
     * @Description 取消预约
     */
    private void productReserveCancel(String reserveSn) {
        RetrofitHelper.getInstance().getProductReserveCancel(reserveSn, new Subscriber<ResultModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                T.normal(e.getMessage());
            }

            @Override
            public void onNext(ResultModel result) {
                cancelTimer();
                tv_reserve_goods.setText("预约商品");
                tv_reserve_goods.setEnabled(true);
                isReserve = false;
            }
        });
    }

    /**
     * 添加收藏
     */

    private void getCollectionAdd() {

        RetrofitHelper.getInstance().getCollectionAdd( pId, new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                T.normal(e.getMessage());
            }

            @Override
            public void onNext(Object orderDetailModel) {
                isCollection = true;
                goods_info_collection.setImageResource(R.mipmap.icon_like);
                T.normal("收藏成功");
                Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 取消收藏
     */
    private void getCollectionCancel() {

        RetrofitHelper.getInstance().getCollectionCancel( pId, new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                T.normal(e.getMessage());
            }

            @Override
            public void onNext(Object orderDetailModel) {
                isCollection = false;
                goods_info_collection.setImageResource(R.mipmap.icon_like_normal);
                T.normal("取消收藏");
            }
        });
    }


    /**
     * 倒计时类
     */
    public class MyCountDownTimer extends CountDownTimer {
        private TextView tv;

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public MyCountDownTimer(long millisInFuture, long countDownInterval, final TextView tv) {
            super(millisInFuture, countDownInterval);
            this.tv = tv;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long time = millisUntilFinished / 1000;

            if (time <= 59) {
                tv.setText(String.format("取消预约（00:%02d）", time));
            } else {
                tv.setText(String.format("取消预约（%02d:%02d）", time / 60, time % 60));
            }
        }

        @Override
        public void onFinish() {
            tv.setText("预约商品");
            tv_reserve_goods.setEnabled(true);
            cancelTimer();
        }
    }

    /**
     * 开始倒计时
     */
    private void startTimer(long time, TextView tv) {
        if (timer == null) {
            timer = new MyCountDownTimer(time, INTERVAL, tv);
        }
        timer.start();
    }

    /**
     * 取消倒计时
     */
    protected void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
