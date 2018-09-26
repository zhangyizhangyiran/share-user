package com.coolqi.lib.ble.impl.ext;

import android.text.TextUtils;

import com.coolqi.lib.ble.L;
import com.coolqi.lib.ble.OrderUtil;
import com.coolqi.lib.ble.impl.BaseOrderSender;

import java.util.Arrays;


/**
 * Created by yangcai on 17-4-8.
 * 获取token
 */

public class TokenInstruction extends BaseOrderSender {

    private final static byte[] ORDER_TOKEN = {0x06, 0x01, 0x01, 0x01};

    private String lockVersion;

    public TokenInstruction() {
        super(ORDER_TOKEN);

    }

    @Override
    protected boolean handleSucess(byte[] mingwen) {
        if (mingwen[0] == 0x06 && mingwen[1] == 0x02) {
            OrderUtil.setToken(Arrays.copyOfRange(mingwen, 3, 7));
            lockVersion = ((int) mingwen[8]) + "." + ((int) mingwen[9]);
            L.printBytes("token ", OrderUtil.getToken());
            return true;
        }
        return false;
    }

    @Override
    protected void callback() {
        if (TextUtils.isEmpty(lockVersion)) {
            return;
        }
        if (orderSenderListener != null) {
            orderSenderListener.onGetLockVersion(lockVersion);
        }
    }
}
