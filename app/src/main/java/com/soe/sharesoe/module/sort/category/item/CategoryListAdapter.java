package com.soe.sharesoe.module.sort.category.item;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;
import com.soe.sharesoe.module.home.ProductList;

/**
 * @author sj jerry <SuiJing, suijing@foxmail.com>
 * @version ${VERSIONCODE}
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date ${DATA}
 * @time 下午7:17
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CategoryListAdapter extends BaseQuickAdapter<ProductList.ListBean, BaseViewHolder> {

    private Activity mActivity;

    public CategoryListAdapter(Activity mActivity) {
        super(R.layout.item_home_category_list, null);
        this.mActivity = mActivity;
    }


    @Override
    protected void convert(BaseViewHolder helper, ProductList.ListBean item) {

        switch (item.getSource()) {
            case 0:

                helper.setText(R.id.center_category_goods_type, "自营");
                break;
            case 1:
                helper.setText(R.id.center_category_goods_type, "个人");
                break;
            case 2:
                helper.setText(R.id.center_category_goods_type, "合作商户");
                break;
        }
        Glide.with(mActivity)
                .load(item.getProductPic())
                .placeholder(R.mipmap.ic_default_logo)
                .error(R.mipmap.ic_default_logo)
                .dontAnimate()
                .into((ImageView) helper.getView(R.id.center_category_img));
        helper.setText(R.id.center_category_title, item.getProductName());
        helper.setText(R.id.center_category_child_title, item.getIntroduction());
        helper.setText(R.id.center_category_deposit_price, "押金：" + item.getDeposit());
        helper.setText(R.id.center_category_rental_price, item.getRental());
        helper.setText(R.id.center_category_distance, "500m");
    }
}
