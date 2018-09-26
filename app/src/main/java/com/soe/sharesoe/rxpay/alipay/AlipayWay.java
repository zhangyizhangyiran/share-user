package com.soe.sharesoe.rxpay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.soe.sharesoe.rxpay.PaymentStatus;

import java.util.Map;

import io.reactivex.subjects.PublishSubject;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/25
 * @time 下午4:25
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class AlipayWay {
    private static final int SDK_PAY_FLAG = 1;
    static PublishSubject<PaymentStatus> mSubjects = PublishSubject.create();
    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();
            String resultStatus = payResult.getResultStatus();
            if (TextUtils.equals(resultStatus, "9000")) {
                mSubjects.onNext(new PaymentStatus(true));
            } else {
                mSubjects.onNext(new PaymentStatus(false));
            }
        }
    };


    public static void payMoney(final Activity activity, final String orderInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }).start();

    }

    public static PublishSubject<PaymentStatus> getSubjects() {
        return mSubjects;
    }
}
