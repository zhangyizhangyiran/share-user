package com.soe.sharesoe.entity;


import com.soe.sharesoe.module.sort.search.MySection;
import com.soe.sharesoe.module.sort.search.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class DataServer {

    private static final String HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK = "https://avatars1.githubusercontent.com/u/7698209?v=3&s=460";
    private static final String CYM_CHAD = "CymChad";

    private DataServer() {
    }

    public static List<Status> getSampleData(int lenth) {
        List<Status> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            Status status = new Status();
            status.setUserName("Chad" + i);
            status.setCreatedAt("04/05/" + i);
            status.setRetweet(i % 2 == 0);
            status.setUserAvatar("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            status.setText("BaseRecyclerViewAdpaterHelper https://www.recyclerview.org");
            list.add(status);
        }
        return list;
    }

    public static List<Status> addData(List list, int dataSize) {
        for (int i = 0; i < dataSize; i++) {
            Status status = new Status();
            status.setUserName("Chad" + i);
            status.setCreatedAt("04/05/" + i);
            status.setRetweet(i % 2 == 0);
            status.setUserAvatar("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            status.setText("Powerful and flexible RecyclerAdapter https://github.com/CymChad/BaseRecyclerViewAdapterHelper");
            list.add(status);
        }

        return list;
    }

    public static List<MySection> getSampleData() {
        List<MySection> list = new ArrayList<>();
        list.add(new MySection(true, "大家都在找", false));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, "相机")));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, "篮球")));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, "笔记本")));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, "智能机器人")));
        list.add(new MySection(true, "搜索历史", false));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, "VR一体机")));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, "蓝牙音箱")));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, "无人机")));
        list.add(new MySection(true, "清除历史记录", true));

        return list;
    }

    public static List<String> getStrData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String str = HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK;
            if (i % 2 == 0) {
                str = CYM_CHAD;
            }
            list.add(str);
        }
        return list;
    }

    public static List<MultipleItem> getMultipleItemData() {
        List<MultipleItem> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(new MultipleItem(MultipleItem.IMG, 3));
        }
        list.add(new MultipleItem(MultipleItem.TEXT, 6));
        for (int i = 0; i < 9; i++) {
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, 2));
        }
//        list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
//        list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
//        }

        return list;
    }

//    public static List<HomeItem> getHomeItemData() {
//        List<HomeItem> list = new ArrayList<>();
//        list.add(new HomeItem(HomeItem.RECOM_TITILE, 12));
//
//        for (int i = 0; i < 2; i++) {
//            list.add(new HomeItem(HomeItem.RECOM_TOP, 6));
//        }
//        for (int i = 0; i < 4; i++) {
//            list.add(new HomeItem(HomeItem.RECOM_BOTTOM, 3));
//        }
//        list.add(new HomeItem(HomeItem.RECOM_VIEW, 12));
//
//        List<ProductList.ListBean> categoryAllModels = new ArrayList<ProductList.ListBean>();
//
//        for (int i = 0; i < 9; i++) {
//            ProductList.ListBean categoryAllModel = new ProductList.ListBean();
//            categoryAllModel.setCategoryDesc("rv");
//            categoryAllModel.setCategoryLogo("rvfew");
//            categoryAllModel.setCategoryName("errrr");
//            categoryAllModel.setId(9527);
//            categoryAllModels.add(categoryAllModel);
//            list.add(new HomeItem(HomeItem.GOODS_LIST, 12, categoryAllModels));
//        }
//        return list;
//    }

//    public static List<MultipleItem> getMultipleChildView() {
//        List<MultipleItem> list = new ArrayList<>();
//        for (int i = 0; i <= 4; i++) {
//            list.add(new ClickEntity(ClickEntity.CLICK_ITEM_VIEW, MultipleItem.TEXT_SPAN_SIZE, CYM_CHAD));
//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.BIG_IMG_SPAN_SIZE));
//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
//        }
//
//        return list;
//    }


}
