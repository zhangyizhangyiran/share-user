package com.soe.sharesoe.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description 商品详情页
 * @encoding UTF-8
 * @date 2017/11/10
 * @time 下午4:27
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ProductDetailModel implements Serializable {


    /**
     * imgs : []
     * productName : 苹果8
     * deposit : 355
     * rental : 3元/时
     * source : 0
     * detail : &lt;p&gt;好手机号手机号手机&lt;/p&gt;&lt;p&gt;&lt;br&gt;&lt;/p&gt;
     * status : 2
     * me : 0
     * reserveSn : null
     * times : 0
     * boxs : [{"boxName":"A0001","address":"瑞都国际中心","latestLongitude":116.662743,"latestLatitude":39.895514}]
     */

    private String productName;
    private String deposit;
    private String rental;
    private int source;
    private String detail;
    private int status;
    private int me;
    private String reserveSn;
    private int times;
    private List<String> imgs;
    private List<BoxsBean> boxs;
    private int collection;


    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMe() {
        return me;
    }

    public void setMe(int me) {
        this.me = me;
    }

    public String getReserveSn() {
        return reserveSn;
    }

    public void setReserveSn(String reserveSn) {
        this.reserveSn = reserveSn;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<BoxsBean> getBoxs() {
        return boxs;
    }

    public void setBoxs(List<BoxsBean> boxs) {
        this.boxs = boxs;
    }

    public static class BoxsBean {
        /**
         * boxName : A0001
         * address : 瑞都国际中心
         * latestLongitude : 116.662743
         * latestLatitude : 39.895514
         */

        private String boxName;
        private String address;
        private double latestLongitude;
        private double latestLatitude;

        public String getBoxName() {
            return boxName;
        }

        public void setBoxName(String boxName) {
            this.boxName = boxName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getLatestLongitude() {
            return latestLongitude;
        }

        public void setLatestLongitude(double latestLongitude) {
            this.latestLongitude = latestLongitude;
        }

        public double getLatestLatitude() {
            return latestLatitude;
        }

        public void setLatestLatitude(double latestLatitude) {
            this.latestLatitude = latestLatitude;
        }
    }
}
