package com.soe.sharesoe.entity;

/**
 * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
 * @version v1.0
 * @project coolqi
 * @Description
 * @encoding UTF-8
 * @date 16/12/31
 * @time 下午6:58
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class VerisionCodeModel {


    /**
     * result : {"id":2,"dataSource":1,"version":"1.0.1","toVersion":"1.0.1","versionNote":"跟新闪现","forceUpdate":1,"createTime":1482306987000,"updateTime":1482306989000,"updateUrl":"http://patner.coolqi.com/"}
     * code : 0
     * msg : 新版本
     * params : {}
     */

    private ResultBean result;
    private String code;
    private String msg;

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

    public static class ResultBean {

        /**
         * id : 2
         * dataSource : 1
         * version : 1.0.1
         * toVersion : 1.0.1
         * versionNote : 跟新闪现
         * forceUpdate : 1
         * createTime : 1482306987000
         * updateTime : 1482306989000
         * updateUrl : http://patner.coolqi.com/
         */

        private int id;
        private int dataSource;
        private String version;
        private String toVersion;
        private String versionNote;
        private int forceUpdate;
        private long createTime;
        private long updateTime;
        private String updateUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDataSource() {
            return dataSource;
        }

        public void setDataSource(int dataSource) {
            this.dataSource = dataSource;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getToVersion() {
            return toVersion;
        }

        public void setToVersion(String toVersion) {
            this.toVersion = toVersion;
        }

        public String getVersionNote() {
            return versionNote;
        }

        public void setVersionNote(String versionNote) {
            this.versionNote = versionNote;
        }

        public int getForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(int forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }
    }

}
