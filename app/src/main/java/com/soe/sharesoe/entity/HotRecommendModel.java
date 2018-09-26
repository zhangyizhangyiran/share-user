package com.soe.sharesoe.entity;

import java.io.Serializable;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description 首页热门推荐
 * @encoding UTF-8
 * @date 2017/11/14
 * @time 上午11:04
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class HotRecommendModel implements Serializable {

    /**
     * id : 18
     * parentId : 0
     * topicName : 121
     * secondName : 12
     * level : 1
     * showState : 1
     * homeShow : 1
     * sortNum : 0
     * iconUrl : http://share-upyun-com.b0.upaiyun.com/topic/1510566170.jpg
     * updateTime : 1510624727000
     * createUserId : 0
     * updateUserId : 0
     * createTime : 1509971644000
     */

    private long id;
    private int parentId;
    private String topicName;
    private String secondName;
    private int level;
    private int showState;
    private int homeShow;
    private int sortNum;
    private String iconUrl;
    private long updateTime;
    private int createUserId;
    private int updateUserId;
    private long createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getShowState() {
        return showState;
    }

    public void setShowState(int showState) {
        this.showState = showState;
    }

    public int getHomeShow() {
        return homeShow;
    }

    public void setHomeShow(int homeShow) {
        this.homeShow = homeShow;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
