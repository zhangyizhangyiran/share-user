package com.soe.sharesoe.module.sort;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.logger.Logger;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/17
 * @time 下午4:05
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if ((parent.getChildLayoutPosition(view)-1) %2==0) {
            outRect.left = 0;
        }
    }

}

