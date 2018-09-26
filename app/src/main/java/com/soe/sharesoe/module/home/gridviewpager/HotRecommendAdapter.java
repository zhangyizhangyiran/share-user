package com.soe.sharesoe.module.home.gridviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soe.sharesoe.R;
import com.soe.sharesoe.entity.HotRecommendModel;

import java.util.List;


public class HotRecommendAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<HotRecommendModel> data;

    public HotRecommendAdapter(Context context, List<HotRecommendModel> data) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (null == view) {
            view = inflater.inflate(R.layout.item_home_recom_top, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.item_hot_recommend_img);
            holder.name = (TextView) view.findViewById(R.id.item_hot_recommend_title);
            holder.content = (TextView) view.findViewById(R.id.item_hot_recommend_content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(data.get(position).getTopicName());
        holder.content.setText(data.get(position).getSecondName());
        Glide.with(mContext).load(data.get(position).getIconUrl()).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into(holder.icon);

        return view;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
        TextView content;
    }

}
