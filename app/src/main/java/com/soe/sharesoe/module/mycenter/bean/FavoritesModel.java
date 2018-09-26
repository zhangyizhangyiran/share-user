package com.soe.sharesoe.module.mycenter.bean;

import java.util.List;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date 2017/11/22
 * @time 下午4:51
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class FavoritesModel {


    /**
     * result : [{"id":4,"productName":"苹果8","rent":"3元/时","deposit":"355.00","pic":"http://share-upyun-com.b0.upaiyun.com/goods/1510923512.jpg","source":"0","rentType":"1"},{"id":3,"productName":"苹果8","rent":"3元/时","deposit":"355.00","pic":"http://share-upyun-com.b0.upaiyun.com/goods/1510923512.jpg","source":"0","rentType":"1"}]
     * code : 1000
     * msg : success
     * params : {}
     */

    private String code;
    private String msg;
    private ParamsBean params;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ParamsBean {
    }

    public static class ResultBean {
        /**
         * id : 4
         * productName : 苹果8
         * rent : 3元/时
         * deposit : 355.00
         * pic : http://share-upyun-com.b0.upaiyun.com/goods/1510923512.jpg
         * source : 0
         * rentType : 1
         */

        private String id;
        private String productName;
        private String rent;
        private String deposit;
        private String pic;
        private String source;
        private String rentType;

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

        public String getRent() {
            return rent;
        }

        public void setRent(String rent) {
            this.rent = rent;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getRentType() {
            return rentType;
        }

        public void setRentType(String rentType) {
            this.rentType = rentType;
        }
    }
}
