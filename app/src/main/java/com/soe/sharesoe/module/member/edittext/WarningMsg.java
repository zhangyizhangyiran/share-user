package com.soe.sharesoe.module.member.edittext;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 错误提示语
 * @encoding UTF-8
 * @date 17/10/27
 * @time 上午9:25
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class WarningMsg {
    private static final String TAG = "WarningMsg";
    private int mType;
    private String mMsgString;

    public void setType(int typ) {
        mType = typ;
    }

    /**
     * 正则判断错误的提示语
     *
     * @return
     */
    public String getMsg() {
        switch (mType) {
            case EditTextType.TYPE_OF_MOBILE:
                //手机校验
                mMsgString = "请输入正确手机号码";
                break;
            case EditTextType.TYPE_OF_TEL:
                //座机校验
                mMsgString = "请输入正确座机号码";
                break;
            case EditTextType.TYPE_OF_EMAIL:
                //邮箱校验
                mMsgString = "请输入正确邮箱";
                break;
            case EditTextType.TYPE_OF_URL:
                //url校验
                mMsgString = "请输入正确地址";
                break;
            case EditTextType.TYPE_OF_CHZ:
                //汉字校验
                mMsgString = "请输入正确中文";
                break;
            case EditTextType.TYPE_OF_USERNAME:
                //用户名校验
                mMsgString = "请输入正确用户名";
                break;
            case EditTextType.TYPE_OF_USER_DEFINE:
                mMsgString = "";
                break;
            default:
                mMsgString = "";
                break;
        }
        return mMsgString;
    }

    /**
     * 不符合长度要求的提示语
     *
     * @return
     */
    public String getLengthMsg() {
        mMsgString = "不符合要求";
        return mMsgString;
    }

    public String getMinLengthMsg() {
        mMsgString = "不符合要求";
        return mMsgString;
    }

    public String getMaxLengthMsg() {
        mMsgString = "不符合要求";
        return mMsgString;
    }
}
