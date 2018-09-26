package com.soe.sharesoe.module.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.widget.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;



public class GoodsDetailImageActivity extends RxBaseActivity {


    @BindView(R.id.hacky_view_pager)
    HackyViewPager hackyViewPager;

    @BindView(R.id.goods_img_num)
    TextView goodsImgNum;


    private List<String> sList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_image;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {


        Intent intent = getIntent();
        final int pos = intent.getExtras().getInt("pos");

        sList = intent.getStringArrayListExtra("list");

        goodsImgNum.setText(pos+1+"/"+sList.size());
        hackyViewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return sList.size();
            }

            @Override
            public View instantiateItem(ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(container.getContext());
                photoView.setId(position);

                Glide.with(container.getContext())
                        .load(sList.get(position))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.ic_default_logo)
                        .error(R.mipmap.ic_default_logo)
                        .dontAnimate()
                        .into(photoView);

                photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(ImageView view, float x, float y) {
                        finish();
                    }
                });
                // Now just add PhotoView to ViewPager and return it
                container.addView(photoView);

                return photoView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        hackyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                goodsImgNum.setText(position + 1 + "/" + sList.size());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        hackyViewPager.setCurrentItem(pos);

    }

    @Override
    public void onClick(View view) {

    }


}
