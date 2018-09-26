package com.soe.sharesoe.module.mycenter.activity;

import android.content.Intent;
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
import com.soe.sharesoe.entity.HttpBaseResult;
import com.soe.sharesoe.manager.UserDataManager;
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;
import com.soe.sharesoe.module.member.edittext.StringUtils;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.S;
import com.soe.sharesoe.utils.T;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 实名认证页面
 * @encoding UTF-8
 * @date 17/10/25
 * @time 上午9:58
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class AutonymActivity extends RxBaseActivity {


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
    @BindView(R.id.activity_Autonym_phone_auto)
    AutoCheckEditText mActivityAutonymPhoneAuto;
    @BindView(R.id.activity_Autonym_phone_textinp)
    TextInputLayout mActivityAutonymPhoneTextinp;
    @BindView(R.id.activity_autonym_auto_identity)
    AutoCheckEditText mActivityAutonymAutoIdentity;
    @BindView(R.id.activity_autonym_tv_login)
    TextView mActivityAutonymTvLogin;
    private String mCode;
    private String mName;
    private Intent mIntent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_autonym;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        mIntent = getIntent();

        mBarTitle.setText("实名认证");

        initListener();

    }

    private void initListener() {
//        身份证号
        mActivityAutonymAutoIdentity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCode = s.toString().trim();
                boolean empty = StringUtils.isEmpty(mCode);
                if (empty) {

                    mActivityAutonymTvLogin.setBackground(getDrawable(R.drawable.shape_no_login));
                    mActivityAutonymTvLogin.setTextColor(getResources().getColor(R.color.Color_Theme_Main_Background));

                } else {

                    mActivityAutonymTvLogin.setBackground(getDrawable(R.drawable.shape_login));
                    mActivityAutonymTvLogin.setTextColor(getResources().getColor(R.color.white));
                }


            }
        });

//        姓名
        mActivityAutonymPhoneAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mName = s.toString().trim();
                boolean empty = StringUtils.isEmpty(mName);
                if (empty) {

                    mActivityAutonymTvLogin.setBackground(getDrawable(R.drawable.shape_no_login));
                    mActivityAutonymTvLogin.setTextColor(getResources().getColor(R.color.Color_Theme_Main_Background));

                } else {

                    mActivityAutonymTvLogin.setBackground(getDrawable(R.drawable.shape_login));
                    mActivityAutonymTvLogin.setTextColor(getResources().getColor(R.color.white));
                }

            }
        });

    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.bar_left_img, R.id.activity_autonym_tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
            case R.id.activity_autonym_tv_login:

                boolean Code = StringUtils.isEmpty(mCode);
                boolean name = StringUtils.isEmpty(mName);

                if (!Code || !name) {

                    setData();
                } else {
                    T.success("信息不能为空");
                }


                break;
        }
    }

    private void setData() {

        String token = UserDataManager.getToken();
        HashMap<String, String> map = new HashMap<>();
        map.put("realName", mName);
        map.put("idCard", mCode);
        map.put("token", token);
        RetrofitHelper.getInstance().getUserCenterVerify(new Subscriber<HttpBaseResult>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onNext(HttpBaseResult httpBaseResult) {

                int code = httpBaseResult.getCode();
                if (code == 1000) {
                    T.success("认证成功");
                    mIntent.putExtra("code", "1");
                    setResult(100, mIntent);
//                    设置实名认证状态
                    setAttestation();
                    finish();

                } else if (code == -1) {

                    T.success("该信息已认证");

                } else {
                    String msg = httpBaseResult.getMsg();
                    T.success(msg);
                }


            }

        }, map);
    }

    private void setAttestation() {
        S.put(AutonymActivity.this, "attestation", "true");
    }
}
