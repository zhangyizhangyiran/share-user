package com.soe.sharesoe.module.nearby.search.nearby;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/15
 * @time 下午2:46
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class GeoCode {
    GeoCoder mSearch;

    public GeoCode() {
        if (mSearch == null){
            mSearch = GeoCoder.newInstance();//初始化地理编码
        }
    }

    public void getGeoCode(String address, String city, OnGetGeoCoderResultListener listener) {

        mSearch.setOnGetGeoCodeResultListener(listener);//设置回调监听
        mSearch.geocode(new GeoCodeOption().address(address).city(city));//将地址信息转换为坐标点

    }

    public void ReverseGeoCode(double latitude, double longitude, OnGetGeoCoderResultListener listener) {

        mSearch.setOnGetGeoCodeResultListener(listener);//设置回调监听
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latitude, longitude)));//将坐标点转换为地址信息
    }
}
