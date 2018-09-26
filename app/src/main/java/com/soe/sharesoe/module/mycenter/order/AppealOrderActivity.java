package com.soe.sharesoe.module.mycenter.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.utils.FullyGridLayoutManager;
import com.soe.sharesoe.widget.dialog.RefundReasonDialog;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 申请订单
 * @encoding UTF-8
 * @date 2017/11/7
 * @time 上午11:06
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class AppealOrderActivity extends RxBaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right_img)
    ImageView barRightImg;
    @BindView(R.id.appeal_order_ry)
    RecyclerView recyclerView;

    //    相册
    private static final int REQUEST_LIST_CODE = 0;
    //    拍照
    private static final int REQUEST_CAMERA_CODE = 1;
    List<String> pathList = new ArrayList<>();
    private GridImageAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_appeal_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("申诉订单");

        FullyGridLayoutManager manager = new FullyGridLayoutManager(AppealOrderActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(AppealOrderActivity.this, onAddPicClickListener);
        adapter.setList(pathList);
        adapter.setSelectMax(4);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.bar_left_img, R.id.ll_current_goods, R.id.appeal_order_ry})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bar_left_img:

                finish();
                break;
            case R.id.ll_current_goods:

                new RefundReasonDialog().Show(AppealOrderActivity.this, new RefundReasonDialog.ReasonClickListenr() {
                    @Override
                    public void onClick(String payChannel) {

                    }
                });
                break;
            case R.id.appeal_order_ry:

                break;
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 自由配置选项
            Multiselect();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");

            // 图片选择结果回调
            adapter.setList(pathList);
            adapter.notifyDataSetChanged();
        }
    }

    public void Multiselect() {
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录
                .rememberSelected(false)
                // 使用沉浸式状态栏,标题栏颜色
                .statusBarColor(Color.parseColor("#393a3e"))
                .titleBgColor(Color.parseColor("#393a3e"))
                .backResId(R.drawable.ic_back)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 裁剪大小。needCrop为true的时候配置
                //.cropSize(1, 1, 200, 200)
                .maxNum(4)
                .build();

        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
    }

}
