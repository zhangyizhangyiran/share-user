package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-21.
 */

public class OpenInstruction extends BaseOrderSender {
    private static final byte[] ORDER_OPEN_LOCK = {0x05, 0x01, 0x06};

   

    public OpenInstruction() {
        super(ORDER_OPEN_LOCK);
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x05 && characteristic[1] == 0x02 && characteristic[2] == 0x01) {
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
            orderSenderListener.onOpenLock(opStatus);
        }

    }
}
