package com.soe.sharesoe.module.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.coolqi.lib.sqscaner.QrCodeActivity;
import com.soe.sharesoe.R;

/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description  扫码开锁
 * @encoding UTF-8
 * @date 2017/11/9
 * @time 下午2:51
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class AppQrCodeActivity extends QrCodeActivity {
    private static final String APP_TITLE = "APP_TITLE";
    public static final int APP_TITLE_CODE = 2019;
    private TextView title ;
    private ImageView btn_back ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = (TextView) findViewById(com.coolqi.lib.sqscaner.R.id.tv_title);
        btn_back = (ImageView) findViewById(com.coolqi.lib.sqscaner.R.id.btn_back);
        btn_back.setImageResource(R.mipmap.icon_back);
        String appTitle = getIntent().getStringExtra(APP_TITLE);
        if (!TextUtils.isEmpty(appTitle)){
            title.setText(appTitle);
        }
    }

    public static void start(Activity context , String appTitle){
        Intent intent = new Intent(context ,AppQrCodeActivity.class);
        intent.putExtra(APP_TITLE ,appTitle);
        context.startActivityForResult(intent,APP_TITLE_CODE);
    }

    public static void start(Activity context , String appTitle,int requestCode){
        Intent intent = new Intent(context ,AppQrCodeActivity.class);
        intent.putExtra(APP_TITLE ,appTitle);
        context.startActivityForResult(intent,requestCode);
    }
}
