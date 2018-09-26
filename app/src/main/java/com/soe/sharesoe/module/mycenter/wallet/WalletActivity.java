package com.soe.sharesoe.module.mycenter.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.manager.ServiceDataManager;
import com.soe.sharesoe.utils.Q;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/27
 * @time 上午11:37
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class WalletActivity extends RxBaseActivity {

    @BindView(R.id.bar_left_img)
    ImageView bar_left_img;
    @BindView(R.id.bar_title)
    TextView bar_title;
    @BindView(R.id.bar_right)
    TextView bar_right;

    @BindView(R.id.act_wallet_tv_balance)
    TextView act_wallet_tv_balance;
    @BindView(R.id.act_wallet_tv_recharge)
    TextView act_wallet_tv_recharge;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        bar_right.setVisibility(View.VISIBLE);
        EventBus.getDefault().register(this);

        ServiceDataManager.getAccountMyWallet();
    }


    @Subscribe
    public void onEvent(AccountMyWallet accountMyWallet) {
        if (!Q.isEmpty(accountMyWallet.getAvailableAmount())) {
            act_wallet_tv_balance.setText(String.valueOf(accountMyWallet.getAvailableAmount()));
        }
    }


    @OnClick({R.id.bar_left_img, R.id.bar_right, R.id.act_wallet_tv_recharge})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
            case R.id.bar_right:
                startActivity(new Intent(this, DetailsActivity.class));
                break;
            case R.id.act_wallet_tv_recharge:
                startActivity(new Intent(this, RechargeActivity.class));

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
