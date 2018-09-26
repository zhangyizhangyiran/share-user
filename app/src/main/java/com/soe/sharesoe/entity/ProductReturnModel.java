package com.soe.sharesoe.entity;

import java.io.Serializable;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description
 * @encoding UTF-8
 * @date 2017/11/17
 * @time 下午4:39
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ProductReturnModel implements Serializable{


    /**
     * result : {}
     * code : 1000
     * msg :
     * params : {"rent":"(2.00,2元/次)"}
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
        /**
         * rent : (2.00,2元/次)
         */

        private String rent;

        public String getRent() {
            return rent;
        }

        public void setRent(String rent) {
            this.rent = rent;
        }
    }
}
