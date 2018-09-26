package com.soe.sharesoe.module.mycenter.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.PermissionCompat;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.HttpBaseResult;
import com.soe.sharesoe.module.member.activity.ChangePasswordActivity;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.AppCleanMgr;
import com.soe.sharesoe.utils.S;
import com.soe.sharesoe.utils.T;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 绑定界面
 * @encoding UTF-8
 * @date 17/11/6
 * @time 上午10:26
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class SetActivity extends RxBaseActivity {


    @BindView(R.id.bar_left_img)
    ImageView mBarLeftImg;
    @BindView(R.id.bar_left)
    TextView mBarLeft;
    @BindView(R.id.bar_center_img)
    ImageView mBarCenterImg;
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.bar_right)
    TextView mBarRight;
    @BindView(R.id.bar_right_img)
    ImageView mBarRightImg;
    @BindView(R.id.layout_right_text)
    LinearLayout mLayoutRightText;
    @BindView(R.id.bar_right_serch_img)
    ImageView mBarRightSerchImg;
    @BindView(R.id.bar_right_msg_img)
    ImageView mBarRightMsgImg;
    @BindView(R.id.layout_right_img)
    LinearLayout mLayoutRightImg;
    @BindView(R.id.bar_view)
    View mBarView;
    @BindView(R.id.activity_set_lin_binding)
    LinearLayout mActivitySetLinBinding;
    @BindView(R.id.activity_set_lin_revise)
    LinearLayout mActivitySetLinRevise;
    @BindView(R.id.activity_set_lin_clean)
    LinearLayout mActivitySetLinClean;
    @BindView(R.id.activity_set_cache)
    TextView mActivitySetCache;
    @BindView(R.id.activity_set_no_login)
    TextView mActivitySetNoLogin;
    private String mAppClearSize;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

//        判断是否登录
        String isLogin = (String) S.get(this, "isLogin", "false");
//        登录与不登录相关逻辑
        if (isLogin.equals("true")) {
//            已登录

            mActivitySetNoLogin.setVisibility(View.VISIBLE);
        } else {
//            未登录
            mActivitySetNoLogin.setVisibility(View.GONE);
        }

        mBarTitle.setText("设置");
//        获取文件权限
        initPermins();


    }


//    设置缓存大小

    private void setCacheSize() {

        mActivitySetCache.setText(mAppClearSize);
    }


    private void initPermins() {

        PermissionCompat permissionCompat = new PermissionCompat(this);
        boolean isrequestpermission = permissionCompat.Requestpermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 99, "清理缓存需要该权限");
        if (isrequestpermission) {
            //设置缓存大小
            mAppClearSize = AppCleanMgr.getAppClearSize(this);
            setCacheSize();

        }
    }


    @OnClick({R.id.activity_set_lin_binding, R.id.activity_set_lin_revise, R.id.activity_set_lin_clean, R.id.bar_left_img, R.id.activity_set_no_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            退出登录
            case R.id.activity_set_no_login:
                S.remove(this, "isLogin");
                setLogout();
                finish();
                break;
            case R.id.bar_left_img:
                this.finish();
                break;
//            换绑手机
            case R.id.activity_set_lin_binding:
                startActivity(new Intent(this, BindingPhoneActivity.class));
                finish();
                break;
//            修改密码
            case R.id.activity_set_lin_revise:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                finish();
                break;
            case R.id.activity_set_lin_clean:
//                清除缓存
                setClearCache();
                break;
        }
    }


    private void setLogout() {

        RetrofitHelper.getInstance().getUserLogout(new Subscriber<HttpBaseResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpBaseResult httpBaseResult) {
//                1000 代表成功 ；
                if (httpBaseResult.getCode() == 1000) {


                } else {
                    T.success(httpBaseResult.getMsg());
                }

            }
        });
    }


    //    清除缓存
    private void setClearCache() {

        AppCleanMgr.cleanInternalCache(this);
        AppCleanMgr.cleanApplicationData(this);
        mActivitySetCache.setText("");
        T.success("缓存清理成功");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            mAppClearSize = AppCleanMgr.getAppClearSize(this);
            //        设置缓存大小
            setCacheSize();
        } else {
            T.success("没有权限");
        }


    }


    @Override
    public void onClick(View view) {

    }


}
