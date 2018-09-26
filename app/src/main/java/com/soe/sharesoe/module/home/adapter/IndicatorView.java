package com.soe.sharesoe.module.home.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.soe.sharesoe.R;
import com.soe.sharesoe.utils.DisplayUtil;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description viewpager的指示器
 * @encoding UTF-8
 * @date 2017/11/22
 * @time 下午7:38
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class IndicatorView {

    Context mContext;

    //选中点
    private int RadioSelect = R.drawable.shape_homesort_dot_selected;
    //未选中点
    private int RadioUnselected = R.drawable.shape_homesort_dot_normal;

    public IndicatorView(Context context, ViewPager viewPager, int pageSize, LinearLayout llIndicator) {
        mContext = context;

        initLinearLayout(viewPager, pageSize, llIndicator);
    }

    /**
     * 设置指示器，设置ViewPager滑动事件监听
     *
     * @param viewPager    --ViewPager
     * @param pageSize     --View的页数
     * @param linearLayout --LinearLayout
     */
    private void initLinearLayout(ViewPager viewPager, int pageSize, LinearLayout linearLayout) {
        linearLayout.removeAllViews();
        //定义数组放置指示器的点，pageSize是View个数
        final ImageView[] imageViews = new ImageView[pageSize];
        for (int i = 0; i < pageSize; i++) {
            //创建ImageView
            ImageView image = new ImageView(mContext);
            //将ImageView放入数组
            imageViews[i] = image;
            //默认选中第一个
            if (i == 0) {
                //选中的点
                image.setImageResource(RadioSelect);
            } else {
                //未选中的点
                image.setImageResource(RadioUnselected);
            }
            //设置宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DisplayUtil.dp2px(mContext, 5), DisplayUtil.dp2px(mContext, 5));
            params.setMargins(10, 0, 10, 0);
            //将点添加到LinearLayout中
            linearLayout.addView(image, params);
        }

        //ViewPager的滑动事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                //arg0当前ViewPager
                for (int i = 0; i < imageViews.length; i++) {
                    //设置为选中的点
                    imageViews[arg0].setImageResource(RadioSelect);
                    //判断当前的点i如果不等于当前页的话就设置为未选中
                    if (arg0 != i) {
                        imageViews[i].setImageResource(RadioUnselected);
                    }
                }
            }
        });
    }

}
