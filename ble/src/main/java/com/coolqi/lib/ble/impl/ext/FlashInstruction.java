package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-21.
 */

public class FlashInstruction extends BaseOrderSender {

    private static final byte[] ORDER_FLASH = {0x09, 0x01, 0x03};

   

    public FlashInstruction(int color, int time, int durTime) {
        super(creatOrder(color, time, durTime));
    }

    private static byte[] creatOrder(int color, int time, int durTime) {
        byte[] tmp = new byte[6];
        System.arraycopy(ORDER_FLASH, 0, tmp, 0, ORDER_FLASH.length);
        tmp[3] = (byte) color;
        tmp[4] = (byte) time;
        tmp[5] = (byte) durTime;
        return tmp;
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x09 && characteristic[1] == 0x02 && characteristic[2] == 0x01) {
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
            orderSenderListener.onFlash(opStatus);
        }
    }
}
