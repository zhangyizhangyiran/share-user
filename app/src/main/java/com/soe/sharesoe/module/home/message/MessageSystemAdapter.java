package com.soe.sharesoe.module.home.message;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;
import com.soe.sharesoe.entity.Status;

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

public class MessageSystemAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {

    public MessageSystemAdapter() {
        super(R.layout.item_message_system, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setImageResource(R.id.item_message_system_img_hot, R.mipmap.ssdk_oks_classic_qq);
        helper.setText(R.id.item_message_system_tv_title, "标题标题标题标题标题标题标题标题");
        helper.setText(R.id.item_message_system_tv_details, "详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情");
        ((TextView) helper.getView(R.id.item_message_system_tv_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
