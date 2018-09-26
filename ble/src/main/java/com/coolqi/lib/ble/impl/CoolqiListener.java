package com.coolqi.lib.ble.impl;

/**
 * Created by yangcai on 17-6-19.
 */

public interface CoolqiListener {

    void onBleMessage(int messageCode, String message);

    void onBleOpenCallback(int status);

    void onBleLockClosed();

    void onBleOpenPrepare(int lockPower, String lockVersion, int lockStatus);

    void onBleOpenLockStart();

    void onBleLifeSeatBoot();
}
