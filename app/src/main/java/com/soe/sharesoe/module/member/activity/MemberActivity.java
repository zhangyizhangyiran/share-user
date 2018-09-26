package com.soe.sharesoe.module.member.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.manager.ServiceDataManager;
import com.soe.sharesoe.manager.UserDataManager;
import com.soe.sharesoe.module.member.bean.LoginModel;
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;
import com.soe.sharesoe.module.member.edittext.RegexUtils;
import com.soe.sharesoe.module.member.edittext.StringUtils;
import com.soe.sharesoe.module.nearby.map.MyLocationClient;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.CommonUtil;
import com.soe.sharesoe.utils.NetUtil;
import com.soe.sharesoe.utils.S;
import com.soe.sharesoe.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 登录页面
 * @encoding UTF-8
 * @date 17/10/21
 * @time 下午3:35
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class MemberActivity extends RxBaseActivity {


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
    @BindView(R.id.activity_member_auto_phone)
    AutoCheckEditText mActivityMemberAutoPhone;
    @BindView(R.id.activity_member_textinp_phone)
    TextInputLayout mActivityMemberTextinpPhone;
    @BindView(R.id.activity_member_auto_passworld)
    AutoCheckEditText mActivityMemberAutoPassworld;
    @BindView(R.id.activity_member_textinp_passworld)
    TextInputLayout mActivityMemberTextinpPassworld;
    @BindView(R.id.activity_member_tv_lead)
    TextView mActivityMemberTvLead;
    @BindView(R.id.activity_member_tv_login)
    TextView mActivityMemberTvLogin;
    @BindView(R.id.activity_member_tv_register)
    TextView mActivityMemberTvRegister;
    @BindView(R.id.activity_member_tv_forget)
    TextView mActivityMemberTvForget;
    @BindView(R.id.bar_view)
    View mBarView;
    @BindView(R.id.activity_member_imag_lead)
    ImageView mActivityMemberImagLead;
    private boolean mMobileExact = false;
    private boolean mEmpty = true;
    private String mTrim;
    private String mPassworld;
    private double mLongitude;
    private double mLatitude;
    private String mCity;
    private String mCountry;
    private int mVersionCode;
    private String mMAC;
    private String mVersion;
    private boolean isSelecte = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        MyLocationClient myLocationClient = new MyLocationClient(this);

        mBarTitle.setVisibility(View.INVISIBLE);
        mBarView.setVisibility(View.INVISIBLE);

        //初始化基本信息
        initBasicInformation();
        //EditText监听
        initEditTextListener();
        mBarLeftImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_close));


    }

    private void initBasicInformation() {

        // 版本号
        mVersionCode = NetUtil.getVersionCode(this);
//        获取mac地址
        mMAC = NetUtil.getAdresseMAC(this);

//        设备的系统版本
        mVersion = Build.VERSION.RELEASE;

    }

    @Subscribe
    public void onEvent(BDLocation bdLocation) {
        //经度
        mLongitude = bdLocation.getLongitude();
        //纬度
        mLatitude = bdLocation.getLatitude();
        mCity = bdLocation.getCity();
        mCountry = bdLocation.getCountry();

    }

    @Override
    public void onClick(View view) {

    }

    private void initEditTextListener() {

        // 手机号输入监听
        mActivityMemberAutoPhone.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTrim = s.toString().trim();
                //判断手机号是否正确来显示按钮状态
                mMobileExact = RegexUtils.isMobileExact(mTrim);
                if (mMobileExact == false || mEmpty == true) {
                    //按钮不可点击
                    Drawable drawable = getResources().getDrawable(R.drawable.shape_no_login);
                    mActivityMemberTvLogin.setBackground(drawable);
                    mActivityMemberTvLogin.setTextColor(getResources().getColor(R.color.Color_Theme_Text_Hint));
                    mActivityMemberTvLogin.setEnabled(false);

                } else if (mMobileExact == true && mEmpty == false) {
                    //按钮可点击
                    mActivityMemberTvLogin.setEnabled(true);
                    Drawable drawable = getResources().getDrawable(R.drawable.shape_login);
                    mActivityMemberTvLogin.setBackground(drawable);
                    mActivityMemberTvLogin.setTextColor(getResources().getColor(R.color.white));

                }

            }
        });
        //密码监听
        mActivityMemberAutoPassworld.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPassworld = s.toString().trim();
                mEmpty = StringUtils.isEmpty(mPassworld);
                if (mEmpty == true || mMobileExact == false) {
                    //按钮不可点击
                    Drawable drawable = getResources().getDrawable(R.drawable.shape_no_login);
                    mActivityMemberTvLogin.setTextColor(getResources().getColor(R.color.Color_Theme_White));
                    mActivityMemberTvLogin.setBackground(drawable);
                    mActivityMemberTvLogin.setEnabled(false);

                } else if (mEmpty == false && mMobileExact == true) {
                    //按钮可点击
                    mActivityMemberTvLogin.setEnabled(true);
                    Drawable drawable = getResources().getDrawable(R.drawable.shape_login);
                    mActivityMemberTvLogin.setBackground(drawable);
                    mActivityMemberTvLogin.setTextColor(getResources().getColor(R.color.Color_Theme_White));
                }


            }
        });
    }


    @OnClick({R.id.bar_left_img, R.id.activity_member_tv_lead, R.id.activity_member_tv_login, R.id.activity_member_tv_register, R.id.activity_member_tv_forget, R.id.activity_member_imag_lead})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_member_imag_lead:
                isSelecte = !isSelecte;
                if (isSelecte) {
                    mActivityMemberImagLead.setImageResource(R.mipmap.icon_radio_selected);
                    mActivityMemberTvLogin.setEnabled(true);


                } else {
                    mActivityMemberImagLead.setImageResource(R.mipmap.icon_radio_normal);
                    mActivityMemberTvLogin.setEnabled(false);
                }
                break;
            case R.id.bar_left_img:
                this.finish();
                break;
            case R.id.activity_member_tv_lead:
                break;
            case R.id.activity_member_tv_login:
                if (isSelecte) {
                    initData();
                } else {
                    T.success("请阅读并勾选同意按钮 ");
                }

                break;
            case R.id.activity_member_tv_register:
                Toast.makeText(this, "注册", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, RegisterPhoneActivity.class));
                finish();
                break;
            case R.id.activity_member_tv_forget:
                Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ChangePasswordActivity.class));
                finish();
                break;
        }
    }

    private void initData() {


        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", CommonUtil.getIdentity(this));
        map.put("phone", mTrim);
        map.put("pwd", mPassworld);
        map.put("code", mTrim);
        map.put("longitude", mLongitude);
        map.put("latitude", mLatitude);
        map.put("city", mCity);
        map.put("county", mCountry);
        map.put("clientSystem", "Android");
        map.put("clientVersion", mVersionCode);
        map.put("clientMac", mMAC);

        getData(map);
    }

    private void getData(HashMap<String, Object> map) {

        RetrofitHelper.getInstance().getLogin(new Subscriber<LoginModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginModel httpBaseResult) {

                if (httpBaseResult.getCode().equals("1000")) {

//                    保存是否登录,uid与实名状态
                    setLoginUid(httpBaseResult);
                    finish();

                } else {

                    T.success(httpBaseResult.getMsg());

                }


            }
        }, map);
    }

    private void setLoginUid(LoginModel httpBaseResult) {

//        用户登录状态
        S.put(MemberActivity.this, "isLogin", "true");
        UserDataManager.setUid(httpBaseResult.getResult().getUid());
//        token保存
        ServiceDataManager.getAccountMyWallet();
        UserDataManager.setToken(httpBaseResult.getParams().getToken());

        boolean certify = httpBaseResult.getResult().isCertify();
        if (certify) {
            UserDataManager.setCertify("true");
        } else {
            UserDataManager.setCertify("false");
        }

//        保存用户手机号
        UserDataManager.setPhone(mTrim);

    }

}
