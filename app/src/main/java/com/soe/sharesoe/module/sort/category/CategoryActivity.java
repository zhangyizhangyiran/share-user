package com.soe.sharesoe.module.sort.category;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.entity.CategorySubModel;
import com.soe.sharesoe.net.RetrofitHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * author：王晓发
 * date： 2017/11/2 11:13
 * desctiption：分类二级列表
 */

public class CategoryActivity extends RxBaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right_img)
    ImageView barRightImg;

    private List<String> menuList = new ArrayList<>();
    private List<CategorySubModel> homeList = new ArrayList<>();
    private List<Integer> showTitle;

    private ListView lv_menu;
    private ListView lv_home;

    private MenuAdapter menuAdapter;
    private HomeAdapter homeAdapter;
    private int currentItem;

    private TextView tv_title;

    long cid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_category;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("二级分类");

        lv_menu = (ListView) findViewById(R.id.lv_menu);
        tv_title = (TextView) findViewById(R.id.tv_titile);
        lv_home = (ListView) findViewById(R.id.lv_home);

        cid = getIntent().getExtras().getInt("cid");
        Logger.d(cid);


        menuAdapter = new MenuAdapter(CategoryActivity.this, menuList);
        lv_menu.setAdapter(menuAdapter);

        homeAdapter = new HomeAdapter(CategoryActivity.this, homeList);
        lv_home.setAdapter(homeAdapter);

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
                tv_title.setText(menuList.get(position));
                lv_home.setSelection(showTitle.get(position));
            }
        });


        lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = showTitle.indexOf(firstVisibleItem);
//				lv_home.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    tv_title.setText(menuList.get(currentItem));
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });
        loadData();
    }

    @OnClick({R.id.bar_left_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                finish();
                break;
        }
    }

    private void loadData() {

        RetrofitHelper.getInstance().getCategorySub(cid, new Subscriber<List<CategorySubModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<CategorySubModel> listHttpBaseResult) {

                showTitle = new ArrayList<>();
                for (int i = 0; i < listHttpBaseResult.size(); i++) {
                    CategorySubModel categorybean = listHttpBaseResult.get(i);
                    menuList.add(categorybean.getCategoryName());
                    showTitle.add(i);
                    homeList.add(categorybean);
                }
                tv_title.setText(listHttpBaseResult.get(0).getCategoryName());


                menuAdapter.notifyDataSetChanged();
                homeAdapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * 得到json文件中的内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
