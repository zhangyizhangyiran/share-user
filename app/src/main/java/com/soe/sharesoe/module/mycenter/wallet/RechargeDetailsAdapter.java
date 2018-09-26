package com.soe.sharesoe.module.mycenter.wallet;

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

public class RechargeDetailsAdapter extends BaseQuickAdapter<AccountRechargeFlowList.RecordsBean, BaseViewHolder> {

    public RechargeDetailsAdapter() {
        super(R.layout.item_recharge_details, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountRechargeFlowList.RecordsBean item) {
        switch (helper.getLayoutPosition() % 2) {
            case 0:
                helper.setImageResource(R.id.item_recharge_details_img, R.mipmap.icon_pay_alipay_circle);
                break;
            case 1:
                helper.setImageResource(R.id.item_recharge_details_img, R.mipmap.icon_pay_wechat_circle);
                break;
        }
        helper.setText(R.id.item_recharge_details_title, item.getChannelName());
        ((TextView) helper.getView(R.id.item_recharge_details_date)).setText(item.getRechargeTime());
        ((TextView) helper.getView(R.id.item_recharge_details_money)).setText("+"+item.getRechargeAmount());
    }
}
