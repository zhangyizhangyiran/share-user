package com.soe.sharesoe.module.home;

import java.util.List;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/8
 * @time 下午5:48
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class ProductList {

    /**
     * pageNum : 1
     * pageSize : 20
     * size : 6
     * orderBy : null
     * total : 6
     * pages : 1
     * list : [{"id":1,"productName":"苹果6","introduction":"苹果6","productPic":"https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg","deposit":3000,"rental":"1元／天","source":0,"latestLongitude":116.662857,"latestLatitude":39.896409,"distance":null,"status":1},{"id":4,"productName":"苹果6","introduction":"introduction","productPic":"https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg","deposit":11,"rental":"1元／天","source":0,"latestLongitude":116.763,"latestLatitude":39.8974,"distance":null,"status":1},{"id":2,"productName":"九号平衡车","introduction":"年轻人的酷玩具","productPic":"https://i8.mifile.cn/v1/a1/T1PXhgBbdT1RXrhCrK!560x560.jpg","deposit":1200,"rental":"1元／天","source":0,"latestLongitude":116.663,"latestLatitude":39.8974,"distance":null,"status":2},{"id":3,"productName":"九号平衡车","introduction":"年轻人的酷玩具","productPic":"https://i8.mifile.cn/v1/a1/T1PXhgBbdT1RXrhCrK!560x560.jpg","deposit":1111,"rental":"1元／天","source":0,"latestLongitude":116.673,"latestLatitude":39.8874,"distance":null,"status":1},{"id":5,"productName":"九号平衡车","introduction":"年轻人的酷玩具","productPic":"https://i8.mifile.cn/v1/a1/T1PXhgBbdT1RXrhCrK!560x560.jpg","deposit":1,"rental":"1元／天","source":0,"latestLongitude":116.663,"latestLatitude":39.9974,"distance":null,"status":1},{"id":6,"productName":"九号平衡车","introduction":"年轻人的酷玩具","productPic":"https://i8.mifile.cn/v1/a1/T1PXhgBbdT1RXrhCrK!560x560.jpg","deposit":1,"rental":"1元／天","source":0,"latestLongitude":116.662,"latestLatitude":39.8174,"distance":null,"status":1}]
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private Object orderBy;
    private int total;
    private int pages;
    private List<ListBean> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Object getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Object orderBy) {
        this.orderBy = orderBy;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        /**
         * id : 2
         * productName : 苹果8
         * introduction :
         * productPic : http://share-upyun-com.b0.upaiyun.com/goods/1510923512.jpg
         * deposit : 355
         * rental : 3元/时
         * source : 0
         * latestLongitude : 33
         * latestLatitude : 33
         * distance : 5043010
         * status : 1
         * me : 0
         */

        private String id;
        private String productName;
        private String introduction;
        private String productPic;
        private int deposit;
        private String rental;
        private int source;
        private double latestLongitude;
        private double latestLatitude;
        private long distance;
        private int status;
        private int me;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
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

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
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

        public long getDistance() {
            return distance;
        }

        public void setDistance(long distance) {
            this.distance = distance;
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
    }
}
