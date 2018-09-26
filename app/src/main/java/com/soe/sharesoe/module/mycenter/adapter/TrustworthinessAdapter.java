package com.soe.sharesoe.module.mycenter.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.module.mycenter.bean.CreditChangeModel;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date 17/11/7
 * @time 上午11:43
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class TrustworthinessAdapter extends BaseQuickAdapter<CreditChangeModel.ResultBean.ListBean, BaseViewHolder> {

    public TrustworthinessAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditChangeModel.ResultBean.ListBean item) {

    }

}
