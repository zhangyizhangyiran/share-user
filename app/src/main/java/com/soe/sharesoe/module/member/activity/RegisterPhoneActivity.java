package com.soe.sharesoe.module.member.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.module.member.bean.RegisterChechMobile;
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;
import com.soe.sharesoe.module.member.edittext.RegexUtils;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.T;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 手机号验证
 * @encoding UTF-8
 * @date 17/11/1
 * @time 下午4:21
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class RegisterPhoneActivity extends RxBaseActivity {


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
    @BindView(R.id.activity_register_phone_auto)
    AutoCheckEditText mActivityRegisterPhoneAuto;
    @BindView(R.id.activity_register_phone_textinp)
    TextInputLayout mActivityRegisterPhoneTextinp;
    @BindView(R.id.activity_register_phone_tv_service)
    TextView mActivityRegisterPhoneTvService;
    @BindView(R.id.activity_register_phone_tv_login)
    TextView mActivityRegisterPhoneTvLogin;
    @BindView(R.id.activity_register_phone_imag)
    ImageView mActivityRegisterPhoneImag;
    private boolean mMobileExact = false;
    private String mPhone;
    private boolean isSelecte = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_phone;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("注册");
//        初始化监听
        initListener();

    }


    private void initListener() {
        mActivityRegisterPhoneAuto.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mPhone = s.toString().trim();
                mMobileExact = RegexUtils.isMobileExact(mPhone);
                if (mMobileExact) {
                    Drawable drawable = getResources().getDrawable(R.drawable.shape_login);
                    mActivityRegisterPhoneTvLogin.setEnabled(true);
                    mActivityRegisterPhoneTvLogin.setBackground(drawable);
                    mActivityRegisterPhoneTvLogin.setTextColor(getResources().getColor(R.color.white));

                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.shape_no_login);
                    mActivityRegisterPhoneTvLogin.setBackground(drawable);
                    mActivityRegisterPhoneTvLogin.setTextColor(getResources().getColor(R.color.text_gray));
                    mActivityRegisterPhoneTvLogin.setEnabled(false);

                }


            }
        });
    }


    @OnClick({R.id.activity_register_phone_textinp, R.id.activity_register_phone_tv_service, R.id.activity_register_phone_tv_login, R.id.bar_left_img, R.id.activity_register_phone_imag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_register_phone_imag:
                isSelecte = !isSelecte;
                if (isSelecte) {
                    mActivityRegisterPhoneImag.setImageResource(R.mipmap.icon_radio_selected);
                    mActivityRegisterPhoneTvLogin.setEnabled(true);


                } else {
                    mActivityRegisterPhoneImag.setImageResource(R.mipmap.icon_radio_normal);
                    mActivityRegisterPhoneTvLogin.setEnabled(false);
                }
                break;
            case R.id.bar_left_img:
                this.finish();
                break;
            case R.id.activity_register_phone_textinp:
                break;
//            万享服务条款
            case R.id.activity_register_phone_tv_service:
                break;
//            登录
            case R.id.activity_register_phone_tv_login:
                if (isSelecte) {
//                    请求接口验证手机是否注册
                    postPhoneRegister(mPhone);
                } else {
                    T.success("请阅读并勾选同意按钮 ");
                }
                break;
        }
    }

    private void postPhoneRegister(String phone) {

        RetrofitHelper.getInstance().getRegisterPhoneData(new Subscriber<RegisterChechMobile>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RegisterChechMobile registerChechMobile) {
                if (registerChechMobile.getCode().equals("1000")) {
//                   成功跳转
                    setSkipActivity();

                } else {

                    T.success(registerChechMobile.getMsg());
                }

            }
        }, phone);

    }

    private void setSkipActivity() {
        Intent intent = new Intent(RegisterPhoneActivity.this, RegisterActivity.class);
        intent.putExtra("phone", mPhone);
        startActivity(intent);
        RegisterPhoneActivity.this.finish();
    }

    @Override
    public void onClick(View view) {

    }

}
