package com.soe.sharesoe.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.soe.sharesoe.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wangxiaofa
 * @version 1.0.0
 * @project sharesoe
 * @Description 退款原因弹窗
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

public class RefundReasonDialog {

    private ArrayList<String> mDataList;

    public void Show(final Activity mActivity, final ReasonClickListenr listener) {
        final Dialog dialog = new Dialog(mActivity, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.refund_reason_dialog, (ViewGroup) mActivity.getWindow().getDecorView(), false);
        //初始化控件
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.refund_reason_ry);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        Button closeBtn = (Button) inflate.findViewById(R.id.btn_close);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        initData(recyclerView, mActivity);
        //将布局设置给Dialog
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

    public interface ReasonClickListenr {
        void onClick(String payChannel);
    }

    /**
     * 初始化数据
     */

    private void initData(RecyclerView recyclerView, Context mContext) {

        mDataList = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            mDataList.add("内容 - " + i);
        }
        /*
        设置适配器
         */
        MyAdapter myAdapter = new MyAdapter(mDataList, mContext);
        recyclerView.setAdapter(myAdapter);
    }

    /**
     * RecyclerView适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<String> mDataList;
        private Context mContext;
        private int clickPosition = 0;

        public MyAdapter(List<String> list, Context mContext) {
            mDataList = list;
            this.mContext = mContext;
        }

        @Override
        public int getItemCount() {
            // 返回数据集合大小
            return mDataList == null ? 0 : mDataList.size();
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            RadioButton rd = holder.radioBtn;
            rd.setText(mDataList.get(position));

            if (position == clickPosition) {

                rd.setChecked(true);
            } else {
                rd.setChecked(false);
            }
            rd.setTag(position);

            rd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPosition = (int) v.getTag();
                    Toast.makeText(mContext, ":" + clickPosition, Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refund_reason, parent, false));
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private RadioButton radioBtn;

            ViewHolder(View itemView) {
                super(itemView);
                radioBtn = (RadioButton) itemView.findViewById(R.id.item_radio_btn);

            }

        }

    }
}
