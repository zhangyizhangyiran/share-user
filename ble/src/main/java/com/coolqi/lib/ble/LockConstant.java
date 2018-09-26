package com.coolqi.lib.ble;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangcai on 17-6-24.
 */

public class LockConstant {
    private final static Map<Integer, String> LOCK_ERRORS = new HashMap<>();
    protected final static int LOCK_ERROR_50000 = 50000;
    protected final static int LOCK_ERROR_1003 = 1003;
    protected final static int LOCK_ERROR_1005 = 1005;
    protected final static int LOCK_ERROR_1006 = 1006;
    protected final static int LOCK_ERROR_1008 = 1008;
    protected final static int LOCK_ERROR_10081 = 10081;
    protected final static int LOCK_ERROR_1010 = 1010;
    protected final static int LOCK_ERROR_1011 = 1011;
    protected final static int LOCK_ERROR_1014 = 1014;
    protected final static int LOCK_ERROR_1016 = 1016;
    protected final static int LOCK_ERROR_1017 = 1017;
    protected final static int LOCK_ERROR_1018 = 1018;
    protected final static int LOCK_ERROR_1031 = 1031;
    protected final static int LOCK_ERROR_1032 = 1032;
    protected final static int LOCK_ERROR_1033 = 1033;
    protected final static int LOCK_ERROR_1034 = 1034;
    protected final static int LOCK_ERROR_1035 = 1035;
    protected final static int LOCK_ERROR_1036 = 1036;
    protected final static int LOCK_ERROR_1037 = 1037;
    protected final static int LOCK_ERROR_1038 = 1038;
    protected final static int LOCK_ERROR_1039 = 1039;
    protected final static int LOCK_ERROR_1040 = 1040;
    protected final static int LOCK_ERROR_1041 = 1041;
    protected final static int LOCK_ERROR_1042 = 1042;
    protected final static int LOCK_ERROR_1043 = 1043;
    protected final static int LOCK_ERROR_1044 = 1044;
    protected final static int LOCK_ERROR_1045 = 1045;

    static {
        LOCK_ERRORS.put(LOCK_ERROR_1003, "扫描mac地址失败");
        LOCK_ERRORS.put(LOCK_ERROR_1005, "获取token失败");
        LOCK_ERRORS.put(LOCK_ERROR_1006, "发指令失败");
        LOCK_ERRORS.put(LOCK_ERROR_1008, "锁状态异常");
        LOCK_ERRORS.put(LOCK_ERROR_10081, "获取锁状态失败");
        LOCK_ERRORS.put(LOCK_ERROR_1014, "扫描mac地址失败,尝试直连失败");
        LOCK_ERRORS.put(LOCK_ERROR_1016, "扫描mac地址成功,尝试直连失败");
        LOCK_ERRORS.put(LOCK_ERROR_1017, "扫描mac地址成功,尝试直连成功,查找蓝牙服务失败");
        LOCK_ERRORS.put(LOCK_ERROR_50000, "开锁50秒超时");
        LOCK_ERRORS.put(LOCK_ERROR_1010, "获取锁电量失败");
        LOCK_ERRORS.put(LOCK_ERROR_1011, "开锁失败");
        LOCK_ERRORS.put(LOCK_ERROR_1018, "关锁失败");
        LOCK_ERRORS.put(LOCK_ERROR_1031, "固定高度升降失败:超重");
        LOCK_ERRORS.put(LOCK_ERROR_1032, "固定高度升降失败:卡死");
        LOCK_ERRORS.put(LOCK_ERROR_1033, "固定高度升降失败:低电量");
        LOCK_ERRORS.put(LOCK_ERROR_1034, "固定高度升降失败:未知原因");
        LOCK_ERRORS.put(LOCK_ERROR_1035, "偏移高度升降失败:超重");
        LOCK_ERRORS.put(LOCK_ERROR_1036, "偏移高度升降失败:卡死");
        LOCK_ERRORS.put(LOCK_ERROR_1037, "偏移高度升降失败:低电量");
        LOCK_ERRORS.put(LOCK_ERROR_1038, "偏移高度升降失败:未知原因");
        LOCK_ERRORS.put(LOCK_ERROR_1039, "连续高度升降失败:超重");
        LOCK_ERRORS.put(LOCK_ERROR_1040, "连续高度升降失败:卡死");
        LOCK_ERRORS.put(LOCK_ERROR_1041, "连续高度升降失败:低电量");
        LOCK_ERRORS.put(LOCK_ERROR_1042, "连续高度升降失败:未知原因");
        LOCK_ERRORS.put(LOCK_ERROR_1043, "连续升降停止失败");
        LOCK_ERRORS.put(LOCK_ERROR_1044, "获取升降座当前高度失败");
        LOCK_ERRORS.put(LOCK_ERROR_1045, "获取升降座当前电量败");
    }

    public static String getErrorMessageByErrorCode(int errorCode) {
        return LOCK_ERRORS.get(errorCode);
    }
}
