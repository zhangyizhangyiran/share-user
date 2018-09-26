package com.soe.sharesoe.module.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/27
 * @time 上午10:57
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class HomeGoodsAdapter extends BaseQuickAdapter<ProductList.ListBean, BaseViewHolder> {
    Context context;

    public HomeGoodsAdapter(Context context) {
        super(R.layout.item_home_goods_list, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductList.ListBean item) {
        if (0 == item.getSource()) {
            helper.setText(R.id.item_tv_homegoods_type, "自营");
        } else if (1 == item.getSource()) {
            helper.setText(R.id.item_tv_homegoods_type, "个人");
        } else if (2 == item.getSource()) {
            helper.setText(R.id.item_tv_homegoods_type, "合作商");
        } else {
            helper.setText(R.id.item_tv_homegoods_type, "");
        }

        if (1 == item.getStatus()) {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.shape_home_reserve_bg);
            ((TextView) helper.getView(R.id.item_tv_homegoods_reservego)).setBackground(drawable);
            ((TextView) helper.getView(R.id.item_tv_homegoods_reservego)).setTextColor(mContext.getResources().getColor(R.color.Color_Theme_White));

            helper.setText(R.id.item_tv_homegoods_reservego, "去预约");
            helper.getView(R.id.item_tv_homegoods_reserve).setVisibility(View.GONE);
        } else if (2 == item.getStatus()) {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.shape_home_reserve_bg);
            ((TextView) helper.getView(R.id.item_tv_homegoods_reservego)).setBackground(drawable);
            ((TextView) helper.getView(R.id.item_tv_homegoods_reservego)).setTextColor(mContext.getResources().getColor(R.color.Color_Theme_White));

            helper.setText(R.id.item_tv_homegoods_reservego, "去预约");
            helper.getView(R.id.item_tv_homegoods_reserve).setVisibility(View.GONE);

        } else if (3 == item.getStatus()) {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.shape_home_reserve_fram);
            ((TextView) helper.getView(R.id.item_tv_homegoods_reservego)).setBackground(drawable);
            ((TextView) helper.getView(R.id.item_tv_homegoods_reservego)).setTextColor(mContext.getResources().getColor(R.color.Color_Theme_Main_Red));
            helper.setText(R.id.item_tv_homegoods_reservego, "上线通知我");
            helper.setText(R.id.item_tv_homegoods_reserve, "已预约");
            helper.getView(R.id.item_tv_homegoods_reserve).setVisibility(View.VISIBLE);
        } else {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.shape_home_reserve_bg);
            ((TextView) helper.getView(R.id.item_tv_homegoods_reservego)).setBackground(drawable);
            ((TextView) helper.getView(R.id.item_tv_homegoods_reservego)).setTextColor(mContext.getResources().getColor(R.color.Color_Theme_White));

            helper.setText(R.id.item_tv_homegoods_reservego, "去预约");
            helper.getView(R.id.item_tv_homegoods_reserve).setVisibility(View.GONE);

        }


        Glide.with(mContext).load(item.getProductPic()).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into((ImageView) helper.getView(R.id.item_img_homegoods_img));
        helper.setText(R.id.item_tv_homegoods_name, item.getProductName());
        helper.setText(R.id.item_tv_homegoods_details, item.getIntroduction());
        helper.setText(R.id.item_tv_homegoods_price, item.getRental());
        helper.setText(R.id.item_tv_homegoods_deposit, "押金：" + item.getDeposit());
        helper.setText(R.id.item_tv_homegoods_reserveover, item.getProductName());

        ((TextView) helper.getView(R.id.item_tv_homegoods_reservego)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
