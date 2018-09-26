package com.coolqi.lib.ble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.text.TextUtils;

import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by yangcai on 17-6-20.
 */

public class OrderUtil {
    protected static final UUID BLE_SERVER_UUID = UUID.fromString("0000fee7-0000-1000-8000-00805f9b34fb");
    private static final UUID BLE_READDATA_UUID = UUID.fromString("000036f6-0000-1000-8000-00805f9b34fb");

    private static final UUID BLE_CLIENT_CHARACTERISTIC_CONFIG2 = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private static final UUID BLE_CLIENT_CHARACTERISTIC_CONFIG1 = UUID.fromString("00002901-0000-1000-8000-00805f9b34fb");

    private static final UUID BLE_WRITEDATA_UUID = UUID.fromString("000036f5-0000-1000-8000-00805f9b34fb");
    private static byte[] sKey;
    private static byte[] password;
    private static byte[] token;

    public static void setKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            L.d(key);
            sKey = getByte4Str(key);
        } else {
            sKey = new byte[]{32, 87, 47, 82, 54, 75, 63, 71, 48, 80, 65, 88, 17, 99, 45, 43};
        }
    }

    public static void setKey(byte[] sKey) {
        OrderUtil.sKey = sKey;
    }

    protected static void clear() {
        sKey = null;
        token = null;
        password = null;
    }

    public static void setToken(byte[] token) {
        OrderUtil.token = token;
    }

    public static byte[] getToken() {
        return token;
    }

    public static void setPassword(String password) {
        if (!TextUtils.isEmpty(password)) {
            L.d(password);
            OrderUtil.password = getByte4Str(password);
        } else {
            OrderUtil.password = new byte[]{0, 0, 0, 0, 0, 0};
        }
    }

    public static void setPassword(byte[] password) {
        OrderUtil.password = password;
    }

    public static byte[] getPassword() {
        return password;
    }

    // 加密
    private static byte[] encrypt(byte[] sSrc, byte[] sKey) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc);
            return encrypted;
        } catch (Exception ex) {
            return null;
        }
    }

    // 解密
    private static byte[] decrypt(byte[] sSrc, byte[] sKey) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] dncrypted = cipher.doFinal(sSrc);
            return dncrypted;
        } catch (Exception ex) {
            return null;
        }
    }

    public static byte[] getMingwen(BluetoothGattCharacteristic characteristic) {
        byte[] values = characteristic.getValue();
        byte[] x = new byte[16];
        System.arraycopy(values, 0, x, 0, 16);
        return decrypt(x, sKey);
    }

    public static byte[] getMiwen(byte[] sendData) {
        byte[] x = new byte[16];
        System.arraycopy(sendData, 0, x, 0, 16);
        return encrypt(x, sKey);
    }

    protected static byte[] getByte4Str(String byteStr) {
        String[] byteStrArr = byteStr.split(",");
        byte[] bytes = new byte[byteStrArr.length];
        for (int i = 0; i < byteStrArr.length; i++) {
            bytes[i] = Byte.parseByte(byteStrArr[i]);
        }
        return bytes;
    }

    public static boolean equals(byte[] left, byte[] right) {
        if (left != null && right != null && left.length == right.length) {
            for (int i = 0; i < left.length; i++) {
                if (left[i] != right[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    private static BluetoothGattService getService(BluetoothGatt mGattPeripheral) {
        return mGattPeripheral.getService(BLE_SERVER_UUID);
    }

    public static BluetoothGattCharacteristic getReadCharacteristic(BluetoothGatt mGattPeripheral) {
        return getService(mGattPeripheral).getCharacteristic(BLE_READDATA_UUID);
    }

    public static BluetoothGattCharacteristic getWriteCharacteristic(BluetoothGatt mGattPeripheral) {
        return getService(mGattPeripheral).getCharacteristic(BLE_WRITEDATA_UUID);
    }

    public static BluetoothGattDescriptor getReadDescriptor(BluetoothGatt mGattPeripheral) {
        return getReadCharacteristic(mGattPeripheral).getDescriptor(BLE_CLIENT_CHARACTERISTIC_CONFIG2);
    }

    /**
     * 握手，并触发接下来的流程
     */
    public static void handshakeRrigger(BluetoothGatt mGattPeripheral) {
        List<BluetoothGattService> listServices = mGattPeripheral.getServices();
        for (BluetoothGattService service : listServices) {
            L.d("********************");
            L.d(String.format("service uuid %s type %s", service.getUuid(), service.getType()));
            List<BluetoothGattCharacteristic> characters = service.getCharacteristics();
            for (BluetoothGattCharacteristic tmp : characters) {
                L.d(String.format("character uuid %s Properties %s Permissions %s", tmp.getUuid(), Utils.getCharPropertie(tmp.getProperties()), Utils.getCharPermission(tmp.getPermissions())));
                List<BluetoothGattDescriptor> descriptors = tmp.getDescriptors();
                for (BluetoothGattDescriptor tmp1 : descriptors) {
                    L.d(String.format("descriptors uuid %s getCharacteristic().getUuid() %s Permissions %s", tmp1.getUuid(), tmp1.getCharacteristic().getUuid(), Utils.getCharPermission(tmp1.getPermissions())));
                    L.printBytes("descriptors value", tmp1.getValue());
                }
            }
        }
        boolean scuess = mGattPeripheral.setCharacteristicNotification(getReadCharacteristic(mGattPeripheral), true);
        L.d("setCharacteristicNotification " + scuess);
    }

    public static void enableDescriptor2(final BluetoothGatt mGattPeripheral, final boolean first) {
        BluetoothGattCharacteristic readChar = getReadCharacteristic(mGattPeripheral);
        final BluetoothGattDescriptor descriptor = readChar.getDescriptor(BLE_CLIENT_CHARACTERISTIC_CONFIG2);
        if (first) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
        }
        Config.runOnUIThreadDelayed(new Runnable() {
            @Override
            public void run() {
                boolean b = mGattPeripheral.writeDescriptor(descriptor);
                L.d("enableDescriptor2 " + b + " first " + first + " Thread " + Thread.currentThread().getName());
            }
        }, 10);
    }

    public static void enableDescriptor1(final BluetoothGatt mGattPeripheral, final boolean first) {
        BluetoothGattCharacteristic readChar = getReadCharacteristic(mGattPeripheral);
        final BluetoothGattDescriptor descriptor = readChar.getDescriptor(BLE_CLIENT_CHARACTERISTIC_CONFIG1);
        if (first) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
        }
        Config.runOnUIThreadDelayed(new Runnable() {
            @Override
            public void run() {
                boolean b = mGattPeripheral.writeDescriptor(descriptor);
                L.d("enableDescriptor1 " + b + " first " + first + " Thread " + Thread.currentThread().getName());
            }
        }, 10);
    }
}
