package com.soe.sharesoe.entity;

import java.io.Serializable;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description 商品列表要传入的参数，key = topicGoodsParams
 * @encoding UTF-8
 * @date 2017/11/14
 * @time 下午7:02
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class TopicGoodsParams implements Serializable{
    //区分页面的id
    private int pageId;

    private double latitude;
    private double longitude;
    private String keyWord;
    private String order;
    private int page;
    private int pageSize;
    private long topicId;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }
}
