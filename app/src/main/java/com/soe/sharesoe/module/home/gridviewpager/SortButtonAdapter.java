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
import com.soe.sharesoe.module.mycenter.view.CircleImageView;

import java.util.List;


public class SortButtonAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<CategoryNavigation> data;

    public SortButtonAdapter(Context context, List<CategoryNavigation> data) {
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
            view = inflater.inflate(R.layout.item_home_sort_gridview, null);
            holder = new ViewHolder();
            holder.icon = (CircleImageView) view.findViewById(R.id.imageView);
            holder.name = (TextView) view.findViewById(R.id.textView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(data.get(position).getCategoryName());
        Glide.with(mContext).load(data.get(position).getCategoryLogo()).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into(holder.icon);

        return view;
    }

    class ViewHolder {
        CircleImageView icon;
        TextView name;
    }

}
