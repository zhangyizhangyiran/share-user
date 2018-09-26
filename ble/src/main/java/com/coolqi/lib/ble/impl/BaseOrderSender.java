package com.coolqi.lib.ble.impl;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.coolqi.lib.ble.Config;
import com.coolqi.lib.ble.L;
import com.coolqi.lib.ble.OrderUtil;
import com.coolqi.lib.ble.impl.ext.ChangeKeyInstruction;
import com.coolqi.lib.ble.impl.ext.ChangePasswordInstruction;

import java.util.Random;


/**
 * Created by yangcai on 17-6-19.
 */

public abstract class BaseOrderSender implements OrderSender {

    protected final static int STATUS_UNKONW = -1;

    public final static int STATUS_SCUESS = 1;

    public final static int STATUS_FAILED = 2;

    public final static int STATUS_BUSY = 3;

    public final static int STATUS_ADJUSTING = 4;

    public final static int STATUS_OVER_LOAD = 5;

    public final static int STATUS_LOW_POWER = 6;

    public final static int STATUS_STUCK = 7;

    protected int opStatus = STATUS_UNKONW;

    protected byte[] currSendOrder; //发送的指令

    private int liftSeatCheckPowerTime = 0;

    protected boolean writeBusy;

    protected boolean changBusy;

    protected BaseOrderSender() {
    }

    protected BaseOrderSender(byte[] currSendOrder) {
        this.currSendOrder = currSendOrder;
    }


    protected OrderSenderListener orderSenderListener;

    @Override
    public boolean sendInstructions(final BluetoothGatt mGattPeripheral) {
        completeInstructions();
        L.printBytes("sendInstructions", currSendOrder);
        byte[] miwen = OrderUtil.getMiwen(currSendOrder);
        if (miwen != null) {
            final BluetoothGattCharacteristic writer = OrderUtil.getWriteCharacteristic(mGattPeripheral);
            writer.setValue(miwen);
            writeBusy = true;
            changBusy = true;
            Config.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    L.d(Thread.currentThread().getName());
                    boolean b = mGattPeripheral.writeCharacteristic(writer);
                    L.d("writeCharacteristic " + b);
                }
            });
        }
        return false;
    }

    @Override
    public boolean sendSucess(BluetoothGattCharacteristic characteristic) {
        byte[] mingwen = OrderUtil.getMingwen(characteristic);
        if ((mingwen[0] == (byte) 0xCB && mingwen[1] == currSendOrder[0] && mingwen[2] == currSendOrder[1])) {
            changBusy = false;
            return true;
        }
        return false;
    }

    protected void onSendSucess() {

    }

    @Override
    public boolean sendInstructionComplete() {
        return true;
    }


    private void completeInstructions() {
        if (currSendOrder[0] == 0x05 && currSendOrder[1] == 0x01 && currSendOrder[2] == 0x06 && OrderUtil.getPassword() != null) {
            byte[] tmp = new byte[9];
            System.arraycopy(currSendOrder, 0, tmp, 0, currSendOrder.length);
            System.arraycopy(OrderUtil.getPassword(), 0, tmp, currSendOrder.length, OrderUtil.getPassword().length);
            currSendOrder = tmp;
        }
        int length = currSendOrder.length;
        if (length != 16) {
            byte[] tmp = new byte[16];
            for (int i = 0; i < 16; i++) {
                tmp[i] = (byte) (new Random().nextInt(10) + 1);
            }
            System.arraycopy(currSendOrder, 0, tmp, 0, currSendOrder.length);
            if (OrderUtil.getToken() != null && length <= 12) {
                System.arraycopy(OrderUtil.getToken(), 0, tmp, length, OrderUtil.getToken().length);
            }
            currSendOrder = tmp;
        }
    }

    @Override
    public boolean onCharacteristicChanged(BluetoothGattCharacteristic characteristic) {
        byte[] minwen = OrderUtil.getMingwen(characteristic);
        if (!checkMinWen(minwen)) {
            return false;
        }
        if ((minwen[0] & 0xff) == 0xcb) {
            if (sendSucess(characteristic)) {
                if (this instanceof ChangeKeyInstruction || this instanceof ChangePasswordInstruction) {
                    if (!sendInstructionComplete()) {
                        onCallback();
                    }
                }
                return true;
            } else {
                if (orderSenderListener != null) {
                    orderSenderListener.onOrderSendfailed();
                }
            }
        } else if (handleSucess(minwen)) {
            onCallback();
            return true;
        }
        return dealBleOtherOrder(minwen);
    }

    protected abstract boolean handleSucess(byte[] characteristic);

    private void onCallback() {
        L.d(String.format("writeBusy %s changBusy %s  sendInstructionComplete %s", writeBusy, changBusy, sendInstructionComplete()));
        if (writeBusy) {
            return;
        }
        if (changBusy) {
            return;
        }
        if (!sendInstructionComplete()) {
            onSendSucess();
        } else {
            callback();
        }
    }

    protected abstract void callback();

    @Override
    public boolean onCharacteristicWrite(BluetoothGattCharacteristic characteristic) {
        byte[] minwen = OrderUtil.getMingwen(characteristic);
        if (OrderUtil.equals(currSendOrder, minwen)) {
            writeBusy = false;
            onCallback();
            return true;
        }
        return false;
    }

    public void setOrderSenderListener(OrderSenderListener orderSenderListener) {
        this.orderSenderListener = orderSenderListener;
    }

    private boolean checkMinWen(byte[] bytes) {
        if (bytes == null) {
            return false;
        }
        if (bytes.length != 16) {
            return false;
        }
        return true;
    }

    private boolean dealBleOtherOrder(byte[] minwen) {
        //close lock
        if (minwen[0] == 0x05 && minwen[1] == 0x08 && minwen[2] == 0x01) {
            if (orderSenderListener != null) {
                if (minwen[3] == 0x00) {
                    orderSenderListener.onCloseLock(STATUS_SCUESS);
                } else {
                    orderSenderListener.onCloseLock(STATUS_SCUESS);
                }
            }
            return true;
        } else if (minwen[0] == 0x0a && minwen[1] == 0x16 && minwen[2] == 0x01) {//lift seat boot
            if (orderSenderListener != null) {
                orderSenderListener.onliftSeatBoot(minwen[3] & 0xff);
            }
            return true;
        } else if (minwen[0] == 0x0a && minwen[1] == 0x08 && minwen[2] == 0x01) {//lift seat boot check power
            liftSeatCheckPowerTime++;
            if (orderSenderListener != null && liftSeatCheckPowerTime == 2) {
                liftSeatCheckPowerTime = 0;
                orderSenderListener.onliftSeatBootCheckPower(minwen[3] & 0xff);
            }
            return true;
        }
        return false;
    }
}
