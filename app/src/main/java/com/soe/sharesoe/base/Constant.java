package com.soe.sharesoe.base;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangxiaofa xiaofa <xiaofa, wangxiaofa_sir@163.com>
 * @version v1.0
 * @project sharesoe
 * @Description 存放常量 类
 * @encoding UTF-8
 * @date 16/12/5
 * @time 下午7:46
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class Constant {

    public static String TAG = "Coolqi_tag==>>";

    public static final String TOPIC_GOODS_PARAMS = "topicGoodsParams";
    public static final String EXTRA_ORDER = "extra_order";

    public static final String PAGE_PAY_ORDER = "PayOrderActivity";
    public static final String PAGE_ORDER_LIST = "OrderListActivity";

    /**
     * 网络请求返回结果:
     * status分为以下状态:
     * 200: 调用成功
     * 500: 预期失败
     * 404: 系统错误
     * 403: 重复登录
     */
    public static final int STATUS_SUCCESS = 0;
    public static final int CODE_LOGIN_TIMEOUT = 203;//登录超时
    public static final int CODE_LOGIN_FAIL = 204;//登录验证失败
    public static final int CODE_LOGIN_CHANGE = 205;//登录超时

    public static String SHARE_TYPE_URL = ""; //分享-URL链接地址
    public static String SHARE_TITLE = ""; //分享-TITLE
    public static String SHARE_CONTENT = ""; //分享-CONTENT

    //又拍云
    public static final String YOUPAIYUN_URL = "https://coolqi-img.b0.upaiyun.com/";
    public static final String YOUPAIYUN_PARAMS_BUCKET = "coolqi-img";
    public static final String YOUPAIYUN_KEY = "Tzf4J0nnMceG2lvcueDLmRkmCAo=";


    /**
     * 命名又拍云照片名
     *
     * @return
     */
    public static String upyPhotoName(String prefixPhotoName) {

        String identity = java.util.UUID.randomUUID().toString();
        if (identity.contains("-")) {
            identity = identity.replace("-", "");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return "/" + prefixPhotoName + "/" + str + "/" + identity + ".png";
    }

//    // 自定义图片加载器,已进行压缩处理
//    private static ImageLoader loader = new ImageLoader() {
//        @Override
//        public void displayImage(Context context, String path, ImageView imageView) {
//            // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
//            Glide.with(context).load(path).into(imageView);
//        }
//    };
//
//    // 自由配置选项
//    public static void setConfigImage(Context context, boolean needCrop, int x, int y, int code) {
//        ImgSelConfig config = new ImgSelConfig.Builder(context, loader)
//                // 是否多选
//                .multiSelect(false)
//                .btnText("")
//                //裁剪大小。needCrop为true的时候配置
//                .cropSize(1, 1, x, y)
//                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
//                .rememberSelected(false)
//                .needCrop(needCrop)
//                // 第一个是否显示相机
//                .needCamera(true)
//                .titleBgColor(Color.parseColor("#93D209"))
//                // 最大选择图片数量
//                .maxNum(1)
//                .build();
//
//        // 跳转到图片选择器
//        ImgSelActivity.startActivity((Activity) context, config, code);
//    }


}
