package com.soe.sharesoe.module.member.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.module.member.CountDownButtonHelper;
import com.soe.sharesoe.module.member.bean.RegisterChechMobile;
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;
import com.soe.sharesoe.module.member.edittext.StringUtils;
import com.soe.sharesoe.net.RetrofitHelper;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project 注册界面
 * @Description
 * @encoding UTF-8
 * @date 17/10/27
 * @time 下午8:37
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class RegisterActivity extends RxBaseActivity implements CountDownButtonHelper.OnFinishListener {


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
    @BindView(R.id.autoCheckEditText)
    AutoCheckEditText mAutoCheckEditText;
    @BindView(R.id.activity_register_textinp)
    TextInputLayout mActivityRegisterTextinp;
    @BindView(R.id.activity_register_tv_service)
    TextView mActivityRegisterTvService;
    @BindView(R.id.activity_register_tv_login)
    TextView mActivityRegisterTvLogin;
    @BindView(R.id.activity_register_tv_phone_code)
    TextView mActivityRegisterTvPhoneCode;
    @BindView(R.id.activity_register_tv_captcha)
    TextView mActivityRegisterTvCaptcha;
    private boolean mEmpty = true;
    private String mPhone;
    private CountDownButtonHelper mHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        mBarTitle.setText("填写验证码");
        mPhone = (String) getIntent().getExtras().get("phone");
        mActivityRegisterTvPhoneCode.setText(mPhone);
//        初始化倒计时功能
        initCountdown();
//        获取验证码接口
        initData(mPhone);
//        事件监听
        initListener();


    }

    @Override
    public void onClick(View view) {

    }

    private void initCountdown() {

        mHelper = new CountDownButtonHelper(mActivityRegisterTvCaptcha,
                "后重试", 60, 1);
        mHelper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

            @Override
            public void finish() {
                mActivityRegisterTvCaptcha.setText("重新获取");

            }
        });
        mHelper.start();

    }

    private void initData(String code) {
        RetrofitHelper.getInstance().getRegisterPhoneCode(new Subscriber<RegisterChechMobile>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RegisterChechMobile registerChechMobile) {


            }
        }, code);

    }

    private void initListener() {
        mAutoCheckEditText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = s.toString().trim();
                mEmpty = StringUtils.isEmpty(trim);
                if (mEmpty) {
                    //按钮不可点击
                    Drawable drawable = getResources().getDrawable(R.drawable.shape_no_login);
                    mActivityRegisterTvLogin.setBackground(drawable);
                    mActivityRegisterTvLogin.setTextColor(getResources().getColor(R.color.Color_Theme_Main_Background));
                    mActivityRegisterTvLogin.setEnabled(false);


                } else {
                    //按钮可点击
                    mActivityRegisterTvLogin.setEnabled(true);
                    Drawable drawable = getResources().getDrawable(R.drawable.shape_login);
                    mActivityRegisterTvLogin.setBackground(drawable);
                    mActivityRegisterTvLogin.setTextColor(getResources().getColor(R.color.white));


                }

            }
        });

        mAutoCheckEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mActivityRegisterTextinp.setHintAnimationEnabled(true);
                mActivityRegisterTextinp.setHintEnabled(true);
                return false;

            }
        });


    }


    @OnClick({R.id.bar_title, R.id.activity_register_tv_service, R.id.activity_register_tv_login, R.id.activity_register_tv_captcha, R.id.autoCheckEditText, R.id.bar_left_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                this.finish();
                break;
            case R.id.bar_title:
                break;
            case R.id.activity_register_tv_service:
                break;
            case R.id.activity_register_tv_login:
                if (mEmpty) {
                    return;
                } else {
                    // 请求数据
                    getcCodestatus(mPhone, "000000");
                }

                break;
            case R.id.activity_register_tv_captcha:
                Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
                if (StringUtils.isEmpty(mPhone)) {
                    return;
                } else {
                    initData(mPhone);
                }

                break;
            case R.id.autoCheckEditText:

                break;
        }
    }

    private void getcCodestatus(String phone, String verifyCode) {
        RetrofitHelper.getInstance().getRegisterMobileCodeCheck(new Subscriber<RegisterChechMobile>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RegisterChechMobile registerChechMobile) {
                Intent intent = new Intent(RegisterActivity.this, PasswordSetActivity.class);
                intent.putExtra("phone", mPhone);
                startActivity(intent);
                mHelper.countDownTimer.cancel();
                finish();

            }
        }, phone, verifyCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.countDownTimer.cancel();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            mHelper.countDownTimer.cancel();

            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
