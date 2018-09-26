package com.coolqi.lib.ble;

import com.coolqi.lib.ble.callback.BleGattCallback;
import com.coolqi.lib.ble.impl.BaseOrderSender;
import com.coolqi.lib.ble.impl.OrderSenderListener;
import com.coolqi.lib.ble.impl.ext.AdjustPositionInstruction;
import com.coolqi.lib.ble.impl.ext.ChangeKeyInstruction;
import com.coolqi.lib.ble.impl.ext.ChangePasswordInstruction;
import com.coolqi.lib.ble.impl.ext.CheckLiftSeatHeightInstruction;
import com.coolqi.lib.ble.impl.ext.CheckLiftSeatPowerInstruction;
import com.coolqi.lib.ble.impl.ext.ContinuousLiftInstruction;
import com.coolqi.lib.ble.impl.ext.DynamicSoundInstruction;
import com.coolqi.lib.ble.impl.ext.FlashInstruction;
import com.coolqi.lib.ble.impl.ext.OffsetPositionInstruction;
import com.coolqi.lib.ble.impl.ext.OpenInstruction;
import com.coolqi.lib.ble.impl.ext.PowerInstruction;
import com.coolqi.lib.ble.impl.ext.ResetInstruction;
import com.coolqi.lib.ble.impl.ext.SoundInstruction;
import com.coolqi.lib.ble.impl.ext.StatusInstruction;
import com.coolqi.lib.ble.impl.ext.StopLIftInstruction;
import com.coolqi.lib.ble.impl.ext.TokenInstruction;

/**
 * Created by yangcai on 17-6-20.
 */

public class OrderSenderHelper {

    protected static void sendTokenOrder(BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new TokenInstruction(), bleGattCallback, listener);
    }

    protected static void sendStatusOrder(BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new StatusInstruction(), bleGattCallback, listener);
    }

    protected static void sendPowerOrder(BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new PowerInstruction(), bleGattCallback, listener);
    }

    protected static void sendOpenOrder(BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new OpenInstruction(), bleGattCallback, listener);
    }

    protected static void sendResetOrder(BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new ResetInstruction(), bleGattCallback, listener);
    }

    protected static void sendFlashOrder(int color, int time, int durTime, BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new FlashInstruction(color, time, durTime), bleGattCallback, listener);
    }

    protected static void sendSoundOrder(int time, int code, BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new SoundInstruction(time, code), bleGattCallback, listener);
    }

    protected static void sendLiftSeatCheckPowerOrder(BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new CheckLiftSeatPowerInstruction(), bleGattCallback, listener);
    }

    protected static void sendLiftSeatCheckHeightOrder(BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new CheckLiftSeatHeightInstruction(), bleGattCallback, listener);
    }

    protected static void sendLiftSeatAdjustPosition(int position, BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new AdjustPositionInstruction(position), bleGattCallback, listener);
    }

    protected static void sendLiftSeatContinuousPosition(boolean upOrDow, BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new ContinuousLiftInstruction(upOrDow), bleGattCallback, listener);
    }

    protected static void sendLiftSeatStopPosition(BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new StopLIftInstruction(), bleGattCallback, listener);
    }


    protected static void sendDynamicSoundOrder(String voiceText, BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new DynamicSoundInstruction(voiceText), bleGattCallback, listener);
    }

    protected static void sendLiftSeatOffsetPosition(boolean upOrDow, int position, BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new OffsetPositionInstruction(upOrDow, position), bleGattCallback, listener);
    }

    protected static void sendChangePwdOrder(byte[] pwd, BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new ChangePasswordInstruction(pwd), bleGattCallback, listener);
    }

    protected static void sendChangeKeyOrder(byte[] key, BleGattCallback bleGattCallback, OrderSenderListener listener) {
        sendOrder(new ChangeKeyInstruction(key), bleGattCallback, listener);
    }

    private static void sendOrder(BaseOrderSender orderSender, BleGattCallback bleGattCallback, OrderSenderListener listener) {
        orderSender.setOrderSenderListener(listener);
        if (bleGattCallback != null)
            bleGattCallback.setmOrderSender(orderSender);
    }
}

