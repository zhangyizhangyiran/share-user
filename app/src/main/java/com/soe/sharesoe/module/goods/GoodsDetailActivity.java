package com.soe.sharesoe.module.goods;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailActivity extends RxBaseActivity {


    @BindView(R.id.goods_fragment)
    FrameLayout fragment;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right_img)
    ImageView barRightImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        barTitle.setText("商品详情");
        barRightImg.setImageResource(R.mipmap.icon_share);
        barRightImg.setVisibility(View.VISIBLE);

        FragmentManager fm = this.getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.goods_fragment, new GoodsDetailFragment());

        // addToBackStack添加到回退栈,addToBackStack与ft.add(R.id.fragment, new
        // MyFragment())效果相当
        // ft.addToBackStack("test");

        ft.commit();
    }

    @OnClick({R.id.bar_left_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
        }
    }


}
