package com.soe.sharesoe.module.home.gridviewpager;

import android.view.View;

import java.util.List;

/**
 * @author FengTong
 * @date 2017/6/1
 */
public interface IDynamicSore<T> {
    void setGridView(View view, int type, List<T> data);
}
