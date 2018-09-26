package com.soe.sharesoe.widget.IndexBar.bean;


import com.soe.sharesoe.widget.IndexBar.suspension.ISuspensionInterface;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description 索引类的标志位的实体基类
 * @encoding UTF-8
 * @date 2017/10/31
 * @time 下午4:40
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public abstract class BaseIndexBean implements ISuspensionInterface {
    private String baseIndexTag;//所属的分类（城市的汉语拼音首字母）

    public String getBaseIndexTag() {
        return baseIndexTag;
    }

    public BaseIndexBean setBaseIndexTag(String baseIndexTag) {
        this.baseIndexTag = baseIndexTag;
        return this;
    }

    @Override
    public String getSuspensionTag() {
        return baseIndexTag;
    }

    @Override
    public boolean isShowSuspension() {
        return true;
    }
}
