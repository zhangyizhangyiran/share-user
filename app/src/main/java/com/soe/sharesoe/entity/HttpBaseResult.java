package com.soe.sharesoe.entity;

import java.io.Serializable;

/**
 * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
 * @version v1.0
 * @project coolqi
 * @Description
 * @encoding UTF-8
 * @date 16/11/30
 * @time 下午12:13
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class HttpBaseResult<T> implements Serializable {


    /**
     * result : []
     * code : 0
     * msg : success
     * params : {}
     */

    private int code;
    private String msg;
    private ParamsBean params;
    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static class ParamsBean {

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
