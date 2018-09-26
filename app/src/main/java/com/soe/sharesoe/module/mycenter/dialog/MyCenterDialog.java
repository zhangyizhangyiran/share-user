package com.soe.sharesoe.module.mycenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soe.sharesoe.R;
import com.soe.sharesoe.module.mycenter.activity.MyCenterActivity;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project
 * @Description 个人中心对话框
 * @encoding UTF-8
 * @date 17/10/24
 * @time 下午2:59
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class MyCenterDialog extends Dialog {
    @BindView(R.id.dialog_me_tv_head)
    TextView mDialogMeTvHead;
    @BindView(R.id.dialog_me_tv_sex)
    TextView mDialogMeTvSex;
    @BindView(R.id.dialog_me_tv_date)
    TextView mDialogMeTvDate;
    private String mType;
    private MyCenterActivity mMyCenterActivity;
    //    相册
    private static final int REQUEST_LIST_CODE = 0;
    //    拍照
    private static final int REQUEST_CAMERA_CODE = 1;


    public MyCenterDialog(@NonNull Context context, String type, MyCenterActivity myCenterActivity) {
        super(context, R.style.coupon_style);
        mType = type;
        mMyCenterActivity = (MyCenterActivity)myCenterActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_center_dialog);
        ButterKnife.bind(this);

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).placeholder(R.mipmap.ic_default_logo).error(R.mipmap.ic_default_logo).into(imageView);
            }
        });


//        "1" 代表头像 "2" 代表 性别
        if (mType.equals("1")) {

            mDialogMeTvHead.setText("拍照");
            mDialogMeTvSex.setText("从相册选择");
            mDialogMeTvDate.setText("取消");

        } else if (mType.equals("2")) {
            mDialogMeTvHead.setText("男");
            mDialogMeTvSex.setText("女");
            mDialogMeTvDate.setText("取消");
        }

        //获取屏幕宽高
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        //屏幕宽
        int widthPixels = displayMetrics.widthPixels;
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        //设置给attributes
        attributes.width = (int) (widthPixels * 1);
        //让Dialog位置居底部
        attributes.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(attributes);
        //点击空白处不dismiss
        setCanceledOnTouchOutside(true);
    }

    @OnClick({R.id.dialog_me_tv_head, R.id.dialog_me_tv_sex, R.id.dialog_me_tv_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_me_tv_head:
                if (mType.equals("1")) {
                    ISCameraConfig configs = new ISCameraConfig.Builder()
                            .needCrop(true)
                            .cropSize(1, 1, 200, 200)
                            .build();

                    ISNav.getInstance().toCameraActivity(mMyCenterActivity, configs, REQUEST_CAMERA_CODE);
                } else {
                    EventBus.getDefault().post("1");

                }


                this.dismiss();

                break;
            case R.id.dialog_me_tv_sex:

                if (mType.equals("1")) {
                    ISListConfig config = new ISListConfig.Builder()
                            // 是否多选
                            .multiSelect(false)
                            .btnText("Confirm")
                            // 确定按钮背景色
                            //.btnBgColor(Color.parseColor(""))
                            // 确定按钮文字颜色
                            .btnTextColor(Color.WHITE)
                            // 使用沉浸式状态栏
                            .statusBarColor(Color.parseColor("#3F51B5"))
                            // 返回图标ResId
                            .title("图片")
                            .titleColor(Color.WHITE)
                            .titleBgColor(Color.parseColor("#3F51B5"))
                            .allImagesText("All Images")
                            .needCrop(true)
                            .cropSize(1, 1, 200, 200)
                            // 第一个是否显示相机
                            .needCamera(true)
                            // 最大选择图片数量
                            .maxNum(9)
                            .build();

                    ISNav.getInstance().toListActivity(mMyCenterActivity, config, REQUEST_LIST_CODE);


                } else {
                    EventBus.getDefault().post("2");
                }



                this.dismiss();
                break;
            case R.id.dialog_me_tv_date:
                this.dismiss();
                break;
        }
    }
}
