package com.soe.sharesoe.module.mycenter.wallet;

import java.util.List;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/9
 * @time 上午10:06
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class AccountRechargeFlowList {

    /**
     * count : 9
     * pageNO : 1
     * totalPage : 1
     * hasNextPage : false
     * pageSize : 10
     * records : [{"channelName":"支付宝支付","rechargeTime":"2017-11-01 15:13:50","rechargeAmount":101},{"channelName":"支付宝支付","rechargeTime":"2017-11-01 15:13:50","rechargeAmount":101},{"channelName":"支付宝支付","rechargeTime":"2017-11-01 15:13:50","rechargeAmount":101},{"channelName":"支付宝支付","rechargeTime":"2017-11-01 15:13:50","rechargeAmount":101},{"channelName":"支付宝支付","rechargeTime":"2017-11-01 15:13:50","rechargeAmount":101},{"channelName":"支付宝支付","rechargeTime":"2017-11-01 15:13:50","rechargeAmount":101},{"channelName":"支付宝支付","rechargeTime":"2017-11-01 15:13:50","rechargeAmount":101},{"channelName":"支付宝支付","rechargeTime":"2017-11-01 15:13:50","rechargeAmount":101},{"channelName":"支付宝支付","rechargeTime":"2017-11-01 15:13:50","rechargeAmount":100}]
     * firstIndex : 0
     * params : null
     */

    private int count;
    private int pageNO;
    private int totalPage;
    private boolean hasNextPage;
    private int pageSize;
    private int firstIndex;
    private Object params;
    private List<RecordsBean> records;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageNO() {
        return pageNO;
    }

    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * channelName : 支付宝支付
         * rechargeTime : 2017-11-01 15:13:50
         * rechargeAmount : 101
         */

        private String channelName;
        private String rechargeTime;
        private int rechargeAmount;

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getRechargeTime() {
            return rechargeTime;
        }

        public void setRechargeTime(String rechargeTime) {
            this.rechargeTime = rechargeTime;
        }

        public int getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(int rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }
    }
}
