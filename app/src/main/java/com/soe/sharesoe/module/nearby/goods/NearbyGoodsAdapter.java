package com.soe.sharesoe.module.nearby.goods;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;
import com.soe.sharesoe.entity.Status;
import com.soe.sharesoe.module.home.ProductList;
import com.soe.sharesoe.module.nearby.map.cupboard.CupboardProduct;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/27
 * @time 上午10:57
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class NearbyGoodsAdapter extends BaseQuickAdapter<CupboardProduct, BaseViewHolder> {

    public NearbyGoodsAdapter() {
        super(R.layout.item_nearby_goods, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, CupboardProduct item) {
        Glide.with(mContext).load(item.getProductPic()).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into((ImageView) helper.getView(R.id.item_img_nearbygoods_img));

        if (0 == item.getSource()) {
            helper.setText(R.id.item_tv_nearbygoods_type, "自营");
        } else if (1 == item.getSource()) {
            helper.setText(R.id.item_tv_nearbygoods_type, "个人");
        } else if (2 == item.getSource()) {
            helper.setText(R.id.item_tv_nearbygoods_type, "合作商");
        } else {
            helper.setText(R.id.item_tv_nearbygoods_type, "");
        }

        helper.setText(R.id.item_tv_nearbygoods_name, item.getProductName());
        helper.setText(R.id.item_tv_nearbygoods_details, item.getIntroduction());
        helper.setText(R.id.item_tv_nearbygoods_price, item.getRental());
        helper.setText(R.id.item_tv_nearbygoods_deposit, "押金：" + item.getDeposit());

    }

}
