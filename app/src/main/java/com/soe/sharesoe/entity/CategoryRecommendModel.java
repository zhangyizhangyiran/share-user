package com.soe.sharesoe.entity;

import java.io.Serializable;

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
public class CategoryRecommendModel implements Serializable {


    /**
     * id : 1
     * productName : 苹果6
     * productPic : https://img12.360buyimg.com/n1/s450x450_jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg
     */

    private int id;
    private String productName;
    private String productPic;

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

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }
}
