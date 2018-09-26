package com.soe.sharesoe.rxpay.wechatpay;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/25
 * @time 下午4:25
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class WXPayWay {


    public static void payMoney(Activity context, JSONObject json) {
        Toast.makeText(context,"微信支付状态："+5,Toast.LENGTH_SHORT).show();

        String appId = getAppId(context);
        IWXAPI api = WXAPIFactory.createWXAPI(context, appId);
        api.registerApp(appId);
        PayReq payReq = new PayReq();
        payReq.appId = appId;
        try {
            payReq.partnerId = json.getString("partnerid");
            payReq.prepayId = json.getString("prepayid");
            payReq.nonceStr = json.getString("noncestr");
            payReq.timeStamp = json.getString("timestamp");
            payReq.sign = json.getString("sign");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        payReq.packageValue = "Sign=WXPay";
        api.sendReq(payReq);
    }

    public static String getAppId(Activity context) {
        ApplicationInfo info = null;
        try {
            info = context.getApplication().getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            String wx_appid = info.metaData.getString("WX_APPID");
            if (wx_appid == null) {
                throw new NullPointerException("appid not null");
            }
            return wx_appid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
