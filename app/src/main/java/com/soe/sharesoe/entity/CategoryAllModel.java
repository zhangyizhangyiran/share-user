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
public class CategoryAllModel implements Serializable {


    /**
     * result : [{"id":6,"categoryName":"手机数码","categoryLogo":"https://ss1.baidu.com/70cFfyinKgQFm2e88IuM_a/forum/pic/item/72f082025aafa40fe37eba76a364034f79f019e4.jpg","categoryDesc":"各种手机"},{"id":7,"categoryName":"五金","categoryLogo":"http://pic.qiantucdn.com/58pic/16/93/16/43w58PICyjS_1024.jpg","categoryDesc":"五金百货"},{"id":5,"categoryName":"扫地机器人","categoryLogo":"https://i1.mifile.cn/f/i/g/2015/cn-index/saodijiqiren320-220.jpg","categoryDesc":"扫地机器人"},{"id":8,"categoryName":"无人机","categoryLogo":"https://i1.mifile.cn/f/i/g/2015/123321.jpg","categoryDesc":"好玩的无人机"}]
     * code : 0
     * msg : success
     * params : {}
     */
    /**
     * id : 6
     * categoryName : 手机数码
     * categoryLogo : https://ss1.baidu.com/70cFfyinKgQFm2e88IuM_a/forum/pic/item/72f082025aafa40fe37eba76a364034f79f019e4.jpg
     * categoryDesc : 各种手机
     */

    private int id;
    private String categoryName;
    private String categoryLogo;
    private String categoryDesc;



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

}
