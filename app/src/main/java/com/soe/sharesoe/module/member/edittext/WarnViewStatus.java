package com.soe.sharesoe.module.member.edittext;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 警告语显示监听
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
public interface WarnViewStatus {
    /**
     * 展示警告语
     *
     * @param msgs
     */
    void show(String... msgs);

    /**
     * 隐藏警告语
     */
    void hide();
}
