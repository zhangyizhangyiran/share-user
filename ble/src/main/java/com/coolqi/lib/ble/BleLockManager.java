package com.coolqi.lib.ble;

import android.text.TextUtils;

import com.coolqi.lib.ble.callback.PeriodScanCallback;
import com.coolqi.lib.ble.impl.CoolqiListener;
import com.coolqi.lib.ble.impl.GattConnection;
import com.coolqi.lib.ble.impl.LiftOrderCallback;
import com.coolqi.lib.ble.impl.OrderCallback;
import com.coolqi.lib.ble.impl.OrderSenderListener;
import com.coolqi.lib.ble.model.LockInfo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangcai on 17-6-19.
 */

public class BleLockManager {
    private static BleLockManager instance;

    public static BleLockManager getInstance() {
        if (instance == null) {
            synchronized (BleLockManager.class) {
                if (instance == null) {
                    instance = new BleLockManager();
                }
            }
        }
        return instance;
    }

    private OrderSenderListener mOrderSenderListener;

    private GattConnection mGattConnection;

    private CoolqiListener mCoolqiListener;

    private BleLockBase mBleLockBase;

    private ReentrantLock reentrantLock;

    private LockInfo mLockInfo;

    private OrderCallback orderCallback;

    private LiftOrderCallback liftOrderCallback;

    private boolean resetResetOpen;

    private boolean twoOpen;

    private BleLockManager() {
        mBleLockBase = new BleLockBase();
        reentrantLock = new ReentrantLock(true);
        initListener();
        mBleLockBase.setGattConnection(mGattConnection);
        mBleLockBase.setOrderSenderListener(mOrderSenderListener);
    }


    public void openLock(LockInfo mLockInfo) {
        this.mLockInfo = mLockInfo;
        OrderUtil.setKey(mLockInfo.getKey());
        OrderUtil.setPassword(mLockInfo.getPassword());
        mBleLockBase.sancBleDevice(mLockInfo.getMacAddress());
    }

    public void setCoolqiListener(CoolqiListener mCoolqiListener) {
        this.mCoolqiListener = mCoolqiListener;
        mBleLockBase.setmCoolqiListener(mCoolqiListener);
        mBleLockBase.setReentrantLock(reentrantLock);
    }

    public void setMaxOpenLockTime(long maxOpenLockTime) {
        mBleLockBase.setMaxOpenLockTime(maxOpenLockTime);
    }

    public void openLock() {
        openLock((OrderCallback) null);
    }

    public void openLock(OrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.openLock2();
            orderCallback = callback;
        }
    }

    public void openLockSync(int lockStatus, String lockVersion, boolean resetResetOpen, boolean twoOpen) {
        if (mBleLockBase == null) {
            return;
        }
        this.resetResetOpen = resetResetOpen;
        this.twoOpen = twoOpen;
        orderCallback = null;
        if (opResetResetOpenLock(lockStatus, lockVersion) && resetResetOpen) {
            mBleLockBase.resetLock();
        } else
            mBleLockBase.openLockSync();
    }


    private void openLockSync() {
        if (mBleLockBase != null)
            mBleLockBase.openLockSync();
    }

    public void resetLock(OrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.resetLock();
            orderCallback = callback;
        }
    }

    public void resetLock() {
        resetLock(null);
    }

    public void flash(int color, int time, int durTime, OrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.flash(color, time, durTime);
            orderCallback = callback;
        }
    }

    public void flash(int color, int time, int durTime) {
        flash(color, time, durTime, null);
    }

    public void sound(int time, int code, OrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.sound(time, code);
            orderCallback = callback;
        }
    }

    public void sound(int time, int code) {
        sound(time, code, null);
    }

    public void dynamicSound(String voiceText, OrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.dynamicSound(voiceText);
            orderCallback = callback;
        }
    }

    public void dynamicSound(String voiceText) {
        dynamicSound(voiceText, null);
    }

    public void checkLiftSeatPower(OrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.checkLiftSeatPowerOrder();
            orderCallback = callback;
        }
    }

    public void checkLiftSeatPower() {
        checkLiftSeatPower(null);
    }

    public void checkLiftSeatHeight(LiftOrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.checkLiftSeatHeightOrder();
            liftOrderCallback = callback;
        }
    }

    public void checkLiftSeatHeight() {
        checkLiftSeatHeight(null);
    }

    public void adjustLiftSeatPosition(int position, LiftOrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.adjustLiftSeatPosition(position);
            liftOrderCallback = callback;
        }
    }

    public void adjustLiftSeatPosition(int position) {
        adjustLiftSeatPosition(position, null);
    }

    public void upOffsetLiftSeatPosition(int offset, LiftOrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.offsetLiftSeatPosition(true, offset);
            liftOrderCallback = callback;
        }
    }

    public void upOffsetLiftSeatPosition(int offset) {
        upOffsetLiftSeatPosition(offset, null);
    }

    public void downOffsetLiftSeatPosition(int offset, LiftOrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.offsetLiftSeatPosition(false, offset);
            liftOrderCallback = callback;
        }
    }

    public void downOffsetLiftSeatPosition(int offset) {
        downOffsetLiftSeatPosition(offset, null);
    }

    public void upContinuousLift() {
        upContinuousLift(null);
    }

    public void upContinuousLift(LiftOrderCallback callback) {
        if (mBleLockBase != null) {
            liftOrderCallback = callback;
            mBleLockBase.continuousLift(true);
        }
    }

    public void downContinuousLift() {
        downContinuousLift(null);
    }

    public void downContinuousLift(LiftOrderCallback callback) {
        if (mBleLockBase != null) {
            liftOrderCallback = callback;
            mBleLockBase.continuousLift(false);
        }
    }

    public void stopLift(LiftOrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.stopLift();
            liftOrderCallback = callback;
        }
    }

    public void stopLift() {
        stopLift(null);
    }


    public void getTokenAndVersion(OrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.getTokenAndVersion();
            orderCallback = callback;
        }
    }

    public void getTokenAndVersion() {
        getTokenAndVersion(null);
    }

    public void getLockStatus(OrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.getLockStatus();
            orderCallback = callback;
        }
    }

    public void getLockStatus() {
        getLockStatus(null);
    }

    public void getLockPower(OrderCallback callback) {
        if (mBleLockBase != null) {
            mBleLockBase.getLockPower();
            orderCallback = callback;
        }
    }

    public void changePassword(String password, OrderCallback callback) {
        if (mBleLockBase != null) {
            orderCallback = callback;
            mBleLockBase.changePassword(OrderUtil.getByte4Str(password));
        }
    }

    public void changePassword(String password) {
        changePassword(password, null);
    }

    public void changeKey(String key, OrderCallback callback) {
        if (mBleLockBase != null) {
            orderCallback = callback;
            mBleLockBase.changeKey(OrderUtil.getByte4Str(key));
        }
    }

    public void changeKey(String key) {
        changeKey(key, null);
    }


    public void getLockPower() {
        getLockPower(null);
    }

    private void initListener() {
        if (mGattConnection == null) {
            mGattConnection = new GattConnection() {
                @Override
                public void connectFailed(final int connectStaus) {
                    L.d("connectFailed " + connectStaus);
                    if (connectStaus == PeriodScanCallback.SERACH_OK) {
                        mBleLockBase.bleMessage(LockConstant.LOCK_ERROR_1016);
                    } else if (connectStaus == PeriodScanCallback.SERACH_FAILED) {
                        mBleLockBase.bleMessage(LockConstant.LOCK_ERROR_1014);
                    } else {
                        mBleLockBase.bleMessage(LockConstant.LOCK_ERROR_1017);
                    }
                }

                @Override
                public void connectScuess() {
                    getTokenAndVersion();
                }
            };
        }
        if (mOrderSenderListener == null) {
            mOrderSenderListener = new OrderSenderListener() {

                private int soundTime = 0;

                private String lockVersion;

                private int lockStatus;

                private int resettIime = 0;

                @Override
                public void onOrderSendfailed() {

                }

                @Override
                public void onGetLockVersion(String lockVersion) {
                    L.d("onGetLockVersion " + lockVersion);
                    this.lockVersion = lockVersion;
                    if (!orderCallback(lockVersion)) {
                        return;
                    }
                    getLockStatus();
                }

                @Override
                public void onGetLockStatus(int lockStatus) {
                    L.d("onGetLockStatus " + lockStatus);
                    this.lockStatus = lockStatus;
                    if (mCoolqiListener != null && lockStatus == 1) {
                        bleMessage(LockConstant.LOCK_ERROR_1008);
                    }
                    if (!orderCallback("" + lockStatus)) {
                        return;
                    }
                    if (mLockInfo.isNeedVoice()) {
                        if (TextUtils.isEmpty(mLockInfo.getVoiceText())) {
                            sound(1, 0);
                        } else {
                            dynamicSound(mLockInfo.getVoiceText());
                        }
                    } else {
                        getLockPower();
                    }
                }

                @Override
                public void onGetLockPower(final int lockPower) {
                    L.d("onGetLockPower " + lockPower);
                    //电量的范围0-100 超过该范围表示查询失败
                    if (lockPower < 0 || lockPower > 100) {
                        bleMessage(LockConstant.LOCK_ERROR_1010);
                    }
                    if (!orderCallback("" + lockPower)) {
                        return;
                    }
                    if (mCoolqiListener != null) {
                        Config.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                mCoolqiListener.onBleOpenPrepare(lockPower, lockVersion, lockStatus);
                            }
                        });
                    }
                }

                @Override
                public void onOpenLock(final int openSatus) {
                    L.d("onOpenLock " + openSatus);
                    if (openSatus == 2) {
                        bleMessage(LockConstant.LOCK_ERROR_1011);
                    }
                    if (!orderCallback("" + openSatus)) {
                        return;
                    }
                    if (isLock4jiali(lockVersion) && twoOpen) {
                        twoOpen = false;
                        openLock();
                    } else if (mCoolqiListener != null) {
                        Config.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                mCoolqiListener.onBleOpenCallback(openSatus);
                            }
                        });
                    }
                }

                @Override
                public void onCloseLock(int closeStatus) {
                    L.d("onCloseLock 关锁成功");
                    if (closeStatus == 2) {
                        bleMessage(LockConstant.LOCK_ERROR_1018);
                        return;
                    }
                    if (mCoolqiListener != null) {
                        Config.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                mCoolqiListener.onBleLockClosed();
                            }
                        });
                    }
                    close();
                }

                @Override
                public void onliftSeatBoot(int power) {
                    L.d("onliftSeatBoot " + power);
                }

                @Override
                public void onliftSeatBootCheckPower(int power) {
                    L.d("onliftSeatBootCheckPower " + power);
                    if (mCoolqiListener != null) {
                        Config.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                mCoolqiListener.onBleLifeSeatBoot();
                            }
                        });
                    }
                }

                @Override
                public void onliftSeatCheckPower(int power) {
                    L.d("onliftSeatCheckPower " + power);
                    orderCallback("" + power);
                }

                @Override
                public void onliftSeatCheckHeight(int status, int height) {
                    L.d("onliftSeatCheckHeight " + status + " heigth " + height);
                    liftOrderCallback(status, "" + height);
                    if (status == 2) {
                        bleMessage(LockConstant.LOCK_ERROR_1044);
                    }
                }

                @Override
                public void onAdjustLiftSeatHeight(int status, int height) {
                    L.d("onAdjustLiftSeatHeight " + status + " heigth " + height);
                    liftOrderCallback(status, "" + height);
                    switch (status) {
                        case 3:
                            bleMessage(LockConstant.LOCK_ERROR_1034);
                            break;
                        case 5:
                            bleMessage(LockConstant.LOCK_ERROR_1031);
                            break;
                        case 6:
                            bleMessage(LockConstant.LOCK_ERROR_1033);
                            break;
                        case 7:
                            bleMessage(LockConstant.LOCK_ERROR_1032);
                            break;
                    }
                }

                @Override
                public void onOffsetLiftSeatHeight(int status, int height) {
                    L.d("onOffsetLiftSeatHeight " + status + " heigth " + height);
                    liftOrderCallback(status, "" + height);
                    switch (status) {
                        case 3:
                            bleMessage(LockConstant.LOCK_ERROR_1038);
                            break;
                        case 5:
                            bleMessage(LockConstant.LOCK_ERROR_1035);
                            break;
                        case 6:
                            bleMessage(LockConstant.LOCK_ERROR_1037);
                            break;
                        case 7:
                            bleMessage(LockConstant.LOCK_ERROR_1036);
                            break;
                    }
                }

                @Override
                public void onContinuousLiftSeatHeight(int status, int height) {
                    L.d("onContinuousLiftSeatHeight " + status + " heigth " + height);
                    liftOrderCallback(status, "" + height);
                    switch (status) {
                        case 3:
                            bleMessage(LockConstant.LOCK_ERROR_1032);
                            break;
                        case 5:
                            bleMessage(LockConstant.LOCK_ERROR_1039);
                            break;
                        case 6:
                            bleMessage(LockConstant.LOCK_ERROR_1041);
                            break;
                        case 7:
                            bleMessage(LockConstant.LOCK_ERROR_1040);
                            break;
                    }
                }

                @Override
                public void onStopLiftSeatHeight(int status, int height) {
                    L.d("onStopLiftSeatHeight " + status + " heigth " + height);
                    liftOrderCallback(status, "" + height);
                    if (status == 2) {
                        bleMessage(LockConstant.LOCK_ERROR_1043);
                    }
                }

                @Override
                public void onResetLock(int resetStatus) {
                    L.d("onResetLock " + resetStatus);
                    if (!orderCallback("" + resetStatus)) {
                        return;
                    }
                    if (opResetResetOpenLock(lockStatus, lockVersion) && resetResetOpen) {
                        resettIime++;
                        if (resettIime == 1) {
                            resetLock();
                        } else if (resettIime >= 2) {
                            resettIime = 0;
                            resetResetOpen = false;
                            openLockSync();
                        }
                    }
                }

                @Override
                public void onFlash(int flashStatus) {
                    L.d("onFlash " + flashStatus);
                    orderCallback("" + flashStatus);
                }

                @Override
                public void onSound(int soundStatus) {
                    L.d("onSound " + soundStatus);
                    if (orderCallback("" + soundStatus)) {
                        soundTime++;
                        if (soundTime == 1) {
                            sound(1, 1);
                        } else if (soundTime >= 2) {
                            soundTime = 0;
                            getLockPower();
                        }
                    }
                }

                @Override
                public void onDynamicSound(int dynamicSoundStatus) {
                    L.d("onSound " + dynamicSoundStatus);
                    if (orderCallback("" + dynamicSoundStatus)) {
                        getLockPower();
                    }
                }

                @Override
                public void onChangePassword(int status) {
                    L.d("onChangePassword " + status);
                    orderCallback("" + status);
                }

                @Override
                public void onChangeKey(int status) {
                    L.d("onChangeKey " + status);
                    orderCallback("" + status);
                }


                private boolean liftOrderCallback(final int status, final String data) {
                    if (liftOrderCallback == null) {
                        return true;
                    }
                    Config.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            liftOrderCallback.callback(status, data);
                        }
                    });
                    return false;
                }

                private boolean orderCallback(final String status) {
                    if (orderCallback == null) {
                        return true;
                    }
                    Config.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            orderCallback.callback(status);
                        }
                    });
                    return false;
                }
            };
        }
    }


    public void close() {
        if (mBleLockBase != null) {
            mBleLockBase.close();
        }
        OrderUtil.clear();
    }


    public boolean isResetResetOpenLock(String lockVersion) {
        if ("1.2".equals(lockVersion) || "1.3".equals(lockVersion)) {
            return true;
        }
        return false;
    }

    private boolean opResetResetOpenLock(int lockStatus, String lockVersion) {
        if (lockStatus == 1 && isResetResetOpenLock(lockVersion)) {
            return true;
        }
        return false;
    }


    public boolean isLock4jiali(String lockVersion) {
        if ("2.0".equals(lockVersion) || "2.1".equals(lockVersion) || "2.2".equals(lockVersion) || "2.3".equals(lockVersion)) {
            return true;
        }
        return false;
    }

    protected void bleMessage(final int messageCode) {
        if (mCoolqiListener != null) {
            Config.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    mCoolqiListener.onBleMessage(messageCode, LockConstant.getErrorMessageByErrorCode(messageCode));
                }
            });
        }
    }
}
