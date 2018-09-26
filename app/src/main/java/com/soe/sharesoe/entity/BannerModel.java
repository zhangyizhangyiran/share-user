package com.soe.sharesoe.entity;

import java.util.List;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date 2017/11/14
 * @time 下午7:39
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class BannerModel {

    /**
     * result : [{"bannerType":0,"productCode":1,"productUrl":"1","imgUrl":"/aa","indexSequence":1,"clickTo":0}]
     * code : 1000
     * msg : SUCCESS
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
         * bannerType : 0
         * productCode : 1
         * productUrl : 1
         * imgUrl : /aa
         * indexSequence : 1
         * clickTo : 0
         */

        private String bannerType;
        private String productCode;
        private String productUrl;
        private String imgUrl;
        private String indexSequence;
        private String clickTo;

        public String getBannerType() {
            return bannerType;
        }

        public void setBannerType(String bannerType) {
            this.bannerType = bannerType;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductUrl() {
            return productUrl;
        }

        public void setProductUrl(String productUrl) {
            this.productUrl = productUrl;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getIndexSequence() {
            return indexSequence;
        }

        public void setIndexSequence(String indexSequence) {
            this.indexSequence = indexSequence;
        }

        public String getClickTo() {
            return clickTo;
        }

        public void setClickTo(String clickTo) {
            this.clickTo = clickTo;
        }
    }
}
