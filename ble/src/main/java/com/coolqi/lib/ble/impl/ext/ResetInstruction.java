package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-21.
 */

public class ResetInstruction extends BaseOrderSender {
    public static final byte[] ORDER_RESET_LOCK = {0x05, 0x0c, 0x01, 0x01};



    public ResetInstruction() {
        super(ORDER_RESET_LOCK);
    }


    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x05 && characteristic[1] == 0x0d && characteristic[2] == 0x01) {
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
    protected void callback() {
        if (opStatus == STATUS_UNKONW) {
            return;
        }
        if (orderSenderListener != null) {
            orderSenderListener.onResetLock(opStatus);
        }

    }
}
