package com.soe.sharesoe.module.home.message;

import java.util.List;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date 2017/11/16
 * @time 下午6:55
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class productLists {

    /**
     * result : {"pageNum":1,"pageSize":20,"size":2,"orderBy":"asc1","total":2,"pages":1,"list":[{"id":4,"productName":"苹果6","introduction":"introduction","productPic":"https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg","deposit":11,"rental":"11元/时","source":0,"latestLongitude":116.660933,"latestLatitude":39.896501,"distance":null,"status":3,"me":1},{"id":1,"productName":"苹果6","introduction":"苹果6","productPic":"https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg","deposit":3000,"rental":"2元/次","source":0,"latestLongitude":116.662825,"latestLatitude":39.89602,"distance":null,"status":3,"me":1}]}
     * code : 1000
     * msg : success
     * params : {}
     */

    private ResultBean result;
    private String code;
    private String msg;
    private ParamsBean params;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ResultBean {
        /**
         * pageNum : 1
         * pageSize : 20
         * size : 2
         * orderBy : asc1
         * total : 2
         * pages : 1
         * list : [{"id":4,"productName":"苹果6","introduction":"introduction","productPic":"https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg","deposit":11,"rental":"11元/时","source":0,"latestLongitude":116.660933,"latestLatitude":39.896501,"distance":null,"status":3,"me":1},{"id":1,"productName":"苹果6","introduction":"苹果6","productPic":"https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg","deposit":3000,"rental":"2元/次","source":0,"latestLongitude":116.662825,"latestLatitude":39.89602,"distance":null,"status":3,"me":1}]
         */

        private String pageNum;
        private String pageSize;
        private String size;
        private String orderBy;
        private String total;
        private String pages;
        private List<ListBean> list;

        public String getPageNum() {
            return pageNum;
        }

        public void setPageNum(String pageNum) {
            this.pageNum = pageNum;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
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
             * id : 4
             * productName : 苹果6
             * introduction : introduction
             * productPic : https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg
             * deposit : 11
             * rental : 11元/时
             * source : 0
             * latestLongitude : 116.660933
             * latestLatitude : 39.896501
             * distance : null
             * status : 3
             * me : 1
             */

            private int id;
            private String productName;
            private String introduction;
            private String productPic;
            private int deposit;
            private String rental;
            private int source;
            private double latestLongitude;
            private double latestLatitude;
            private Object distance;
            private int status;
            private int me;

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

            public Object getDistance() {
                return distance;
            }

            public void setDistance(Object distance) {
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

    public static class ParamsBean {
    }
}
