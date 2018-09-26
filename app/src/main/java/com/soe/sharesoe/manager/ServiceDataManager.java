package com.soe.sharesoe.manager;

import com.soe.sharesoe.module.mycenter.wallet.AccountMyWallet;
import com.soe.sharesoe.net.RetrofitHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/16
 * @time 下午3:06
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class ServiceDataManager {
    public static void getAccountMyWallet() {
        Map<String, String> map = new HashMap<String, String>();

        RetrofitHelper.getInstance().getAccountMyWallet(map, new Subscriber<AccountMyWallet>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AccountMyWallet accountMyWallets) {
                EventBus.getDefault().post(accountMyWallets);
                UserDataManager.setWalletAvailableAmount(String.valueOf(accountMyWallets.getAvailableAmount()));
            }
        });
    }
}
