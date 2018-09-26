package com.soe.sharesoe.module.member.edittext;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description
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
public interface EditTextType {

    //手机校验类型
    int TYPE_OF_MOBILE = 0xb0;
    //座机校验类型
    int TYPE_OF_TEL = 0xb1;
    //邮箱校验类型
    int TYPE_OF_EMAIL = 0xb2;
    //url校验类型
    int TYPE_OF_URL = 0xb3;
    //汉字校验类型
    int TYPE_OF_CHZ = 0xb4;
    //用户名校验类型
    int TYPE_OF_USERNAME = 0xb5;
    //用户自定义
    int TYPE_OF_USER_DEFINE = 0xbb;
    //无需校验
    int TYPE_OF_NULL = 0xbf;
}
