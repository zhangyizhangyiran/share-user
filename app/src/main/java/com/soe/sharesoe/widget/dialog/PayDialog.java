package com.soe.sharesoe.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.soe.sharesoe.R;
import com.soe.sharesoe.manager.UserDataManager;
import com.soe.sharesoe.rxpay.Pay;

import java.util.Map;


/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 支付弹窗
 * @encoding UTF-8
 * @date 2017/11/7
 * @time 下午2:11
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
//                PayDialog.payShow(YearCardActivity.this, new PayDialog.PayClickListenr() {
//                  @Override
//                  public void onClick(String payChannel) {
//                       LogCoolqi.i("yearcard-->" + payChannel);
//                          setYearCard(payChannel);
//                          }
//                  });

public class PayDialog {
    public static void payShow(final Activity mActivity, final Pay.PayResultListenr listener, final Map<String, String> map) {
        final Dialog dialog = new Dialog(mActivity, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.pay_dialog, (ViewGroup) mActivity.getWindow().getDecorView(), false);
        //初始化控件
        //将布局设置给Dialog
        final RadioGroup recharge_rdgroup = (RadioGroup) inflate.findViewById(R.id.recharge_rdgroup);
        TextView tvPayAmount = (TextView) inflate.findViewById(R.id.pay_amount);
        TextView tvPayInfo = (TextView) inflate.findViewById(R.id.pay_info);
        RadioButton radioButton3 = (RadioButton) inflate.findViewById(R.id.recharge_radiobtn3);

        String payAmount = map.get("payAmount");
        double amount = Double.parseDouble(payAmount.equals("") ? "0" : payAmount);

        String walletAvailableAmount = UserDataManager.getWalletAvailableAmount();
        double availableAmount = Double.parseDouble(walletAvailableAmount.equals("") ? "0" : walletAvailableAmount);

        tvPayAmount.setText("￥" + amount);
        radioButton3.setText("余额支付  ¥" + availableAmount);
        if (amount > availableAmount) {
            tvPayInfo.setVisibility(View.VISIBLE);
        } else {
            tvPayInfo.setVisibility(View.GONE);
        }


        Button btn_pay = (Button) inflate.findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    return;
                }
                int id = recharge_rdgroup.getCheckedRadioButtonId();
                if (id == R.id.recharge_radiobtn1) {
                    map.put("channel", "1");
                } else if (id == R.id.recharge_radiobtn2) {
                    map.put("channel", "2");

                } else if (id == R.id.recharge_radiobtn3) {
                    map.put("channel", "3");
                }
                dialog.dismiss();

                new Pay(mActivity).requestPay(map, listener);
            }
        });
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            //lp.y = 10;//设置Dialog距离底部的距离
            //将属性设置给窗体
            lp.horizontalMargin = 0;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
        }
    }

}
