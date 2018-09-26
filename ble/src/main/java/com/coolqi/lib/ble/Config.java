package com.coolqi.lib.ble;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by yangcai on 17-6-17.
 */

public class Config {

    private static boolean DEBUG = false;

    private static boolean SAVE_LOG = false;

    private static String LOG_FILE_PATH = "";

    private static Context context;

    private static  Handler mHandler = new Handler(Looper.getMainLooper());
    public static void init(Context mContext) {
        context = mContext;
    }

    public static void setDebugMode(boolean debug) {
        DEBUG = debug;
    }

    public static void saveLog(boolean save, String logFilePath) {
        SAVE_LOG = save;
        LOG_FILE_PATH = logFilePath;
    }

    public static boolean debugEnable() {
        return DEBUG;
    }

    public static boolean saveLogEnable() {
        return SAVE_LOG;
    }

    public static String getLogFilePath() {
        return LOG_FILE_PATH;
    }

    public static Context getContext() {
        return context;
    }

    public static void toast(final String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void runOnUIThread(Runnable r){
        mHandler.post(r);
    }

    public static void runOnUIThreadDelayed(Runnable r,long delayedTime){
        mHandler.postDelayed(r,delayedTime);
    }

    public static void removeRunnable(Runnable r){
        mHandler.removeCallbacks(r);
    }

}
