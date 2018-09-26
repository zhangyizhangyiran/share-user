package com.soe.sharesoe.module.mycenter.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 忘记密码
 * @encoding UTF-8
 * @date 17/11/7
 * @time 下午5:32
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class ForgetPasswordActivity extends RxBaseActivity {


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
    @BindView(R.id.activity_forget_inpt_phone)
    AutoCheckEditText mActivityForgetInptPhone;
    @BindView(R.id.activity_forget_inpt_password)
    TextInputLayout mActivityForgetInptPassword;
    @BindView(R.id.activity_forget_tv_get_code)
    TextView mActivityForgetTvGetCode;
    @BindView(R.id.activity_forget_tv_next)
    TextView mActivityForgetTvNext;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("修改密码");

    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.activity_forget_tv_get_code, R.id.activity_forget_tv_next,R.id.bar_left_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                this.finish();
                break;
            case R.id.activity_forget_tv_get_code:
                Toast.makeText(this, "i", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_forget_tv_next:
                break;
        }
    }
}
