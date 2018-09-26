package com.soe.sharesoe.entity;

import java.io.Serializable;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 
 * @encoding UTF-8
 * @date 2017/11/9
 * @time 下午4:32
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ProductReserveModel implements Serializable{


    /**
     * productName : 无人机
     * productImg : imgUrl
     * productLocation : 这里
     * productTag : null
     * deposit : 23
     * rentAmount : 45
     * status : 1
     * expireTime : 1510213642483
     */
    private int id;

    private String productName;
    private String productImg;
    private String productLocation;
    private Object productTag;
    private String deposit;
    private String rent;
    private String reverseSn;
    private int status;
    private long expireTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public Object getProductTag() {
        return productTag;
    }

    public void setProductTag(Object productTag) {
        this.productTag = productTag;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getReverseSn() {
        return reverseSn;
    }

    public void setReverseSn(String reverseSn) {
        this.reverseSn = reverseSn;
    }
}
