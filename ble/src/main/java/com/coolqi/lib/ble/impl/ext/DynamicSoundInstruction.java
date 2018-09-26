package com.coolqi.lib.ble.impl.ext;

import android.bluetooth.BluetoothGatt;

import com.coolqi.lib.ble.L;
import com.coolqi.lib.ble.impl.BaseOrderSender;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by yangcai on 17-6-22.
 */

public class DynamicSoundInstruction extends BaseOrderSender {

    private byte[][] voiceInstruction;

    private int dynamicTimes;

    private int time = 0;

    private BluetoothGatt mGattPeripheral;

    public DynamicSoundInstruction(String voiceText) {
        voiceInstruction = createOrder(voiceText);
        dynamicTimes = voiceInstruction.length;
        this.currSendOrder = voiceInstruction[0];
        time = 0;
    }

    private static byte[][] createOrder(String voiceText) {
        L.d(voiceText);
        byte[] voiceBytesTmp = voiceText.getBytes(Charset.forName("unicode"));
        L.printBytes("createOrder", voiceBytesTmp);
        byte[] voiceBytes = new byte[voiceBytesTmp.length - 2];
        System.arraycopy(voiceBytesTmp, 2, voiceBytes, 0, voiceBytes.length);
        L.printBytes("createOrder", voiceBytes);
        int dynamicTimes = voiceBytes.length / 12;
        if (voiceBytes.length % 12 != 0) {
            dynamicTimes++;
        }
        byte[][] voiceInstruction = new byte[dynamicTimes][16];
        byte[] instruction = {0x09, 0x05, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01};
        int srcPos = 0;
        for (int i = 0; i < dynamicTimes; i++) {
            byte[] instructionTmp = Arrays.copyOf(instruction, instruction.length);
            if (srcPos + 12 > voiceBytes.length) {
                int postion = voiceBytes.length % 12 + 1;
                instructionTmp[2] = Byte.parseByte("" + postion);
                System.arraycopy(voiceBytes, srcPos, instructionTmp, 3, voiceBytes.length % 12);
            } else {
                instructionTmp[2] = Byte.parseByte("13");
                System.arraycopy(voiceBytes, srcPos, instructionTmp, 3, 12);
                if (srcPos + 12 == voiceBytes.length) {
                    instructionTmp[instructionTmp.length - 1] = 0x00;
                }
            }
            srcPos += 12;
            voiceInstruction[i] = instructionTmp;
            L.printBytes("voiceInstruction[" + i + "]", voiceInstruction[i]);
        }
        return voiceInstruction;
    }


    @Override
    protected boolean handleSucess(byte[] characteristic) {
        if (characteristic[0] == 0x09 && characteristic[1] == 0x06 && characteristic[2] == 0x01) {
            if (characteristic[3] == 0x00 ) {
                if (sendInstructionComplete()){
                    opStatus = STATUS_SCUESS;
                }else {
                    this.currSendOrder = voiceInstruction[time];
                    sendInstructions(mGattPeripheral);
                }
            } else {
                opStatus = STATUS_FAILED;
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean sendInstructionComplete() {
        if (time != dynamicTimes) {
            return false;
        }
        time = 0;
        return true;
    }

    @Override
    public boolean sendInstructions(BluetoothGatt mGattPeripheral) {
        time++;
        this.mGattPeripheral = mGattPeripheral;
        return super.sendInstructions(mGattPeripheral);
    }

    @Override
    protected void callback() {
        if (opStatus == STATUS_UNKONW) {
            return;
        }
        if (orderSenderListener != null) {
            orderSenderListener.onDynamicSound(opStatus);
        }
    }
}
