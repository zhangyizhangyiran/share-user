package com.soe.sharesoe.entity;

import java.io.Serializable;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description
 * @encoding UTF-8
 * @date 2017/11/16
 * @time 下午6:56
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class OrderDetailModel implements Serializable {

    /**
     * orderSn : 20171116143257591990
     * productId : 0
     * reserveSn : null
     * depositPayMethod : 3
     * depositPayTime : 1510814969000
     * depositWithdrawTime : null
     * depositTradeNo : ch_2017111614492859078510
     * rentPayMethod : 0
     * rentPayTime : null
     * rentTradeNo : null
     * endTime : null
     * amount : null
     * rent : 1
     * deposit : 999
     * startUseTime : null
     * endUseTime : null
     * pricingStrategy : null
     * cancelRentTime : null
     * productName : 安卓 华为(HUAWEI)M3 8.4英寸平板电脑
     * productCode : 安卓 华为(HUAWEI)M3 8.4英寸平板电脑
     * productPic : http://share-upyun-com.b0.upaiyun.com/goods/1510714608.png
     * boxCode : null
     * productStatus : 0
     * introduction : 国货当志强
     * source : 0
     * status : 200
     */

    private String orderSn;
    private int productId;
    private String reserveSn;
    private int depositPayMethod;
    private long depositPayTime;
    private long depositWithdrawTime;
    private String depositTradeNo;
    private int rentPayMethod;
    private long rentPayTime;
    private String rentTradeNo;
    private long endTime;
    private String amount;
    private int rent;
    private int deposit;
    private long startUseTime;
    private long endUseTime;
    private String pricingStrategy;
    private long cancelRentTime;
    private String productName;
    private String productCode;
    private String productPic;
    private String boxCode;
    private int productStatus;
    private String introduction;
    private String useTime;
    private int source;
    private int status;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getReserveSn() {
        return reserveSn;
    }

    public void setReserveSn(String reserveSn) {
        this.reserveSn = reserveSn;
    }

    public int getDepositPayMethod() {
        return depositPayMethod;
    }

    public void setDepositPayMethod(int depositPayMethod) {
        this.depositPayMethod = depositPayMethod;
    }

    public long getDepositPayTime() {
        return depositPayTime;
    }

    public void setDepositPayTime(long depositPayTime) {
        this.depositPayTime = depositPayTime;
    }

    public long getDepositWithdrawTime() {
        return depositWithdrawTime;
    }

    public void setDepositWithdrawTime(long depositWithdrawTime) {
        this.depositWithdrawTime = depositWithdrawTime;
    }

    public String getDepositTradeNo() {
        return depositTradeNo;
    }

    public void setDepositTradeNo(String depositTradeNo) {
        this.depositTradeNo = depositTradeNo;
    }

    public int getRentPayMethod() {
        return rentPayMethod;
    }

    public void setRentPayMethod(int rentPayMethod) {
        this.rentPayMethod = rentPayMethod;
    }

    public long getRentPayTime() {
        return rentPayTime;
    }

    public void setRentPayTime(long rentPayTime) {
        this.rentPayTime = rentPayTime;
    }

    public String getRentTradeNo() {
        return rentTradeNo;
    }

    public void setRentTradeNo(String rentTradeNo) {
        this.rentTradeNo = rentTradeNo;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public long getStartUseTime() {
        return startUseTime;
    }

    public void setStartUseTime(long startUseTime) {
        this.startUseTime = startUseTime;
    }

    public long getEndUseTime() {
        return endUseTime;
    }

    public void setEndUseTime(long endUseTime) {
        this.endUseTime = endUseTime;
    }

    public String getPricingStrategy() {
        return pricingStrategy;
    }

    public void setPricingStrategy(String pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public long getCancelRentTime() {
        return cancelRentTime;
    }

    public void setCancelRentTime(long cancelRentTime) {
        this.cancelRentTime = cancelRentTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
