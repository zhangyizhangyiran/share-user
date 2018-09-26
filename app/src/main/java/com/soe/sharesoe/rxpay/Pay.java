package com.soe.sharesoe.rxpay;

import android.app.Activity;
import android.widget.Toast;

import com.soe.sharesoe.manager.ServiceDataManager;
import com.soe.sharesoe.module.mycenter.order.PayProductDeposit;
import com.soe.sharesoe.net.RetrofitHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.functions.Consumer;
import rx.Subscriber;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/16
 * @time 上午11:01
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class Pay {
    private RxPay rxPay;
    private Activity activity;

    public Pay(Activity activity) {
        this.activity = activity;
        if (rxPay != null) {
            rxPay = new RxPay(activity);

        }
    }

    public void requestPay(Map<String, String> map, final PayResultListenr listenr) {
        RetrofitHelper.getInstance().getPayProductDeposit(map, new Subscriber<PayProductDeposit>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (listenr != null) {
                    listenr.onResult(false, e.getMessage());
                }
            }

            @Override
            public void onNext(PayProductDeposit payProductDeposit) {
                if (payProductDeposit.getCode() == 1000) {
                    listenr.onResult(true, payProductDeposit.getMsg());
                    ServiceDataManager.getAccountMyWallet();
                } else {
                    listenr.onResult(false, payProductDeposit.getMsg());

                }
            }
        });

    }

    public void requestAlipay(String jsonAlipay, final PayResultListenr listenr) {
        rxPay.requestAlipay(jsonAlipay)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        listenr.onResult(aBoolean, "微信支付状态：" + aBoolean);
                        Toast.makeText(activity, "微信支付状态：" + aBoolean, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listenr.onResult(false, "阿里支付状态：" + throwable.getMessage());

                        Toast.makeText(activity, "阿里支付状态：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void requestWechatpay(String jsonWechatpay, final PayResultListenr listenr) throws JSONException {
        rxPay.requestWXpay(new JSONObject(jsonWechatpay))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        listenr.onResult(aBoolean, "微信支付状态：" + aBoolean);
                        Toast.makeText(activity, "微信支付状态：" + aBoolean, Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listenr.onResult(false, "微信支付状态：" + throwable.getMessage());
                        Toast.makeText(activity, "微信支付状态：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public interface PayResultListenr {
        void onResult(boolean status, String msg);
    }
}
