package com.soe.sharesoe.entity;

/**
 * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
 * @version v1.0
 * @project coolqi
 * @Description
 * @encoding UTF-8
 * @date 16/12/1
 * @time 下午6:30
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class JsonParams {

    /**
     * token : fk8XEiiYnfwU4o9v6SMYePas9wvElD93NWKNXaybm1tOJLtklJbhrg7MpN5lQIJYUJoe11WmcOVYrDIuhDqobw
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private int redPackage;
    private int wxpay;
    private int alipay;
    private int deposit;

    public int getRedPackage() {
        return redPackage;
    }

    public void setRedPackage(int redPackage) {
        this.redPackage = redPackage;
    }

    public int getWxpay() {
        return wxpay;
    }

    public void setWxpay(int wxpay) {
        this.wxpay = wxpay;
    }

    public int getAlipay() {
        return alipay;
    }

    public void setAlipay(int alipay) {
        this.alipay = alipay;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }
}
