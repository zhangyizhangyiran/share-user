package com.soe.sharesoe.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.soe.sharesoe.manager.ActivityManager;
import com.soe.sharesoe.module.member.activity.MemberActivity;
import com.soe.sharesoe.utils.S;

import butterknife.ButterKnife;
import butterknife.Unbinder;

//import com.baidu.mapapi.SDKInitializer;

/**
 * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
 * @version v1.0
 * @project sharesoe
 * @Description
 * @encoding UTF-8
 * @date 16/11/24
 * @time 上午9:34
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public abstract class RxBaseActivity extends PermissionCompatActivity {

    private Unbinder bind;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setSystemBarTransparent();

        //设置布局内容
        setContentView(getLayoutId());

        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);

        ActivityManager.getInstance().addActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void onClick(View view);

    private void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // KITKAT解决方案
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //PermissionReq.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /*---------------------------------------------------------------------------以下是android6.0动态授权的封装十分好用---------------------------------------------------------------------------*/
//    private int mPermissionIdx = 0x10;//请求权限索引
//    private SparseArray<GrantedResult> mPermissions = new SparseArray<>();//请求权限运行列表
//
//    @SuppressLint("Override")
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        GrantedResult runnable = mPermissions.get(requestCode);
//        if (runnable == null) {
//            return;
//        }
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            runnable.mGranted = true;
//        }
//        runOnUiThread(runnable);
//    }
//
//    public void requestPermission(String[] permissions, String reason, GrantedResult runnable) {
//        if (runnable == null) {
//            return;
//        }
//        runnable.mGranted = false;
//        if (Build.VERSION.SDK_INT < 23 || permissions == null || permissions.length == 0) {
//            runnable.mGranted = true;//新添加
//            runOnUiThread(runnable);
//            return;
//        }
//        final int requestCode = mPermissionIdx++;
//        mPermissions.put(requestCode, runnable);
//
//		/*
//            是否需要请求权限
//		 */
//        boolean granted = true;
//        for (String permission : permissions) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                granted = granted && checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
//            }
//        }
//
//        if (granted) {
//            runnable.mGranted = true;
//            runOnUiThread(runnable);
//            return;
//        }
//
//		/*
//            是否需要请求弹出窗
//		 */
//        boolean request = true;
//        for (String permission : permissions) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                request = request && !shouldShowRequestPermissionRationale(permission);
//            }
//        }
//
//        if (!request) {
//            final String[] permissionTemp = permissions;
//            AlertDialog dialog = new AlertDialog.Builder(this)
//                    .setMessage(reason)
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                requestPermissions(permissionTemp, requestCode);
//                            }
//                        }
//                    })
//                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            GrantedResult runnable = mPermissions.get(requestCode);
//                            if (runnable == null) {
//                                return;
//                            }
//                            runnable.mGranted = false;
//                            runOnUiThread(runnable);
//                        }
//                    }).create();
//            dialog.show();
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(permissions, requestCode);
//            }
//        }
//    }
//
//    public static abstract class GrantedResult implements Runnable {
//        private boolean mGranted;
//
//        public abstract void onResult(boolean granted);
//
//        @Override
//        public void run() {
//            onResult(mGranted);
//        }
//    }


    public void setLogin(Intent intent) {

        String isLogin = (String) S.get(this, "isLogin", "false");

        if (isLogin.equals("true")) {
            startActivity(intent);
        } else {
            startActivity(new Intent(this, MemberActivity.class));

        }
    }

    /**
     * 返回false 跳转到登录页，true是已经登录
     * @return
     */
    public boolean isLogin() {

        String isLogin = (String) S.get(this, "isLogin", "false");

        if (isLogin.equals("true")) {
            return true;
        } else {
            startActivity(new Intent(this, MemberActivity.class));
            return false;
        }
    }
}
