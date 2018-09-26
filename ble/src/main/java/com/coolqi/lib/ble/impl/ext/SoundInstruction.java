package com.coolqi.lib.ble.impl.ext;

import com.coolqi.lib.ble.impl.BaseOrderSender;

/**
 * Created by yangcai on 17-6-21.
 */

public class SoundInstruction extends BaseOrderSender {

    private static final byte[] ORDER_SOUND = {0x09, 0x03, 0x02};

    public SoundInstruction(int time, int code) {
        super(creatOrder(time, code));
    }

    private static byte[] creatOrder(int time, int code) {
        byte[] tmp = new byte[5];
        System.arraycopy(ORDER_SOUND, 0, tmp, 0, ORDER_SOUND.length);
        tmp[3] = (byte) time;
        tmp[4] = (byte) code;
        return tmp;
    }

    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x09 && characteristic[1] == 0x04 && characteristic[2] == 0x01) {
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
            orderSenderListener.onSound(opStatus);
        }
    }
}
