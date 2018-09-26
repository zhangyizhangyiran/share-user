//package com.soe.sharesoe.widget.dialog;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.soe.sharesoe.R;
//import com.soe.sharesoe.base.Constant;
//import com.soe.sharesoe.utils.L;
//
//
///**
// * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
// * @version v1.0
// * @project 封装的 分享功能弹窗
// * @Description
// * @encoding UTF-8
// * @date 16/12/11
// * @time 上午11:44
// * @修改记录 <pre>
// * 版本       修改人         修改时间         修改内容描述
// * --------------------------------------------------
// * <p>
// * --------------------------------------------------
// * </pre>
// */
//public class ShareDialog {
//
//    //自定义的弹出框类
//    private View inflate;
//    private TextView wechat;
//    private TextView wxcircle;
//    private TextView qq;
//    private TextView qzone;
//    private TextView sina;
//    private Dialog dialog;
//    Activity mActivity;
//
//    private String mSharePage = "";
//    public boolean mShareFalg;
//
//    public ShareDialog(Activity mActivity) {
//        this.mActivity = mActivity;
//    }
//
//    public ShareDialog(Activity mActivity, String sharePage) {
//        this.mActivity = mActivity;
//        this.mSharePage = sharePage;
//    }
//
//    public void show(View view) {
//        dialog = new Dialog(mActivity, R.style.ActionSheetDialogStyle);
//        //填充对话框的布局
//        inflate = LayoutInflater.from(mActivity).inflate(R.layout.share_dialog, null);
//        //初始化控件
//        wechat = (TextView) inflate.findViewById(R.id.wechat);
//        wxcircle = (TextView) inflate.findViewById(R.id.wxcircle);
//        qq = (TextView) inflate.findViewById(R.id.qq);
//        qzone = (TextView) inflate.findViewById(R.id.qzone);
//        sina = (TextView) inflate.findViewById(R.id.sina);
//        wechat.setOnClickListener(mClick);
//        wxcircle.setOnClickListener(mClick);
//        qq.setOnClickListener(mClick);
//        qzone.setOnClickListener(mClick);
//        sina.setOnClickListener(mClick);
//        //将布局设置给Dialog
//        dialog.setContentView(inflate);
//        //获取当前Activity所在的窗体
//        Window dialogWindow = dialog.getWindow();
//        //设置Dialog从窗体底部弹出
//        if (dialogWindow != null) {
//            dialogWindow.setGravity(Gravity.BOTTOM);
//            //获得窗体的属性
//            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//            //lp.y = 10;//设置Dialog距离底部的距离
//            //将属性设置给窗体
//            lp.horizontalMargin = 0;
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            dialogWindow.setAttributes(lp);
//            dialog.show();//显示对话框
//        }
//
//    }
//
//    private String picurl = "";
//    private String title = Constant.SHARE_TITLE;
//    private String content = Constant.SHARE_CONTENT;
//    private View.OnClickListener mClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.wechat:
//                    SharePic(title, content, SHARE_MEDIA.WEIXIN);
//
//                    break;
//                case R.id.wxcircle:
//                    SharePic(title, content, SHARE_MEDIA.WEIXIN_CIRCLE);
//
//                    break;
//                case R.id.qq:
//                    SharePic(title, content, SHARE_MEDIA.QQ);
//
//                    break;
//                case R.id.qzone:
//                    SharePic(title, content, SHARE_MEDIA.QZONE);
//
//                    break;
//                case R.id.sina:
//                    SharePic(title, content, SHARE_MEDIA.SINA);
//
//                    break;
//            }
//            dialog.dismiss();
//        }
//    };
//
///*调用方式
//    public void share() {
//        ShareDialog shareDialog = new ShareDialog(mActivity);
//        if (host.equals("wechatshare")) {
//            shareDialog.shareData(title, content, SHARE_MEDIA.WEIXIN);
//            L.e("wechat---------");
//
//        } else if (host.equals("wechatimelineshare")) {
//            shareDialog.shareData(title, content, SHARE_MEDIA.WEIXIN_CIRCLE);
//        } else if (host.equals("qqshare")) {
//            shareDialog.shareData(title, content, SHARE_MEDIA.QQ);
//        } else if (host.equals("qzoneshare")) {
//            shareDialog.shareData(title, content, SHARE_MEDIA.QZONE);
//        } else if (host.equals("weiboshare")) {
//            shareDialog.shareData(title, content, SHARE_MEDIA.SINA);
//        }
//    }
//*/
//    public void shareData(String title, String content, SHARE_MEDIA Sharetype) {
//        SharePic(title, content, Sharetype);
//    }
//
//    /**
//     * 判断分享是否有图片
//     *
//     * @param title
//     * @param context
//     * @param Sharetype
//     */
//    private void SharePic(String title, String context, SHARE_MEDIA Sharetype) {
//
//        UMImage image = new UMImage(mActivity, R.mipmap.ic_launcher_round);//资源文件
//
//        UMWeb web = new UMWeb(Constant.SHARE_TYPE_URL);
//        web.setTitle(title);//标题
//        web.setThumb(image);  //缩略图
//        web.setDescription(context);//描述
//
//        new ShareAction(mActivity).setPlatform(Sharetype)
//                .withText(context)
//                .withMedia(web)
//                .setCallback(umShareListener)
//                .share();
//
//        L.e("sharePic------");
// /*       new ShareAction(mActivity).setPlatform(Sharetype)
//                .withText(context)
////                .withTitle(title)
//                .withMedia(new UMImage(mActivity, R.mipmap.ic_launcher))
////                .withTargetUrl(Constant.SHARE_TYPE_URL)
//                .setCallback(umShareListener)
//                .share();*/
//    }
//
//    private UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onStart(SHARE_MEDIA share_media) {
//
//        }
//
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            L.d("plat", "platform" + platform);
//
//            Toast.makeText(mActivity, " 分享成功", Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//
//            Toast.makeText(mActivity, " 分享失败啦,请检测您是否安装该应用!", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                L.d("throw", "throw:" + t.getMessage());
//            }
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(mActivity, " 分享取消", Toast.LENGTH_SHORT).show();
//
//        }
//    };
//
//}
