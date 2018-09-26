package com.soe.sharesoe.module.mycenter.wallet;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.soe.sharesoe.R;
import com.soe.sharesoe.utils.T;

import java.util.List;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/27
 * @time 下午3:17
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class RechargeMoneyAdapter extends BaseQuickAdapter<RechargeAmountConfig, BaseViewHolder> {

    public RechargeMoneyAdapter() {
        super(R.layout.item_wallet_recharge_money, null);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (RechargeAmountConfig data : getData()) {
                    data.setSelected(false);
                }
                getData().get(position).setSelected(true);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeAmountConfig item) {

        if (item.isSelected()) {
            helper.setText(R.id.item_btn_recharge_money, String.valueOf(item.getMoney()) + "元");
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.shape_wallet_recharge_bg);
            ((TextView) helper.getView(R.id.item_btn_recharge_money)).setBackground(drawable);
            ((TextView) helper.getView(R.id.item_btn_recharge_money)).setTextColor(mContext.getResources().getColor(R.color.Color_Theme_White));

        } else {
            helper.setText(R.id.item_btn_recharge_money, String.valueOf(item.getMoney()) + "元");
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.shape_wallet_recharge_fram);
            ((TextView) helper.getView(R.id.item_btn_recharge_money)).setBackground(drawable);
            ((TextView) helper.getView(R.id.item_btn_recharge_money)).setTextColor(mContext.getResources().getColor(R.color.Color_Theme_Main_Green));
        }

    }
}
