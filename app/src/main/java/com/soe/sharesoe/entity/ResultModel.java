package com.soe.sharesoe.entity;

/**
 * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
 * @version v1.0
 * @project coolqi
 * @Description
 * @encoding UTF-8
 * @date 16/11/30
 * @time 上午11:35
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ResultModel {

    private Object result; //json数据
    private int code;       //code码
    private String msg;     //状态信息
    private JsonParams params;  //返回参数
    public void setResult(Object result) {
        this.result = result;
    }
    public Object getResult() {
        return result;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setParams(JsonParams params) {
        this.params = params;
    }
    public JsonParams getParams() {
        return params;
    }
    public class JsonParams {

    }
}
