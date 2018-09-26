package com.coolqi.lib.ble.impl;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Created by yangcai on 17-6-19.
 */

public interface OrderSender {

    //发送指令
    boolean sendInstructions(BluetoothGatt mGattPeripheral);

    //发送指令是否成功
    boolean sendSucess(BluetoothGattCharacteristic characteristic);

    //指令是否发送结束
    boolean sendInstructionComplete();

    boolean onCharacteristicChanged(BluetoothGattCharacteristic characteristic);

    boolean onCharacteristicWrite(BluetoothGattCharacteristic characteristic);

}
