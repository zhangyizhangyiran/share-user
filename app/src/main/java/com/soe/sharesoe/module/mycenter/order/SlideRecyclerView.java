package com.soe.sharesoe.module.mycenter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

/**
 * Created by chengel on 2017/10/28.
 */

public class SlideRecyclerView extends RecyclerView{
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //isScrolling 为 true 表示是 Fling 状态
        boolean isScrolling = getScrollState() == SCROLL_STATE_SETTLING;
        boolean ans = super.onInterceptTouchEvent(e);
        if (ans && isScrolling && e.getAction() == MotionEvent.ACTION_DOWN) {
            //先调用 onTouchEvent() 使 RecyclerView 停下来
            onTouchEvent(e);
            //反射恢复 ScrollState
            try {
                Field field = RecyclerView.class.getDeclaredField("mScrollState");
                field.setAccessible(true);
                field.setInt(this, SCROLL_STATE_IDLE);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            return false;
        }
        return ans;
    }

    public SlideRecyclerView(Context context, AttributeSet ss) {
        super(context,ss);
    }
}
