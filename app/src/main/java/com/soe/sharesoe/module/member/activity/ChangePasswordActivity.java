package com.soe.sharesoe.module.member.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.HttpBaseResult;
import com.soe.sharesoe.module.member.CountDownButtonHelper;
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.X;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 修改密码界面
 * @encoding UTF-8
 * @date 17/10/25
 * @time 下午7:29
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class ChangePasswordActivity extends RxBaseActivity {


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
    @BindView(R.id.activity_change_passworld_auto_phone)
    AutoCheckEditText mActivityChangePassworldAutoPhone;
    @BindView(R.id.activity_change_passworld_auto_code)
    AutoCheckEditText mActivityChangePassworldAutoCode;
    @BindView(R.id.activity_register_textinp)
    TextInputLayout mActivityRegisterTextinp;
    @BindView(R.id.activity_change_passworld_tv_captcha)
    TextView mActivityChangePassworldTvCaptcha;
    @BindView(R.id.activity_change_passworld_tv_login)
    TextView mActivityChangePassworldTvLogin;
    private String mTrim;
    private CountDownButtonHelper mCountDownButtonHelper;
    private Object mLoginData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mCountDownButtonHelper = new CountDownButtonHelper(mActivityChangePassworldTvCaptcha, "后重试", 10, 1);
        initListener();
        mBarTitle.setText("修改密码");

    }

    private void initListener() {

        //手机号格式监听
        mActivityChangePassworldAutoPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTrim = s.toString().trim();
                boolean mobilePhoneVerify = X.isMobilePhoneVerify(mTrim);
                if (mobilePhoneVerify) {
                    mActivityChangePassworldTvLogin.setBackgroundResource((R.drawable.shape_login));
                    mActivityChangePassworldTvLogin.setTextColor(getResources().getColor(R.color.white));
                    mActivityChangePassworldTvLogin.setEnabled(true);

                } else {

                    mActivityChangePassworldTvLogin.setBackgroundResource(R.drawable.shape_no_login);
                    mActivityChangePassworldTvLogin.setTextColor(getResources().getColor(R.color.Color_Theme_Main_Background));
                    mActivityChangePassworldTvLogin.setEnabled(false);

                }

            }
        });

        // 验证码监听

        mActivityChangePassworldAutoCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.bar_left_img, R.id.activity_change_passworld_tv_captcha, R.id.activity_change_passworld_tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                mCountDownButtonHelper.countDownTimer.cancel();

                finish();
                break;
            case R.id.activity_change_passworld_tv_captcha:
                getCodeData();

                break;
            case R.id.activity_change_passworld_tv_login:
                getLoginData();
                break;
        }
    }

    private void getCodeData() {


        mCountDownButtonHelper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            @Override
            public void finish() {
                mActivityChangePassworldTvCaptcha.setText("重新获取");

            }
        });

        mCountDownButtonHelper.start();

        initData();
    }

    private void initData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mTrim);


        RetrofitHelper.getInstance().getUserCenterVerityCode(new Subscriber<HttpBaseResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpBaseResult httpBaseResult) {

                Toast.makeText(ChangePasswordActivity.this, "onNext", Toast.LENGTH_SHORT).show();


            }


        }, map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    public void getLoginData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mTrim);
        map.put("verifyCode", "000000");
        RetrofitHelper.getInstance().getUserCenterVerityCodeCheck(new Subscriber<HttpBaseResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpBaseResult httpBaseResult) {

                Intent intent = new Intent(ChangePasswordActivity.this, SetChangePasswordActivity.class);
                intent.putExtra("phone", mTrim);
                startActivity(intent);
                mCountDownButtonHelper.countDownTimer.cancel();
                ChangePasswordActivity.this.finish();


            }
        }, map);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            mCountDownButtonHelper.countDownTimer.cancel();
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
