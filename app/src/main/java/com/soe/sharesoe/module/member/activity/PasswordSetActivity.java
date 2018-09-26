package com.soe.sharesoe.module.member.activity;

import android.os.Build;
import android.os.Bundle;
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
import com.soe.sharesoe.module.member.bean.RegisterChechMobile;
import com.soe.sharesoe.module.member.edittext.AutoCheckEditText;
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

import static com.soe.sharesoe.R.id.bar_title;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project study1
 * @Description 密码设置
 * @encoding UTF-8
 * @date 17/11/8
 * @time 下午3:47
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class PasswordSetActivity extends RxBaseActivity {


    @BindView(R.id.bar_left_img)
    ImageView mBarLeftImg;
    @BindView(R.id.bar_left)
    TextView mBarLeft;
    @BindView(R.id.bar_center_img)
    ImageView mBarCenterImg;
    @BindView(bar_title)
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
    @BindView(R.id.acticity_password_set_auto)
    AutoCheckEditText mActicityPasswordSetAuto;
    @BindView(R.id.activity_password_set_tv_login)
    TextView mActivityPasswordSetTvLogin;
    private boolean mEmpty;
    private String mTrim;
    private String mPhone;
    private MyLocationClient mMyLocationClient;
    private String mCity;
    private String mCountry;
    private double mLatitude;
    private double mLongitude;
    private String mIpAddress;
    private int mVersionCode;
    private String mMAC;
    private String mVersion;
    private String mProvince;

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_set;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {


        mBarTitle.setText("设置登录密码");

//        初始化基本信息
        initBasicInformation();

//        百度地图获取定位信息初始化
        mMyLocationClient = new MyLocationClient(this);
        EventBus.getDefault().register(this);

//        手机号
        mPhone = (String) getIntent().getExtras().get("phone");

//        初始化Edie监听
        initEditListener();

    }


    public void initBasicInformation() {

//        版本号
        mVersionCode = NetUtil.getVersionCode(this);
//        获取mac地址
        mMAC = NetUtil.getAdresseMAC(this);

//        设备的系统版本
        mVersion = Build.VERSION.RELEASE;

    }


    private void initEditListener() {


        mActicityPasswordSetAuto.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTrim = s.toString().trim();
                mEmpty = StringUtils.isEmpty(mTrim);
                if (mEmpty) {
                    mActivityPasswordSetTvLogin.setEnabled(false);
                } else {

                    mActivityPasswordSetTvLogin.setEnabled(true);
                    mActivityPasswordSetTvLogin.setBackground(getDrawable(R.drawable.shape_login));
                    mActivityPasswordSetTvLogin.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });

    }


    @OnClick({R.id.bar_left_img, R.id.activity_password_set_tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                this.finish();
                break;
            case R.id.activity_password_set_tv_login:
                initData();
                break;
        }
    }

    //定位回调
    @Subscribe
    public void onEvent(BDLocation bdLocation) {

        mCity = bdLocation.getCity();
        mCountry = bdLocation.getCountry();
        mLatitude = bdLocation.getLatitude();
        mLongitude = bdLocation.getLongitude();
        mProvince = bdLocation.getProvince();

    }

    private void initData() {


        HashMap<String, String> map = new HashMap<>();
        map.put("uuid", CommonUtil.getIdentity(this));
        map.put("phone", mPhone);
        map.put("pwd", mTrim);
        map.put("code", mTrim);
        map.put("longitude", String.valueOf(mLongitude));
        map.put("latitude", String.valueOf(mLatitude));
        map.put("county", mCountry);
        map.put("city", mCity);
        map.put("province", mProvince);
        map.put("clientSystem", mVersion);
        map.put("appVersion", mVersion);
        map.put("clientVersion", String.valueOf(mVersionCode));
        map.put("clientMac", mMAC);

        setData(map);
    }


    private void setData(HashMap<String, String> map) {
        RetrofitHelper.getInstance().getRegisteArffirm(new Subscriber<RegisterChechMobile>() {
            @Override
            public void onCompleted() {
                Toast.makeText(PasswordSetActivity.this, "onCompleted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onNext(RegisterChechMobile registerChechMobile) {

                String code = registerChechMobile.getCode();

                if (code.equals("1000")) {

//                    保存登录状态与UId
                    setSkilActivity(registerChechMobile);
                    ServiceDataManager.getAccountMyWallet();

                } else {

                    T.success(registerChechMobile.getMsg());
                }


            }
        }, map);

    }


    private void setSkilActivity(RegisterChechMobile registerChechMobile) {

        RegisterChechMobile.ParamsBean params = registerChechMobile.getParams();
        UserDataManager.setToken(params.getToken());
        S.put(PasswordSetActivity.this, "isLogin", "true");
//        保存用户手机号
        UserDataManager.setPhone(mPhone);

        finish();
    }


    @Override
    public void onClick(View view) {

    }

}
