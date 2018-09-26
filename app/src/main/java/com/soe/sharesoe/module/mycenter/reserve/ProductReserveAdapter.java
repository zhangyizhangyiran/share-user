package com.soe.sharesoe.module.mycenter.reserve;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;
import com.soe.sharesoe.entity.ProductReserveModel;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 预约列表
 * @encoding UTF-8
 * @date 2017/11/9
 * @time 下午3:39
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ProductReserveAdapter extends BaseQuickAdapter<ProductReserveModel, BaseViewHolder> {

    private String timeStr1;
    private String timeStr2;
    private long time = 160 * 1000L;
    private final long INTERVAL = 1000L;
    private MyCountDownTimer timer;

    //用于退出activity,避免countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownMap;

    private Activity mActivity;

    public ProductReserveAdapter(Activity mActivity) {
        super(R.layout.layout_order, null);
        this.mActivity = mActivity;
        countDownMap = new SparseArray<>();

    }

    @Override
    protected void convert(final BaseViewHolder helper, ProductReserveModel item) {
        switch (item.getStatus()) {
            case 0:

                helper.setText(R.id.top_goods_type, "自营");
                break;
            case 1:
                helper.setText(R.id.top_goods_type, "个人");
                break;
            case 2:
                helper.setText(R.id.top_goods_type, "合作商户");
                break;
        }
        helper.getView(R.id.view_line).setVisibility(View.GONE);
        helper.setText(R.id.center_goods_title, item.getProductName() + "");
        helper.setText(R.id.center_goods_distance, item.getProductLocation() + "");
        helper.setText(R.id.center_goods_rental, "租金：￥" + item.getRent() + "");
        helper.setText(R.id.center_goods_deposit, "押金：￥" + item.getDeposit() + "");
        Glide.with(mActivity)
                .load(item.getProductImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_default_logo)
                .error(R.mipmap.ic_default_logo)
                .dontAnimate()
                .into((ImageView) helper.getView(R.id.center_goods_img));


        helper.getView(R.id.bottom_goods_order_btn1).setVisibility(View.VISIBLE);
        helper.getView(R.id.bottom_goods_order_lly).setVisibility(View.VISIBLE);
        helper.getView(R.id.center_goods_use_days).setVisibility(View.INVISIBLE);

        helper.addOnClickListener(R.id.bottom_goods_order_btn1);

        time = item.getExpireTime() - System.currentTimeMillis();

        TextView tv = helper.getView(R.id.top_goods_status);
        tv.setTextColor(Color.parseColor("#515151"));
        //启动计时
        startTimer(tv);
    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        Log.e("TAG", "size :  " + countDownMap.size());
        for (int i = 0, length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        private TextView tv;

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public MyCountDownTimer(long millisInFuture, long countDownInterval, final TextView tv) {
            super(millisInFuture, countDownInterval);
            this.tv = tv;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long time = millisUntilFinished / 1000;

            if (time <= 59) {
                tv.setText(String.format("已预约（00:%02d）", time));
            } else {
                tv.setText(String.format("已预约（%02d:%02d）", time / 60, time % 60));
            }
            countDownMap.put(tv.hashCode(), this);
        }

        @Override
        public void onFinish() {
            tv.setText("已预约 00:00");
            cancelTimer();
            Log.e("timer-->", "time====hhhh");
        }
    }

    /**
     * 开始倒计时
     */
    private void startTimer(TextView tv) {
//        if (timer == null) {
        timer = new MyCountDownTimer(time, INTERVAL, tv);
//        }
        timer.start();
    }

    /**
     * 取消倒计时
     */
    protected void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
