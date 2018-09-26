package com.coolqi.lib.ble.impl.ext;

import android.bluetooth.BluetoothGatt;

import com.coolqi.lib.ble.OrderUtil;
import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-27.
 */

public class ChangeKeyInstruction extends BaseOrderSender {
    private final static byte[] ORDER_OLD = {7, 1, 8};

    private final static byte[] ORDER_NEW = {7, 2, 8};

    private byte[][] orders;

    private int time = 0;

    private BluetoothGatt mGattPeripheral;

    private byte[] newKey ;

    public ChangeKeyInstruction(byte[] newPwd) {
        newKey = newPwd;
        orders = createOrder(newPwd);
        time = 0;
        currSendOrder = orders[time];
    }


    private static byte[][] createOrder(byte[] newKey) {
        byte[][] orders = new byte[2][11];
        System.arraycopy(ORDER_OLD, 0, orders[0], 0, 3);
        System.arraycopy(newKey, 0, orders[0], 3, 8);
        System.arraycopy(ORDER_NEW, 0, orders[1], 0, 3);
        System.arraycopy(newKey, 8, orders[1], 3, 8);
        return orders;
    }


    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x07 && characteristic[1] == 0x03 && characteristic[2] == 0x01) {
            if (characteristic[3] == 0x00) {
                opStatus = STATUS_SCUESS;
            } else {
                opStatus = STATUS_FAILED;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onSendSucess() {
        if (!sendInstructionComplete()) {
            currSendOrder = orders[time];
            sendInstructions(mGattPeripheral);
        }
    }

    @Override
    protected void callback() {
        if (opStatus == STATUS_UNKONW) {
            return;
        }
        if (opStatus ==STATUS_SCUESS){
            OrderUtil.setKey(newKey);
        }
        if (orderSenderListener != null) {
            orderSenderListener.onChangeKey(opStatus);
        }
    }

    @Override
    public boolean sendInstructionComplete() {
        if (time < 2) {
            return false;
        }
        return super.sendInstructionComplete();
    }

    @Override
    public boolean sendInstructions(BluetoothGatt mGattPeripheral) {
        time++;
        this.mGattPeripheral = mGattPeripheral;
        return super.sendInstructions(mGattPeripheral);
    }
}
