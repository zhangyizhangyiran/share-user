package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-22.
 */

public class CheckLiftSeatHeightInstruction extends BaseOrderSender {

    private final static byte[] ORDER_CHECK_POWER = {0x0a, 0x13, 0x00};

    private int height;

    public CheckLiftSeatHeightInstruction() {
        super(ORDER_CHECK_POWER);
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x0a && characteristic[1] == 0x14) {
            if (characteristic[2] == 0x02) {
                opStatus = STATUS_SCUESS;
                height = characteristic[4] & 0xff;
            } else {
                opStatus = STATUS_FAILED;
                height = 0;
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
            orderSenderListener.onliftSeatCheckHeight(opStatus, height);
        }
    }
}
