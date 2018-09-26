package com.soe.sharesoe.module.sort.category.item;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.Constant;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.TopicGoodsParams;
import com.soe.sharesoe.module.goods.GoodsDetailActivity;
import com.soe.sharesoe.module.home.ProductList;
import com.soe.sharesoe.net.RetrofitHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 商品list列表, 距离排序 租金排序
 * @encoding UTF-8
 * @date 2017/11/8
 * @time 下午4:02
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CategoryListActivity extends RxBaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right_img)
    ImageView barRightImg;
    @BindView(R.id.category_list_rg)
    RadioGroup category_list_rg;
    @BindView(R.id.category_list_distance_sort)
    RadioButton categoryListDistanceSort;
    @BindView(R.id.category_list_rental_sort)
    RadioButton categoryListRentalSort;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CategoryListAdapter mAdapter;

    private int mNextRequestPage = 1;
    private String order = "";
    private static final int PAGE_SIZE = 4;
    private boolean isAsc = true;

    TopicGoodsParams topicGoodsParams;
    int pageId = 0;
    long topicId = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_category_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("商品列表");
        Intent i = getIntent();
        topicGoodsParams = (TopicGoodsParams) i.getSerializableExtra(Constant.TOPIC_GOODS_PARAMS);

        if (topicGoodsParams != null) {
            pageId = topicGoodsParams.getPageId();
            topicId = topicGoodsParams.getTopicId();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        initRefreshLayout();

        category_list_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                // desc1 押金倒序 asc1 押金升序
                // desc2 距离倒序 asc2 距离升序
                switch (checkedId) {
                    case R.id.category_list_distance_sort:
                        categoryListRentalSort.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.icon_desc_default, 0);
                        isAsc = true;
                        order = "";
                        mNextRequestPage = 1;
                        postRefreshData();
                        break;
                    case R.id.category_list_rental_sort:
                        mNextRequestPage = 1;
                        order = "asc1";
                        postRefreshData();
                        break;
                }

            }
        });
    }

    @OnClick({R.id.category_list_distance_sort, R.id.category_list_rental_sort, R.id.bar_left_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.category_list_distance_sort:


                break;
            case R.id.category_list_rental_sort:
                if (isAsc) {
                    categoryListRentalSort.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.icon_desc_up, 0);

                    order = "asc1";
                    isAsc = false;
                } else {
                    categoryListRentalSort.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.icon_desc_down, 0);

                    order = "desc1";
                    isAsc = true;
                }
                mNextRequestPage = 1;
                postRefreshData();
                break;
            case R.id.bar_left_img:
                finish();
                break;
        }
    }

    private void initAdapter() {
        mAdapter = new CategoryListAdapter(CategoryListActivity.this);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mNextRequestPage++;
                getProductList();
            }
        });
//        mAdapter.openLoadAnimation(BaseQuickAdapter.);
//        mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {

                String pid = ((ProductList.ListBean) adapter.getData().get(position)).getId();
                int status = ((ProductList.ListBean) adapter.getData().get(position)).getStatus();
                startActivity(new Intent(CategoryListActivity.this, GoodsDetailActivity.class)
                        .putExtra("pId", pid)
                        .putExtra("status", status)
                );

            }
        });
    }


    private void initRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNextRequestPage = 1;
                getProductList();
            }
        });
        postRefreshData();
    }

    private void postRefreshData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getProductList();
            }
        });

    }


    private void setData(boolean isRefresh, List data) {

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

    private void getProductList() {
        Map<String, Object> map = new HashMap<String, Object>();

        if (pageId == 101) {

            map.put("latitude", "");
            map.put("longitude", "");
            map.put("keyWord", "");
            map.put("categoryId", "");
            map.put("recommend", "");
            map.put("order", order);
            map.put("page", mNextRequestPage);
            map.put("pageSize", "20");
            map.put("topicId", topicId);
            RetrofitHelper.getInstance().getHotRecommendGoods(map, new Subscriber<ProductList>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    if (mNextRequestPage == 1) {
                        mAdapter.setEnableLoadMore(true);
                        mSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        mAdapter.loadMoreFail();
                    }
                }

                @Override
                public void onNext(ProductList productLists) {
                    if (mNextRequestPage == 1) {
                        setData(true, productLists.getList());
                        mAdapter.setEnableLoadMore(true);
                        mSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        setData(false, productLists.getList());
                    }

                }
            });

        } else {
            map.put("latitude", "");
            map.put("longitude", "");
            map.put("keyWord", "");
            map.put("categoryId", "");
            map.put("recommend", "");
            map.put("order", order);
            map.put("page", mNextRequestPage);
            map.put("pageSize", "20");
            RetrofitHelper.getInstance().getProductList(map, new Subscriber<ProductList>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    if (mNextRequestPage == 1) {
                        mAdapter.setEnableLoadMore(true);
                        mSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        mAdapter.loadMoreFail();
                    }
                }

                @Override
                public void onNext(ProductList productLists) {
                    if (mNextRequestPage == 1) {
                        setData(true, productLists.getList());
                        mAdapter.setEnableLoadMore(true);
                        mSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        setData(false, productLists.getList());
                    }

                }
            });
        }
    }

}
