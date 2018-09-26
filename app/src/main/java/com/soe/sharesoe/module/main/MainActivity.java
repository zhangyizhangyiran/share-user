package com.soe.sharesoe.module.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.coolqi.lib.sqscaner.utils.QrUtils;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.App;
import com.soe.sharesoe.base.PermissionCompat;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.ProductReserveModel;
import com.soe.sharesoe.module.home.HomeFragment;
import com.soe.sharesoe.module.home.message.MessageActivity;
import com.soe.sharesoe.module.mycenter.MeFragment;
import com.soe.sharesoe.module.mycenter.activity.AutonymActivity;
import com.soe.sharesoe.module.mycenter.order.PayOrderActivity;
import com.soe.sharesoe.module.mycenter.reserve.ProductReserveActivity;
import com.soe.sharesoe.module.nearby.map.MyLocationClient;
import com.soe.sharesoe.module.nearby.map.NearbyFragment;
import com.soe.sharesoe.module.qrcode.AppQrCodeActivity;
import com.soe.sharesoe.module.sort.SortFragment;
import com.soe.sharesoe.utils.CommonUtil;
import com.soe.sharesoe.utils.S;
import com.soe.sharesoe.utils.T;
import com.soe.sharesoe.widget.dialog.PromptDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity implements BadgeDismissListener, OnTabSelectListener {

    public static final int APP_CORRECT_CODE = 2019;

    @Titles
    private static final String[] mTitles = {"首页", "附近", "分类", "我的"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.mipmap.icon_home, R.mipmap.icon_near, R.mipmap.icon_classift, R.mipmap.icon_user};

    @NorIcons
    private static final int[] mNormalIcons = {R.mipmap.icon_home_normal, R.mipmap.icon_near_normal, R.mipmap.icon_classift_normal, R.mipmap.icon_user_normal};

    private List<Fragment> list = new ArrayList<>();

    @BindView(R.id.view_pager)
    NoScrollViewPager mPager;

    @BindView(R.id.tabbar)
    JPTabBar mTabbar;

    HomeFragment mTabHome;
    NearbyFragment mTabNeaarby;
    SortFragment mTabSort;
    MeFragment mTabMe;

    PermissionCompat permissionCompat;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        permissionCompat = new PermissionCompat(this);

        mTabbar = (JPTabBar) findViewById(R.id.tabbar);
        mPager = (NoScrollViewPager) findViewById(R.id.view_pager);
//        mTabbar.setTitles("qwe","asd","qwe","asdsa").setNormalIcons(R.mipmap.tab1_normal,R.mipmap.tab2_normal,R.mipmap.tab3_normal,R.mipmap.tab4_normal)
//                .setSelectedIcons(R.mipmap.tab1_selected,R.mipmap.tab2_selected,R.mipmap.tab3_selected,R.mipmap.tab4_selected).generate();

        mTabHome = new HomeFragment();
        mTabNeaarby = new NearbyFragment();
        mTabSort = new SortFragment();
        mTabMe = new MeFragment();

        mTabbar.setTabListener(this);

        list.add(mTabHome);
        list.add(mTabNeaarby);
        list.add(mTabSort);
        list.add(mTabMe);
        mPager.setAdapter(new MainAdapter(getSupportFragmentManager(), list));
        mPager.setNoScroll(true);
        mTabbar.setContainer(mPager);
        mTabbar.setDismissListener(this);
        mPager.setOffscreenPageLimit(4);
        //显示圆点模式的徽章
        //设置容器
        mTabbar.hideBadge(0);
        mTabbar.setUseFilter(true);
        mTabbar.setUseScrollAnimate(false);
//        mTabbar.setAnimation(AnimationType.FLIP);


        //设置Badge消失的代理
        mTabbar.setTabListener(this);
        mTabbar.setUseScrollAnimate(true);
        mTabbar.getMiddleView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLogin()) {

                    if (!"true".equals(S.get(MainActivity.this, "attestation", ""))){
                        PromptDialog.showDialog(MainActivity.this, "还没有进行实名认证呦！", new PromptDialog.ReserveClickListenr() {
                            @Override
                            public void onClick() {
                                startActivityForResult(new Intent(MainActivity.this, AutonymActivity.class), 300);

                            }
                        });
                        return;
                    }


                    if (permissionCompat.Requestpermission(Manifest.permission.CAMERA, 99, "需要获取相机权限")) {
                        if (!CommonUtil.isLocationEnabled(MainActivity.this)) {
                            CommonUtil.doNetWorkSettingDialog(MainActivity.this, 1001);
                            return;
                        }
                        AppQrCodeActivity.start(MainActivity.this, "请扫码正确二维码", APP_CORRECT_CODE);
                    }
                }

            }
        });
        //参数说明：要请求的权限数组，请求标记码（requestCode）,用户拒绝权限提示内容
        String[] perms = {
                Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.ACCESS_FINE_LOCATION};
        if (permissionCompat.Requestpermission(perms, 102, "需要获取手机状态、位置权限")) {
            MyLocationClient myLocationClient = new MyLocationClient(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE) {
            String[] perms = {
                    Manifest.permission.READ_PHONE_STATE
                    , Manifest.permission.ACCESS_FINE_LOCATION};
            permissionCompat.Requestpermission(perms, 102, "需要获取手机状态、位置权限");
        }

        if (requestCode == 1001) {
            if (!CommonUtil.isLocationEnabled(MainActivity.this)) {
                CommonUtil.doNetWorkSettingDialog(MainActivity.this, 1001);
                return;
            }
            AppQrCodeActivity.start(MainActivity.this, "请扫码正确二维码", APP_CORRECT_CODE);
        }
        if (data == null) {
            return;
        }
        String qrCode = data.getStringExtra(QrUtils.RESULT_STRING);
        //Toast.makeText(this, "qrCode=" + qrCode, Toast.LENGTH_SHORT).show();
        if (!checkoutCodeUrl(qrCode)) {
            Toast.makeText(this, "请扫码共享商品二维码", Toast.LENGTH_SHORT).show();
            return;
        }
        String pid = getPid(qrCode);
        String type = getType(qrCode);
        if (TextUtils.isEmpty(qrCode)) {
            return;
        }
        if (requestCode == APP_CORRECT_CODE) {
            if (pid.contains("\t")) {
                Toast.makeText(this, "正确的二维码应不包含\\t", Toast.LENGTH_SHORT).show();
                return;
            }

            startActivity(new Intent(MainActivity.this, PayOrderActivity.class).putExtra("pid", pid));
        }

    }

    private boolean checkoutCodeUrl(String url) {
        if (!url.contains("pid=") && !url.contains("type=") && !url.contains("www.sharesoe")) {
            return false;
        }
        if (!url.contains("pid=") || !url.contains("type=")) {
            return false;
        }
        return true;
    }

    private String getPid(String url) {
        String pid = Uri.parse(url).getQueryParameter("pid");

        return pid;
    }

    private String getType(String url) {
        String type = Uri.parse(url).getQueryParameter("type");

        return type;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //检测是否有未通过的权限
        for (int i : grantResults) {
            if (i != PackageManager.PERMISSION_GRANTED) { //有权限未通过
                permissionCompat.popAlterDialog();
                return;
            }
        }

        switch (requestCode) {
            case 99:

                AppQrCodeActivity.start(MainActivity.this, "请扫码正确二维码", APP_CORRECT_CODE);
                break;
            case 102:

                MyLocationClient myLocationClient = new MyLocationClient(this);
                break;

        }
    }


    //红点点击回调
    @Override
    public void onClick(View view) {

    }

    //红点消失回调
    @Override
    public void onDismiss(int position) {
        if (position == 0) {
            //mTabHome.clearCount();
        }
    }

    //选中回调
    @Override
    public void onTabSelect(int index) {

    }

    //获取TabBar
    public JPTabBar getTabbar() {
        return mTabbar;
    }

    //切换页面回调
    @Subscribe
    public void onEvent(OnChangeTabListener onChangeTabListener) {
        mTabbar.setSelectTab(onChangeTabListener.getIndex());
        mPager.setCurrentItem(onChangeTabListener.getIndex());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
