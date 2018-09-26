package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-20.
 */

public class StatusInstruction extends BaseOrderSender {
    private final static byte[] ORDER_STATUS = {0x05, 0x0e, 0x01, 0x01};

    private int opStatus = STATUS_UNKONW;

    public StatusInstruction() {
        super(ORDER_STATUS);
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x05 && characteristic[1] == 0x0f && characteristic[2] == 0x01) {
            if (characteristic[3] == 0x00) {
                opStatus = STATUS_SCUESS;//open
            } else {
                opStatus = STATUS_FAILED;//close
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
            orderSenderListener.onGetLockStatus(opStatus);
        }
    }
}
