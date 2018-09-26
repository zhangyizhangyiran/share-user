package com.soe.sharesoe.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description d订单列表
 * @encoding UTF-8
 * @date 2017/11/14
 * @time 下午8:04
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ProductOrderListModel implements Serializable {


    /**
     * pageSize : 10
     * pageNO : 1
     * params :
     * firstIndex : 0
     * count : 1
     * hasNextPage : 0
     * totalPage : 1
     * records : [{"status":"100","productTag":"","productLocation":"这里","useTime":"45","boxCode":"","productName":"小米（MI）滑板车米家电动滑板自行车成人可折叠男女代驾通用两轮电动平衡车踏板车代步车 小米米家电动滑板车 黑色","deposit":"1299.00","rentAmount":"2.00","source":"0","productImg":"http://share-upyun-com.b0.upaiyun.com/test/1510221194.jpg"}]
     */

    private String pageSize;
    private String pageNO;
    private String params;
    private String firstIndex;
    private String count;
    private String hasNextPage;
    private String totalPage;
    private List<RecordsBean> records;

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNO() {
        return pageNO;
    }

    public void setPageNO(String pageNO) {
        this.pageNO = pageNO;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(String firstIndex) {
        this.firstIndex = firstIndex;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(String hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * status : 100
         * productTag :
         * productLocation : 这里
         * useTime : 45
         * boxCode :
         * productName : 小米（MI）滑板车米家电动滑板自行车成人可折叠男女代驾通用两轮电动平衡车踏板车代步车 小米米家电动滑板车 黑色
         * deposit : 1299.00
         * rentAmount : 2.00
         * source : 0
         * productImg : http://share-upyun-com.b0.upaiyun.com/test/1510221194.jpg
         */

        private int status;
        private String productTag;
        private String productLocation;
        private String useTime;
        private String boxCode;
        private String productName;
        private String deposit;
        private String rent;
        private String amount;
        private int source;
        private String productImg;
        private String sn;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getProductTag() {
            return productTag;
        }

        public void setProductTag(String productTag) {
            this.productTag = productTag;
        }

        public String getProductLocation() {
            return productLocation;
        }

        public void setProductLocation(String productLocation) {
            this.productLocation = productLocation;
        }

        public String getUseTime() {
            return useTime;
        }

        public void setUseTime(String useTime) {
            this.useTime = useTime;
        }

        public String getBoxCode() {
            return boxCode;
        }

        public void setBoxCode(String boxCode) {
            this.boxCode = boxCode;
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

        public String getRent() {
            return rent;
        }

        public void setRent(String rent) {
            this.rent = rent;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }
    }
}
