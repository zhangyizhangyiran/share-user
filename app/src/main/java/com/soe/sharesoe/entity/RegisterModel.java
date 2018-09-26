package com.soe.sharesoe.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
 * @version v1.0
 * @project coolqi
 * @Description 注册model
 * @encoding UTF-8
 * @date 16/12/1
 * @time 下午6:26
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class RegisterModel implements Serializable{
    /**
     * 主键id
     */
    public String id="";
    /**
     *职位id
     */
    public String posId="";

    /**
     * 职位名称
     */
    public String posName="";

    /**
     * 职位编码
     */
    public String posCode="";


    /**
     *合伙人id
     */
    public String parId="";

    /**
     * 合伙人名称
     */
    public String parName="";

    /**
     *姓名
     */
    public String name="";

    /**
     *手机号
     */
    public String phonenum="";
    /**
     *部门
     */
    public String dep="";

    /**
     * 部门名称
     */
    public String depName="";

    /**
     * 部门编码
     */
    public String deptCode="";
    /**
     *地市 code
     */
    public String city="";

    /**
     *地市名称
     */
    public String cityName="";

    /**
     * 最后登录时间
     */
    public String lastLoginTime="";


    public ArrayList<Modle>  funMenus;

    public ArrayList<TaskModle>  taskTypes;


    @Override
    public String toString() {
        return "RegisterModel{" +
                "id='" + id + '\'' +
                ", posId='" + posId + '\'' +
                ", posName='" + posName + '\'' +
                ", posCode='" + posCode + '\'' +
                ", parId='" + parId + '\'' +
                ", parName='" + parName + '\'' +
                ", name='" + name + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", dep='" + dep + '\'' +
                ", depName='" + depName + '\'' +
                ", depCode='" + deptCode + '\'' +
                ", city='" + city + '\'' +
                ", cityName='" + cityName + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                '}';
    }


    public static class Modle implements Serializable{
        public String id;
        public String code;
        public String name;
    }

    public static class TaskModle implements Serializable{
        public String id;
        public String code;
        public String name;
    }
}
