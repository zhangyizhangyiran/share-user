package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-22.
 */

public class AdjustPositionInstruction extends BaseOrderSender {

    private final static byte[] ORDER_ADJUST_POSITION = {0x0a, 0x01, 0x02, 0x00, 0x00};

    private int heightOrPower = 0;

    public AdjustPositionInstruction(int position) {
        super(createOrder(position, ORDER_ADJUST_POSITION));
    }

    private static byte[] createOrder(int position, byte[] order) {
        order[3] = (byte) position;
        return order;
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x0a && characteristic[1] == 0x02) {
            if (characteristic[2] == 0x01 && characteristic[3] == 0x00) {
                opStatus = STATUS_ADJUSTING;
                heightOrPower = 0;
                return true;
            } else if (characteristic[2] == 0x01 && characteristic[3] == 0x02) {
                opStatus = STATUS_BUSY;
                heightOrPower = 0;
                return true;
            } else if (characteristic[2] == 0x01 && characteristic[3] == 0x03) {
                opStatus = STATUS_SCUESS;
                heightOrPower = 0;
                return true;
            } else if (characteristic[2] == 0x02 && characteristic[3] == 0x01) {
                opStatus = STATUS_OVER_LOAD;
                heightOrPower = characteristic[4] & 0xff;
                return true;
            } else if (characteristic[2] == 0x02 && characteristic[3] == 0x04) {
                opStatus = STATUS_STUCK;
                heightOrPower = characteristic[4] & 0xff;
                return true;
            } else if (characteristic[2] == 0x02 && characteristic[3] == 0x05) {
                opStatus = STATUS_LOW_POWER;
                heightOrPower = characteristic[4] & 0xff;
                return true;
            }
        }
        return false;
    }

    @Override
    protected void callback() {
        if (opStatus == STATUS_UNKONW) {
            return;
        }
        if (orderSenderListener != null) {
            orderSenderListener.onAdjustLiftSeatHeight(opStatus, heightOrPower);
        }
    }
}
