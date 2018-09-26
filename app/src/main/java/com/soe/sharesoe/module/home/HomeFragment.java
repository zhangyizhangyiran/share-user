package com.soe.sharesoe.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxLazyFragment;
import com.soe.sharesoe.entity.BannerModel;
import com.soe.sharesoe.entity.HotRecommendModel;
import com.soe.sharesoe.module.goods.GoodsDetailActivity;
import com.soe.sharesoe.module.home.adapter.HomeSortAdpter;
import com.soe.sharesoe.module.home.adapter.HomeViewPagerAdapter;
import com.soe.sharesoe.module.home.adapter.HotRecommendAdpter;
import com.soe.sharesoe.module.home.adapter.IndicatorView;
import com.soe.sharesoe.module.home.gridviewpager.CategoryNavigation;
import com.soe.sharesoe.module.home.gridviewpager.IDynamicSore;
import com.soe.sharesoe.module.home.message.MessageActivity;
import com.soe.sharesoe.module.nearby.map.MyLocation;
import com.soe.sharesoe.module.nearby.map.MyLocationClient;
import com.soe.sharesoe.module.nearby.search.SearchNearbyAndCityActivity;
import com.soe.sharesoe.module.sort.search.SearchGoodsActivity;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.CommonUtil;
import com.soe.sharesoe.widget.CustomEmptyView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/26
 * @time 上午11:00
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class HomeFragment extends RxLazyFragment implements IDynamicSore {

    @BindView(R.id.bar_left_img)
    ImageView bar_left_img;
    @BindView(R.id.bar_center_img)
    ImageView bar_center_img;
    @BindView(R.id.bar_title)
    TextView bar_title;
    @BindView(R.id.layout_right_text)
    LinearLayout layout_right_text;
    @BindView(R.id.layout_right_img)
    LinearLayout layout_right_img;
    @BindView(R.id.bar_right_serch_img)
    ImageView bar_right_serch_img;
    @BindView(R.id.bar_right_msg_img)
    ImageView bar_right_msg_img;

    @BindView(R.id.frg_honm_slide_recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.frg_honm_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.frg_honm_empty_layout)
    CustomEmptyView mCustomEmptyView;

    private HomeGoodsAdapter mAdapter;
    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 6;

    private Banner banner;
    //    private List<String> imgUrls = new ArrayList<>();
    private ArrayList images;

    double latitude;
    double longitude;

    View bannerview;

    View sortview;
    View hotSoreView;

    ViewPager dSsortview;
    ViewPager hotRecommendSoreView;

    LinearLayout linerIndicator1;
    LinearLayout linerIndicator2;
    private List<BannerModel.ResultBean> mResult;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void finishCreateView(Bundle state) {

        bannerview = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_banner, (ViewGroup) mRecyclerView.getParent(), false);
        banner = (Banner) bannerview.findViewById(R.id.banner);

        sortview = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_sort, (ViewGroup) mRecyclerView.getParent(), false);
        dSsortview = (ViewPager) sortview.findViewById(R.id.dynamicSoreView);
        linerIndicator1 = (LinearLayout) sortview.findViewById(R.id.liner_indicator1);


        hotSoreView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_hot_recommend, (ViewGroup) mRecyclerView.getParent(), false);
        hotRecommendSoreView = (ViewPager) hotSoreView.findViewById(R.id.hot_recommend_SoreView);
        linerIndicator2 = (LinearLayout) hotSoreView.findViewById(R.id.liner_indicator);

        //dSsortview.setGridView(R.layout.include_home_sort_gridview);
        //hotRecommendSoreView.setGridView(R.layout.include_home_hot_gridview);

        images = new ArrayList();

        bar_left_img.setVisibility(View.GONE);
        bar_center_img.setVisibility(View.VISIBLE);
        bar_title.setText("地理位置获取");
        layout_right_text.setVisibility(View.GONE);
        layout_right_img.setVisibility(View.VISIBLE);

        EventBus.getDefault().register(this);

        initRefreshLayout();

        initAdapter();


    }


    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                String pid = ((ProductList.ListBean) adapter.getItem(position)).getId();
                int status = ((ProductList.ListBean) adapter.getItem(position)).getStatus();
                startActivity(new Intent(getActivity(), GoodsDetailActivity.class)
                        .putExtra("pId", pid)
                        .putExtra("status", status)
                );
            }
        });

        mAdapter = new HomeGoodsAdapter(getActivity());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });


        mAdapter.addHeaderView(bannerview);
        mAdapter.addHeaderView(sortview);
        mAdapter.addHeaderView(hotSoreView);


        //mAdapter.setHeaderAndEmpty(true);

//        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
//        mAdapter.setNotDoAnimationCount(10);

        mRecyclerView.setAdapter(mAdapter);

    }

    //初始化加载数据
    protected void initRefreshLayout() {

        //设置界面监听
        //dSsortview.setiDynamicSore(this);
        //设置界面监听
        //hotRecommendSoreView.setiDynamicSore(this);

        setStopSwipeRefreshing();
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
//                mSwipeRefreshLayout.setRefreshing(true);
//                refresh();
            }
        });

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();

        if (isRefresh) {
            mAdapter.setNewData(data);
            mAdapter.addData(0,data);
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

    //定位回调
    @Subscribe
    public void onEvent(BDLocation bdLocation) {
        if (bdLocation.getAddrStr() != null) {
            this.latitude = bdLocation.getLatitude();
            this.longitude = bdLocation.getLongitude();
            refresh();
            hideEmptyView();

            String addrStr = bdLocation.getAddrStr();
            if (addrStr.startsWith("中国")) {
                String[] ss = addrStr.split("中国");
                addrStr = ss[1];
            }
            bar_title.setText(addrStr);
            myLocation.setCity(bdLocation.getCity());
            myLocation.setAddrStr(bdLocation.getAddrStr());
            myLocation.setLatitude(bdLocation.getLatitude());
            myLocation.setLongitude(bdLocation.getLongitude());
        } else {
            bar_title.setText("地理位置获取失败");
            initEmptyView();
        }
    }

    MyLocation myLocation = new MyLocation();

    //选择定位回调
    @Subscribe
    public void onEvent(MyLocation location) {
        if (location != null) {
            bar_title.setText(location.getAddrStr());
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
            refresh();
            hideEmptyView();

            myLocation.setAddrStr(location.getAddrStr());
            myLocation.setLatitude(location.getLatitude());
            myLocation.setLongitude(location.getLongitude());

        } else {
            bar_title.setText("地理位置获取失败");
            initEmptyView();
        }
    }

    @OnClick({R.id.bar_title, R.id.bar_right_serch_img, R.id.bar_right_msg_img})
    public void onClicks(View v) {
        switch (v.getId()) {
            case R.id.bar_title:
                if (!CommonUtil.isLocationEnabled(getActivity())) {
                    CommonUtil.doNetWorkSettingDialog(getActivity(), 1002);
                    return;
                }
                if (myLocation.getAddrStr() != null) {
                    startActivity(new Intent(getActivity(), SearchNearbyAndCityActivity.class).putExtra("MyLocation", (MyLocation) myLocation));

                } else {
                    bar_title.setText("正在获取地理位置…");
                    MyLocationClient myLocationClient = new MyLocationClient(getActivity());
                }
                break;
            case R.id.bar_right_serch_img:
                startActivity(new Intent(getActivity(), SearchGoodsActivity.class));
                break;
            case R.id.bar_right_msg_img:
                setLogin(new Intent(getActivity(), MessageActivity.class));
                break;
            default:
                break;
        }
    }

    //加载更多数据
    private void loadMore() {
        getProductList();
    }

    //刷新数据
    private void refresh() {
        initBannerData();
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载

        getCategoryNavigation();
        getHotRecommendNavigation();
        getProductList();
    }

    private void initBannerData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("location", "1");
        map.put("cityId", "0");

        getBannerData(map);
    }

    //请求banner数据
    private void getBannerData(HashMap<String, String> map) {
        RetrofitHelper.getInstance().getBanner(new Subscriber<BannerModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                bannerview.setVisibility(View.GONE);

            }

            @Override
            public void onNext(BannerModel httpBaseResults) {

                mResult = httpBaseResults.getResult();
                for (int i = 0; i < mResult.size(); i++) {

                    images.add(mResult.get(i).getImgUrl());
                }

                if (mResult.size() <= 0) {
                    bannerview.setVisibility(View.GONE);
                } else {
                    setBanner();
                    bannerview.setVisibility(View.VISIBLE);
                }


            }
        }, map);
    }


    private void setBanner() {

        //设置banner样式
        //banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.update(images);
        images.clear();

        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        //banner.isAutoPlay(true);
        //设置轮播时间
        //banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        //banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(new OnBannerListener() {

            @Override
            public void OnBannerClick(int position) {
//                点击跳转
                if (mResult.get(position).getClickTo().equals("0")) {
//                    代表跳转原生
                    String productCode = mResult.get(position).getProductCode();
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("pId", productCode);
                    startActivity(intent);

                } else if (mResult.get(position).getClickTo().equals("1")) {
//                    代表跳转web

                }

            }

        });
        //banner设置方法全部调用完毕时最后调用
//        banner.start();

    }


    int APP_PAGE_SIZE = 8;

    //首页分类
    private void datadSsortview(List<CategoryNavigation> list) {
        //控件相关设置
        // dSsortview.init(list);

        ArrayList<GridView> arrayRy1 = new ArrayList<GridView>();
        final int count = (int) Math.ceil((double) list.size() / APP_PAGE_SIZE);
        int pageCount = count <= 1 ? 1 : count;
        Log.i("pageCount==>>", pageCount + "");
        for (int i = 0; i < pageCount; i++) {

            HomeSortAdpter homeSortAdpter = new HomeSortAdpter(getActivity(), list, i);
            GridView appPage = new GridView(getActivity());
            appPage.setOverScrollMode(View.OVER_SCROLL_NEVER);
            appPage.setVerticalScrollBarEnabled(false);
            appPage.setAdapter(homeSortAdpter);
            appPage.setNumColumns(4);
            //appPage.setVerticalSpacing(30);
            arrayRy1.add(appPage);

        }

        HomeViewPagerAdapter homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity(), arrayRy1);
        dSsortview.setAdapter(homeViewPagerAdapter);

        if (pageCount <= 1) {
            linerIndicator1.setVisibility(View.GONE);
        } else {
            new IndicatorView(getActivity(), dSsortview, pageCount, linerIndicator1);
            linerIndicator1.setVisibility(View.VISIBLE);
        }

    }


    //热门推荐
    private void datadHotRecommend(List<HotRecommendModel> list) {

        //控件相关设置
        // hotRecommendSoreView.init(list);

        ArrayList<GridView> arrayRy2 = new ArrayList<GridView>();
        final int count = (int) Math.ceil((double) list.size() / 4);
        int pageCount = count <= 1 ? 1 : count;
        Log.i("pageCount==>>", pageCount + "");

        for (int i = 0; i < pageCount; i++) {

            HotRecommendAdpter hotRecommendAdpter = new HotRecommendAdpter(getActivity(), list, i);
            GridView appPage = new GridView(getActivity());
            appPage.setOverScrollMode(View.OVER_SCROLL_NEVER);
            appPage.setVerticalScrollBarEnabled(false);
            appPage.setNumColumns(2);
            //appPage.setVerticalSpacing(30);
            appPage.setAdapter(hotRecommendAdpter);
            arrayRy2.add(appPage);
        }

        HomeViewPagerAdapter homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity(), arrayRy2);
        hotRecommendSoreView.setAdapter(homeViewPagerAdapter);

        if (pageCount <= 1) {
            linerIndicator2.setVisibility(View.GONE);
        } else {
            new IndicatorView(getActivity(), hotRecommendSoreView, pageCount, linerIndicator2);
            linerIndicator2.setVisibility(View.VISIBLE);
        }
    }


    //请求列表数据
    private void getProductList() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("keyWord", "");
        map.put("categoryId", "");
        map.put("recommend", "");
        map.put("order", "");
        map.put("page", mNextRequestPage);
        map.put("pageSize", PAGE_SIZE);
        RetrofitHelper.getInstance().getProductList(map, new Subscriber<ProductList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mNextRequestPage == 1) {
                    mAdapter.setEnableLoadMore(true);
                    setStopSwipeRefreshing();
                    initEmptyView();
                } else {
                    mAdapter.loadMoreFail();
                    hideEmptyView();
                }
            }

            @Override
            public void onNext(ProductList productLists) {
                hideEmptyView();

                if (mNextRequestPage == 1) {
                    setData(true, productLists.getList());
                    mAdapter.setEnableLoadMore(true);
                    setStopSwipeRefreshing();
                } else {
                    setData(false, productLists.getList());
                }

            }
        });
    }

    //请求热门推荐数据
    private void getHotRecommendNavigation() {


        RetrofitHelper.getInstance().getHotRecommend(20, 1, new Subscriber<List<HotRecommendModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                hotSoreView.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<HotRecommendModel> hotRecommendModel) {
                if (hotRecommendModel.size() <= 0) {
                    hotSoreView.setVisibility(View.GONE);
                } else {
                    hotSoreView.setVisibility(View.VISIBLE);
                    datadHotRecommend(hotRecommendModel);
                }
            }
        });

    }

    //请求首页分类数据
    private void getCategoryNavigation() {

        RetrofitHelper.getInstance().getCategoryNavigation(new Subscriber<List<CategoryNavigation>>() {
            @Override
            public void onCompleted() {
                setStopSwipeRefreshing();
            }

            @Override
            public void onError(Throwable e) {
                setStopSwipeRefreshing();
                Log.e("onerror", e.getMessage());
                sortview.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<CategoryNavigation> categoryNavigations) {
                if (categoryNavigations.size() <= 0) {
                    sortview.setVisibility(View.GONE);
                } else {
                    datadSsortview(categoryNavigations);
                    sortview.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    // 停止刷新
    private void setStopSwipeRefreshing() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 显示empty视图
     */
    private void initEmptyView() {

        setStopSwipeRefreshing();
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.icon_no_net);
        mCustomEmptyView.setEmptyText("网络开小差了！刷新试试~");
//        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    /**
     * 隐藏empty视图
     */
    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void setGridView(View view, int type, final List data) {
        switch (view.getId()) {
//            case gridView:
//
//                final GridView gv1 = (GridView) view.findViewById(R.id.gridView);
//                dSsortview.setNumColumns(gv1);
//                SortButtonAdapter adapter = new SortButtonAdapter(getActivity(), data);
//                gv1.setAdapter(adapter);
//                gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        GridView view1 = (GridView) parent;
//                        int dd = (int) view1.getItemAtPosition(position);
//                        CategoryNavigation item = (CategoryNavigation) data.get(dd);
//                        startActivity(new Intent(getActivity(), CategoryActivity.class).putExtra("cid", item.getId()));
//                    }
//                });
//                break;
//            case R.id.hot_gridView:
//                GridView gv2 = (GridView) view.findViewById(R.id.hot_gridView);
//
//                HotRecommendAdapter adapter2 = new HotRecommendAdapter(getActivity(), data);
//                hotRecommendSoreView.setNumColumns(gv2);
//
//                gv2.setAdapter(adapter2);
//                gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        long topicId = ((HotRecommendModel) data.get(position)).getId();
//                        TopicGoodsParams topGoodsParams = new TopicGoodsParams();
//                        topGoodsParams.setPageId(101);
//                        topGoodsParams.setTopicId(topicId);
//                        topGoodsParams.setLatitude(0);
//                        topGoodsParams.setLongitude(0);
//                        startActivity(new Intent(getActivity(), CategoryListActivity.class).putExtra(Constant.TOPIC_GOODS_PARAMS, topGoodsParams));
//                    }
//                });
//                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
