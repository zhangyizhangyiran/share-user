package com.soe.sharesoe.module.home.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.Constant;
import com.soe.sharesoe.entity.HotRecommendModel;
import com.soe.sharesoe.entity.TopicGoodsParams;
import com.soe.sharesoe.module.sort.category.item.CategoryListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 热门推荐商品条目适配器
 * @encoding UTF-8
 * @date 2017/11/22
 * @time 下午7:31
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class HotRecommendAdpter extends BaseAdapter {

    private List<HotRecommendModel> mList;// 定义一个list对象
    private Context mContext;// 上下文
    public static final int APP_PAGE_SIZE = 4;// 每一页装载数据的大小
    private PackageManager pm;// 定义一个PackageManager对象

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param list    所有APP的集合
     * @param page    当前页
     */
    public HotRecommendAdpter(Context context, List<HotRecommendModel> list, int page) {
        mContext = context;
        pm = context.getPackageManager();
        mList = new ArrayList<HotRecommendModel>();
        // 根据当前页计算装载的应用，每页只装载8个
        int i = page * APP_PAGE_SIZE;// 当前页的其实位置
        int iEnd = i + APP_PAGE_SIZE;// 所有数据的结束位置
        while ((i < list.size()) && (i < iEnd)) {
            mList.add(list.get(i));
            i++;
        }
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_home_recom_top, parent, false);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.item_hot_recommend_img);
        TextView name = (TextView) convertView.findViewById(R.id.item_hot_recommend_title);
        TextView content = (TextView) convertView.findViewById(R.id.item_hot_recommend_content);


        final HotRecommendModel appInfo = mList.get(position);
        Glide.with(mContext).load(appInfo.getIconUrl()).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into(icon);
        name.setText(appInfo.getTopicName());
        content.setText(appInfo.getSecondName());


        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                long topicId = appInfo.getId();
                TopicGoodsParams topGoodsParams = new TopicGoodsParams();
                topGoodsParams.setPageId(101);
                topGoodsParams.setTopicId(topicId);
                topGoodsParams.setLatitude(0);
                topGoodsParams.setLongitude(0);
                mContext.startActivity(new Intent(mContext, CategoryListActivity.class).putExtra(Constant.TOPIC_GOODS_PARAMS, topGoodsParams));

            }
        });
        return convertView;
    }

}
