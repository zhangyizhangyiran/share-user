package com.soe.sharesoe.module.mycenter.wallet;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxLazyFragment;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.widget.CustomEmptyView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Subscriber;


/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/27
 * @time 下午3:11
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class TransDetailsFragment extends RxLazyFragment {

    @BindView(R.id.frg_transdetaisl_recycle_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.frg_transdetaisl_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.frg_transdetaisl_empty_layout)
    CustomEmptyView mCustomEmptyView;

    private TransDetailsAdapter mAdapter;
    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 20;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_trans_details;
    }


    @Override
    public void finishCreateView(Bundle state) {
        init();
    }

    protected void init() {
        initAdapter();
        initRefreshLayout();
    }

    private void getAccountTransactionDetails() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", String.valueOf(mNextRequestPage));
        map.put("pageSize", String.valueOf(PAGE_SIZE));
        RetrofitHelper.getInstance().getAccountTransactionDetails(map, new Subscriber<AccountTransactionDetails>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mNextRequestPage == 1) {
                    mAdapter.setEnableLoadMore(true);
                    mSwipeRefreshLayout.setRefreshing(false);
                    initEmptyView();
                } else {
                    mAdapter.loadMoreFail();
                    hideEmptyView();
                }
            }

            @Override
            public void onNext(AccountTransactionDetails accountTransactionDetails) {
                List<?> records = accountTransactionDetails.getRecords();
                if (mNextRequestPage == 1) {
                    setData(true, records);
                    mAdapter.setEnableLoadMore(true);
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    setData(false, records);
                }
            }
        });
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TransDetailsAdapter();
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
//        mAdapter.openLoadAnimation(BaseQuickAdapter.);
//        mAdapter.setPreLoadNumber(3);

        View mTopView = getActivity().getLayoutInflater().inflate(R.layout.include_trans_details_topview, (ViewGroup) mRecyclerView.getParent(), false);

        mAdapter.addHeaderView(mTopView);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }


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
                mSwipeRefreshLayout.setRefreshing(true);
                refresh();
            }
        });

    }

    private void loadMore() {
        getAccountTransactionDetails();
    }

    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        getAccountTransactionDetails();
    }


    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size <= 0) {
                initEmptyView();
            } else {
                hideEmptyView();
                mAdapter.setNewData(data);
            }
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(false);
        } else {
            mAdapter.loadMoreComplete();
        }
    }


    private void initEmptyView() {

        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.icon_no_trandetails);
        mCustomEmptyView.setEmptyText("您目前还没有交易明细哦～");
//        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    /**
     * 隐藏empty视图
     */
    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
