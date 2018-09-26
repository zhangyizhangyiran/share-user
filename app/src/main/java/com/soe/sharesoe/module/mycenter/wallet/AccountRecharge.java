package com.soe.sharesoe.module.mycenter.wallet;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/15
 * @time 上午10:01
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class AccountRecharge {

    /**
     * channelParamsStr : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017062207544109&biz_content=%7B%22out_trade_no%22%3A%22ch_2017111510203200011447%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22100%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&sign=CopLTgt6yIxzdi13OIhfpJs58BTiVNZjRRT1Pt2nP8wQztfzmf%2BmtrXgXmiZFS15utHWUuRQ%2BbKQRR9Xn2%2FAfWe7aZ7jVRx2WN%2B%2BVy2n4vEkdjn6aVHFuB3UQMaeKoXwx1%2BPecXZfuSg%2BSweqCIp08pjCCYAEXTeXrLCdHj2%2FG0qLxP52APwGq4WXdXcllSZUPhY0aJUy0%2BH%2FU7yqrAZsu%2FqqA59E28ZyGoRXBa9EokVu4sXjSYjmKzsn5es3o%2FHNni63ctPgynTxivmjBvmK4sCyCh5Oit%2FFGL3W3lGLH9Y9oCJ5PYNxI5ioHiDgDSGh%2BWPCVkq73HsKu%2BsdiNudg%3D%3D&sign_type=RSA2×tamp=2017-11-15+10%3A20%3A32&version=1.0
     * channel : 1
     */

    private String channelParamsStr;
    private int channel;

    public String getChannelParamsStr() {
        return channelParamsStr;
    }

    public void setChannelParamsStr(String channelParamsStr) {
        this.channelParamsStr = channelParamsStr;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
