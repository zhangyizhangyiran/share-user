package com.soe.sharesoe.module.nearby.search.city;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description 通用的RecyclerView 的ItemClickListener，包含点击和长按
 * @encoding UTF-8
 * @date 2017/11/1
 * @time 上午10:46
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public interface OnItemClickListener<T>
{
    void onItemClick(ViewGroup parent, View view, T t, int position);
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}