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
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;
import com.soe.sharesoe.module.member.edittext.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 个人中心修改名称等界面
 * @encoding UTF-8
 * @date 17/11/8
 * @time 下午6:06
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class MyCenterEditActivity extends RxBaseActivity implements TextWatcher {


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
    @BindView(R.id.activity_my_center_auto_name)
    AutoCheckEditText mActivityMyCenterAutoName;
    @BindView(R.id.activity_my_center_TextInputLayout_name)
    TextInputLayout mActivityMyCenterTextInputLayoutName;

    private String mValue;


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_center_edit;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {

        String type = getIntent().getStringExtra("type");

        if ("one".endsWith(type)) {
            mBarTitle.setText("修改昵称");
            mActivityMyCenterTextInputLayoutName.setHint("修改昵称");

        } else if ("two".endsWith(type)) {
            mBarTitle.setText("修改名称");
            mActivityMyCenterTextInputLayoutName.setHint("修改名称");

        }
        mBarRight.setVisibility(View.VISIBLE);
        mBarRight.setText("保存");
        mBarRight.setTextColor(getResources().getColor(R.color.Color_Theme_Main_Green));


        mActivityMyCenterAutoName.addTextChangedListener(this);


    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.bar_left_img, R.id.bar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
            case R.id.bar_right:
                boolean empty = StringUtils.isEmpty(mValue);
                if (empty) {

                    finish();
                } else {
                    setForResult();
                }


                break;
        }
    }

    private void setForResult() {
        Intent intent = new Intent();
        intent.putExtra("name", mValue);
        this.setResult(100, intent);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mValue = s.toString().toString();


    }


}
