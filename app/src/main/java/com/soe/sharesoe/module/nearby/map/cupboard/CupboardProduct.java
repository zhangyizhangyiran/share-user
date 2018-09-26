package com.soe.sharesoe.module.nearby.map.cupboard;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/15
 * @time 下午2:12
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class CupboardProduct {

    /**
     * id : 24
     * deposit : 999
     * rental : 1
     * rentType : 1
     * pricingStrategy :
     * productName : 安卓 华为(HUAWEI)M3 8.4英寸平板电脑
     * productCode : 32294334
     * productPic : http://share-upyun-com.b0.upaiyun.com/goods/1510714608.png
     * boxCode : null
     * productStatus : 1
     * introduction : 国货当志强
     * source : 0
     */

    private String id;
    private int deposit;
    private String rental;
    private int rentType;
    private String pricingStrategy;
    private String productName;
    private String productCode;
    private String productPic;
    private Object boxCode;
    private int productStatus;
    private String introduction;
    private int source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public int getRentType() {
        return rentType;
    }

    public void setRentType(int rentType) {
        this.rentType = rentType;
    }

    public String getPricingStrategy() {
        return pricingStrategy;
    }

    public void setPricingStrategy(String pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
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

    public Object getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(Object boxCode) {
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

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
