package com.coolqi.lib.ble.callback;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;

import com.coolqi.lib.ble.Config;
import com.coolqi.lib.ble.L;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangcai on 17-6-28.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public abstract class PeriodScanerCallback extends ScanCallback {

    private String macAddress;

    private boolean removeTimeOutHandler = false;

    private ReentrantLock mLock = new ReentrantLock(true);


    public PeriodScanerCallback(int defaultTime, String macAddress) {
        this.macAddress = macAddress;
        Config.runOnUIThreadDelayed(mRunnable, defaultTime);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mLock.lock();
                if (!removeTimeOutHandler) {
                    onScanResultOut(macAddress.toUpperCase(), PeriodScanCallback.SERACH_FAILED);
                }
            } finally {
                mLock.unlock();
            }
        }
    };

    @Override
    public void onScanFailed(int errorCode) {
        super.onScanFailed(errorCode);
        L.d("onScanFailed SERACH_FAILED");
        callbackOut(PeriodScanCallback.SERACH_FAILED);
    }

    private void callbackOut(int status) {
        try {
            mLock.lock();
            if (!removeTimeOutHandler) {
                removeTimeOutHandler = true;
                Config.removeRunnable(mRunnable);
                onScanResultOut(macAddress.toUpperCase(), status);
            }
        } finally {
            mLock.unlock();
        }
    }


    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);
        if (result.getDevice().getAddress().equalsIgnoreCase(macAddress)) {
            L.d("onScanResult SERACH_OK");
            callbackOut(PeriodScanCallback.SERACH_OK);
        }
    }

    public abstract void onScanResultOut(String macAddress, int resultCode);
}
