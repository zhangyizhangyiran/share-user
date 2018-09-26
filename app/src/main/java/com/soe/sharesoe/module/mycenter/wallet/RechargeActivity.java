package com.soe.sharesoe.module.mycenter.wallet;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.coolqi.lib.ble.L;
import com.orhanobut.logger.Logger;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;

import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.rxpay.Pay;
import com.soe.sharesoe.rxpay.RxPay;
import com.soe.sharesoe.utils.T;
import com.soe.sharesoe.widget.CustomEmptyView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import rx.Subscriber;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/27
 * @time 下午3:24
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class RechargeActivity extends RxBaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.bar_left_img)
    ImageView bar_left_img;
    @BindView(R.id.bar_title)
    TextView bar_title;

    @BindView(R.id.act_recharge_money_recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.act_recharge_money_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.act_recharge_money_emptylayout)
    CustomEmptyView mCustomEmptyView;

    View mBottomView;
    RadioGroup bike_recharge_rdgroup;
    TextView act_tv_recharge_go;
    TextView act_tv_recharge_protocol;


    private RechargeMoneyAdapter mAdapter;
    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 10;


//    private String jsonAlipay = "{ \"appid\": \"ssss\", \"noncestr\": \"nnnn\"}";//服务器产生的订单号
//    private String jsonWechatpay = "{ \"appid\": \"ssss\", \"noncestr\": \"nnnn\",\"partnerid\":\"ppp\",\"prepayid\":\"sss\",\"timestamp\":\"tttt\",\"sign\":\"sss\"}";//服务器生成订单后的json

    String channel;
    String rechargeAmount;

    @Override
    public int getLayoutId() {
        mBottomView = getLayoutInflater().inflate(R.layout.include_recharge_bottom_view, null, false);
        bike_recharge_rdgroup = (RadioGroup) mBottomView.findViewById(R.id.bike_recharge_rdgroup);
        act_tv_recharge_go = (TextView) mBottomView.findViewById(R.id.act_tv_recharge_go);
        act_tv_recharge_protocol = (TextView) mBottomView.findViewById(R.id.act_tv_recharge_protocol);

        return R.layout.activity_recharge;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        bar_title.setText("充值");

        bike_recharge_rdgroup.setOnCheckedChangeListener(this);
        act_tv_recharge_go.setOnClickListener(this);
        act_tv_recharge_protocol.setOnClickListener(this);

        initAdapter();
        initRefreshLayout();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_tv_recharge_go:
                int checkedRadioButtonId = bike_recharge_rdgroup.getCheckedRadioButtonId();
                if (R.id.act_rb_recharge_radiobtn1 == checkedRadioButtonId) {
                    channel = "2";
                } else if (R.id.act_rb_recharge_radiobtn2 == checkedRadioButtonId) {
                    channel = "1";

                }

                List<RechargeAmountConfig> data = mAdapter.getData();
                for (RechargeAmountConfig rechargeAmountConfig : data) {
                    if (rechargeAmountConfig.isSelected()) {
                        rechargeAmount = String.valueOf(rechargeAmountConfig.getMoney());
                    }
                }

                getaccountRecharge();
                break;
            case R.id.act_tv_recharge_protocol:
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.bar_left_img})
    public void onClicks(View v) {
        switch (v.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.act_rb_recharge_radiobtn1:
                T.normal("微信");
                break;
            case R.id.act_rb_recharge_radiobtn2:
                T.normal("支付宝");
                break;
            default:
                break;
        }
    }

    //充值
    private void getaccountRecharge() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("rechargeAmount", rechargeAmount);
        map.put("channel", channel);

        RetrofitHelper.getInstance().getaccountRecharge(map, new Subscriber<AccountRecharge>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mSwipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onNext(AccountRecharge accountRecharge) {
                mSwipeRefreshLayout.setRefreshing(false);
                new Pay(RechargeActivity.this).requestAlipay(accountRecharge.getChannelParamsStr(), new Pay.PayResultListenr() {
                    @Override
                    public void onResult(boolean status, String msg) {
                        T.normal(status + msg);
                    }
                });
            }
        });
    }

    //获取充值金额
    private void getRechargeAmountConfig() {
        Map<String, String> map = new HashMap<String, String>();

        RetrofitHelper.getInstance().getRechargeAmountConfig(map, new Subscriber<List<Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mSwipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onNext(List<Integer> accountMyWallets) {
                mSwipeRefreshLayout.setRefreshing(false);

                List<RechargeAmountConfig> rechargeAmountConfigs = new ArrayList<RechargeAmountConfig>();
                for (int i = 0; i < accountMyWallets.size(); i++) {

                    RechargeAmountConfig rechargeAmountConfig = new RechargeAmountConfig();
                    rechargeAmountConfig.setMoney(accountMyWallets.get(i));
                    if (i == 0) {
                        rechargeAmountConfig.setSelected(true);
                    } else {
                        rechargeAmountConfig.setSelected(false);

                    }
                    rechargeAmountConfigs.add(rechargeAmountConfig);
                }
                Logger.d(rechargeAmountConfigs.size());
                setData(true, rechargeAmountConfigs);
            }
        });
    }


    private void initAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new RechargeMoneyAdapter();
//        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                loadMore();
//            }
//        });
//        mAdapter.openLoadAnimation(BaseQuickAdapter.);
//        mAdapter.setPreLoadNumber(3);


        mAdapter.addFooterView(mBottomView);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(RechargeActivity.this, String.valueOf(((RechargeAmountConfig) adapter.getData().get(position)).getMoney()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected void initRefreshLayout() {

        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.Color_Theme_Main_Green);
//        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                loadMore();
            }
        });

    }

    private void loadMore() {
        getRechargeAmountConfig();
    }

    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        getRechargeAmountConfig();
    }


    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
//            Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }


    private void initEmptyView() {

        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.icon_classift);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
//        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    /**
     * 隐藏empty视图
     */
    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


}
