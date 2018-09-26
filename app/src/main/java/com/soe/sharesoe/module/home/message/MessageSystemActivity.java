package com.soe.sharesoe.module.home.message;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.Status;

import com.soe.sharesoe.widget.CustomEmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

public class MessageSystemActivity extends RxBaseActivity {


    @BindView(R.id.bar_left_img)
    ImageView bar_left_img;
    @BindView(R.id.bar_title)
    TextView bar_title;

    @BindView(R.id.act_message_system_recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.act_message_system_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.act_message_system_emptylayout)
    CustomEmptyView mCustomEmptyView;

    private MessageSystemAdapter mAdapter;
    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 10;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_system;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        bar_title.setText("系统通知");

        initAdapter();
        initRefreshLayout();
        refresh();
    }

    @OnClick({R.id.bar_left_img})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.bar_left_img:
                finish();
                break;

            default:
                break;
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MessageSystemAdapter();
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
                Toast.makeText(MessageSystemActivity.this, Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }


    protected void initRefreshLayout() {

        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.Color_Theme_Main_Green);
//        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
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
                loadMore();
            }
        });

    }
    private void loadMore() {
        List<Status> data = new ArrayList<Status>();
        Status status = new Status();
        status.setCreatedAt("qqq");
        status.setRetweet(true);
        status.setText("sss");
        status.setUserAvatar("svcvcvc");
        status.setUserName("fffff");

        data.add(status);
        data.add(status);
        data.add(status);
        data.add(status);

        setData(false, data);
        mSwipeRefreshLayout.setRefreshing(false);



//        mAdapter.loadMoreFail();

    }

    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载


        List<Status> data = new ArrayList<Status>();
        Status status = new Status();
        status.setCreatedAt("qqq");
        status.setRetweet(true);
        status.setText("sss");
        status.setUserAvatar("svcvcvc");
        status.setUserName("fffff");

        data.add(status);
        data.add(status);
        data.add(status);
        data.add(status);
        data.add(status);
        data.add(status);
        data.add(status);
        data.add(status);

        setData(true, data);
        mAdapter.setEnableLoadMore(true);
        mSwipeRefreshLayout.setRefreshing(false);



//        mAdapter.setEnableLoadMore(true);
//        mSwipeRefreshLayout.setRefreshing(false);


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
            Toast.makeText(MessageSystemActivity.this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }


    private void initEmptyView() {

        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.icon_no_notice);
        mCustomEmptyView.setEmptyText("目前还没有消息哦～");
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
