package com.soe.sharesoe.module.home;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.soe.sharesoe.entity.CategoryAllModel;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HomeItem implements MultiItemEntity {
    public static final int RECOM_TITILE = 1;
    public static final int RECOM_TOP = 2;
    public static final int RECOM_BOTTOM = 3;
    public static final int RECOM_VIEW = 4;
    public static final int GOODS_LIST = 5;


    public static final int RECOM_TITILE_SPAN_SIZE = 1;
    public static final int RECOM_TOP_SPAN_SIZE = 2;
    public static final int RECOM_BOTTOM_SPAN_SIZE = 3;
    public static final int RECOM_VIEW_SPAN_SIZE = 1;
    public static final int SORT_LIST_SPAN_SIZE = 1;


    private int itemType;
    private int spanSize;

    public HomeItem(int itemType, int spanSize, String content) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.content = content;
    }

    public HomeItem(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public HomeItem(int itemType, int spanSize, List<ProductList.ListBean> productList) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.productList = productList;

    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }


    private String content;
    private List<ProductList.ListBean> productList;

    public List<ProductList.ListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductList.ListBean> productList) {
        this.productList = productList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
