package com.soe.sharesoe.module.nearby.search.city;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description 美团最顶部Header
 * @encoding UTF-8
 * @date 2017/11/1
 * @time 上午10:45
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class MeituanTopHeaderBean {
    private String txt;

    public MeituanTopHeaderBean(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public MeituanTopHeaderBean setTxt(String txt) {
        this.txt = txt;
        return this;
    }

}
