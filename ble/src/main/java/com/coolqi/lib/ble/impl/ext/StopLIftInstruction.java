package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-22.
 */

public class StopLIftInstruction extends BaseOrderSender {

    private final static byte[] ORDER_STOP_LIFT = {0x0a, 0x11, 0x00};
    private int height;

    public StopLIftInstruction() {
        super(ORDER_STOP_LIFT);
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x0a && characteristic[1] == 0x12) {
            if (characteristic[2] == 0x02 && characteristic[3] == 0x00) {
                opStatus = STATUS_SCUESS;
                height = characteristic[4] & 0x0ff;
            } else if (characteristic[2] == 0x01 && characteristic[3] == 0x01) {
                height = 0;
                opStatus = STATUS_FAILED;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void callback() {
        if (opStatus == STATUS_UNKONW) {
            return;
        }
        if (orderSenderListener != null) {
            orderSenderListener.onContinuousLiftSeatHeight(opStatus, height);
        }
    }
}
