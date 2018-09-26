package com.soe.sharesoe.module.sort.category;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soe.sharesoe.R;
import com.soe.sharesoe.entity.CategorySubModel;
import com.soe.sharesoe.module.goods.GoodsDetailActivity;

import java.util.List;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 右侧item的适配器
 * @encoding UTF-8
 * @date 2017/11/2 下午3:15
 * @time 上午11:26
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class HomeItemAdapter extends BaseAdapter {

    private Context context;
    private List<CategorySubModel.ProductListBean> foodDatas;

    public HomeItemAdapter(Context context, List<CategorySubModel.ProductListBean> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }


    @Override
    public int getCount() {
        if (foodDatas != null) {
            return foodDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CategorySubModel.ProductListBean subcategory = foodDatas.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home_category, null);
            viewHold = new ViewHold();
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.item_home_name);
            viewHold.iv_icon = (ImageView) convertView.findViewById(R.id.item_album);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(subcategory.getProductName());
        Uri uri = Uri.parse(subcategory.getProductPic().trim());

        Glide.with(context)
                .load(uri)
                .placeholder(R.mipmap.ic_default_logo)
                .error(R.mipmap.ic_default_logo)
                .dontAnimate()
                .into(viewHold.iv_icon);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GoodsDetailActivity.class)
                        .putExtra("pId", subcategory.getId())
                        .putExtra("status", subcategory.getStatus())
                );

            }
        });

        return convertView;


    }

    private static class ViewHold {
        private TextView tv_name;
        private ImageView iv_icon;
    }

}
