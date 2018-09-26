package com.soe.sharesoe.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coolqi.lib.sqscaner.utils.ScreenUtils;
import com.soe.sharesoe.R;

/**
 * @author sj jerry <SuiJing, suijing@foxmail.com>
 * @version ${VERSIONCODE}
 * @project study1
 * @Description
 * @encoding UTF-8
 * @date ${DATA}
 * @time 下午2:12
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class PromptDialog {

    public static void showDialog(Context mContext, String content,final ReserveClickListenr reserveClickListenr) {

        final CustomDialog dialog = new CustomDialog(mContext,
                ScreenUtils.getScreenWidth(mContext) * 3 / 4,
                ScreenUtils.getScreenHeight(mContext) * 2 / 9,
                R.layout.base_dialog_xml, R.style.Theme_dialog);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_commit = (Button) dialog.findViewById(R.id.btn_commit);
        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        tv_content.setText(content);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserveClickListenr.onClick();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public interface ReserveClickListenr {
        void onClick();
    }

}
