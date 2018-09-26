package com.soe.sharesoe.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * Created by wangxiaofa on 2017/10/20.
 */

public class ActivityManager {

    public static ActivityManager mInstance;

    private ActivityManager(){

    }

    /**打开的activity**/
    private List<Activity> activities = new ArrayList<Activity>();
    /**应用实例**/
    /**
     *  获得实例
     * @return
     */
    public static ActivityManager getInstance(){
        if (null==mInstance){
            mInstance=new ActivityManager();
        }
        return mInstance;
    }
    /**
     * 新建了一个activity
     * @param activity
     */
    public void addActivity(Activity activity){
        activities.add(activity);
    }
    /**
     *  结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if (activity!=null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 任务详情页
     * */
    private List<Activity> taskActivitys = new ArrayList<Activity>();
    public void addTask(Activity activity) {
        taskActivitys.add(activity);
    }
    public void removeTask(Activity activity) {
        activity.finish();
    }
    public void closeTasks() {
        for (Activity activity : taskActivitys) {
            if (null != activity) {
                activity.finish();
            }
        }
    }

    public static void startActivityForResult(Activity fromActivity, Class<?> toClass, int requestCode) {
        Intent intent = new Intent(fromActivity, toClass);
        fromActivity.startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Activity fromActivity, Class<?> toClass) {
        Intent intent = new Intent(fromActivity, toClass);
        fromActivity.startActivity(intent);
    }

    public static void startActivity(Context fromActivity, Class<?> toClass) {
        Intent intent = new Intent(fromActivity, toClass);
        fromActivity.startActivity(intent);
    }
    /**
     * 判断某一个类是否存在任务栈里面
     * @return
     */
    public static boolean isExsitMianActivity(Context context,Class<?> cls){
        Intent intent = new Intent(context, cls);
        ComponentName cmpName = intent.resolveActivity(context.getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<android.app.ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (android.app.ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }
}
