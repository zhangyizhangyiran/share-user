package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-21.
 */

public class PowerInstruction extends BaseOrderSender {

    private static final byte[] ORDER_POWER = {0x02, 0x01, 0x01, 0x01};

    private int batter = -1;

    public PowerInstruction() {
        super(ORDER_POWER);
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x02 && characteristic[1] == 0x02 && characteristic[2] == 0x01) {
            batter = characteristic[3];
            return true;
        }
        return false;
    }

    @Override
    protected void callback() {
        if (batter == -1) {
            return;
        }
        if (orderSenderListener != null) {
            orderSenderListener.onGetLockPower(batter);
        }
    }
}
