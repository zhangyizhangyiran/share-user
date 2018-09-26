package com.soe.sharesoe.module.nearby.map;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.orhanobut.logger.Logger;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxLazyFragment;
import com.soe.sharesoe.module.main.OnChangeTabListener;
import com.soe.sharesoe.module.nearby.goods.NearbyGoodsBottomSheet;
import com.soe.sharesoe.module.nearby.map.cupboard.Cupboard;
import com.soe.sharesoe.module.nearby.map.overlay.WalkingRouteOverlay;
import com.soe.sharesoe.module.nearby.search.nearby.GeoCode;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.Q;
import com.soe.sharesoe.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
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
 * @date 2017/10/28
 * @time 上午10:14
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class NearbyFragment extends RxLazyFragment implements SensorEventListener {

    private MyLocationConfiguration.LocationMode mCurrentMode;
    private static final int accuracyCircleFillColor = 0x00000000;
    private static final int accuracyCircleStrokeColor = 0x00000000;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    @BindView(R.id.fra_mv_nearby_mapView)
    MapView mMapView;

    @BindView(R.id.fra_tv_nearby_location)
    TextView fra_tv_nearby_location;
    @BindView(R.id.fra_img_go_location)
    ImageView fra_img_go_location;


    BaiduMap mBaiduMap;

    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;


    @BindView(R.id.bottomsheet)
    BottomSheetLayout bottomSheet;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_nearby;
    }

    @Override
    public void finishCreateView(Bundle state) {
        EventBus.getDefault().register(this);

        mSensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng startPt = new LatLng(mCurrentLat, mCurrentLon);
                LatLng endPt = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);

                clickMarker(startPt, endPt, marker);

                return true;
            }

        });
//        mBaiduMap.removeMarkerClickListener(onMarkerClickListener);
//        mBaiduMap.clear();
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                setCenterMarker(mapStatus.target.latitude, mapStatus.target.longitude);

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                getReverseGeoCode(mapStatus.target.latitude, mapStatus.target.longitude);
            }
        });
    }
    @OnClick({R.id.fra_img_go_location})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fra_img_go_location:
                MyLocationClient myLocationClient = new MyLocationClient(getActivity());
                break;
            default:
                break;
        }
    }

    //反地理编码
    private void getReverseGeoCode(final double latitude, final double longitude) {
        new GeoCode().ReverseGeoCode(latitude, longitude, new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                } else {
                    getCupboardSearchAroundCupboard(String.valueOf(reverseGeoCodeResult.getCityCode()), latitude, longitude);
                    fra_tv_nearby_location.setText(reverseGeoCodeResult.getAddress());
                    MyLocation myLocation = new MyLocation();
                    myLocation.setAddrStr(reverseGeoCodeResult.getAddress());
                    myLocation.setLatitude(latitude);
                    myLocation.setLongitude(longitude);
                    EventBus.getDefault().post(myLocation);
                }

            }
        });
    }


    //定位自己回调
    @Subscribe
    public void onEvent(BDLocation bdLocation) {
        fra_tv_nearby_location.setText(bdLocation.getAddrStr());
        //map view 销毁后不在处理新接收的位置
        if (bdLocation == null || mMapView == null) {
            return;
        }
        mCurrentLat = bdLocation.getLatitude();
        mCurrentLon = bdLocation.getLongitude();
        mCurrentAccracy = bdLocation.getRadius();
        locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        if (isFirstLoc) {
            isFirstLoc = false;
            setLocationMarker(bdLocation.getLatitude(), bdLocation.getLongitude());
        }

        setMapCenter(mCurrentLat, mCurrentLon);

    }

    //选择定位回调
    @Subscribe
    public void onEvent(MyLocation myLocation) {
        if (myLocation != null) {
            fra_tv_nearby_location.setText("" + myLocation.getAddrStr());
            if (myLocation.isGoCenter()) {
                setMapCenter(myLocation.getLatitude(), myLocation.getLongitude());
            }
        }
    }

    //切换页面回调
    @Subscribe
    public void onEvent(OnChangeTabListener onChangeTabListener) {
        setMapCenter(onChangeTabListener.getLatitude(), onChangeTabListener.getLongitude());
    }

    //获取附近柜子
    public void getCupboardSearchAroundCupboard(String cityCode, double lat, double lon) {
        Map<String, String> map = new HashMap<String, String>();

        map.put("baiduCode", cityCode);
        map.put("lat", String.valueOf(lat));
        map.put("lng", String.valueOf(lon));

        RetrofitHelper.getInstance().getCupboardSearchAroundCupboard(map, new Subscriber<List<Cupboard>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Cupboard> cupboardList) {
                setShelfMarker(cupboardList);
            }
        });
    }


    //定位标记
    private void setLocationMarker(double lat, double lon) {
        // 修改为自定义marker
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_location_now);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));

        setMapCenter(lat, lon);
        setCenterMarker(lat, lon);
    }

    //箱架标记
    private void setShelfMarker(List<Cupboard> cupboardList) {
        //创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        //构建Marker图标
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_sort_box);
        for (Cupboard cupboard : cupboardList) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Cupboard", ((Serializable) cupboard));
            options.add(new MarkerOptions().extraInfo(bundle).position(new LatLng(cupboard.getLat(), cupboard.getLng())).icon(mCurrentMarker));
        }
        //在地图上批量添加
        mBaiduMap.addOverlays(options);
    }

    Marker mMarkerD;

    //中心点标记
    private void setCenterMarker(double lat, double lon) {
        LatLng point = new LatLng(lat, lon);
        //构建Marker图标
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_localize_center);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(mCurrentMarker);
        if (mMarkerD == null) {
            //在地图上添加Marker，并显示
            mMarkerD = (Marker) mBaiduMap.addOverlay(option);
            mMarkerD.setPosition(point);
        } else {
            mMarkerD.setPosition(point);
        }

    }

    //经纬度居中
    private void setMapCenter(double lat, double lon) {
        LatLng ll = new LatLng(lat, lon);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    Bundle oldBundle;

    //Marker被点击
    private void clickMarker(LatLng latLngStart, LatLng latLngEnd, Marker marker) {
        Bundle bundle = marker.getExtraInfo();
        Cupboard cupboard;
        if (bundle != null) {
            oldBundle = bundle;
            cupboard = (Cupboard) bundle.getSerializable("Cupboard");
        } else {
            cupboard = (Cupboard) oldBundle.getSerializable("Cupboard");
        }

        double distance = DistanceUtil.getDistance(latLngStart, latLngEnd);//两点距离计算
        new NearbyGoodsBottomSheet(getActivity(), bottomSheet, cupboard.getId(), distance);
        initInfoWindow(latLngStart, latLngEnd, distance, marker);
    }

    //初始化泡泡窗口,步行路线规划
    private void initInfoWindow(LatLng latLngStart, LatLng latLngEnd, double distance, final Marker marker) {
        walkingPlan(latLngStart, latLngEnd);

        LatLng latLng = marker.getPosition();
        View view = View.inflate(getActivity(), R.layout.item_nearby_window_dg, null);
        ((TextView) view.findViewById(R.id.item_nearby_win_distance)).setText(String.valueOf(Math.round(distance)) + "米");
        ((TextView) view.findViewById(R.id.item_nearby_win_time)).setText(String.valueOf(Math.round(distance / 50)) + "分钟");
        InfoWindow infoWindow = new InfoWindow(view, latLng, -150);
        mBaiduMap.showInfoWindow(infoWindow);
    }

    //步行路线规划
    private void walkingPlan(LatLng latLngStart, LatLng latLngEnd) {
        RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
        OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {

            public void onGetWalkingRouteResult(WalkingRouteResult result) {
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    setWalkingPlan(result);
                } else {
                    T.normal("路线规划失败");
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };
        mSearch.setOnGetRoutePlanResultListener(listener);

        PlanNode stNode = PlanNode.withLocation(latLngStart);
        PlanNode enNode = PlanNode.withLocation(latLngEnd);

        mSearch.walkingSearch((new WalkingRoutePlanOption())

                .from(stNode)
                .to(enNode));

    }

    WalkingRouteOverlay overlay;

    //步行路线规划
    private void setWalkingPlan(WalkingRouteResult result) {
        if (overlay == null) {
            overlay = new WalkingRouteOverlay(mBaiduMap);
//        mBaiduMap.setOnMarkerClickListener(overlay);
        } else {

        }
        overlay.setData(result.getRouteLines().get(0));
        overlay.addToMap();
        overlay.zoomToSpan();

//        LatLng northeast = new LatLng(39.899345, 116.66588);//东北
//        LatLng southwest = new LatLng(39.897601, 116.661999);//西南
//        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
//                .include(southwest).build();
//        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngBounds(bounds,500,500);

//        MapStatusUpdate mapStatusUpdate1 = MapStatusUpdateFactory.scrollBy(0, -600);//x,y

//        mBaiduMap.setMapStatus(mapStatusUpdate);// 设置显示在屏幕中的地图地理范围
//
//           mMapView.scrollBy(100,100);
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(10));

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mBaiduMap.clear();
        if (mMapView != null) {
            mMapView.onDestroy();
            mMapView = null;
        }
        super.onDestroy();
    }


    //==================================================================================================================================================================================================================================================//

//    private void WalkNaviinit() {
    //    private WalkNavigateHelper mWNaviHelper;
//    WalkNaviLaunchParam walkParam;
//        mWNaviHelper = WalkNavigateHelper.getInstance();
//        walkParam = new WalkNaviLaunchParam().stPt(startPt).endPt(endPt);
//
//        //开始导航
//        startWalkNavi();
//    }

//    private void startWalkNavi() {
//        Log.d("View", "startWalkNavi");
//        try {
//            mWNaviHelper.initNaviEngine(getActivity(), new IWEngineInitListener() {
//                @Override
//                public void engineInitSuccess() {
//                    Log.d("View", "engineInitSuccess");
//                    routePlanWithWalkParam();
//                }
//
//                @Override
//                public void engineInitFail() {
//                    Log.d("View", "engineInitFail");
//                }
//            });
//        } catch (Exception e) {
//            Log.d("Exception", "startWalkNavi");
//            e.printStackTrace();
//        }
//    }

//    private void routePlanWithWalkParam() {
//        mWNaviHelper.routePlanWithParams(walkParam, new IWRoutePlanListener() {
//            @Override
//            public void onRoutePlanStart() {
//                Log.d("View", "onRoutePlanStart");
//            }
//
//            @Override
//            public void onRoutePlanSuccess() {
//                Log.d("View", "onRoutePlanSuccess");
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), WNaviGuideActivity.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onRoutePlanFail(WalkRoutePlanError error) {
//                Log.d("View", "onRoutePlanFail");
//            }
//
//        });
//    }
}

