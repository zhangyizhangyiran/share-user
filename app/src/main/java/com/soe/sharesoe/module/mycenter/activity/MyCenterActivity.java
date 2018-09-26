package com.soe.sharesoe.module.mycenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.module.member.edittext.StringUtils;
import com.soe.sharesoe.module.mycenter.bean.UserInfoModel;
import com.soe.sharesoe.module.mycenter.dialog.DialogChooseDate;
import com.soe.sharesoe.module.mycenter.dialog.MyCenterDialog;
import com.soe.sharesoe.module.mycenter.view.CircleImageView;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.S;
import com.soe.sharesoe.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 个人中心
 * @encoding UTF-8
 * @date 17/10/24
 * @time 下午2:02
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pr>
 */


public class MyCenterActivity extends RxBaseActivity {

    @BindView(R.id.activity_mycenter_img_head)
    CircleImageView mActivityMycenterImgHead;
    @BindView(R.id.activity_mycenter_lin_head)
    LinearLayout mActivityMycenterLinHead;
    @BindView(R.id.activity_mycenter_lin_nickname)
    LinearLayout mActivityMycenterLinNickname;
    @BindView(R.id.activity_mycenter_lin_name)
    LinearLayout mActivityMycenterLinName;
    @BindView(R.id.activity_mycenter_lin_sex)
    LinearLayout mActivityMycenterLinSex;
    @BindView(R.id.activity_mycenter_lin_birthday)
    LinearLayout mActivityMycenterLinBirthday;
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
    @BindView(R.id.activity_mycenter_lin_attestation)
    LinearLayout mActivityMycenterLinAttestation;
    @BindView(R.id.activity_my_center_monicker)
    TextView mActivityMyCenterMonicker;
    @BindView(R.id.activity_my_center_name)
    TextView mActivityMyCenterName;
    @BindView(R.id.activity_my_center_gender)
    TextView mActivityMyCenterGender;
    @BindView(R.id.activity_mycenter_tv_birthday)
    TextView activity_mycenter_tv_birthday;
    @BindView(R.id.activity_mycenter_tv_attestation)
    TextView mActivityMycenterTvAttestation;
    @BindView(R.id.activity_mycenter_imag_icon)
    ImageView mActivityMycenterImagIcon;
    private Intent mIntent;

    private boolean mEmpty = true;


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_center;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        mBarTitle.setText("个人中心");
        mBarRightSerchImg.setVisibility(View.INVISIBLE);
        mBarRightMsgImg.setVisibility(View.INVISIBLE);
        mIntent = new Intent(this, MyCenterEditActivity.class);

    }

    @OnClick({R.id.bar_left_img, R.id.activity_mycenter_lin_head, R.id.activity_mycenter_lin_nickname, R.id.activity_mycenter_lin_name, R.id.activity_mycenter_lin_sex, R.id.activity_mycenter_lin_birthday, R.id.activity_mycenter_lin_attestation})
    public void onClick(View view) {
        switch (view.getId()) {
            //            返回
            case R.id.bar_left_img:
                this.finish();
                break;
            //            头像
            case R.id.activity_mycenter_lin_head:


                if (mEmpty) {

                    new MyCenterDialog(this, "1", this).show();
                } else {
                    T.success("已注册不可更改");
                }


                break;
            //            昵称
            case R.id.activity_mycenter_lin_nickname:

                mIntent.putExtra("type", "one");


                if (mEmpty) {

                    startActivityForResult(mIntent, 200);
                } else {
                    T.success("已注册不可更改");
                }


                break;
            //            姓名
            case R.id.activity_mycenter_lin_name:
                mIntent.putExtra("type", "two");


                if (mEmpty) {

                    startActivityForResult(mIntent, 100);
                } else {
                    T.success("已注册不可更改");
                }


                break;
            //            性别
            case R.id.activity_mycenter_lin_sex:


                if (mEmpty) {

                    new MyCenterDialog(this, "2", this).show();
                } else {
                    T.success("已注册不可更改");
                }


                break;
            //            生日
            case R.id.activity_mycenter_lin_birthday:


                if (mEmpty) {

                    getPhoto();
                } else {
                    T.success("已注册不可更改");
                }


                break;
//            认证
            case R.id.activity_mycenter_lin_attestation:


                if (mEmpty) {

                    startActivityForResult(new Intent(this, AutonymActivity.class), 300);
                } else {
                    T.success("已注册不可更改");
                }


                break;
        }
    }

    private void getPhoto() {
        DialogChooseDate dialogChooseDate = new DialogChooseDate(this, 1, new DialogChooseDate.Dialogcallback() {
            @Override
            public void pickWeightResult(String sex) {

                activity_mycenter_tv_birthday.setText(sex);
            }
        });
        dialogChooseDate.show();
    }

    //    图片选择回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {

            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path : pathList) {
                Glide.with(this).load(new File(path)).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into(mActivityMycenterImgHead);
                S.put(this, "photo", path);
            }


        } else if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            String path = data.getStringExtra("result");
            S.put(this, "photo", path);
            Glide.with(this).load(new File(path)).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into(mActivityMycenterImgHead);

        } else if (requestCode == 100 && data != null) {

            String name = (String) data.getExtras().get("name");
            mActivityMyCenterName.setText(name);


        } else if (requestCode == 200 && data != null) {

            String name = (String) data.getExtras().get("name");
            mActivityMyCenterMonicker.setText(name);


        } else if (requestCode == 300 && data != null) {


            String code = (String) data.getExtras().get("code");
            if (code.equals("1")) {
                mActivityMycenterTvAttestation.setText("已认证");
            } else {
                mActivityMycenterTvAttestation.setText("未认证");
            }


        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String event) {

        if (event.equals("1")) {
            mActivityMyCenterGender.setText("男");
        } else if (event.equals("2")) {
            mActivityMyCenterGender.setText("女");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserInfo();
        setPathPhoto();


    }

    private void initUserInfo() {
        RetrofitHelper.getInstance().getUserInfo(new Subscriber<UserInfoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoModel userInfoModels) {

                setUserInfo(userInfoModels);

            }
        });
    }

    private void setUserInfo(UserInfoModel userInfoModels) {

        String userRealName = userInfoModels.getResult().getUserRealName();
        mEmpty = StringUtils.isEmpty(userRealName);
        if (mEmpty) {
            mActivityMycenterImagIcon.setVisibility(View.INVISIBLE);
            mActivityMycenterTvAttestation.setText("未认证");
        } else {
            mActivityMycenterImagIcon.setVisibility(View.VISIBLE);
            mActivityMycenterTvAttestation.setText("已认证");
        }

    }

    private void setPathPhoto() {

        String photo = (String) S.get(this, "photo", "");
        boolean empty = StringUtils.isEmpty(photo);
        if (empty) {

        } else {


            Glide.with(this).load(new File(photo)).error(R.mipmap.ic_default_logo).into(mActivityMycenterImgHead);
        }
    }

}
