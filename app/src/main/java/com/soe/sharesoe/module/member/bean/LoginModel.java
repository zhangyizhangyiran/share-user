package com.soe.sharesoe.module.member.bean;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date 2017/11/23
 * @time 上午11:18
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class LoginModel {

    /**
     * result : {"userNickname":"","userPhone":"188****9999","userRealName":"曹阳","userCardType":null,"userCardNo":"3207*********99010114019","userSex":1,"userHeadImage":"","userStatus":1,"inviteCode":"","lastLoginTime":989669098072859,"credit":null,"uid":14,"certify":true}
     * code : 1000
     * msg : 请求成功
     * params : {"token":"j7KoBXj7Mv6umK7rGosifxDcRKcAL02rflN3JZUKpxU"}
     */

    private ResultBean result;
    private String code;
    private String msg;
    private ParamsBean params;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ResultBean {
        /**
         * userNickname :
         * userPhone : 188****9999
         * userRealName : 曹阳
         * userCardType : null
         * userCardNo : 3207*********99010114019
         * userSex : 1
         * userHeadImage :
         * userStatus : 1
         * inviteCode :
         * lastLoginTime : 989669098072859
         * credit : null
         * uid : 14
         * certify : true
         */

        private String userNickname;
        private String userPhone;
        private String userRealName;
        private Object userCardType;
        private String userCardNo;
        private int userSex;
        private String userHeadImage;
        private int userStatus;
        private String inviteCode;
        private long lastLoginTime;
        private Object credit;
        private String uid;
        private boolean certify;

        public String getUserNickname() {
            return userNickname;
        }

        public void setUserNickname(String userNickname) {
            this.userNickname = userNickname;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public Object getUserCardType() {
            return userCardType;
        }

        public void setUserCardType(Object userCardType) {
            this.userCardType = userCardType;
        }

        public String getUserCardNo() {
            return userCardNo;
        }

        public void setUserCardNo(String userCardNo) {
            this.userCardNo = userCardNo;
        }

        public int getUserSex() {
            return userSex;
        }

        public void setUserSex(int userSex) {
            this.userSex = userSex;
        }

        public String getUserHeadImage() {
            return userHeadImage;
        }

        public void setUserHeadImage(String userHeadImage) {
            this.userHeadImage = userHeadImage;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public Object getCredit() {
            return credit;
        }

        public void setCredit(Object credit) {
            this.credit = credit;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public boolean isCertify() {
            return certify;
        }

        public void setCertify(boolean certify) {
            this.certify = certify;
        }
    }

    public static class ParamsBean {
        /**
         * token : j7KoBXj7Mv6umK7rGosifxDcRKcAL02rflN3JZUKpxU
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
