package com.soe.sharesoe.module.mycenter.reserve;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.ProductReserveModel;
import com.soe.sharesoe.entity.ResultModel;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.widget.CustomEmptyView;
import com.soe.sharesoe.widget.dialog.PromptDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static com.soe.sharesoe.R.id.bar_title;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 预约列表
 * @encoding UTF-8
 * @date 2017/11/15
 * @time 下午3:38
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ProductReserveActivity extends RxBaseActivity {

    @BindView(bar_title)
    TextView barTitle;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.frg_honm_empty_layout)
    CustomEmptyView mCustomEmptyView;

    private ProductReserveAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_reserve;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("已预约");

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setRefreshing(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        initRefreshLayout();
    }


    @OnClick({R.id.bar_left_img})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
        }
    }


    /**
     * 初始化适配器
     */
    private void initAdapter() {


        mAdapter = new ProductReserveAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {

                switch (view.getId()) {
                    case R.id.bottom_goods_order_btn1:
                        PromptDialog.showDialog(ProductReserveActivity.this, "确定取消预约吗？", new PromptDialog.ReserveClickListenr() {
                            @Override
                            public void onClick() {

                                List<ProductReserveModel> data = mAdapter.getData();

                                //取消订单并从视图中删除
                                productReserveCancel(data.get(position).getReverseSn(), position);
                                mAdapter.remove(position);

                                //当预约列表中没有数据时，显示空view
                                if (data.size() <= 0) {
                                    initEmptyView();
                                }

                            }
                        });
                        break;

                }

            }
        });
    }


    /**
     * @author wangxiaofa
     * @time 2017/11/21 下午3:24
     * @Description 初始化
     */
    protected void initRefreshLayout() {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.Color_Theme_Main_Green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });

    }


    /**
     * @author wangxiaofa
     * @time 2017/11/10 下午3:08
     * @Description 刷新数据
     */
    private void refresh() {
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        RetrofitHelper.getInstance().getProductReserveList(new Subscriber<List<ProductReserveModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mAdapter.setEnableLoadMore(true);
                initEmptyView();

            }

            @Override
            public void onNext(List<ProductReserveModel> productReserveModels) {
                mAdapter.cancelAllTimers();
                mAdapter.setNewData(productReserveModels);
                mAdapter.setEnableLoadMore(true);

                if (productReserveModels.size() <= 0) {
                    initEmptyView();
                } else {
                    hideEmptyView();

                }
            }
        });
    }

    /**
     * @author wangxiaofa
     * @time 2017/11/10 下午3:08
     * @Description 取消预约
     */
    private void productReserveCancel(String reserveSn, final int position) {
        RetrofitHelper.getInstance().getProductReserveCancel(reserveSn, new Subscriber<ResultModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultModel result) {
                Toast.makeText(ProductReserveActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示empty视图
     */
    private void initEmptyView() {

        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.ic_no_reserve);
        mCustomEmptyView.setEmptyText("您没有预约的商品哦~");
//        Snail.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    /**
     * 隐藏empty视图
     */
    public void hideEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cancelAllTimers();
    }
}
