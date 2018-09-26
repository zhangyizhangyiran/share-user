package com.soe.sharesoe.widget;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description d定时器
 * @encoding UTF-8
 * @date 2017/11/15
 * @time 下午3:08
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class MyCountDownTimer extends CountDownTimer {

    private long time = 160 * 1000L;
    private final long INTERVAL = 1000L;
    private MyCountDownTimer timer;
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
    }

    @Override
    public void onFinish() {
        tv.setText("已预约 00:00");
        cancelTimer();
    }

    /**
     * 开始倒计时
     */
    public void startTimer(TextView tv) {
//        if (timer == null) {
        timer = new MyCountDownTimer(time, INTERVAL, tv);
//        }
        timer.start();
    }

    /**
     * 取消倒计时
     */
    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}