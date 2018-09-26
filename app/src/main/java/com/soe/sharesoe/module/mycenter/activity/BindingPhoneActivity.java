package com.soe.sharesoe.module.mycenter.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.manager.UserDataManager;
import com.soe.sharesoe.module.member.CountDownButtonHelper;
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;
import com.soe.sharesoe.module.member.edittext.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 换绑手机
 * @encoding UTF-8
 * @date 17/11/6
 * @time 下午4:03
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class BindingPhoneActivity extends RxBaseActivity {


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
    @BindView(R.id.activity_binding_tv_phone_code)
    TextView mActivityBindingTvPhoneCode;
    @BindView(R.id.autoCheckEditText)
    AutoCheckEditText mAutoCheckEditText;
    @BindView(R.id.activity_binding_textinp)
    TextInputLayout mActivityBindingTextinp;
    @BindView(R.id.activity_binding_tv_captcha)
    TextView mActivityBindingTvCaptcha;
    @BindView(R.id.activity_binding_tv_login)
    TextView mActivityBindingTvLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_binding_phone;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("换绑手机");
//        用户手机号
        String phone = UserDataManager.getPhone();
        mActivityBindingTvPhoneCode.setText(phone);
//        初始化时间
        initTime();
//        设置获取验证码监听
        initListenter();

    }

    @Override
    public void onClick(View view) {

    }

    private void initTime() {
        CountDownButtonHelper countDownButtonHelper = new CountDownButtonHelper(mActivityBindingTvCaptcha, "后重试", 60, 1);
        countDownButtonHelper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            @Override
            public void finish() {

            }
        });
    }

    private void initListenter() {
        mAutoCheckEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                验证码上传验证
                String trim = s.toString().trim();
                boolean empty = StringUtils.isEmpty(trim);
                if (empty) {

                } else {

                }


            }
        });
    }


    @OnClick({R.id.activity_binding_tv_captcha, R.id.activity_binding_tv_login, R.id.bar_left_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                this.finish();
                break;
//            获取验证码
            case R.id.activity_binding_tv_captcha:
                Toast.makeText(this, "获取验证码", Toast.LENGTH_SHORT).show();
                break;
//            下一步
            case R.id.activity_binding_tv_login:
                break;
        }
    }
}
