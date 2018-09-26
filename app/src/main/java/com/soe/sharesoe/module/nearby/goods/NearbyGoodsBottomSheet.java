package com.soe.sharesoe.module.nearby.goods;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.soe.sharesoe.R;
import com.soe.sharesoe.module.goods.GoodsDetailActivity;
import com.soe.sharesoe.module.home.ProductList;
import com.soe.sharesoe.module.main.OnChangeTabListener;
import com.soe.sharesoe.module.nearby.map.cupboard.CupboardInfo;
import com.soe.sharesoe.module.nearby.map.cupboard.CupboardProduct;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.widget.CustomEmptyView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/10
 * @time 下午5:21
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class NearbyGoodsBottomSheet {

    RecyclerView mRecyclerView;
    CustomEmptyView mCustomEmptyView;
    private NearbyGoodsAdapter mAdapter;
    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 10;
    ImageView fra_img_go_top;

    FragmentActivity activity;
    BottomSheetLayout bottomSheet;

    View mCupboardTop;
    TextView item_tv_cupboard_name;
    TextView item_tv_cupboard_location;
    TextView item_tv_cupboard_bigbox;
    TextView item_tv_cupboard_midbox;
    TextView item_tv_cupboard_smallbox;
    TextView item_tv_cupboard_distance;

    String cupboardId;
    String distance;

    public NearbyGoodsBottomSheet(FragmentActivity activity, BottomSheetLayout bottomSheet, int cupboardId, double distance) {
        this.activity = activity;
        this.bottomSheet = bottomSheet;
        this.cupboardId = String.valueOf(cupboardId);
        this.distance = String.valueOf(Math.round(distance));

        EventBus.getDefault().register(this);

        getCupboardByCupboardId();

        getProductByCupboardId();

        View inflate = LayoutInflater.from(activity).inflate(R.layout.sheet_main, bottomSheet, false);
        mCustomEmptyView = (CustomEmptyView) inflate.findViewById(R.id.fra_map_empty_layout);
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.fra_map_recycle_view);
        fra_img_go_top = (ImageView) inflate.findViewById(R.id.fra_img_go_top);

        initTopView();

        initAdapter();

        mAdapter.addHeaderView(mCupboardTop);

        fra_img_go_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        bottomSheet.showWithSheetView(inflate);
        bottomSheet.setShouldDimContentView(false);
        bottomSheet.setPeekSheetTranslation(1010);
    }

    //切换页面回调
    @Subscribe
    public void onEvent(OnChangeTabListener onChangeTabListener) {
        bottomSheet.dismissSheet();
    }

    private void initTopView() {
        mCupboardTop = LayoutInflater.from(activity).inflate(R.layout.item_nearby_cupboard_top, null, false);
        item_tv_cupboard_name = (TextView) mCupboardTop.findViewById(R.id.item_tv_cupboard_name);
        item_tv_cupboard_location = (TextView) mCupboardTop.findViewById(R.id.item_tv_cupboard_location);
        item_tv_cupboard_bigbox = (TextView) mCupboardTop.findViewById(R.id.item_tv_cupboard_bigbox);
        item_tv_cupboard_midbox = (TextView) mCupboardTop.findViewById(R.id.item_tv_cupboard_midbox);
        item_tv_cupboard_smallbox = (TextView) mCupboardTop.findViewById(R.id.item_tv_cupboard_smallbox);
        item_tv_cupboard_distance = (TextView) mCupboardTop.findViewById(R.id.item_tv_cupboard_distance);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mAdapter = new NearbyGoodsAdapter();
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
//        mAdapter.openLoadAnimation(BaseQuickAdapter.);
//        mAdapter.setPreLoadNumber(3);


        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                activity.startActivity(new Intent(activity, GoodsDetailActivity.class).putExtra("pId", ((CupboardProduct) adapter.getItem(position)).getId()));

            }
        });
    }

    private void loadMore() {
        getProductByCupboardId();
    }


    private void getProductByCupboardId() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("cupboardId", cupboardId);
        RetrofitHelper.getInstance().getProductByCupboardId(map, new Subscriber<List<CupboardProduct>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mNextRequestPage == 1) {
                    mAdapter.setEnableLoadMore(true);
                } else {
                    mAdapter.loadMoreFail();
                }
            }

            @Override
            public void onNext(List<CupboardProduct> cupboardProductList) {
                if (mNextRequestPage == 1) {
                    setData(true, cupboardProductList);
                    mAdapter.setEnableLoadMore(true);
                } else {
                    setData(false, cupboardProductList);
                }

            }
        });
    }


    public void getCupboardByCupboardId() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("cupboardId", cupboardId);


        RetrofitHelper.getInstance().getCupboardByCupboardId(map, new Subscriber<CupboardInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CupboardInfo cupboardInfo) {
                item_tv_cupboard_name.setText(cupboardInfo.getCupboardName());
                item_tv_cupboard_location.setText(cupboardInfo.getCupboardLocation());
                item_tv_cupboard_bigbox.setText("空" + String.valueOf(cupboardInfo.getBigBoxFree()));
                item_tv_cupboard_midbox.setText("空" + String.valueOf(cupboardInfo.getMidBoxFree()));
                item_tv_cupboard_smallbox.setText("空" + String.valueOf(cupboardInfo.getSmallBoxFree()));
                item_tv_cupboard_distance.setText(distance+"m");
            }
        });
    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }


    private void initEmptyView() {
        mRecyclerView.setVisibility(View.GONE);
//        mCustomEmptyView.setVisibility(View.VISIBLE);
//        mCustomEmptyView.setEmptyImage(R.mipmap.logo);
//        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
    }

    /**
     * 隐藏empty视图
     */
    public void hideEmptyView() {
//        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

}
