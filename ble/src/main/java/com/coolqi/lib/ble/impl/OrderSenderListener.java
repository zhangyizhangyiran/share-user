package com.coolqi.lib.ble.impl;

/**
 * Created by yangcai on 17-6-20.
 */

public interface OrderSenderListener {

    void onOrderSendfailed();

    void onGetLockVersion(String lockVersion);

    void onGetLockStatus(int lockStatus);

    void onGetLockPower(int lockPower);

    void onOpenLock(int openStatus);

    void onCloseLock(int closeStatus);

    void onliftSeatBoot(int power);

    void onliftSeatBootCheckPower(int power);

    void onliftSeatCheckPower(int power);

    void onliftSeatCheckHeight(int status,int height);

    void onAdjustLiftSeatHeight(int status,int height);

    void onOffsetLiftSeatHeight(int status,int height);

    void onContinuousLiftSeatHeight(int status,int height);

    void onStopLiftSeatHeight(int status,int height);

    void onResetLock(int resetStatus);

    void onFlash(int flashStatus);

    void onSound(int soundStatus);

    void onDynamicSound(int dynamicSoundStatus);

    void onChangePassword(int status);

    void onChangeKey(int status);
}
