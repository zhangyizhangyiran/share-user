package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-22.
 */

public class CheckLiftSeatPowerInstruction extends BaseOrderSender {
    private final static byte[] ORDER_CHECK_POWER = {0x0a, 0x07, 0x00};

    private int power = -1;

    public CheckLiftSeatPowerInstruction() {
        super(ORDER_CHECK_POWER);
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x0a && characteristic[1] == 0x08 && characteristic[2] == 0x01) {
            power = characteristic[3] & 0xff;
            opStatus = STATUS_SCUESS;
            return true;
        }
        return false;
    }

    @Override
    protected void callback() {
        if (power == -1) {
            return;
        }
        if (orderSenderListener != null) {
            orderSenderListener.onliftSeatCheckPower(power);
        }
    }
}
