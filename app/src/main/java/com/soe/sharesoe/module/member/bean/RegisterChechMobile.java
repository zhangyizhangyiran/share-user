package com.soe.sharesoe.module.member.bean;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date 17/11/1
 * @time 下午4:46
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class RegisterChechMobile {

    /**
     * result : {}
     * code : 0
     * msg : 校验通过
     * params : {}
     */

    private ResultBean result;
    private String code;
    private String msg;
    private ParamsBean params;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        String token;
    }
}
