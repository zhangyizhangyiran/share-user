package com.soe.sharesoe.module.mycenter.order;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;
import com.soe.sharesoe.entity.ProductOrderListModel;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 订单列表
 * @encoding UTF-8
 * @date 2017/11/15
 * @time 下午6:07
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class OrderListAdapter extends BaseQuickAdapter<ProductOrderListModel.RecordsBean, BaseViewHolder> {

    private Activity mActivity;

    public OrderListAdapter(Activity mActivity) {
        super(R.layout.layout_order, null);
        this.mActivity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductOrderListModel.RecordsBean item) {

        helper.getView(R.id.view_line).setVisibility(View.GONE);
        //订单状态
        switch (item.getStatus()) {
            case 100:
                helper.setText(R.id.top_goods_status, "待付押金");

                helper.getView(R.id.bottom_goods_deposit_rl).setVisibility(View.VISIBLE);
                ((TextView) helper.getView(R.id.bottom_goods_deposit_real)).setTextColor(Color.parseColor("#2A2A2A"));
                helper.getView(R.id.bottom_goods_deposit_real).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_deposit_real, "押金：￥" + item.getDeposit());

                helper.getView(R.id.bottom_goods_order_lly).setVisibility(View.VISIBLE);
                helper.getView(R.id.bottom_goods_order_btn3).setVisibility(View.VISIBLE);
                helper.getView(R.id.bottom_goods_order_btn1).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_order_btn1, "取消订单");

                helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
                break;
            case 101:
                helper.setText(R.id.top_goods_status, "已付押金");

                helper.getView(R.id.bottom_goods_deposit_rl).setVisibility(View.VISIBLE);
                helper.getView(R.id.bottom_goods_deposit_real).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_deposit_real, "押金：￥" + item.getDeposit() + "（已付）");

                helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
                break;
            case 200:
                helper.setText(R.id.top_goods_status, "使用中");

                helper.getView(R.id.center_goods_use_days).setVisibility(View.VISIBLE);
                helper.setText(R.id.center_goods_use_days, item.getUseTime());

                helper.getView(R.id.bottom_goods_deposit_rl).setVisibility(View.VISIBLE);
                helper.getView(R.id.bottom_goods_deposit_real).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_deposit_real, "押金：￥" + item.getDeposit() + "（已付）");

                helper.getView(R.id.bottom_goods_order_lly).setVisibility(View.VISIBLE);
                helper.getView(R.id.bottom_goods_order_btn2).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_order_btn2, "归还商品");

                helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
                break;
            case 300:
                helper.setText(R.id.top_goods_status, "待付租金");

                helper.getView(R.id.bottom_goods_deposit_rl).setVisibility(View.VISIBLE);
                helper.getView(R.id.bottom_goods_deposit_real).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_deposit_real, "押金：￥" + item.getDeposit() + "（已付）");
                helper.getView(R.id.bottom_goods_rental_real).setVisibility(View.VISIBLE);
                ((TextView) helper.getView(R.id.bottom_goods_rental_real)).setTextColor(Color.parseColor("#2A2A2A"));
                helper.setText(R.id.bottom_goods_rental_real, "租金：￥" + item.getAmount());

                helper.getView(R.id.bottom_goods_order_lly).setVisibility(View.VISIBLE);
                helper.getView(R.id.bottom_goods_order_btn3).setVisibility(View.VISIBLE);

                helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
                break;
            case 400: //已付租金
                helper.setText(R.id.top_goods_status, "退押金中");

                helper.getView(R.id.bottom_goods_deposit_rl).setVisibility(View.VISIBLE);
                helper.getView(R.id.bottom_goods_deposit_real).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_deposit_real, "押金：￥" + item.getDeposit());
                helper.getView(R.id.bottom_goods_rental_real).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_rental_real, "租金：￥" + item.getAmount() + "（已付）");

                helper.getView(R.id.bottom_goods_order_lly).setVisibility(View.GONE);
                break;
            case 500:
                helper.setText(R.id.top_goods_status, "交易完成");

                helper.getView(R.id.center_goods_use_days).setVisibility(View.VISIBLE);
                helper.setText(R.id.center_goods_use_days,  item.getUseTime());

                helper.getView(R.id.bottom_goods_deposit_rl).setVisibility(View.VISIBLE);
                helper.getView(R.id.bottom_goods_deposit_real).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_deposit_real, "押金：￥" + item.getDeposit() + "（已退）");
                helper.getView(R.id.bottom_goods_rental_real).setVisibility(View.VISIBLE);
                helper.setText(R.id.bottom_goods_rental_real, "租金：￥" + item.getAmount() + "（已付）");

                helper.getView(R.id.bottom_goods_order_lly).setVisibility(View.GONE);
                break;
            case 600:
                helper.setText(R.id.top_goods_status, "交易失败");
                break;
            case 700:
                helper.setText(R.id.top_goods_status, "申诉中");
            case 800: //取消"
                helper.setText(R.id.top_goods_status, "交易关闭");
//            case xxx:
//                helper.setText(R.id.top_goods_status, "退押金中");
//
//                helper.getView(R.id.bottom_goods_deposit_rl).setVisibility(View.VISIBLE);
//                helper.getView(R.id.bottom_goods_deposit_real).setVisibility(View.VISIBLE);
//                helper.setText(R.id.bottom_goods_deposit_real, "押金：￥" + item.getDeposit());
//                helper.getView(R.id.bottom_goods_rental_real).setVisibility(View.VISIBLE);
//                helper.setText(R.id.bottom_goods_rental_real, "租金：￥" + item.getRentAmount() + "（已付）");
//                break;
            default:
                break;
        }

        //公共显示内容
        //来源
        switch (item.getSource()) {
            case 0:

                helper.setText(R.id.top_goods_type, "自营");
                break;
            case 1:
                helper.setText(R.id.top_goods_type, "个人");
                break;
            case 2:
                helper.setText(R.id.top_goods_type, "合作商户");
                break;
        }

        Glide.with(mActivity)
                .load(item.getProductImg())
                .placeholder(R.mipmap.ic_default_logo)
                .error(R.mipmap.ic_default_logo)
                .dontAnimate()
                .into((ImageView) helper.getView(R.id.center_goods_img));
        helper.setText(R.id.center_goods_title, item.getProductName());
        helper.setText(R.id.center_goods_num, "× 1");
        helper.setText(R.id.center_goods_rental, "租金：￥" + item.getRent());
        helper.setText(R.id.center_goods_deposit, "押金：￥" + item.getDeposit());


        helper.addOnClickListener(R.id.bottom_goods_order_btn3);
        helper.addOnClickListener(R.id.bottom_goods_order_btn2);
        helper.addOnClickListener(R.id.bottom_goods_order_btn1);

    }

}
