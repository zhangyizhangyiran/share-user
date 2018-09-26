package com.soe.sharesoe.module.qrcode;

import android.app.Activity;

import com.soe.sharesoe.base.Constant;
import com.soe.sharesoe.net.RetrofitHelper;

import rx.Subscriber;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description
 * @encoding UTF-8
 * @date 2017/11/17
 * @time 下午4:18
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class LockManager {

    private Activity mActivity;

    public LockManager(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * @param orderSn 订单编号
     * @param page    页面
     */
    public boolean openLockData(String orderSn, final String page) {
        final boolean[] isFlag = {false};
        RetrofitHelper.getInstance().getOrderLockOpen(orderSn, new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                isFlag[0] = false;
            }

            @Override
            public void onNext(Object orderDetailModel) {
              /*  if (orderDetailModel.getCode() == 1000) {
                    Toast.makeText(PayOrderActivity.this, "open success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PayOrderActivity.this, orderDetailModel.getMsg(), Toast.LENGTH_SHORT).show();

                }*/
                if (page == Constant.PAGE_PAY_ORDER) {
                    mActivity.finish();
                }
                isFlag[0] = true;
            }
        });
        return isFlag[0];
    }
}
