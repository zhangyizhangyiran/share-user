package com.soe.sharesoe.module.mycenter.bean;

import java.util.List;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date 17/11/9
 * @time 上午11:28
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class CreditChangeModel {


    /**
     * result : {"pageNum":1,"pageSize":8,"size":8,"orderBy":null,"startRow":0,"endRow":7,"total":8,"pages":1,"list":[{"id":1,"uid":13,"description":"购买商品","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510311735000},{"id":2,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629794000},{"id":3,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629799000},{"id":5,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629912000},{"id":7,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629920000},{"id":9,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629931000},{"id":11,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629938000},{"id":13,"uid":13,"description":"租赁商品","scores":10,"type":1,"credit":10,"creditType":1,"createTime":1510646237000}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
     * code : 1000
     * msg : 查询成功
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
         * pageSize : 8
         * size : 8
         * orderBy : null
         * startRow : 0
         * endRow : 7
         * total : 8
         * pages : 1
         * list : [{"id":1,"uid":13,"description":"购买商品","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510311735000},{"id":2,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629794000},{"id":3,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629799000},{"id":5,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629912000},{"id":7,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629920000},{"id":9,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629931000},{"id":11,"uid":13,"description":"","scores":0,"type":0,"credit":0,"creditType":0,"createTime":1510629938000},{"id":13,"uid":13,"description":"租赁商品","scores":10,"type":1,"credit":10,"creditType":1,"createTime":1510646237000}]
         * firstPage : 1
         * prePage : 0
         * nextPage : 0
         * lastPage : 1
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         */

        private String pageNum;
        private String pageSize;
        private String size;
        private Object orderBy;
        private String startRow;
        private String endRow;
        private String total;
        private String pages;
        private String firstPage;
        private String prePage;
        private String nextPage;
        private String lastPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

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

        public Object getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(Object orderBy) {
            this.orderBy = orderBy;
        }

        public String getStartRow() {
            return startRow;
        }

        public void setStartRow(String startRow) {
            this.startRow = startRow;
        }

        public String getEndRow() {
            return endRow;
        }

        public void setEndRow(String endRow) {
            this.endRow = endRow;
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

        public String getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(String firstPage) {
            this.firstPage = firstPage;
        }

        public String getPrePage() {
            return prePage;
        }

        public void setPrePage(String prePage) {
            this.prePage = prePage;
        }

        public String getNextPage() {
            return nextPage;
        }

        public void setNextPage(String nextPage) {
            this.nextPage = nextPage;
        }

        public String getLastPage() {
            return lastPage;
        }

        public void setLastPage(String lastPage) {
            this.lastPage = lastPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * id : 1
             * uid : 13
             * description : 购买商品
             * scores : 0
             * type : 0
             * credit : 0
             * creditType : 0
             * createTime : 1510311735000
             */

            private String id;
            private String uid;
            private String description;
            private String scores;
            private String type;
            private String credit;
            private String creditType;
            private long createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getScores() {
                return scores;
            }

            public void setScores(String scores) {
                this.scores = scores;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getCreditType() {
                return creditType;
            }

            public void setCreditType(String creditType) {
                this.creditType = creditType;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }
    }

    public static class ParamsBean {
    }
}
