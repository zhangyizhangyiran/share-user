package com.soe.sharesoe.entity;

import java.io.Serializable;

/**
 * @author sj jerry <SuiJing, suijing@foxmail.com>
 * @version ${VERSIONCODE}
 * @project
 * @Description
 * @encoding UTF-8
 * @date ${DATA}
 * @time 下午2:08
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ProductReserveCreateModel implements Serializable{


    /**
     * result : {}
     * code : 1000
     * msg : 预约成功
     * params : {"reserveSn":"2017111013560097489395"}
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
         * reserveSn : 2017111013560097489395
         */

        private String reserveSn;
        private long times;

        public String getReserveSn() {
            return reserveSn;
        }

        public void setReserveSn(String reserveSn) {
            this.reserveSn = reserveSn;
        }

        public long getTimes() {
            return times;
        }

        public void setTimes(long times) {
            this.times = times;
        }
    }
}
