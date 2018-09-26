package com.soe.sharesoe.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author sj jerry <SuiJing, suijing@foxmail.com>
 * @version ${VERSIONCODE}
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date ${DATA}
 * @time 下午4:17
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CategorySubModel implements Serializable {


    /**
     * id : 10
     * categoryName : 手机通信
     * categoryLogo :
     * categoryDesc :
     * productList : [{"id":1,"productName":"苹果6","productPic":"https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg "}]
     */

    private int id;
    private String categoryName;
    private String categoryLogo;
    private String categoryDesc;
    private List<ProductListBean> productList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryLogo() {
        return categoryLogo;
    }

    public void setCategoryLogo(String categoryLogo) {
        this.categoryLogo = categoryLogo;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class ProductListBean {
        /**
         * id : 1
         * productName : 苹果6
         * productPic : https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg
         */

        private String id;
        private String productName;
        private String productPic;
        private int status;

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

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
