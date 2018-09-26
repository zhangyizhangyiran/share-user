package com.soe.sharesoe.entity;

/**
 * Created by zhangqi on 2016/12/6.
 * ClassName:又拍云结果对象
 * Description:又拍云返回回来的json字符串转化对象
 */

public class YouPaiYunBean {
    private String sign;
    private int code;
    private String url;
    private long time;
    private String message;
    private String mimetype;
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign() {
        return sign;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setTime(long time) {
        this.time = time;
    }
    public long getTime() {
        return time;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
    public String getMimetype() {
        return mimetype;
    }


}
