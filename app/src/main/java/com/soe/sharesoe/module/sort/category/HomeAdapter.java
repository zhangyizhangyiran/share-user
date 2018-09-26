package com.soe.sharesoe.module.sort.category;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.entity.CategorySubModel;

import java.util.List;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description  右侧主界面ListView的适配器
 * @encoding UTF-8
 * @date 2017/11/9
 * @time 上午11:25
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<CategorySubModel> foodDatas;

    public HomeAdapter(Context context, List<CategorySubModel> foodDatas) {
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
        CategorySubModel dataBean = foodDatas.get(position);
        List<CategorySubModel.ProductListBean> dataList = dataBean.getProductList();
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home, null);
            viewHold = new ViewHold();
            viewHold.gridView = (GridViewForScrollView) convertView.findViewById(R.id.gridView);
            viewHold.blank = (TextView) convertView.findViewById(R.id.blank);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        HomeItemAdapter adapter = new HomeItemAdapter(context, dataList);
        viewHold.blank.setText(dataBean.getCategoryName());
        viewHold.gridView.setAdapter(adapter);


        return convertView;
    }

    private static class ViewHold {
        private GridViewForScrollView gridView;
        private TextView blank;
    }

}
