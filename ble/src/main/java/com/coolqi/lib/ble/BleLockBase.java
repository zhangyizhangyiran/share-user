package com.coolqi.lib.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.widget.Toast;

import com.coolqi.lib.ble.callback.BleGattCallback;
import com.coolqi.lib.ble.callback.PeriodScanCallback;
import com.coolqi.lib.ble.callback.PeriodScanerCallback;
import com.coolqi.lib.ble.impl.CoolqiListener;
import com.coolqi.lib.ble.impl.GattConnection;
import com.coolqi.lib.ble.impl.OrderSenderListener;
import com.coolqi.lib.ble.impl.ext.OpenInstruction;
import com.coolqi.lib.ble.impl.ext.PowerInstruction;
import com.coolqi.lib.ble.impl.ext.StatusInstruction;
import com.coolqi.lib.ble.impl.ext.TokenInstruction;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangcai on 17-6-19.
 */

public class BleLockBase {

    private BleGattCallback mBluetoothGattCallback;

    private CoolqiListener mCoolqiListener;

    private ReentrantLock reentrantLock;

    private long maxOpenLockTime = 50000;

    private boolean openLockTimeout = false;

    private OrderSenderListener mOrderSenderListener;

    private GattConnection mGattConnection;

    private Runnable timeOutRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                reentrantLock.lock();
                if (!openLockTimeout) {
                    openLockTimeout = true;
                    if (mCoolqiListener != null) {
                        bleErrorMessage();
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    };

    private boolean isBleEnable(BluetoothAdapter mBluetoothAdapter) {
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            return false;
        }
        return true;
    }

    protected void sancBleDevice(String macAddress) {
        if (TextUtils.isEmpty(macAddress)) {
            Toast.makeText(Config.getContext(), Config.getContext().getString(R.string.incorrect_mac_address), Toast.LENGTH_SHORT).show();
            return;
        }
        BluetoothManager mBluetoothManager = (BluetoothManager) Config.getContext().getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (!isBleEnable(mBluetoothAdapter)) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Config.getContext().startActivity(intent);
            Config.toast(Config.getContext().getString(R.string.open_bluetooth));
            return;
        }
        if (mCoolqiListener != null) {
            mCoolqiListener.onBleOpenLockStart();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            final BluetoothLeScanner bluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
            ScanFilter.Builder mScanFilterBuilder = new ScanFilter.Builder();
            mScanFilterBuilder.setDeviceAddress(macAddress.toUpperCase())
                    .setServiceUuid(new ParcelUuid(OrderUtil.BLE_SERVER_UUID));
            ScanSettings.Builder mScanSettingsBuilder = new ScanSettings.Builder();
            mScanSettingsBuilder.setReportDelay(0);
            mScanSettingsBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
            if (Build.VERSION.SDK_INT >= 23) {
                mScanSettingsBuilder.setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);
                mScanSettingsBuilder.setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT);
            }
            bluetoothLeScanner.startScan(Arrays.asList(mScanFilterBuilder.build()), mScanSettingsBuilder.build(), new PeriodScanerCallback(10000, macAddress) {
                @Override
                public void onScanResultOut(String macAddress, int resultCode) {
                    L.d("onScanResultOut " + resultCode);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        bluetoothLeScanner.stopScan(this);
                    }
                    connectBle(mBluetoothAdapter.getRemoteDevice(macAddress), resultCode);
                }
            });
        } else {
            LeScanCallback mLeScanCallback = new PeriodScanCallback(10000, macAddress) {
                @Override
                public void onScanResult(String macAddress, int status) {
                    L.d("onScanResult " + status);
                    mBluetoothAdapter.stopLeScan(this);
                    connectBle(mBluetoothAdapter.getRemoteDevice(macAddress), status);
                }
            };
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }
    }

    private void connectBle(final BluetoothDevice remoteDevice, int connectStatus) {
        initListener();
        mBluetoothGattCallback.setReentrantLock(reentrantLock);
        mBluetoothGattCallback.setConnection(mGattConnection);
        mBluetoothGattCallback.setConnectStatus(connectStatus);
        Config.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                BleConnectionCompat connectionCompat = new BleConnectionCompat(Config.getContext());
                connectionCompat.connectGatt(remoteDevice, false, mBluetoothGattCallback);
            }
        });
        Config.runOnUIThreadDelayed(timeOutRunnable, maxOpenLockTime);
    }

    private void initListener() {
        if (mBluetoothGattCallback == null) {
            mBluetoothGattCallback = new BleGattCallback();
        }
    }

    protected void setReentrantLock(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    protected void setmCoolqiListener(CoolqiListener mCoolqiListener) {
        this.mCoolqiListener = mCoolqiListener;
    }

    protected void setMaxOpenLockTime(long maxOpenLockTime) {
        this.maxOpenLockTime = maxOpenLockTime;
    }


    protected void openLockSync() {
        try {
            reentrantLock.lock();
            if (!openLockTimeout) {
                Config.removeRunnable(timeOutRunnable);
                openLockTimeout = true;
                OrderSenderHelper.sendOpenOrder(mBluetoothGattCallback, mOrderSenderListener);
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    protected void openLock2() {
        OrderSenderHelper.sendOpenOrder(mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void resetLock() {
        OrderSenderHelper.sendResetOrder(mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void sound(int time, int code) {
        OrderSenderHelper.sendSoundOrder(time, code, mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void dynamicSound(String voiceText) {
        OrderSenderHelper.sendDynamicSoundOrder(voiceText, mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void flash(int color, int time, int durTime) {
        OrderSenderHelper.sendFlashOrder(color, time, durTime, mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void checkLiftSeatPowerOrder() {
        OrderSenderHelper.sendLiftSeatCheckPowerOrder(mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void checkLiftSeatHeightOrder() {
        OrderSenderHelper.sendLiftSeatCheckHeightOrder(mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void adjustLiftSeatPosition(int position) {
        OrderSenderHelper.sendLiftSeatAdjustPosition(position, mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void offsetLiftSeatPosition(boolean upOrDow, int position) {
        OrderSenderHelper.sendLiftSeatOffsetPosition(upOrDow, position, mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void continuousLift(boolean upOrDow) {
        OrderSenderHelper.sendLiftSeatContinuousPosition(upOrDow, mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void stopLift() {
        OrderSenderHelper.sendLiftSeatStopPosition(mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void getTokenAndVersion() {
        OrderSenderHelper.sendTokenOrder(mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void getLockStatus() {
        OrderSenderHelper.sendStatusOrder(mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void getLockPower() {
        OrderSenderHelper.sendPowerOrder(mBluetoothGattCallback, mOrderSenderListener);
    }

    public void changePassword(byte[] pwd) {
        OrderSenderHelper.sendChangePwdOrder(pwd, mBluetoothGattCallback, mOrderSenderListener);
    }

    public void changeKey(byte[] pwd) {
        OrderSenderHelper.sendChangeKeyOrder(pwd, mBluetoothGattCallback, mOrderSenderListener);
    }

    protected void setOrderSenderListener(OrderSenderListener mOrderSenderListener) {
        this.mOrderSenderListener = mOrderSenderListener;
    }

    protected void setGattConnection(GattConnection mGattConnection) {
        this.mGattConnection = mGattConnection;
    }

    protected void close() {
        if (mBluetoothGattCallback != null) {
            mBluetoothGattCallback.close();
            mBluetoothGattCallback = null;
        }
        openLockTimeout = false;
    }

    private void bleErrorMessage() {
        int errorCodeTmp = LockConstant.LOCK_ERROR_50000 ;
        if (mBluetoothGattCallback.getmOrderSender() == null) {
            errorCodeTmp = LockConstant.LOCK_ERROR_1014;
        } else if (mBluetoothGattCallback.getmOrderSender() instanceof TokenInstruction) {
            errorCodeTmp = LockConstant.LOCK_ERROR_1005;
        } else if (mBluetoothGattCallback.getmOrderSender() instanceof PowerInstruction) {
            errorCodeTmp = LockConstant.LOCK_ERROR_1010;
        } else if (mBluetoothGattCallback.getmOrderSender() instanceof StatusInstruction) {
            errorCodeTmp = LockConstant.LOCK_ERROR_10081;
        } else if (mBluetoothGattCallback.getmOrderSender() instanceof OpenInstruction) {
            errorCodeTmp = LockConstant.LOCK_ERROR_1011;
        }
        final int errorCode = errorCodeTmp;
        Config.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mCoolqiListener.onBleMessage(errorCode, LockConstant.getErrorMessageByErrorCode(errorCode));
            }
        });
    }

    protected void bleMessage(final int messageCode) {
        try {
            reentrantLock.lock();
            if (!openLockTimeout) {
                openLockTimeout = true;
                Config.removeRunnable(timeOutRunnable);
                if (mCoolqiListener != null) {
                    Config.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            mCoolqiListener.onBleMessage(messageCode, LockConstant.getErrorMessageByErrorCode(messageCode));
                        }
                    });
                }
            }
        } finally {
            reentrantLock.unlock();
        }
    }
}
