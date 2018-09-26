package com.soe.sharesoe.module.home.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/2
 * @time 上午11:20
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class MessageActivity extends RxBaseActivity {

    @BindView(R.id.bar_left_img)
    ImageView bar_left_img;
    @BindView(R.id.bar_title)
    TextView bar_title;
    @BindView(R.id.act_ll_message_system)
    LinearLayout act_ll_message_system;
    @BindView(R.id.act_ll_message_news)
    LinearLayout act_ll_message_news;


    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        bar_title.setText("消息");
    }
    @OnClick({R.id.bar_left_img,R.id.act_ll_message_system,R.id.act_ll_message_news})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
            case R.id.act_ll_message_system:
                startActivity(new Intent(this,MessageSystemActivity.class));

                break;
            case R.id.act_ll_message_news:

                break;
            default:
                break;
        }
    }
}
