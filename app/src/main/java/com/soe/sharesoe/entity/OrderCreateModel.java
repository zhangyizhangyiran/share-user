package com.soe.sharesoe.entity;

import java.io.Serializable;

/**
 * @author wangxiaofa
 * @version ${VERSIONCODE}
 * @project sharesoe
 * @Description 创建订单
 * @encoding UTF-8
 * @date 2017/11/13
 * @time 下午2:48
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class OrderCreateModel implements Serializable{


    /**
     * result : {"id":3,"deposit":1111,"rental":11,"rentType":1,"pricingStrategy":"{\"c\":0,\"e\":0,\"r\":0,\"m\":1,\"t\":1}","productName":"九号平衡车","productCode":"JH000011","productPic":"https://i8.mifile.cn/v1/a1/T1PXhgBbdT1RXrhCrK!560x560.jpg","boxCode":null,"productStatus":1,"introduction":"年轻人的酷玩具","source":0}
     * code : 1000
     * msg :
     * params : {"sn":"20171115095934722878"}
     */

    private ResultBean result;
    private int code;
    private String msg;
    private ParamsBean params;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
         * id : 3
         * deposit : 1111
         * rental : 11
         * rentType : 1
         * pricingStrategy : {"c":0,"e":0,"r":0,"m":1,"t":1}
         * productName : 九号平衡车
         * productCode : JH000011
         * productPic : https://i8.mifile.cn/v1/a1/T1PXhgBbdT1RXrhCrK!560x560.jpg
         * boxCode : null
         * productStatus : 1
         * introduction : 年轻人的酷玩具
         * source : 0
         */

        private int id;
        private String deposit;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

    public static class ParamsBean {
        /**
         * sn : 20171115095934722878
         */

        private String sn;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }
    }
}
