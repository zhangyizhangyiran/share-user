package com.soe.sharesoe.net.http;

/**
 * Created by wangxiaofa on 16/11/23.
 */
public class ApiException extends RuntimeException {

    public static final int CODE_GET_ERROR = -1;
    public static final int WRONG_PASSWORD = -2;
    public static final int CODE_LOGIN_TIMEOUT = 203;//登录超时
    public static final int CODE_LOGIN_FAIL = 204;//登录验证失败

    public ApiException(int resultCode,String msg) {
        this(getApiExceptionMessage(resultCode,msg));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code, String message) {
        String msg = "";
        switch (code) {
            case CODE_GET_ERROR:
                msg = message;
                break;
            case WRONG_PASSWORD:
                msg = message;
                break;
            case CODE_LOGIN_FAIL:
                msg = message;
                break;
            case CODE_LOGIN_TIMEOUT:
                msg = message;
                break;
            default:
                msg = message;
                break;

        }
        return msg;
    }}

