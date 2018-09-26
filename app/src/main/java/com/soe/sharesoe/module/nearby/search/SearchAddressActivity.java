package com.soe.sharesoe.module.nearby.search;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.orhanobut.logger.Logger;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.module.mycenter.wallet.RechargeDetailsAdapter;
import com.soe.sharesoe.module.nearby.map.MyLocation;
import com.soe.sharesoe.utils.Q;
import com.soe.sharesoe.widget.CustomEmptyView;

import org.greenrobot.eventbus.EventBus;

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

public class SearchAddressActivity extends RxBaseActivity implements TextWatcher {

    @BindView(R.id.bar_left_img)
    ImageView bar_left_img;
    @BindView(R.id.bar_title)
    TextView bar_title;
    @BindView(R.id.search_edittext)
    EditText search_edittext;
    @BindView(R.id.search_clear)
    ImageView search_clear;
    @BindView(R.id.search_cancle)
    TextView search_cancle;

    @BindView(R.id.act_search_add_recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.act_search_add_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.act_search_add_emptylayout)
    CustomEmptyView mCustomEmptyView;


    PoiSearch mPoiSearch;
    String CityName;

    private SearchAddressAdapter mAdapter;
    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 20;

    String keyword;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_address;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        bar_title.setText("定位地址");
        CityName = getIntent().getExtras().getString("CityName");
        search_edittext.addTextChangedListener(this);
        initAdapter();
        initRefreshLayout();
    }

    private void SuggestionSearch() {
        SuggestionSearch mSuggestionSearch = SuggestionSearch.newInstance();
        OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
            public void onGetSuggestionResult(SuggestionResult res) {

                if (res == null || res.getAllSuggestions() == null) {
                    return;
                    //未找到相关结果
                }
                //获取在线建议检索结果
                List<SuggestionResult.SuggestionInfo> allSuggestions = res.getAllSuggestions();
                for (SuggestionResult.SuggestionInfo suggestionInfo : allSuggestions) {
                    Logger.d(suggestionInfo.key);

                }
            }
        };
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
// 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新

        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())

                .keyword(keyword)
                .citylimit(true)
                .city(CityName));
    }

    private void PoiSearch() {
        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            public void onGetPoiResult(PoiResult result) {
                if (result.error != SearchResult.ERRORNO.NO_ERROR) {
//                    if (mNextRequestPage == 1) {
//                        mAdapter.setEnableLoadMore(false);
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    } else {
//                        mAdapter.loadMoreFail();
//                    }
//                    initEmptyView();
                } else {
//                    hideEmptyView();
                    //检索成功
                    List<PoiInfo> allPoi = result.getAllPoi();
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (mNextRequestPage == 1) {
                        setData(true, allPoi);
                        mAdapter.setEnableLoadMore(true);
                    } else {
                        setData(false, allPoi);
                    }
                }
            }

            public void onGetPoiDetailResult(PoiDetailResult result) {
                //获取Place详情页检索结果
                Logger.d(result.address);

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                Logger.d(poiIndoorResult.getPoiNum());

            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(CityName)
                .keyword(keyword)
                .pageCapacity(PAGE_SIZE)
                .pageNum(mNextRequestPage));
    }

    @OnClick({R.id.bar_left_img, R.id.search_cancle,R.id.search_clear})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
            case R.id.search_cancle:
                finish();
                break;
            case R.id.search_clear:
                search_edittext.setText("");
                break;
            default:
                break;
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAddressAdapter();
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
                PoiInfo poiInfo = (PoiInfo)adapter.getData().get(position);
                MyLocation myLocation = new MyLocation();
                myLocation.setGoCenter(true);
                myLocation.setCity(poiInfo.city);
                myLocation.setAddrStr(poiInfo.address);
                myLocation.setLatitude(poiInfo.location.latitude);
                myLocation.setLongitude(poiInfo.location.longitude);
                finish();
                SearchNearbyAndCityActivity.activity.finish();
                EventBus.getDefault().post(myLocation);
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
//                mSwipeRefreshLayout.setRefreshing(true);
//                refresh();
            }
        });

    }

    private void loadMore() {
        PoiSearch();
    }

    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        PoiSearch();
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
            Toast.makeText(SearchAddressActivity.this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }


    private void initEmptyView() {

        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.logo);
        mCustomEmptyView.setEmptyText("没有数据~(≧▽≦)~啦啦啦.");
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        keyword = charSequence.toString();
        if(!Q.isEmpty(keyword)){
            search_clear.setVisibility(View.VISIBLE);
        }else {
            search_clear.setVisibility(View.GONE);
            return;
        }
        Logger.d(keyword);

        mNextRequestPage = 1;
        PoiSearch();
//        SuggestionSearch();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
