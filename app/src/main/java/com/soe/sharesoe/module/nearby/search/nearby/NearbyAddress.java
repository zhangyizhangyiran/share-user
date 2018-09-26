package com.soe.sharesoe.module.nearby.search.nearby;


import com.soe.sharesoe.widget.IndexBar.bean.BaseIndexPinyinBean;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
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

public class NearbyAddress extends BaseIndexPinyinBean {
    private String name;
    private String city;//城市名字
    private double latitude;
    private double longitude;
    private String addstr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddstr() {
        return addstr;
    }

    public void setAddstr(String addstr) {
        this.addstr = addstr;
    }

    public NearbyAddress() {
    }

    public NearbyAddress(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public NearbyAddress setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String getTarget() {
        return name;
    }
}
