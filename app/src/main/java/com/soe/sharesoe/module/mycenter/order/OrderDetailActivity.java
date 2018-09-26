package com.soe.sharesoe.module.mycenter.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.OrderDetailModel;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.rxpay.Pay;
import com.soe.sharesoe.utils.D;
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
 * @Description 订单详情
 * @encoding UTF-8
 * @date 2017/11/7
 * @time 上午11:39
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class OrderDetailActivity extends RxBaseActivity {


    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right_img)
    ImageView barRightImg;
    @BindView(R.id.bottom_goods_deposit_rl)
    RelativeLayout bottomGoodsDepositRl;
    @BindView(R.id.bottom_goods_order_lly)
    LinearLayout bottomGoodsOrderLly;
    @BindView(R.id.bottom_goods_deposit_real)
    TextView bottomGoodsDepositReal;
    @BindView(R.id.bottom_goods_rental_real)
    TextView bottomGoodsRentalReal;
    @BindView(R.id.bottom_goods_order_btn1)
    Button bottomGoodsOrderBtn1;
    @BindView(R.id.bottom_goods_order_btn2)
    Button bottomGoodsOrderBtn2;
    @BindView(R.id.bottom_goods_order_btn3)
    Button bottomGoodsOrderBtn3;
    //顶端展示控件
    @BindView(R.id.top_goods_order_img)
    ImageView topGoodsOrderImg;
    @BindView(R.id.top_goods_order_title)
    TextView topGoodsOrderTitle;
    @BindView(R.id.top_goods_order_content)
    TextView topGoodsOrderContent;

    @BindView(R.id.top_goods_type)
    TextView topGoodsType;
    @BindView(R.id.top_goods_name)
    TextView topGoodsName;
    @BindView(R.id.top_goods_status)
    TextView topGoodsStatus;
    @BindView(R.id.center_goods_img)
    ImageView centerGoodsImg;
    @BindView(R.id.center_goods_title)
    TextView centerGoodsTitle;
    @BindView(R.id.center_goods_num)
    TextView centerGoodsNum;
    @BindView(R.id.center_goods_use_days)
    TextView centerGoodsUseDays;
    @BindView(R.id.center_goods_rental)
    TextView centerGoodsRental;
    @BindView(R.id.center_goods_deposit)
    TextView centerGoodsDeposit;

    @BindView(R.id.order_detail_text)
    TextView orderDetailText;

    private String money, orderSn;
    private int status;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("订单详情");

        Intent i = getIntent();
        orderSn = i.getStringExtra("sn");
        setOrderDetailData(orderSn);
    }

    @OnClick({R.id.bottom_goods_order_btn2, R.id.bottom_goods_order_btn3, R.id.bar_left_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_goods_order_btn2:
                startActivity(new Intent(OrderDetailActivity.this, AppealOrderActivity.class));
                break;
            case R.id.bottom_goods_order_btn3:
                payProductOrder();
                break;
            case R.id.bar_left_img:
                finish();
                break;
        }
    }

    /**
     * 订单详情
     *
     * @param sn
     */
    private void setOrderDetailData(String sn) {

        RetrofitHelper.getInstance().getOrderDetail(sn, new Subscriber<OrderDetailModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderDetailModel orderDetail) {

                orderSn = orderDetail.getOrderSn();
                status = orderDetail.getStatus();

                Glide.with(OrderDetailActivity.this)
                        .load(orderDetail.getProductPic())
                        .placeholder(R.mipmap.ic_default_logo)
                        .error(R.mipmap.ic_default_logo)
                        .dontAnimate()
                        .into(centerGoodsImg);
                topGoodsName.setText(orderDetail.getBoxCode() == null ? "箱子001A" : orderDetail.getBoxCode());
                centerGoodsTitle.setText(orderDetail.getProductName());
                centerGoodsNum.setText(orderDetail.getUseTime());
                centerGoodsRental.setText("租金：￥" + orderDetail.getRent());
                centerGoodsDeposit.setText("押金：￥" + orderDetail.getDeposit());

                String startUseTime = orderDetail.getStartUseTime() == 0 ? "" : D.getStringByFormat(orderDetail.getStartUseTime(), "yyyy-MM-dd HH:mm:ss");
                String depositPayTime = orderDetail.getDepositPayTime() == 0 ? "" : D.getStringByFormat(orderDetail.getDepositPayTime(), "yyyy-MM-dd HH:mm:ss");
                String rentPayTime = orderDetail.getRentPayTime() == 0 ? "" : D.getStringByFormat(orderDetail.getRentPayTime(), "yyyy-MM-dd HH:mm:ss");
                String endTime = orderDetail.getEndTime() == 0 ? "" : D.getStringByFormat(orderDetail.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                String depositWithdrawTime = orderDetail.getDepositWithdrawTime() == 0 ? "" : D.getStringByFormat(orderDetail.getDepositWithdrawTime(), "yyyy-MM-dd HH:mm:ss");
                String endUseTime = orderDetail.getEndUseTime() == 0 ? "" : D.getStringByFormat(orderDetail.getEndUseTime(), "yyyy-MM-dd HH:mm:ss");
                String cancelRentTime = orderDetail.getCancelRentTime() == 0 ? "" : D.getStringByFormat(orderDetail.getCancelRentTime(), "yyyy-MM-dd HH:mm:ss");


                String detailText = " 订单编号：" + orderDetail.getOrderSn() + "\n 押金交易单号：" + orderDetail.getDepositTradeNo() + "\n 租金交易单号：" + orderDetail.getRentTradeNo()

                        + "\n\n 开始使用时间：" + startUseTime
                        + "\n 押金付款时间：" + depositPayTime
                        + "\n 租金付款时间：" + rentPayTime
                        + "\n 押金退款时间：" + depositWithdrawTime
                        + "\n 租金退款时间：" + cancelRentTime
                        + "\n 结束使用时间：" + endUseTime
                        + "\n 交易完成时间：" + endTime;

                orderDetailText.setText(detailText);
                //来源
                switch (orderDetail.getSource()) {
                    case 0:
                        topGoodsType.setText("自营");

                        break;
                    case 1:
                        topGoodsType.setText("个人");
                        break;
                    case 2:
                        topGoodsType.setText("合作商户");
                        break;
                }
                //订单状态
                setStatus(orderDetail);
            }
        });
    }

    private void setStatus(OrderDetailModel orderDetail) {

        switch (orderDetail.getStatus()) {
            case 100:
                topGoodsStatus.setText("待付押金");
                //顶部展示
                topGoodsOrderImg.setImageResource(R.mipmap.icon_order_wait);
                topGoodsOrderTitle.setText("等待用户付押金");
                topGoodsOrderContent.setVisibility(View.VISIBLE);
                topGoodsOrderContent.setText("剩余xx小时自动关闭");

                bottomGoodsDepositRl.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setTextColor(Color.parseColor("#2A2A2A"));
                bottomGoodsDepositReal.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setText("押金：￥" + orderDetail.getDeposit());

                bottomGoodsOrderLly.setVisibility(View.VISIBLE);
                bottomGoodsOrderBtn3.setVisibility(View.VISIBLE);
                bottomGoodsOrderBtn1.setVisibility(View.VISIBLE);

                money = orderDetail.getDeposit() + "";
                break;
            case 101:
                topGoodsStatus.setText("已付押金");
                //顶部展示
                topGoodsOrderImg.setImageResource(R.mipmap.icon_order_wait);
                topGoodsOrderTitle.setText("已付押金");

                bottomGoodsDepositRl.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setText("押金：￥" + orderDetail.getDeposit() + "（已付）");
                break;
            case 200:
                topGoodsStatus.setText("使用中");
                //顶部展示
                topGoodsOrderImg.setImageResource(R.mipmap.icon_order_wait);
                topGoodsOrderTitle.setText("使用中");

                centerGoodsUseDays.setVisibility(View.VISIBLE);
                centerGoodsUseDays.setText(orderDetail.getUseTime());

                bottomGoodsDepositRl.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setText("押金：￥" + orderDetail.getDeposit() + "（已付）");

                break;
            case 300:
                topGoodsStatus.setText("待付租金");
                //顶部展示
                topGoodsOrderImg.setImageResource(R.mipmap.icon_order_wait);
                topGoodsOrderTitle.setText("待付租金");

                bottomGoodsDepositRl.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setText("押金：￥" + orderDetail.getDeposit() + "（已付）");
                bottomGoodsRentalReal.setVisibility(View.VISIBLE);
                bottomGoodsRentalReal.setTextColor(Color.parseColor("#2A2A2A"));
                bottomGoodsRentalReal.setText("租金：￥" + orderDetail.getAmount());

                bottomGoodsOrderLly.setVisibility(View.VISIBLE);
                bottomGoodsOrderBtn3.setVisibility(View.VISIBLE);

                money = orderDetail.getAmount() + "";
                break;
            case 400: //已付租金
                topGoodsStatus.setText("退押金中");

                //顶部展示
                topGoodsOrderImg.setImageResource(R.mipmap.icon_order_wait);
                topGoodsOrderTitle.setText("退押金中");

                bottomGoodsDepositRl.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setText("押金：￥" + orderDetail.getDeposit());
                bottomGoodsRentalReal.setVisibility(View.VISIBLE);
                bottomGoodsRentalReal.setText("租金：￥" + orderDetail.getAmount() + "（已付）");
                break;
            case 500:
                topGoodsStatus.setText("交易完成");

                //顶部展示
                topGoodsOrderImg.setImageResource(R.mipmap.icon_order_success);
                topGoodsOrderTitle.setText("退押金中");

                centerGoodsUseDays.setVisibility(View.VISIBLE);
                centerGoodsUseDays.setText(orderDetail.getUseTime());

                bottomGoodsDepositRl.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setVisibility(View.VISIBLE);
                bottomGoodsDepositReal.setText("押金：￥" + orderDetail.getDeposit() + "（已退）");
                bottomGoodsRentalReal.setVisibility(View.VISIBLE);
                bottomGoodsRentalReal.setText("租金：￥" + orderDetail.getAmount() + "（已付）");

                break;
            case 600:
                topGoodsStatus.setText("交易失败");
                //顶部展示
                topGoodsOrderImg.setImageResource(R.mipmap.icon_order_wait);
                topGoodsOrderTitle.setText("交易失败");

                break;
            case 700:
                topGoodsStatus.setText("申诉中");
                //顶部展示
                topGoodsOrderImg.setImageResource(R.mipmap.icon_order_wait);
                topGoodsOrderTitle.setText("申诉中");
            case 800: //取消"
                topGoodsStatus.setText("交易关闭");
                //顶部展示
                topGoodsOrderImg.setImageResource(R.mipmap.icon_order_wait);
                topGoodsOrderTitle.setText("交易关闭");
            default:
                break;
        }

    }

    /**
     * 支付押金、租金
     */
    private void payProductOrder() {

        Map<String, String> map = new HashMap<String, String>();

        if (status == 100) {

            map.put("payCategory", "1");
        } else if (status == 300) {

            map.put("payCategory", "2");
        }
        map.put("payAmount", money);
        map.put("orderSn", orderSn);

        PayDialog.payShow(this, new Pay.PayResultListenr() {
            @Override
            public void onResult(boolean status, String msg) {
                Logger.d(status + "===" + msg);
                T.normal(msg + "");
            }
        }, map);
    }

}
