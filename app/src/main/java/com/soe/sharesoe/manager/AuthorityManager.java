package com.soe.sharesoe.manager;



/**
 * Created by wangxiaofa on 2017/10/20.
 */

public class AuthorityManager {

    public final static String OPERATION = "WXRY"; //维修人员
    public final static String OPERATIONLERDER = "YWZZ"; //运维组长
    public final static String DISPATCH = "DDZY"; // 调度专员
    public final static String WAREHOUSE = "KG"; // 库管专员
    public final static String OPERATIONSUPERVISOR = "YWZG"; // 运维主管
    public final static String OPERATIONSUPERVISOR_CITY = "CSJL"; // 城市经理
    public final static String YWJL_CITY = "YWJL"; // 运维经理

    public final static String WDRW = "WDRW"; //我的任务
    public final static String CLFB = "CLFB"; //车辆分布
    public final static String WXSB = "WXSB"; // 维修上报
    public final static String CSTS = "CLTS"; // 车锁调试
    public final static String ZHSQ = "ZHSQ"; // 召回申请
    public final static String LYSC = "LYSC"; // 蓝牙搜车
    public final static String WXJL = "WXJL"; // 维修记录
    public final static String RYJK = "RYJK"; // 人员监控
    public final static String ZPRW = "ZPRW"; // 指派任务
    public final static String CSXJ = "CSXJ"; // 车锁巡检
    public final static String XJJL = "XJJL"; // 巡检记录
    public final static String GHCS = "GHCS"; // 更换车锁
    public final static String HSJL = "HSJL"; // 未换锁记录


    //CrashHandler实例
    private static AuthorityManager instance;

    private String mAuthority = "";

    private boolean isHaveTask;


    /**
     * 获取TraceManager实例 ,单例模式
     */
    public static AuthorityManager getInstance() {
        if (instance == null)
            instance = new AuthorityManager();
        return instance;
    }



    //是否有任务权限


    //获得职位类型

    //设置职位类型
    public void setAuthorityType(String authorityType) {
        mAuthority = authorityType;
    }

}
