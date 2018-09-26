package com.soe.sharesoe.module.nearby.map.cupboard;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/15
 * @time 下午3:17
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class CupboardInfo {

    /**
     * id : 1
     * cupboardName : 地铁旁柜子
     * cupboardLocation : 瑞都国际中心
     * lng : 39.89602
     * lat : 39.89602
     * bigBoxFree : 4
     * midBoxFree : 4
     * smallBoxFree : 4
     */

    private int id;
    private String cupboardName;
    private String cupboardLocation;
    private double lng;
    private double lat;
    private int bigBoxFree;
    private int midBoxFree;
    private int smallBoxFree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCupboardName() {
        return cupboardName;
    }

    public void setCupboardName(String cupboardName) {
        this.cupboardName = cupboardName;
    }

    public String getCupboardLocation() {
        return cupboardLocation;
    }

    public void setCupboardLocation(String cupboardLocation) {
        this.cupboardLocation = cupboardLocation;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getBigBoxFree() {
        return bigBoxFree;
    }

    public void setBigBoxFree(int bigBoxFree) {
        this.bigBoxFree = bigBoxFree;
    }

    public int getMidBoxFree() {
        return midBoxFree;
    }

    public void setMidBoxFree(int midBoxFree) {
        this.midBoxFree = midBoxFree;
    }

    public int getSmallBoxFree() {
        return smallBoxFree;
    }

    public void setSmallBoxFree(int smallBoxFree) {
        this.smallBoxFree = smallBoxFree;
    }
}
