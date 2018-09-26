package com.soe.sharesoe.module.sort;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;
import com.soe.sharesoe.entity.MultipleItem;

import java.util.List;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    private Context context;

    private Integer[] colorRs;

    public MultipleItemQuickAdapter(Context context, List<MultipleItem> data) {
        super(data);
        this.context = context;
        addItemType(MultipleItem.RECOM_VIEW, R.layout.include_trans_sort_topview);
        addItemType(MultipleItem.TEXT, R.layout.item_text_view);
        addItemType(MultipleItem.IMG, R.layout.item_image_view);
        addItemType(MultipleItem.IMG_TEXT, R.layout.item_img_text_view);

        colorRs = new Integer[]{R.color.sort_color_1, R.color.sort_color_2, R.color.sort_color_3,
                R.color.sort_color_4, R.color.sort_color_5, R.color.sort_color_6,
                R.color.sort_color_7, R.color.sort_color_8, R.color.sort_color_9,};
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.RECOM_VIEW:

                break;
            case MultipleItem.TEXT:
                helper.setText(R.id.category_all_text, item.getContent());
                break;
            case MultipleItem.IMG_TEXT:

                Integer position = (helper.getLayoutPosition() - 5) % 9;
                Log.i("layoutPosition-->", "" + colorRs[position]);
                ((ImageView) helper.getView(R.id.iv)).setImageResource(colorRs[position]);

                helper.setText(R.id.tv, item.getCategoryAllList().getCategoryName());
                break;
            case MultipleItem.IMG:
                switch (helper.getLayoutPosition() % 4) {
                    case 0:
                        Glide.with(context).load(R.mipmap.vryanjing).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into((ImageView) helper.getView(R.id.image_large));
                        break;
                    case 1:
                        Glide.with(context).load(R.mipmap.saodijiqiren).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into((ImageView) helper.getView(R.id.image_large));
                        break;
                    case 2:
                        Glide.with(context).load(R.mipmap.jingshhuiqi).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into((ImageView) helper.getView(R.id.image_large));
                        break;
                    case 3:
                        Glide.with(context).load(R.mipmap.erji).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into((ImageView) helper.getView(R.id.image_large));
                        break;
                }
                break;

        }
        helper.addOnClickListener(R.id.iv);
        helper.addOnClickListener(R.id.lly);
    }


}
