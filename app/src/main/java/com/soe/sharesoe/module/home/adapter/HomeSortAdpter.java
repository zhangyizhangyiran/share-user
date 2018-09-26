package com.soe.sharesoe.module.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soe.sharesoe.R;
import com.soe.sharesoe.module.home.gridviewpager.CategoryNavigation;
import com.soe.sharesoe.module.mycenter.view.CircleImageView;
import com.soe.sharesoe.module.sort.category.CategoryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 分类适配器
 * @encoding UTF-8
 * @date 2017/11/22
 * @time 下午7:30
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class HomeSortAdpter extends BaseAdapter {
    private List<CategoryNavigation> mList;// 定义一个list对象
    private Context mContext;// 上下文
    public static final int APP_PAGE_SIZE = 8;// 每一页装载数据的大小
    private PackageManager pm;// 定义一个PackageManager对象

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param list    所有APP的集合
     * @param page    当前页
     */
    public HomeSortAdpter(Context context, List<CategoryNavigation> list, int page) {
        mContext = context;
        pm = context.getPackageManager();
        mList = new ArrayList<CategoryNavigation>();
        // 根据当前页计算装载的应用，每页只装载8个
        int i = page * APP_PAGE_SIZE;// 当前页的其实位置
        int iEnd = i + APP_PAGE_SIZE;// 所有数据的结束位置
        while ((i < list.size()) && (i < iEnd)) {
            mList.add(list.get(i));
            i++;
        }
    }

    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_home_sort_gridview, parent, false);
        }
        final CategoryNavigation appInfo = mList.get(position);
        CircleImageView appicon = (CircleImageView) convertView.findViewById(R.id.imageView);
        final TextView appname = (TextView) convertView.findViewById(R.id.textView);
        Glide.with(mContext).load(appInfo.getCategoryLogo()).into(appicon);
        appname.setText(appInfo.getCategoryName());

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CategoryActivity.class).putExtra("cid", appInfo.getId()));
            }
        });
        return convertView;
    }

}
