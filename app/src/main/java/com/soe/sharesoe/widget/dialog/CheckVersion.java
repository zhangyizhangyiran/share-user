package com.soe.sharesoe.widget.dialog;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


/**
 * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
 * @version v1.0
 * @project sharesoe
 * @Description 检查版本更新   调用方式         new CheckVersion(this);//检测版本更新
 * @encoding UTF-8
 * @date 16/12/31
 * @time 下午7:16
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CheckVersion {

    private Context context;

    public CheckVersion(Context context) {
        this.context = context;

        checkVersionCode();
    }

    /**
     * 获取版本状态
     */
    private void checkVersionCode() {

//        RetrofitHelper.getInstance().getLatestVersion(new Observer<VerisionCodeModel>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(VerisionCodeModel verisionCodeModel) {
//
//                String code = verisionCodeModel.getCode();
//
//                if (("0").equals(code)) {
//                    VerisionCodeModel.ResultBean result = verisionCodeModel.getResult();
//
//                    int forceUpdate = result.getForceUpdate();//是否是强制更新,1:强制更新
//                    String content = result.getVersionNote();//更新内容
//                    String version = result.getVersion();//版本号
//                    String apkUrl = result.getUpdateUrl();//apkUrl
//
//                    int newVersion = Integer.parseInt(GetDeleteShort(result.getVersion()));
//                    //LogCoolqi.i("version==>", newVersion + "");
//                    //版本号比较
//                    if (newVersion > getVersion()) {
//
//                       UpdateVersionDialog dialog = new UpdateVersionDialog(context, R.style.ActionSheetDialogStyle,apkUrl);//弹出提示框下载APP
//                        dialog.setCancelable(false);//弹框不可消失
//                        dialog.title = "发现新版本," + version;
//                        dialog.content = content;
//                        if (forceUpdate == 1) {
//
//                            dialog.viewFlag = View.GONE;
//                        } else {
//                            //dialog.setCancelable(true);//弹框可消失
//                            dialog.viewFlag = View.VISIBLE;
//                        }
//                        dialog.show();
//                       /* UpdateHelper.getInstance().init(context.getApplicationContext(), Color.parseColor("#93D209"));
//                        if (forceUpdate==1) {
//                            UpdateHelper.getInstance().autoUpdate(context.getPackageName(), true, 10*1000);
//                        }else {
//                            UpdateHelper.getInstance().autoUpdate(context.getPackageName(), false, 10*1000);
//                        }*/
//
//                    } else {
//                        //版本号无更新 if (getVersion()<=oldVersion)
//                        //LogCoolqi.i("version==>", "版本号无更新");
//                    }
//                } else {
//                    //未获取到数据
//                    //LogCoolqi.i("version==>", "未获取到数据");
//                }
//            }
//        });
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersion() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            int integerVersion = Integer.parseInt(GetDeleteShort(version));
            //LogCoolqi.i("version==>", integerVersion + "");
            return integerVersion;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 去掉String字符串中的.
     *
     * @param str
     * @return
     */
    public static String GetDeleteShort(String str) {
        String s = str.replace(".", "");
        return s;
    }
}
