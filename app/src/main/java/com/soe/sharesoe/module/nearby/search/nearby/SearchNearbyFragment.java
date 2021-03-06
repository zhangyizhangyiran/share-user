package com.soe.sharesoe.module.nearby.search.nearby;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.orhanobut.logger.Logger;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxLazyFragment;
import com.soe.sharesoe.module.nearby.map.MyLocation;
import com.soe.sharesoe.module.nearby.map.MyLocationClient;
import com.soe.sharesoe.module.nearby.search.city.CommonAdapter;
import com.soe.sharesoe.module.nearby.search.city.HeaderRecyclerAndFooterWrapperAdapter;
import com.soe.sharesoe.module.nearby.search.city.MeituanAdapter;
import com.soe.sharesoe.module.nearby.search.city.MeituanHeaderBean;
import com.soe.sharesoe.module.nearby.search.city.MeituanTopHeaderBean;
import com.soe.sharesoe.module.nearby.search.city.ViewHolder;
import com.soe.sharesoe.widget.IndexBar.bean.BaseIndexPinyinBean;
import com.soe.sharesoe.widget.IndexBar.suspension.SuspensionDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/31
 * @time 下午4:54
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class SearchNearbyFragment extends RxLazyFragment {

    @BindView(R.id.fra_nearby_recycle_view)
    RecyclerView mRecyclerView;

//    @BindView(R.id.tvSideBarHint)
//    TextView mTvSideBarHint;
//    @BindView(R.id.indexBar)
//    IndexBar mIndexBar;


    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private LinearLayoutManager mManager;

    private SuspensionDecoration mDecoration;


    //设置给InexBar、ItemDecoration的完整数据集
    private List<BaseIndexPinyinBean> mSourceDatas;
    //头部数据源
    private List<MeituanHeaderBean> mHeaderDatas;
    private MeituanAdapter mAdapter;
    //主体部分数据源（城市数据）
    private List<NearbyAddress> mBodyDatas;

    private String AddrStr;
    private double latitude;
    private double longitude;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_search_nearby;
    }

    @Override
    public void finishCreateView(Bundle state) {
        Bundle bundle = getArguments();
        MyLocation myLocation = (MyLocation) bundle.getSerializable("MyLocation");
        this.AddrStr = myLocation.getAddrStr();
        this.latitude = myLocation.getLatitude();
        this.longitude = myLocation.getLongitude();

        init();
    }

    protected void init() {
        mRecyclerView.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));

        EventBus.getDefault().register(this);

        mSourceDatas = new ArrayList<>();
        mHeaderDatas = new ArrayList<>();

        List<String> locationCity = new ArrayList<>();
        locationCity.add(AddrStr + "");
        mHeaderDatas.add(new MeituanHeaderBean(locationCity, "当前位置", "↑"));

        List<String> recentCitys = new ArrayList<>();
        mHeaderDatas.add(new MeituanHeaderBean(recentCitys, "附近位置", "近"));

        mSourceDatas.addAll(mHeaderDatas);


        mAdapter = new MeituanAdapter(getActivity(), R.layout.item_search_nearby, mBodyDatas);
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                switch (layoutId) {
                    case R.layout.meituan_item_header:
                        final MeituanHeaderBean meituanHeaderBean = (MeituanHeaderBean) o;
                        //网格
                        RecyclerView recyclerView = holder.getView(R.id.rvCity);
                        recyclerView.setAdapter(
                                new CommonAdapter<String>(getActivity(), R.layout.include_search_city_top, meituanHeaderBean.getCityList()) {
                                    @Override
                                    public void convert(ViewHolder holder, final String cityName) {
                                        holder.setText(R.id.tvName, cityName);
                                        holder.getView(R.id.tvlocation).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                MyLocationClient myLocationClient = new MyLocationClient(getActivity());

                                            }
                                        });
                                    }
                                });
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        break;
                    case R.layout.meituan_item_header_top:
                        MeituanTopHeaderBean meituanTopHeaderBean = (MeituanTopHeaderBean) o;
                        holder.setText(R.id.tvCurrent, meituanTopHeaderBean.getTxt());
                        break;
                    default:
                        break;
                }
            }
        };

        mHeaderAdapter.setHeaderView(0, R.layout.meituan_item_header, mHeaderDatas.get(0));
        mHeaderAdapter.setHeaderView(1, R.layout.meituan_item_header, mHeaderDatas.get(1));


        mRecyclerView.setAdapter(mHeaderAdapter);
        mRecyclerView.addItemDecoration(mDecoration = new SuspensionDecoration(getActivity(), mSourceDatas)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xFFF5F5F5)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()))
                .setColorTitleFont(getActivity().getResources().getColor(R.color.Color_Theme_Text_Detail))
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size()));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        //使用indexBar
//        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
//        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar

//        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
//                .setNeedRealIndex(true)//设置需要真实的索引
//                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
//                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size());

//        initDatas(getResources().getStringArray(R.array.provinces));


        mAdapter.setOnItemClickListener(new com.soe.sharesoe.module.nearby.search.city.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                MyLocation myLocation = new MyLocation();
                myLocation.setGoCenter(true);
                myLocation.setCity(((NearbyAddress) mAdapter.getItem(position)).getCity());
                myLocation.setAddrStr(((NearbyAddress) mAdapter.getItem(position)).getAddstr());
                myLocation.setLatitude(((NearbyAddress) mAdapter.getItem(position)).getLatitude());
                myLocation.setLongitude(((NearbyAddress) mAdapter.getItem(position)).getLongitude());
                getActivity().finish();
                EventBus.getDefault().post(myLocation);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });

        getReverseGeoCode(latitude, longitude);
    }

    private void getReverseGeoCode(double latitude, double longitude) {
        new GeoCode().ReverseGeoCode(latitude, longitude, new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                } else {
                    setData(reverseGeoCodeResult.getPoiList());
                }

            }
        });
    }

    //定位回调
    @Subscribe
    public void onEvent(BDLocation bdLocation) {
        getReverseGeoCode(bdLocation.getLatitude(), bdLocation.getLongitude());
        this.AddrStr = bdLocation.getAddrStr();
    }


    //设置数据
    private void setData(List<PoiInfo> poiInfoList) {
        MeituanHeaderBean header1 = mHeaderDatas.get(0);
        header1.getCityList().clear();
        header1.getCityList().add(AddrStr + "");


        mBodyDatas = new ArrayList<>();
        for (PoiInfo poiInfo : poiInfoList) {
            NearbyAddress nearbyAddress = new NearbyAddress();
            nearbyAddress.setName(poiInfo.name);
            nearbyAddress.setCity(poiInfo.city);
            nearbyAddress.setAddstr(poiInfo.address);
            nearbyAddress.setLatitude(poiInfo.location.latitude);
            nearbyAddress.setLongitude(poiInfo.location.longitude);
            mBodyDatas.add(nearbyAddress);
        }

        if (mSourceDatas != null) {
            mSourceDatas.clear();
        }

        mAdapter.setDatas(mBodyDatas);
        mHeaderAdapter.notifyDataSetChanged();
        mSourceDatas.addAll(mHeaderDatas);
        mSourceDatas.addAll(mBodyDatas);
        mDecoration.setmDatas(mSourceDatas);
    }

    //更新数据
//    private void updateDatas() {
//        MeituanHeaderBean header1 = mHeaderDatas.get(0);
//        header1.getCityList().clear();
//        header1.getCityList().add(bdLocation.getLocationDescribe());
//
////        mHeaderAdapter.notifyItemRangeChanged(1, 2);
//        mBodyDatas.clear();
//        for (int i = 0; i < bdLocation.getPoiList().size(); i++) {
//            mBodyDatas.add(new NearbyAddress(bdLocation.getPoiList().get(i).getName()));
//
//        }
//        mSourceDatas.clear();
//        mSourceDatas.addAll(mHeaderDatas);
//        mSourceDatas.addAll(mBodyDatas);
//
//        mHeaderAdapter.notifyDataSetChanged();
//    }


    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {
        //延迟两秒 模拟加载数据中....
        getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBodyDatas = new ArrayList<>();
//                for (int i = 0; i < data.length; i++) {
//                    NearbyAddress cityBean = new NearbyAddress();
//                    cityBean.setCity(data[i]);//设置城市名称
//                    mBodyDatas.add(cityBean);
//                }
                mBodyDatas.add(new NearbyAddress().setCity("通州区罗斯福广场"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区家乐福超市"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区北京银行"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区罗斯福广场"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区家乐福超市"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区北京银行"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区罗斯福广场"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区家乐福超市"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区北京银行"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区罗斯福广场"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区家乐福超市"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区北京银行"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区罗斯福广场"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区家乐福超市"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区北京银行"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区罗斯福广场"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区家乐福超市"));
                mBodyDatas.add(new NearbyAddress().setCity("通州区北京银行"));

                //先排序
//                mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);

                mAdapter.setDatas(mBodyDatas);
                mHeaderAdapter.notifyDataSetChanged();
                mSourceDatas.addAll(mBodyDatas);

//                mIndexBar.setmSourceDatas(mSourceDatas)//设置数据
//                        .invalidate();
                mDecoration.setmDatas(mSourceDatas);
            }
        }, 1000);

        //延迟两秒加载头部
        getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                MeituanHeaderBean header1 = mHeaderDatas.get(0);
                header1.getCityList().clear();
                header1.getCityList().add("上海");

//                MeituanHeaderBean header2 = mHeaderDatas.get(1);
//                List<String> recentCitys = new ArrayList<>();
//                recentCitys.add("通州区罗斯福广场");
//                recentCitys.add("通州区家乐福超市");
//                recentCitys.add("通州区北京银行");
//                header2.setCityList(recentCitys);
//
//                MeituanHeaderBean header3 = mHeaderDatas.get(2);
//                List<String> hotCitys = new ArrayList<>();
//                hotCitys.add("上海");
//                hotCitys.add("北京");
//                hotCitys.add("杭州");
//                hotCitys.add("广州");
//                header3.setCityList(hotCitys);

                mHeaderAdapter.notifyItemRangeChanged(1, 2);

            }
        }, 2000);

    }

    /**
     * 更新数据源
     *
     * @param view
     */
    public void updateDatas(View view) {
        for (int i = 0; i < 5; i++) {
            mBodyDatas.add(new NearbyAddress("通州区罗斯福广场"));
            mBodyDatas.add(new NearbyAddress("通州区家乐福超市"));
            mBodyDatas.add(new NearbyAddress("通州区北京银行"));

        }
        //先排序
//        mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);
        mSourceDatas.clear();
        mSourceDatas.addAll(mHeaderDatas);
        mSourceDatas.addAll(mBodyDatas);

        mHeaderAdapter.notifyDataSetChanged();
//        mIndexBar.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
