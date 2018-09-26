package com.soe.sharesoe.manager;//package com.soe.sharesoe.manager;
//
//import android.app.Notification;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Handler;
//import android.os.Message;
//import android.os.PowerManager;
//import android.text.TextUtils;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static android.content.Context.MODE_PRIVATE;
//
///**
// * Created by conghoulin on 2017/5/22.
// */
//
//public class TraceManager {
//
//    //CrashHandler实例
//    private static TraceManager instance;
//    //CrashHandler实例
//    private static String Tag = "TraceManager=>";
//
//    public SharedPreferences trackConf = null;
//
//    private AtomicInteger mSequenceGenerator = new AtomicInteger();
//
//    private LocRequest locRequest = null;
//
//    private Context mContext;
//    // 轨迹服务ID
//    long serviceId = 143298;
//    // 设备标识
//    public String entityName = "";
//
//    // 是否需要对象存储服务，默认为：false，关闭对象存储服务。注：鹰眼 Android SDK v3.0以上版本支持随轨迹上传图像等对象数据，若需使用此功能，该参数需设为 true，且需导入bos-android-sdk-1.0.2.jar。
//    boolean isNeedObjectStorage = false;
//    // 定位周期(单位:秒)
//    int gatherInterval = 15;
//    // 打包回传周期(单位:秒)
//    int packInterval = 60;
//
//    public Trace mTrace = null;
//    public LBSTraceClient mTraceClient = null;
//
//    /**
//     * 轨迹服务监听器
//     */
//    private OnTraceListener traceListener = null;
//    public static boolean isTraceStarted;
//    /**
//     * 轨迹监听器(用于接收纠偏后实时位置回调)
//     */
//    public static boolean isGatherStarted;
//
//
//    public boolean isRegisterReceiver = false;
//    /**
//     * Entity监听器(用于接收实时定位回调)
//     */
//    private boolean isStartLoction;
//
//    /**
//     * 获取TraceManager实例 ,单例模式
//     */
//    public static TraceManager getInstance() {
//        if (instance == null)
//            instance = new TraceManager();
//        return instance;
//    }
//
//
//    private PowerManager powerManager;
//    private PowerManager.WakeLock wakeLock = null;
//
//    private TrackReceiver trackReceiver = null;
//
//    public void init(Context context, Notification notification, PowerManager powerManagerA) {
//        if (BuildConfig.DEBUG) {
//            serviceId = 142676;
//        }
//        mContext = context;
//        trackConf = context.getSharedPreferences("track_conf", MODE_PRIVATE);
//        clearTraceStatus();
//        powerManager = powerManagerA;
//        if (!TextUtils.isEmpty(UserDataManager.getUserInfo().phonenum)) {
//            entityName = UserDataManager.getUserInfo().phonenum;
//        }
//        // 初始化轨迹服务
//        mTrace = new Trace(serviceId, entityName, isNeedObjectStorage);
//        // 初始化轨迹服务客户端
//        mTraceClient = new LBSTraceClient(context);
//
//        mTrace.setNotification(notification);
//        // 设置定位和打包周期
//        mTraceClient.setInterval(gatherInterval, packInterval);
//
//        locRequest = new LocRequest(serviceId);
//        initListener();
//    }
//
//    //开启鹰眼服务
//    public void start() {
//        mTraceClient.startTrace(mTrace, traceListener);
//        realTimeHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mTraceClient.startGather(traceListener);
//            }
//        }, 1000);
//    }
//
//    public void stop() {
//        CommonUtil.saveCurrentLocation();
//        if (trackConf.contains("is_trace_started")
//                && TraceManager.getInstance().trackConf.getBoolean("is_trace_started", true)) {
//            // 退出app停止轨迹服务时，不再接收回调，将OnTraceListener置空
//            mTraceClient.setOnTraceListener(null);
//            mTraceClient.stopTrace(mTrace, null);
//        } else {
//            mTraceClient.clear();
//        }
//        isTraceStarted = false;
//        isGatherStarted = false;
//        SharedPreferences.Editor editor = trackConf.edit();
//        editor.remove("is_trace_started");
//        editor.remove("is_gather_started");
//        editor.apply();
//        BitmapUtil.clear();
//    }
//
//
//
//    /**
//     * 实时定位任务
//     */
//    private RealTimeHandler realTimeHandler = new RealTimeHandler();
//
//    static class RealTimeHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    }
//
//
//    /**
//     * 获取当前位置
//     */
//    public void getCurrentLocation(final OnGetGeoCoderResultListener listener) {
//        mTraceClient.queryRealTimeLoc(locRequest,  new OnEntityListener() {
//
//            @Override
//            public void onReceiveLocation(TraceLocation location) {
//
//
//                if (StatusCodes.SUCCESS != location.getStatus() || CommonUtil.isZeroPoint(location.getLatitude(),
//                        location.getLongitude())) {
//                    ReverseGeoCodeResult result= new ReverseGeoCodeResult();
//                    result.error= SearchResult.ERRORNO.REQUEST_ERROR;
//                    if (listener!=null){
//                        listener.onGetReverseGeoCodeResult(result);
//                    }
//
//                    return;
//                }
//                LatLng currentLatLng = convertTraceLocation2Map(location);
//                if (null == currentLatLng ) {
//                    ReverseGeoCodeResult result= new ReverseGeoCodeResult();
//                    result.error= SearchResult.ERRORNO.REQUEST_ERROR;
//                    if (listener!=null){
//                        listener.onGetReverseGeoCodeResult(result);
//                    }
//                    return;
//                }
//                if (listener!=null){
//                    latlngToAddress(currentLatLng, listener );
//                }
//
//            }
//
//        });
//    }
//    /**
//     * 经纬度或地址相互转换
//     *
//     * @param latlng
//     */
//    private void latlngToAddress(LatLng latlng,OnGetGeoCoderResultListener listener) {
//        GeoCoder geoCoder = GeoCoder.newInstance();
//        // 设置反地理经纬度坐标,请求位置时,需要一个经纬度
//        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latlng));
//        //设置地址或经纬度反编译后的监听,这里有两个回调方法,
//        geoCoder.setOnGetGeoCodeResultListener(listener);
//    }
//
//
//    /**
//     * 获取请求标识
//     *
//     * @return
//     */
//    public int getTag() {
//        return mSequenceGenerator.incrementAndGet();
//    }
//
//
//    private void initListener() {
//
//        traceListener = new OnTraceListener() {
//
//
//            @Override
//            public void onBindServiceCallback(int i, String s) {
//
//            }
//
//            @Override
//            public void onStartTraceCallback(int errorNo, String message) {
//                if (StatusCodes.SUCCESS == errorNo || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
//                    isTraceStarted = true;
//                    SharedPreferences.Editor editor = trackConf.edit();
//                    editor.putBoolean("is_trace_started", true);
//                    editor.apply();
//                    L.e(Tag, "鹰眼服务开启成功");
//                }
//
//            }
//
//            @Override
//            public void onStopTraceCallback(int errorNo, String message) {
//                if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
//                    isTraceStarted = false;
//                    // 停止成功后，直接移除is_trace_started记录（便于区分用户没有停止服务，直接杀死进程的情况）
//                    SharedPreferences.Editor editor = trackConf.edit();
//                    editor.remove("is_trace_started");
//                    editor.remove("is_gather_started");
//                    editor.apply();
//                    unregisterPowerReceiver();
//                }
//            }
//
//            @Override
//            public void onStartGatherCallback(int errorNo, String message) {
//
//                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STARTED == errorNo) {
//                    isGatherStarted = true;
//                    SharedPreferences.Editor editor = trackConf.edit();
//                    editor.putBoolean("is_gather_started", true);
//                    editor.apply();
//                    L.e(Tag, "鹰眼轨迹收集成功");
//                }
//            }
//
//            @Override
//            public void onStopGatherCallback(int errorNo, String message) {
//                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {
//                    isGatherStarted = false;
//                    SharedPreferences.Editor editor = trackConf.edit();
//                    editor.remove("is_gather_started");
//                    editor.apply();
//                }
//            }
//
//            @Override
//            public void onPushCallback(byte messageType, PushMessage pushMessage) {
//            }
//        };
//
//    }
//
//    /**
//     * 将轨迹实时定位点转换为地图坐标
//     *
//     * @param location
//     * @return
//     */
//    public static LatLng convertTraceLocation2Map(TraceLocation location) {
//        if (null == location) {
//            return null;
//        }
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//        if (Math.abs(latitude - 0.0) < 0.000001 && Math.abs(longitude - 0.0) < 0.000001) {
//            return null;
//        }
//        LatLng currentLatLng = new LatLng(latitude, longitude);
//        L.e("CoordType="+location.getCoordType());
//
//        L.e("CoordAd="+location.toString());
//        if (CoordType.wgs84 == location.getCoordType()) {
//            LatLng sourceLatLng = currentLatLng;
//            CoordinateConverter converter = new CoordinateConverter();
//            converter.from(CoordinateConverter.CoordType.GPS);
//            converter.coord(sourceLatLng);
//            currentLatLng = converter.convert();
//        }
//        return currentLatLng;
//    }
//
//
//
//
//    private void unregisterPowerReceiver() {
//        if (!isRegisterReceiver) {
//            return;
//        }
//        if (null != trackReceiver) {
//            CoolqiOperations.getInstance().unregisterReceiver(trackReceiver);
//        }
//        isRegisterReceiver = false;
//    }
//
//    /**
//     * 清除Trace状态：初始化app时，判断上次是正常停止服务还是强制杀死进程，根据trackConf中是否有is_trace_started字段进行判断。
//     * <p>
//     * 停止服务成功后，会将该字段清除；若未清除，表明为非正常停止服务。
//     */
//    private void clearTraceStatus() {
//        if (trackConf.contains("is_trace_started") || trackConf.contains("is_gather_started")) {
//            SharedPreferences.Editor editor = trackConf.edit();
//            editor.remove("is_trace_started");
//            editor.remove("is_gather_started");
//            editor.apply();
//        }
//    }
//}
