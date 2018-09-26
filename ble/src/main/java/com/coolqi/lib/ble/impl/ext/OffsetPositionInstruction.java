package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-22.
 */

public class OffsetPositionInstruction extends BaseOrderSender {

    private final static byte[] ORDER_OFFSET_POSITION = {0x0a, 0x09, 0x02, 0x00, 0x00};
    private int heightOrPower= 0;

    public OffsetPositionInstruction(boolean upOrDown, int offset) {
        super(createOrder(upOrDown, offset, ORDER_OFFSET_POSITION));
    }

    private static byte[] createOrder(boolean upOrDown, int offset, byte[] order) {
        order[3] = (byte) (upOrDown ? 0 : 1);
        order[4] = (byte) offset;
        return order;
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x0a && characteristic[1] == 0x0a) {
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
            orderSenderListener.onOffsetLiftSeatHeight(opStatus, heightOrPower);
        }
    }
}
