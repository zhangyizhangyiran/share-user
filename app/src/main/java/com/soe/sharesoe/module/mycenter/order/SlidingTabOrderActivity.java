package com.soe.sharesoe.module.mycenter.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 订单列表
 * @encoding UTF-8
 * @date 2017/11/20
 * @time 下午5:29
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class SlidingTabOrderActivity extends RxBaseActivity {


    @BindView(R.id.bar_title)
    TextView barTitle;

    @BindView(R.id.layout_tab)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private String[] titles = new String[]{"全部", "待付押金", "使用中", "已退还"};
    private int[] orders = new int[]{1, 2, 3, 4};

    @Override
    public int getLayoutId() {
        return R.layout.activity_sliding_tab;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的订单");

        Intent intent = getIntent();
        final int pos = intent.getExtras().getInt("position", 0);

        OrderListPagerAdapter orderListPagerAdapter = new OrderListPagerAdapter(getSupportFragmentManager(), titles, orders);
        mViewPager.setAdapter(orderListPagerAdapter);

        mViewPager.setOffscreenPageLimit(orders.length);
        mViewPager.setCurrentItem(pos);

        tabLayout.setupWithViewPager(mViewPager);
        //设置是固定的，还可以设置为TabLayout.MODE_SCROLLABLE,
        //可滚动的，用于多个Tab
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    @OnClick({R.id.bar_left_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
        }
    }

    private  class OrderListPagerAdapter extends FragmentPagerAdapter {
        private String[] titles;
        private int[] orders;

        OrderListPagerAdapter(FragmentManager fm, String[] titles, int[] orders) {
            super(fm);
            this.titles = titles;
            this.orders = orders;
        }

        @Override
        public Fragment getItem(int position) {
            return OrderListFragment.newInstance(orders[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return orders.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
          //  super.destroyItem(container, position, object);
        }


    }

}

