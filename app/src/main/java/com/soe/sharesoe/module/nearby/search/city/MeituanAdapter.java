package com.soe.sharesoe.module.nearby.search.city;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.soe.sharesoe.R;
import com.soe.sharesoe.module.nearby.search.nearby.NearbyAddress;
import com.soe.sharesoe.utils.T;

import java.util.List;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/1
 * @time 上午10:45
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class MeituanAdapter extends CommonAdapter<NearbyAddress> {
    public MeituanAdapter(Context context, int layoutId, List<NearbyAddress> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final NearbyAddress nearbyAddress) {
        holder.setText(R.id.tvCity, nearbyAddress.getName());
    }
}