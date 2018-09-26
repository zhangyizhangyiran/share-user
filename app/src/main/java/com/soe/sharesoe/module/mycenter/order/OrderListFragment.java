package com.soe.sharesoe.module.mycenter.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.orhanobut.logger.Logger;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.Constant;
import com.soe.sharesoe.base.RxLazyFragment;
import com.soe.sharesoe.entity.ProductOrderListModel;
import com.soe.sharesoe.entity.ProductReturnModel;
import com.soe.sharesoe.entity.ResultModel;
import com.soe.sharesoe.module.qrcode.LockManager;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.rxpay.Pay;
import com.soe.sharesoe.utils.T;
import com.soe.sharesoe.widget.CustomEmptyView;
import com.soe.sharesoe.widget.dialog.PayDialog;
import com.soe.sharesoe.widget.dialog.PromptDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Subscriber;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 订单列表
 * @encoding UTF-8
 * @date 2017/11/14
 * @time 下午8:14
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class OrderListFragment extends RxLazyFragment {


    @BindView(R.id.recycle)
    SlideRecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    private OrderListAdapter mAdapter;
    private int mNextRequestPage = 1;
    private final int PAGE_SIZE = 20;//每页展示个数

    private int mType = 1;


    public static OrderListFragment newInstance(int mType) {
        OrderListFragment mFragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ORDER, mType);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_live;
    }

    @Override
    public void finishCreateView(Bundle state) {

        mType = getArguments().getInt(Constant.EXTRA_ORDER);
        isPrepared = true;
        lazyLoad();
    }
    @Override
    protected void lazyLoad() {
        Log.i("lazyLoad==>",isVisible+",isPrepared="+isPrepared);
        if (!isPrepared) {
            return;
        }
        if (isVisible) {
            Log.i("lazyLoad==>", isVisible + "888");
            initAdapter();
            initRefreshLayout();
        }
    }

    private void initAdapter() {

        mAdapter = new OrderListAdapter(getActivity());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                String sn = ((ProductOrderListModel.RecordsBean) adapter.getData().get(position)).getSn();
                startActivity(new Intent(getActivity(), OrderDetailActivity.class)
                        .putExtra("sn", sn));
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                ProductOrderListModel.RecordsBean recordsBean = (ProductOrderListModel.RecordsBean) mAdapter.getData().get(position);

                switch (view.getId()) {
                    case R.id.bottom_goods_order_btn1:
                        PromptDialog.showDialog(getActivity(), "确定取消订单吗？", new PromptDialog.ReserveClickListenr() {
                            @Override
                            public void onClick() {

                                setOrderCancelData(((ProductOrderListModel.RecordsBean) adapter.getData().get(position)).getSn());
                                adapter.remove(position);
                            }
                        });

                        break;
                    case R.id.bottom_goods_order_btn2:

                        setProductReturn(recordsBean);
                        break;
                    case R.id.bottom_goods_order_btn3:

                        if (recordsBean.getStatus() == 100) {
                            payProductOrder(recordsBean, recordsBean.getDeposit());
                        } else if (recordsBean.getStatus() == 300) {
                            payProductOrder(recordsBean, recordsBean.getAmount());
                        }
                        break;
                }

            }
        });
    }

    public void initRefreshLayout() {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.Color_Theme_Main_Green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refresh();

    }

    /**
     * 取消订单
     *
     * @param orderSn
     */
    private void setOrderCancelData(String orderSn) {
        RetrofitHelper.getInstance().getProductOrderCancel(orderSn, new Subscriber<ResultModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultModel resultModel) {

            }
        });
    }

    /**
     * 商品归还
     *
     * @param recordsBean
     */
    private void setProductReturn(final ProductOrderListModel.RecordsBean recordsBean) {

        RetrofitHelper.getInstance().getProductReturn(recordsBean.getSn(), new Subscriber<ProductReturnModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ProductReturnModel orderDetailModel) {
                String rent = orderDetailModel.getParams().getRent();

                Map<String, String> map = new HashMap<String, String>();
                map.put("payCategory", "2");
                map.put("payAmount", rent);
                map.put("orderSn", recordsBean.getSn());

                PayDialog.payShow(getActivity(), new Pay.PayResultListenr() {
                    @Override
                    public void onResult(boolean status, String msg) {
                        Logger.d(status + "===" + msg);
                        T.normal(msg + "");
                        refresh();
                    }
                }, map);
            }
        });
    }

    /**
     * 支付押金、租金
     *
     * @param recordsBean
     */
    private void payProductOrder(final ProductOrderListModel.RecordsBean recordsBean, String money) {

        Map<String, String> map = new HashMap<String, String>();

        if (recordsBean.getStatus() == 100) {

            map.put("payCategory", "1");
        } else if (recordsBean.getStatus() == 300) {

            map.put("payCategory", "2");
        }
        map.put("payAmount", money);
        map.put("orderSn", recordsBean.getSn());

        PayDialog.payShow(getActivity(), new Pay.PayResultListenr() {
            @Override
            public void onResult(boolean status, String msg) {
                Logger.d(status + "===" + msg);
                T.normal(msg + "");
                if (recordsBean.getStatus() == 100) {
                    new LockManager(getActivity()).openLockData(recordsBean.getSn(), Constant.PAGE_ORDER_LIST);//通知开锁成功给服务器
                    mAdapter.notifyDataSetChanged();
                }
                refresh();
            }
        }, map);
    }

    private void loadMore() {
        RetrofitHelper.getInstance().getProductOrderList(mType, mNextRequestPage, PAGE_SIZE, new Subscriber<ProductOrderListModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mAdapter.loadMoreFail();
            }

            @Override
            public void onNext(ProductOrderListModel productOrderListModel) {

                setData(false, productOrderListModel.getRecords());
            }
        });

    }

    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        RetrofitHelper.getInstance().getProductOrderList(mType, mNextRequestPage, PAGE_SIZE, new Subscriber<ProductOrderListModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mAdapter.setEnableLoadMore(true);
                setStopSwipeRefreshing();
                initEmptyView();
            }

            @Override
            public void onNext(ProductOrderListModel productOrderListModel) {

                if (productOrderListModel.getRecords().size() <= 0) {
                    initEmptyView();
                } else {
                    setData(true, productOrderListModel.getRecords());
                    mAdapter.setEnableLoadMore(true);
                    setStopSwipeRefreshing();

                    hideEmptyView();
                }
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

        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.icon_no_order);
        mCustomEmptyView.setEmptyText("空空如也哦~");
//        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    /**
     * 隐藏empty视图
     */
    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    protected void finishTask() {

        hideEmptyView();
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * 停止刷新
     */
    private void setStopSwipeRefreshing() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
