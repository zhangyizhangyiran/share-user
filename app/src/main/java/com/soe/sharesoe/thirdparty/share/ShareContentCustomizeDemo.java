package com.soe.sharesoe.thirdparty.share;

/**
 * @version V1.0
 * @ClassName:
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/1 10:50
 */

import android.content.Context;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 快捷分享项目现在添加为不同的平台添加不同分享内容的方法。
 * 本类用于演示如何区别Twitter的分享内容和其他平台分享内容。
 */

public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

    private Context mContext;
    private String contents;

    public ShareContentCustomizeDemo() {
    }

    public ShareContentCustomizeDemo(Context mContext, String contents) {
        this.mContext = mContext;
        this.contents = contents;

    }
    public ShareContentCustomizeDemo(Context mContext, String contents, String imageUrl) {
        this.mContext = mContext;
        this.contents = contents;

    }

    @Override
    public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
        // 改写twitter分享内容中的text字段，否则会超长，
        // 因为twitter会将图片地址当作文本的一部分去计算长度
        if (QZone.NAME.equals(platform.getName())) {

            paramsToShare.setText(contents + "");
//            paramsToShare.setImageData(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher_right));
//            paramsToShare.setImageUrl(imageUrl);
            paramsToShare.setShareType(Platform.SHARE_WEBPAGE);

        }

        if (QQ.NAME.equals(platform.getName())) {
            paramsToShare.setText(contents + "");
//            paramsToShare.setImageData(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher_right));
//            paramsToShare.setImageUrl(imageUrl);
            paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//            StatService.onEvent(mContext, BaiDustatistic.myrecomed_channel, QQ.NAME, 1);//事件统计
        }

        if (Wechat.NAME.equals(platform.getName())) {
            paramsToShare.setText(contents + "");
//            paramsToShare.setImageData(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher_right));
//            paramsToShare.setImageUrl(imageUrl);
            paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//            StatService.onEvent(mContext, BaiDustatistic.myrecomed_channel, Wechat.NAME, 1);//事件统计
        }

        if (WechatMoments.NAME.equals(platform.getName())) {
            paramsToShare.setTitle(contents + "");
//            paramsToShare.setImageData(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher_right));
//            paramsToShare.setImageUrl(imageUrl);
            paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//            StatService.onEvent(mContext, BaiDustatistic.myrecomed_channel, WechatMoments.NAME, 1);//事件统计
        }

        if (SinaWeibo.NAME.equals(platform.getName())) {
            paramsToShare.setTitle(contents + "");
//            paramsToShare.setImageData(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher_right));
//            paramsToShare.setImageUrl(imageUrl);
            paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//            StatService.onEvent(mContext, BaiDustatistic.myrecomed_channel, SinaWeibo.NAME, 1);//事件统计

        }
    }
}