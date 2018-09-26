package com.soe.sharesoe.module.mycenter.bean;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date 2017/11/14
 * @time 下午2:19
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class UserInfoModel {

    /**
     * result : {"userNickname":"","userPhone":"188****3333","userRealName":"","userCardType":null,"userCardNo":"","userSex":0,"userHeadImage":"","userStatus":1,"inviteCode":"","lastLoginTime":645549880445612,"credit":0}
     * code : 1000
     * msg :
     * params : {}
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
         * userPhone : 188****3333
         * userRealName :
         * userCardType : null
         * userCardNo :
         * userSex : 0
         * userHeadImage :
         * userStatus : 1
         * inviteCode :
         * lastLoginTime : 645549880445612
         * credit : 0
         */

        private String userNickname;
        private String userPhone;
        private String userRealName;
        private Object userCardType;
        private String userCardNo;
        private String userSex;
        private String userHeadImage;
        private String userStatus;
        private String inviteCode;
        private long lastLoginTime;
        private String credit;
        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

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

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public String getUserHeadImage() {
            return userHeadImage;
        }

        public void setUserHeadImage(String userHeadImage) {
            this.userHeadImage = userHeadImage;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
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

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }
    }

    public static class ParamsBean {
    }
}
