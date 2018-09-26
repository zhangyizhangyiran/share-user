package com.soe.sharesoe.module.sort;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxLazyFragment;
import com.soe.sharesoe.entity.CategoryAllModel;
import com.soe.sharesoe.entity.MultipleItem;
import com.soe.sharesoe.module.sort.category.CategoryActivity;
import com.soe.sharesoe.module.sort.search.SearchGoodsActivity;
import com.soe.sharesoe.net.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/10/28
 * @time 上午10:14
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class SortFragment extends RxLazyFragment {

    @BindView(R.id.bar_left_img)
    ImageView bar_left_img;
    @BindView(R.id.bar_right_msg_img)
    ImageView bar_right_msg_img;
    @BindView(R.id.bar_title)
    TextView bar_title;
    @BindView(R.id.bar_right_serch_img)
    ImageView bar_right_serch_img;
    @BindView(R.id.layout_right_img)
    LinearLayout layout_right_img;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_sort;
    }

    @Override
    public void finishCreateView(Bundle state) {
        init();
    }

    protected void init() {
        bar_left_img.setVisibility(View.INVISIBLE);
        bar_title.setText("分类");
        bar_right_msg_img.setVisibility(View.VISIBLE);
        bar_right_msg_img.setImageResource(R.mipmap.icon_home_search);
        layout_right_img.setVisibility(View.VISIBLE);
        bar_right_serch_img.setVisibility(View.INVISIBLE);

        setCategoryAllData();
    }

    @OnClick({R.id.bar_right_msg_img})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_right_msg_img:
                startActivity(new Intent(getActivity(), SearchGoodsActivity.class));
                break;
            default:
                break;
        }
    }

    List<MultipleItem> list = new ArrayList<>();

    private void setCategoryAllData() {

        RetrofitHelper.getInstance().getCategoryAll(new Subscriber<List<CategoryAllModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final List<CategoryAllModel> categoryAllModels) {
                //分割线
                list.add(new MultipleItem(MultipleItem.RECOM_VIEW, 6));
                //热门推荐
                for (int i = 0; i < 4; i++) {
                    MultipleItem multiple0 = new MultipleItem(MultipleItem.IMG, 3);
                    multiple0.setImgRs(R.mipmap.ic_default_logo);
                    list.add(multiple0);
                }

                //全部分类标题
                MultipleItem multiple1 = new MultipleItem(MultipleItem.TEXT, 6);
                multiple1.setContent("全部分类");
                list.add(multiple1);

                //全部分类
                for (int i = 0; i < categoryAllModels.size(); i++) {
                    MultipleItem multiple2 = new MultipleItem(MultipleItem.IMG_TEXT, 2);
                    multiple2.setCategoryAllList(categoryAllModels.get(i));
                    list.add(multiple2);
                }
                //分割线
                list.add(new MultipleItem(MultipleItem.RECOM_VIEW, 6));

//                    final List<MultipleItem> data = DataServer.getMultipleItemData();
                final MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(getContext(), list);

                final GridLayoutManager manager = new GridLayoutManager(getActivity(), 6);
                rv_list.setLayoutManager(manager);


                multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                        return list.get(position).getSpanSize();
                    }
                });
                rv_list.setAdapter(multipleItemAdapter);
                rv_list.addItemDecoration(new SpaceItemDecoration(10));

                multipleItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        getActivity().startActivity(new Intent(getActivity(), CategoryActivity.class).putExtra("cid",
                                ((MultipleItem) adapter.getData().get(position)).getCategoryAllList().getId()));
                    }
                });
            }
        });

    }

//    public static List<MultipleItem> getMultipleItemData() {
//        List<MultipleItem> list = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            list.add(new MultipleItem(MultipleItem.IMG, 3));
//        }
//        list.add(new MultipleItem(MultipleItem.TEXT, 6));
//        for (int i = 0; i < 9; i++) {
//            list.add(new MultipleItem(MultipleItem.IMG_TEXT, 2));
//        }
//
//        return list;
//    }

}
