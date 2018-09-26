package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-22.
 */

public class ContinuousLiftInstruction extends BaseOrderSender {
    private final static byte[] ORDER_CONTINUOUS_LIFT = {0x0a, 0x0f, 0x01, 0x00};
    private int heightOrPower;

    public ContinuousLiftInstruction(boolean upOrDown) {
        super(createOrder(upOrDown, ORDER_CONTINUOUS_LIFT));
    }

    private static byte[] createOrder(boolean upOrDown, byte[] order) {
        order[3] = (byte) (upOrDown ? 0 : 1);
        return order;
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x0a && characteristic[1] == 0x10) {
            if (characteristic[2] == 0x01 && characteristic[3] == 0x00) {
                opStatus = STATUS_ADJUSTING;
                heightOrPower = 0;
                return true;
            } else if (characteristic[2] == 0x01 && characteristic[3] == 0x02) {
                opStatus = STATUS_BUSY;
                heightOrPower = 0;
                return true;
            }  else if (characteristic[2] == 0x02 && characteristic[3] == 0x01) {
                opStatus = STATUS_OVER_LOAD;
                heightOrPower = characteristic[4] & 0xff;
                return true;
            } else if (characteristic[2] == 0x02 && characteristic[3] == 0x03) {
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
            orderSenderListener.onStopLiftSeatHeight(opStatus, heightOrPower);
        }
    }
}
