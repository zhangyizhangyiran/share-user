//package com.soe.sharesoe.module.mycenter.order;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.google.gson.Gson;
//import com.soe.sharesoe.R;
//import com.soe.sharesoe.base.Constant;
//import com.soe.sharesoe.base.RxBaseActivity;
//import com.soe.sharesoe.entity.YouPaiYunBean;
//import com.soe.sharesoe.utils.AppSettingsDialog;
//import com.soe.sharesoe.utils.CommonUtil;
//import com.soe.sharesoe.utils.T;
//import com.upyun.library.common.Params;
//import com.upyun.library.fusion.FusionUpload;
//import com.upyun.library.listener.UpCompleteListener;
//import com.upyun.library.listener.UpProgressListener;
//
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
//public class TakePhotoActivity extends RxBaseActivity {
//
//
//    @BindView(R.id.btn1)
//    Button btn1;
//
//    @BindView(R.id.img)
//    ImageView img;
//
//    private static final int REQUEST_CODE = 1;//更改头像码
//    private static final int RC_CAMERA_PERM = 123;
//    private static final int RC_SETTINGS_SCREEN = 125;
//
//    private WaittingDialog waitDilog;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_take_photo;
//    }
//
//    @Override
//    public void initViews(Bundle savedInstanceState) {
//        waitDilog = new WaittingDialog(this, R.style.CustomDialogStyle, "正在上传...");
//
//    }
//
//    @OnClick({R.id.btn1})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn1:
//                if (CommonUtil.isNetworkAvailable(TakePhotoActivity.this)) {
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(TakePhotoActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CAMERA_PERM);
//                    } else {
//                        tokePhoto();
//                    }
//                } else {
//                    T.error("当前网络不可用");
//                }
//                break;
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 图片选择结果回调
//        if (data == null) {
//            if (waitDilog != null) {
//                waitDilog.dismiss();
//            }
//            return;
//        }
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            List<String> pathList = data.getStringArrayListExtra("result");
//            File file = new File(pathList.get(0));
////                 LogCoolqi.i("zhangqi","KB=="+file.length() / 1024 + "k");
//            waitDilog.setIsCanclable(false);
//            waitDilog.show();
//            //上传服务器头像
//            HashMap<String, Object> paramsMap = new HashMap<>();
//            paramsMap.put(Params.BUCKET, Constant.YOUPAIYUN_PARAMS_BUCKET);
//            paramsMap.put(Params.SAVE_KEY, Constant.upyPhotoName("avatar"));
//            FusionUpload.upload(TakePhotoActivity.this, file, paramsMap, Constant.YOUPAIYUN_KEY, null, completeListener, progressListener);
//
//        }
//        if (requestCode == RC_SETTINGS_SCREEN) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(TakePhotoActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CAMERA_PERM);
//            } else {
//                tokePhoto();
//            }
//
//        }
//    }
//
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case RC_CAMERA_PERM:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    //点击扫描,提示用户必须打开摄,看像头才能扫描以及已获取权限，读取手机相册操作
//                    tokePhoto();
//                } else {// Permission Denied 拒绝
//                    new AppSettingsDialog.Builder(TakePhotoActivity.this, getString(R.string.rationale_photo_sd))
//                            .setTitle(getString(R.string.title_settings_dialog))
//                            .setPositiveButton(getString(R.string.setting))
//                            .setNegativeButton(getString(R.string.cancel), null /* click listener */)
//                            .setRequestCode(RC_SETTINGS_SCREEN)
//                            .build()
//                            .show();
//                }
//                break;
//        }
//    }
//
//    //结束回调，不可为空
//    UpCompleteListener completeListener = new UpCompleteListener() {
//
//        @Override
//        public void onComplete(boolean isSuccess, String result, int uploadType) {
//
//            if (isSuccess) {
//                Gson gson = new Gson();
//                //上传到又拍云服务器的图片操作
//                YouPaiYunBean youPaiYunBean = gson.fromJson(result, YouPaiYunBean.class);
//                //上传到自己的服务器
////                alterPhoto(youPaiYunBean.getUrl());
//
//                Glide.with(TakePhotoActivity.this).load(Constant.YOUPAIYUN_URL + youPaiYunBean.getUrl())
//                        .error(R.mipmap.icon_classift)
//                        .into(img);
//                T.error(youPaiYunBean.getUrl());
//
//
//                if (waitDilog != null) {
//                    waitDilog.dismiss();
//                }
//            } else {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        T.error("图片上传失败,请重新上传");
//
//                        if (waitDilog != null) {
//                            waitDilog.dismiss();
//                        }
//                    }
//                });
//            }
//
//        }
//    };
//    UpProgressListener progressListener = new UpProgressListener() {
//        @Override
//        public void onRequestProgress(final long bytesWrite, final long contentLength) {
//
//        }
//    };
//
//
//    //拍照调用方法
//    private void tokePhoto() {
//
////         Constant.setConfigImage(TakePhotoActivity.this,true, 200, 200, REQUEST_CODE);
//
//    }
//}
