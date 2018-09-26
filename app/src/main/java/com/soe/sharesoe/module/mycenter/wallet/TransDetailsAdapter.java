package com.soe.sharesoe.module.mycenter.wallet;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;
import com.soe.sharesoe.entity.Status;

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

public class TransDetailsAdapter extends BaseQuickAdapter<AccountTransactionDetails, BaseViewHolder> {

    public TransDetailsAdapter() {
        super(R.layout.item_trans_details, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountTransactionDetails item) {
        switch (helper.getLayoutPosition() % 2) {
            case 0:
                helper.setImageResource(R.id.item_trans_details_img, R.mipmap.icon_pay_alipay_circle);
                helper.setText(R.id.item_trans_details_title, "租金-小米9号平衡车青春…");
                helper.setText(R.id.item_trans_details_date, "2017-10-10 12:45:12");
                helper.setText(R.id.item_trans_details_money, "+186.00");
                helper.setTextColor(R.id.item_trans_details_money, Color.parseColor("#FA7567"));
                helper.setText(R.id.item_trans_details_info, "退回支付宝");

                break;
            case 1:
                helper.setImageResource(R.id.item_trans_details_img, R.mipmap.icon_pay_wechat_circle);
                helper.setText(R.id.item_trans_details_title, "租金-小米9号平衡车青春…");
                helper.setText(R.id.item_trans_details_date, "2017-10-10 12:45:12");
                helper.setText(R.id.item_trans_details_money, "-1886.00");
                helper.setTextColor(R.id.item_trans_details_money, Color.parseColor("#2A2A2A"));
                helper.setText(R.id.item_trans_details_info, "支付宝支付");

                break;
        }
    }
}
