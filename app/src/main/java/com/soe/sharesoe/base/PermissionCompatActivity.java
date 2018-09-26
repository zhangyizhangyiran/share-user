package com.soe.sharesoe.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description 权限管理基类
 * @encoding UTF-8
 * @date 2017/11/11
 * @time 下午3:13
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class PermissionCompatActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private String hint;

    public static final int REQUESTCODE = 1000;

    //单个权限请求检测，true不需要请求权限，false需要请求权限
    public boolean isPermissionGranted(String permissionName) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        //判断是否需要请求允许权限
        int hasPermision = ActivityCompat.checkSelfPermission(PermissionCompatActivity.this, permissionName);
        if (hasPermision != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    //多个权限请求检测，返回list,如果list.size为空说明权限全部有了不需要请求，否则请求没有的
    public List<String> isPermissionsAllGranted(String[] permArray) {
        List<String> list = new ArrayList<>();
        //获得批量请求但被禁止的权限列表
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return list;
        }
        for (int i = 0; permArray != null && i < permArray.length; i++) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(PermissionCompatActivity.this, permArray[i])) {
                list.add(permArray[i]);
            }
        }

        return list;
    }

    //单个权限请求
    public boolean Requestpermission(String s, int requestCode, String defeat) {

        boolean flag = false;
        hint = defeat;
        if (!TextUtils.isEmpty(s)) {
            boolean granted = isPermissionGranted(s);
            if (granted) {  //有权限，调用方法
//                okPermissionResult(requestCode);
                flag = true;
            } else {
                ActivityCompat.requestPermissions(PermissionCompatActivity.this, new String[]{s}, requestCode);
                flag = false;
            }
        }

        return flag;
    }

    //多个权限请求
    public boolean Requestpermission(String s[], int requestCode, String defeat) {

        boolean flag = false;
        hint = defeat;
        if (s.length != 0) {
            List<String> perList = isPermissionsAllGranted(s);
            if (perList.size() == 0) {  //有权限，调用方法
//                okPermissionResult(requestCode);
                flag = true;
            } else {
                ActivityCompat.requestPermissions(PermissionCompatActivity.this, perList.toArray(new String[perList.size()]), requestCode);
                Toast.makeText(PermissionCompatActivity.this, "ContextCompat", Toast.LENGTH_SHORT).show();
                flag = false;
            }
        }
        return flag;
    }



    public void popAlterDialog() {
        new AlertDialog.Builder(PermissionCompatActivity.this)
                .setTitle("提示")
                .setMessage(hint)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //前往应用详情界面
                        try {
                            Uri packUri = Uri.parse("package:" + PermissionCompatActivity.this.getPackageName());
                            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packUri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            PermissionCompatActivity.this.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(PermissionCompatActivity.this, "跳转失败", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        Toast.makeText(PermissionCompatActivity.this, "xxxxx", Toast.LENGTH_SHORT).show();
        for (int i : grantResults) {
            if (i != PackageManager.PERMISSION_GRANTED) { //有权限未通过
                popAlterDialog();
                return;
            }
        }
//        okPermissionResult(requestCode);
    }


    //有权限调用
    public void okPermissionResult(int requestCode) {

    }

}
