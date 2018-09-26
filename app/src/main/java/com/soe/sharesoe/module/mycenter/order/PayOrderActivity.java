package com.soe.sharesoe.module.mycenter.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.Constant;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.OrderCreateModel;
import com.soe.sharesoe.module.qrcode.LockManager;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.rxpay.Pay;
import com.soe.sharesoe.utils.T;
import com.soe.sharesoe.widget.dialog.PayDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 订单支付页面
 * @encoding UTF-8
 * @date 2017/11/13
 * @time 上午11:48
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class PayOrderActivity extends RxBaseActivity {


    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.pay_order_img)
    ImageView payOrderImg;
    @BindView(R.id.pay_order_title)
    TextView payOrderTitle;
    @BindView(R.id.pay_order_child_title)
    TextView payOrderChildTitle;
    @BindView(R.id.pay_order_rental)
    TextView payOrderRental;
    @BindView(R.id.pay_order_deposit)
    TextView payOrderDeposit;
    @BindView(R.id.pay_order_num)
    TextView payOrderNum;
    @BindView(R.id.pay_order_money)
    TextView payOrderMoney;
    @BindView(R.id.pay_order_paybtn)
    TextView payOrderPaybtn;

    private String productId = "1";
    private double startLongitude = 33, startLatitude = 33;

    String orderSn;
    String payAmount;
    int status;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        barTitle.setText("订单确认");

        Intent i = getIntent();
        productId = i.getStringExtra("pid");

        setOrderCreateData();
    }

    @OnClick({R.id.bar_left_img, R.id.pay_order_paybtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:

                break;
            case R.id.pay_order_paybtn:
                Map<String, String> map = new HashMap<String, String>();
                map.put("payCategory", "1");
                map.put("orderSn", orderSn);
                map.put("payAmount", payAmount);

                PayDialog.payShow(PayOrderActivity.this, new Pay.PayResultListenr() {
                    @Override
                    public void onResult(boolean status, String msg) {
                        T.normal(msg + ", 祝您租用愉快！");
                        new LockManager(PayOrderActivity.this).openLockData(orderSn, Constant.PAGE_PAY_ORDER);//通知开锁成功给服务器
                    }
                }, map);
                break;
        }

    }


    /**
     * 创建订单
     */
    private void setOrderCreateData() {
        RetrofitHelper.getInstance().getProductOrderCreate(productId, startLongitude, startLatitude, new Subscriber<OrderCreateModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                PayOrderActivity.this.finish();
            }

            @Override
            public void onNext(OrderCreateModel orderCreateModel) {

                if (orderCreateModel.getCode() == 1000) {
                    orderSn = orderCreateModel.getParams().getSn();
                    payAmount = orderCreateModel.getResult().getDeposit();
                    status = orderCreateModel.getResult().getProductStatus();
                    OrderCreateModel.ResultBean resultBean = orderCreateModel.getResult();

                    Glide.with(PayOrderActivity.this)
                            .load(resultBean.getProductPic())
                            .placeholder(R.mipmap.ic_default_logo)
                            .error(R.mipmap.ic_default_logo)
                            .dontAnimate()
                            .into(payOrderImg);
                    payOrderTitle.setText(resultBean.getProductName());
                    payOrderChildTitle.setText(resultBean.getIntroduction());
                    payOrderDeposit.setText("押金：￥" + resultBean.getDeposit());
                    payOrderRental.setText("租金：￥" + resultBean.getRental());
                    payOrderMoney.setText("实付：￥" + resultBean.getDeposit());
                } else {
                    Toast.makeText(PayOrderActivity.this, orderCreateModel.getMsg(), Toast.LENGTH_SHORT).show();
                    PayOrderActivity.this.finish();
                }
            }
        });
    }


}
