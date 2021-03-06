package com.soe.sharesoe.module.mycenter.order;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/16
 * @time 上午11:54
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class PayProductDeposit {

    /**
     * result : {}
     * code : 1000
     * msg : 押金支付成功
     * params : {}
     */

    private ResultBean result;
    private int code;
    private String msg;
    private ParamsBean params;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ResultBean {
    }

    public static class ParamsBean {
    }
}
