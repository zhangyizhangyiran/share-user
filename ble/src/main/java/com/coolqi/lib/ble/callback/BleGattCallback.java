package com.coolqi.lib.ble.callback;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;

import com.coolqi.lib.ble.BleConnectionCompat;
import com.coolqi.lib.ble.Config;
import com.coolqi.lib.ble.L;
import com.coolqi.lib.ble.OrderUtil;
import com.coolqi.lib.ble.R;
import com.coolqi.lib.ble.impl.GattConnection;
import com.coolqi.lib.ble.impl.OrderSender;

import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by yangcai on 17-4-21.
 */

public class BleGattCallback extends BluetoothGattCallback {
    //是否连接上设备了
    private boolean afterDiscover;
    private GattConnection connection;
    private ReentrantLock reentrantLock;
    private OrderSender mOrderSender;
    private BluetoothGatt mGatt;
    private int connectStatus;
    private int enableDescriptor = 0;//开启蓝牙模式

    @Override
    public void onConnectionStateChange(final BluetoothGatt gatt, int status, int newState) {
        L.w(Config.getContext().getString(R.string.find_the_device_connect_successfull) + "status " + status);
        L.w(Config.getContext().getString(R.string.find_the_device_connect_successfull) + "newState " + newState);
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            L.w(Config.getContext().getString(R.string.find_the_device_connect_successfull));
            gatt.discoverServices();
        } else {
            L.w(Config.getContext().getString(R.string.find_the_device_connect_failed));
            if (afterDiscover) {
                if (connection != null) {
                    connection.connectFailed(connectStatus);
                }
                return;
            }
            if (status == 133 || status == 62 && !afterDiscover) {
                afterDiscover = true;//
                Config.runOnUIThreadDelayed(new Runnable() {
                    @Override
                    public void run() {
                        L.w(Config.getContext().getString(R.string.find_the_device_connect_failed_retry));
                        BleConnectionCompat connectionCompat = new BleConnectionCompat(Config.getContext());
                        connectionCompat.connectGatt(gatt.getDevice(), false, BleGattCallback.this);
                    }
                }, 1000);
            }
        }
    }

    // 获取center
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        afterDiscover = true;
        if (status == BluetoothGatt.GATT_SUCCESS) {
            L.d(Config.getContext().getString(R.string.find_services_successfull));
            OrderUtil.handshakeRrigger(gatt);
            OrderUtil.enableDescriptor1(gatt, true);
            mGatt = gatt;
        } else {
            L.d(Config.getContext().getString(R.string.find_services_failed));
            if (connection != null) {
                connection.connectFailed(PeriodScanCallback.SERACH_SERVICE);
            }
        }
    }

    // 握手成功回调不需要center返回值
    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        L.d(Config.getContext().getString(R.string.hand_shake_successfull) + " status " + status);
        L.printBytes("descriptors value", descriptor.getValue());
        enableDescriptor++;
        if (enableDescriptor == 1) {
            OrderUtil.enableDescriptor1(gatt, false);
        } else if (enableDescriptor == 2) {
            OrderUtil.enableDescriptor2(gatt, true);
        } else if (enableDescriptor == 3) {
            OrderUtil.enableDescriptor2(gatt, false);
        } else if (connection != null) {
            connection.connectScuess();
            enableDescriptor = 0;
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        L.printBytes("onCharacteristicWrite", OrderUtil.getMingwen(characteristic));
        try {
            reentrantLock.lock();
            mOrderSender.onCharacteristicWrite(characteristic);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        L.printBytes("onCharacteristicChanged", OrderUtil.getMingwen(characteristic));
        try {
            reentrantLock.lock();
            mOrderSender.onCharacteristicChanged(characteristic);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void setConnection(GattConnection connection) {
        this.connection = connection;
    }

    public void setReentrantLock(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    public void setmOrderSender(OrderSender mOrderSender) {
        this.mOrderSender = mOrderSender;
        mOrderSender.sendInstructions(mGatt);
    }

    public void close() {
        if (mGatt != null) {
            mGatt.disconnect();
            mGatt.close();
            mGatt = null;
            enableDescriptor = 0;
        }
    }

    public OrderSender getmOrderSender() {
        return mOrderSender;
    }

    public void setConnectStatus(int connectStatus) {
        this.connectStatus = connectStatus;
    }
}
