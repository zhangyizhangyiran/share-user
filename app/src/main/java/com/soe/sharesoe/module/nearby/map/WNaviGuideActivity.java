///*
// * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
// */
//package com.soe.sharesoe.module.nearby.map;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.pm.PackageManager;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.baidu.mapapi.walknavi.WalkNavigateHelper;
//import com.baidu.mapapi.walknavi.adapter.IWNaviStatusListener;
//import com.baidu.mapapi.walknavi.adapter.IWRouteGuidanceListener;
//import com.baidu.mapapi.walknavi.adapter.IWTTSPlayer;
//import com.baidu.mapapi.walknavi.model.RouteGuideKind;
//import com.baidu.platform.comapi.walknavi.WalkNaviModeSwitchListener;
//import com.baidu.platform.comapi.walknavi.widget.ArCameraView;
//
//import java.util.List;
//
//
///**
// * @author chengel
// * @version v1.0
// * @project
// * @Description
// * @encoding UTF-8
// * @date 2017/10/25
// * @time 下午4:24
// * @修改记录 <pre>
// * 版本       修改人         修改时间         修改内容描述
// * --------------------------------------------------
// * <p>
// * --------------------------------------------------
// * </pre>
// */
//
//public class WNaviGuideActivity extends Activity {
//
//    private final static String TAG = WNaviGuideActivity.class.getSimpleName();
//
//    private WalkNavigateHelper mNaviHelper;
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mNaviHelper.quit();
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mNaviHelper.resume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mNaviHelper.pause();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mNaviHelper = WalkNavigateHelper.getInstance();
//
//        try {
//            View view = mNaviHelper.onCreate(WNaviGuideActivity.this);
//            if (view != null) {
//                setContentView(view);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mNaviHelper.setWalkNaviStatusListener(new IWNaviStatusListener() {
//            @Override
//            public void onWalkNaviModeChange(int mode, WalkNaviModeSwitchListener listener) {
//                Log.d(TAG, "onWalkNaviModeChange : " + mode);
//                mNaviHelper.switchWalkNaviMode(WNaviGuideActivity.this, mode, listener);
//            }
//
//            @Override
//            public void onNaviExit() {
//                Log.d(TAG, "onNaviExit");
//            }
//        });
//
//        boolean startResult = mNaviHelper.startWalkNavi(WNaviGuideActivity.this);
//        Log.e(TAG, "startWalkNavi result : " + startResult);
//
//        mNaviHelper.setTTsPlayer(new IWTTSPlayer() {
//            /**
//             * 诱导文本回调
//             * @param s 诱导文本
//             * @param b 是否抢先播报
//             * @return
//             */
//            @Override
//            public int playTTSText(final String s, boolean b) {
//                Log.d("tts", s);
//                return 0;
//            }
//        });
//
//        mNaviHelper.setRouteGuidanceListener(this, new IWRouteGuidanceListener() {
//            /**
//             * 诱导图标更新
//             *
//             * @param icon
//             */
//            @Override
//            public void onRouteGuideIconUpdate(Drawable icon) {
//
//            }
//            /**
//             * 诱导枚举信息
//             *
//             * @param routeGuideKind 诱导信息 see {@link RouteGuideKind}
//             */
//            @Override
//            public void onRouteGuideKind(RouteGuideKind routeGuideKind) {
//
//            }
//            /**
//             * 诱导信息
//             *
//             * @param charSequence 第一行显示的信息，比如“沿当前道路”
//             * @param charSequence1 第二行显示的信息，比如“向东出发”，第二行信息也可能为空
//             */
//            @Override
//            public void onRoadGuideTextUpdate(CharSequence charSequence, CharSequence charSequence1) {
//
//            }
//            /**
//             * 总的剩余距离
//             *
//             * @param charSequence 已经带有单位
//             */
//            @Override
//            public void onRemainDistanceUpdate(CharSequence charSequence) {
//
//            }
//            /**
//             * 总的剩余时间
//             *
//             * @param charSequence 已经带有单位
//             */
//            @Override
//            public void onRemainTimeUpdate(CharSequence charSequence) {
//
//            }
//            /**
//             * GPS状态发生变化，来自诱导引擎的消息
//             *
//             * @param charSequence GPS信息
//             * @param drawable
//             */
//            @Override
//            public void onGpsStatusChange(CharSequence charSequence, Drawable drawable) {
//
//
//            }
//            /**
//             * 已经开始偏航
//             *
//             * @param charSequence 偏航信息
//             * @param drawable
//             */
//            @Override
//            public void onRouteFarAway(CharSequence charSequence, Drawable drawable) {
//
//            }
//            /**
//             * 偏航规划中
//             *
//             * @param charSequence 偏航规划中的信息
//             * @param drawable
//             */
//            @Override
//            public void onRoutePlanYawing(CharSequence charSequence, Drawable drawable) {
//
//            }
//            /**
//             * 重新算路成功
//             */
//            @Override
//            public void onReRouteComplete() {
//
//            }
//            /**
//             * 到达目的地
//             */
//            @Override
//            public void onArriveDest() {
//
//            }
//            /**
//             * 震动
//             */
//            @Override
//            public void onVibrate() {
//
//            }
//        });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == ArCameraView.WALK_AR_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                Toast.makeText(WNaviGuideActivity.this, "没有相机权限,请打开后重试", Toast.LENGTH_SHORT).show();
//            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                mNaviHelper.startCameraAndSetMapView(WNaviGuideActivity.this);
//            }
//        }
//    }
//}
