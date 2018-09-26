//package com.soe.sharesoe.module.home.gridviewpager;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.support.v4.view.ViewPager;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.soe.sharesoe.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class SoreButton extends LinearLayout {
//    Context mContext;
//    private ViewPager viewPager;
//    private LinearLayout llIndicator;
//    //选中图片
//    private int RadioSelect;
//    //未选中图片
//    private int RadioUnselected;
//    //圆点间距
//    private int distance;
//
//    List<View> listSoreView = new ArrayList<>();
//    View soreView;
//    private List<Integer> listView;
//
//    //接口
//    private ViewControl viewControl;
//    //设置接口
//    public void setViewControl(ViewControl viewControl) {
//        this.viewControl = viewControl;
//    }
//
//    public SoreButton(Context context) {
//        super(context);
//    }
//
//    public SoreButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mContext = context;
//        LayoutInflater.from(context).inflate(R.layout.anfq_sore_button, this, true);
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        llIndicator = (LinearLayout) findViewById(R.id.llIndicator);
//
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DynamicSoreView);
//        if (typedArray != null) {
//            //选中点
//            RadioSelect = typedArray.getResourceId(R.styleable.DynamicSoreView_SoreRadioSelect, R.drawable.ic_loading_bg);
//            //未选中点
//            RadioUnselected = typedArray.getResourceId(R.styleable.DynamicSoreView_SoreRadioUnselected, R.drawable.ic_loading_bg);
//            //圆点间距
//            distance = typedArray.getInteger(R.styleable.DynamicSoreView_SoreDistance,10);
//            typedArray.recycle();
//        }
//        //设置空布局
//        listView = new ArrayList<>();
//        listView.add(R.layout.viewpager_default);
//    }
//
//    //初始化ViewPager
//    private void initViewPager(){
//        listSoreView = new ArrayList<>();
//        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        int size = listView.size();
//        for (int i = 0; i < size; i++) {
//            //循环拿到传入的View
//            soreView = layoutInflater.inflate(listView.get(i), null);
//            //通过接口回掉的形式返回当前的View,实现接口后开源拿到每个View然后进行操作
//            if (viewControl!=null){
//                viewControl.setView(soreView,i);
//            }
//            //将获取到的View添加到List中
//            listSoreView.add(soreView);
//        }
//        //设置viewPager的Adapter
//        viewPager.setAdapter(new ViewPagerAdapter(listSoreView));
//        //初始化LinearLayout，加入指示器
//        initLinearLayout(viewPager, size, llIndicator);
//    }
//
//    /**
//     * 设置指示器，设置ViewPager滑动事件监听
//     * @param viewPager --ViewPager
//     * @param pageSize --View的页数
//     * @param linearLayout --LinearLayout
//     */
//    private void initLinearLayout(ViewPager viewPager, int pageSize, LinearLayout linearLayout) {
//        linearLayout.removeAllViews();
//        //定义数组放置指示器的点，pageSize是View个数
//        final ImageView[] imageViews = new ImageView[pageSize];
//        for (int i = 0; i < pageSize; i++) {
//            //创建ImageView
//            ImageView image = new ImageView(mContext);
//            //将ImageView放入数组
//            imageViews[i] = image;
//            //默认选中第一个
//            if (i == 0) {
//                //选中的点
//                image.setImageResource(RadioSelect);
//            } else {
//                //未选中的点
//                image.setImageResource(RadioUnselected);
//            }
//            //设置宽高
//            LayoutParams params = new LayoutParams(
//                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            params.setMargins(distance, 0, distance, 0);
//            //将点添加到LinearLayout中
//            linearLayout.addView(image, params);
//        }
//
//        //ViewPager的滑动事件
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrollStateChanged(int arg0) {}
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {}
//            @Override
//            public void onPageSelected(int arg0) {
//                //arg0当前ViewPager
//                for (int i = 0; i < imageViews.length; i++) {
//                    //设置为选中的点
//                    imageViews[arg0].setImageResource(RadioSelect);
//                    //判断当前的点i如果不等于当前页的话就设置为未选中
//                    if (arg0 != i) {
//                        imageViews[i].setImageResource(RadioUnselected);
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 设置圆点距离
//     * @param distance  --距离
//     * @return
//     */
//    @Deprecated
//    public SoreButton setDistance(int distance){
//        this.distance = distance;
//        return this;
//    }
//    /**
//     * 设置指示器图片
//     * @param radioSelect       --选中图片
//     * @param radioUnselected   --未选中图片
//     * @return
//     */
//    @Deprecated
//    public SoreButton setIndicator(int radioSelect,int radioUnselected){
//        //选中图片
//        RadioSelect = radioSelect;
//        //未选中图片
//        RadioUnselected = radioUnselected;
//        return this;
//    }
//    /**
//     * 设置view
//     * @param listView   --view
//     * @return
//     */
//    public SoreButton setView(List<Integer> listView){
//        this.listView = listView;
//        return this;
//    }
//    /**
//     * 设置初始化
//     */
//    public SoreButton init(){
//        initViewPager();
//        return this;
//    }
//}
