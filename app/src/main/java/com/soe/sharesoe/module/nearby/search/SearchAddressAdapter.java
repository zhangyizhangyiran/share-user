package com.soe.sharesoe.module.nearby.search;

import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;
import com.soe.sharesoe.module.mycenter.wallet.AccountRechargeFlowList;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/27
 * @time 下午3:17
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class SearchAddressAdapter extends BaseQuickAdapter<PoiInfo, BaseViewHolder> {

    public SearchAddressAdapter() {
        super(R.layout.item_search_add, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiInfo item) {
        helper.setText(R.id.tvCity, item.name);

        helper.setText(R.id.tvAddress, item.address);
    }
}
