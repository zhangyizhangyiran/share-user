package com.soe.sharesoe.module.member.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.HttpBaseResult;
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.Q;
import com.soe.sharesoe.utils.T;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

public class SetChangePasswordActivity extends RxBaseActivity {


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
    @BindView(R.id.activity_password_set_change_auto_password)
    AutoCheckEditText mActivityPasswordSetChangeAutoPassword;
    @BindView(R.id.activity_password_set_change_auto_check)
    AutoCheckEditText mActivityPasswordSetChangeAutoCheck;
    @BindView(R.id.activity_password_set_change_tv_login)
    TextView mActivityPasswordSetChangeTvLogin;
    private String mTrim;
    private String mString;
    private String mPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_change_password;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mPhone = (String) getIntent().getExtras().get("phone");

        mBarTitle.setVisibility(View.VISIBLE);
        mBarTitle.setText("修改密码");
        initEditListener();
    }

    private void initEditListener() {
//        密码确认
        mActivityPasswordSetChangeAutoCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mString = s.toString().trim();
                int length = mString.length();
                if (length < 6 || length > 6) {

                    mActivityPasswordSetChangeTvLogin.setEnabled(false);
                    mActivityPasswordSetChangeTvLogin.setBackground(getDrawable(R.drawable.shape_no_login));
                    mActivityPasswordSetChangeTvLogin.setTextColor(getResources().getColor(R.color.white));

                } else {

                    mActivityPasswordSetChangeTvLogin.setEnabled(true);
                    mActivityPasswordSetChangeTvLogin.setBackground(getDrawable(R.drawable.shape_login));
                    mActivityPasswordSetChangeTvLogin.setTextColor(getResources().getColor(R.color.white));

                }


            }
        });
//        输入新密码

        mActivityPasswordSetChangeAutoPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTrim = s.toString().trim();
                int length = mTrim.length();
                if (length < 6 || length > 6) {

                    mActivityPasswordSetChangeTvLogin.setEnabled(false);
                    mActivityPasswordSetChangeTvLogin.setBackground(getDrawable(R.drawable.shape_no_login));
                    mActivityPasswordSetChangeTvLogin.setTextColor(getResources().getColor(R.color.white));

                } else {

                    mActivityPasswordSetChangeTvLogin.setEnabled(true);
                    mActivityPasswordSetChangeTvLogin.setBackground(getDrawable(R.drawable.shape_login));
                    mActivityPasswordSetChangeTvLogin.setTextColor(getResources().getColor(R.color.white));

                }
            }
        });


    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.bar_left_img, R.id.activity_password_set_change_tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                finish();
                break;

            case R.id.activity_password_set_change_tv_login:

                if (Q.isEmpty(mTrim)) {
                    if (mTrim.length() < 6 || mTrim.length() > 6) {
                        T.success("新密码格式有误");
                    }
                    return;

                }
                if (Q.isEmpty(mString)) {

                    if (mString.length() < 6 || mString.length() > 6) {
                        T.success("确认密码格式有误");
                    }
                    return;
                }

                if (mString.equals(mTrim)) {

                } else {
                    T.success("密码输入不一致");
                    return;
                }

                getData();
                break;
        }
    }

    public void getData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mPhone);
        map.put("pwd", mTrim);
        map.put("confirmPwd", mString);
        RetrofitHelper.getInstance().getUserCenterVerityCodeConfirm(new Subscriber<HttpBaseResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpBaseResult httpBaseResult) {
                if (httpBaseResult.getCode() == 1000) {
                    startActivity(new Intent(SetChangePasswordActivity.this, MemberActivity.class));
                    finish();
                } else {
                    T.success(httpBaseResult.getMsg());
                }


            }
        }, map);

    }
}
