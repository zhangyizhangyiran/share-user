package com.soe.sharesoe.module.nearby.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.module.nearby.map.MyLocation;
import com.soe.sharesoe.module.nearby.search.city.CityName;
import com.soe.sharesoe.module.nearby.search.city.MeituanHeaderBean;
import com.soe.sharesoe.module.nearby.search.city.SearchCityFragment;
import com.soe.sharesoe.module.nearby.search.nearby.SearchNearbyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/31
 * @time 下午4:54
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class SearchNearbyAndCityActivity extends RxBaseActivity {

    @BindView(R.id.bar_left_img)
    ImageView bar_left_img;
    @BindView(R.id.bar_title)
    TextView bar_title;

    @BindView(R.id.search_tv_city_name)
    TextView search_tv_city_name;
    @BindView(R.id.search_img_city_arrow)
    ImageView search_img_city_arrow;
    @BindView(R.id.search_tv_city_add)
    TextView search_tv_city_add;
    @BindView(R.id.search_ll_select_city)
    LinearLayout search_ll_select_city;


    boolean isCity = false;
    SearchCityFragment searchCityFragment;
    SearchNearbyFragment searchNearbyFragment;

    String CityName = "北京";

    public static Activity activity;

    @Override
    public int getLayoutId() {
        activity = this;
        return R.layout.activity_search_nearby_city;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        bar_title.setText("定位地址");
        EventBus.getDefault().register(this);

        MyLocation myLocation = (MyLocation)getIntent().getExtras().getSerializable("MyLocation");
        Bundle bundle = new Bundle();
        bundle.putSerializable("MyLocation", (MyLocation)myLocation);

        CityName = myLocation.getCity();
        search_tv_city_name.setText(CityName);


        searchCityFragment = new SearchCityFragment();
        searchNearbyFragment = new SearchNearbyFragment();
        searchCityFragment.setArguments(bundle);
        searchNearbyFragment.setArguments(bundle);
        showFragment(searchCityFragment, searchNearbyFragment, 0);

    }

    //定位回调
    @Subscribe
    public void onEvent(BDLocation bdLocation) {
        CityName = bdLocation.getCity();
        search_tv_city_name.setText(CityName);
    }
    //选择城市回调
    @Subscribe
    public void onEvent(CityName myLocation) {
        CityName = myLocation.CityName;
        search_tv_city_name.setText(CityName);
    }

    @OnClick({R.id.bar_left_img, R.id.search_ll_select_city, R.id.search_tv_city_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
            case R.id.search_ll_select_city:
                if (isCity) {
                    isCity = false;
                    showFragment(searchCityFragment, searchNearbyFragment, 0);

                } else {
                    isCity = true;
                    showFragment(searchNearbyFragment, searchCityFragment, 1);
                }

                break;
            case R.id.search_tv_city_add:
                startActivity(new Intent(this, SearchAddressActivity.class).putExtra("CityName", CityName));
                break;
            default:
                break;
        }
    }

    private String[] TAGS = new String[]{"tag1", "tag2"};

    private void showFragment(Fragment from, Fragment to, int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        boolean isAdded = to.isAdded();
        if (!isAdded) {
            transaction.hide(from).add(R.id.act_fm_search_nearbycity, to, TAGS[position]).show(to).commitAllowingStateLoss();
        } else {
            transaction.hide(from).show(to).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
