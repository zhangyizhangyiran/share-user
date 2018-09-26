package com.coolqi.lib.ble.callback;


import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;

import com.coolqi.lib.ble.Config;
import com.coolqi.lib.ble.L;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangcai on 17-6-19.
 */

public abstract class PeriodScanCallback implements LeScanCallback {


    public static final int SERACH_OK = 0;

    public static final int SERACH_FAILED = 1;

    public static final int SERACH_SERVICE = 2;

    /**
     * the mac address that will scan bluetooth
     */
    private String macAddress;


    private boolean removeTimeOutHandler = false;

    private ReentrantLock mLock = new ReentrantLock(true);

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mLock.lock();
                if (!removeTimeOutHandler) {
                    onScanResult(macAddress, SERACH_FAILED);
                }
            } finally {
                mLock.unlock();
            }
        }
    };

    public PeriodScanCallback(int defaultTime, String macAddress) {
        this.macAddress = macAddress.toUpperCase();
        Config.runOnUIThreadDelayed(mRunnable, defaultTime);
    }

    public abstract void onScanResult(String macAddress, int status);


    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        L.d(device.getAddress());
        if (scanRecord[5] == 0x01 && scanRecord[6] == 0x02) {
            if (macAddress.equalsIgnoreCase(device.getAddress())) {
                try {
                    mLock.lock();
                    if (!removeTimeOutHandler) {
                        removeTimeOutHandler = true;
                        Config.removeRunnable(mRunnable);
                        onScanResult(macAddress, SERACH_OK);
                    }
                } finally {
                    mLock.unlock();
                }
            }
        }
    }
}
