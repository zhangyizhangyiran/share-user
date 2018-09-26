package com.soe.sharesoe.module.sort.search;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;

import java.util.List;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/6
 * @time 下午5:34
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class SearchGoodsAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SearchGoodsAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final MySection item) {
        helper.setText(R.id.item_search_goods_head, item.header);
//        helper.setVisible(R.id.more, item.isMore());
//        helper.addOnClickListener(R.id.more);

        switch (helper.getLayoutPosition() % 2) {
            case 0:
                helper.setImageResource(R.id.item_search_goods_img, R.mipmap.icon_search_star);
                break;
            case 1:
                helper.setImageResource(R.id.item_search_goods_img, R.mipmap.icon_search_history);
                break;
        }

        if (item.isMore()) {
            helper.setImageResource(R.id.item_search_goods_img, 0);
            helper.setTextColor(R.id.item_search_goods_head, Color.parseColor("#FA7567"));
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        Video video = (Video) item.t;
        helper.setText(R.id.item_search_goods_name, video.getName());
    }
}
