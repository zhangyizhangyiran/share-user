package com.soe.sharesoe.module.mycenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.module.mycenter.FavoritesDeleteListener;
import com.soe.sharesoe.module.mycenter.adapter.FavoritesAdapter;
import com.soe.sharesoe.module.mycenter.bean.FavoritesModel;
import com.soe.sharesoe.module.mycenter.order.SlideRecyclerView;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.T;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 收藏夹
 * @encoding UTF-8
 * @date 17/10/28
 * @time 下午5:28
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class FavoritesActivity extends RxBaseActivity implements FavoritesDeleteListener {

    @BindView(R.id.bar_left_img)
    ImageView mBarLeftImg;
    @BindView(R.id.bar_left)
    TextView mBarLeft;
    @BindView(R.id.bar_center_img)
    ImageView mBarCenterImg;
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.bar_right)
    TextView mBarRight;
    @BindView(R.id.bar_right_img)
    ImageView mBarRightImg;
    @BindView(R.id.layout_right_text)
    LinearLayout mLayoutRightText;
    @BindView(R.id.bar_right_serch_img)
    ImageView mBarRightSerchImg;
    @BindView(R.id.bar_right_msg_img)
    ImageView mBarRightMsgImg;
    @BindView(R.id.layout_right_img)
    LinearLayout mLayoutRightImg;
    @BindView(R.id.bar_view)
    View mBarView;
    @BindView(R.id.activity_favoites_recy)
    SlideRecyclerView mActivityFavoitesRecy;
    @BindView(R.id.frg_honm_swipe_refresh)
    SwipeRefreshLayout mFrgHonmSwipeRefresh;

    private FavoritesAdapter mFavoritesAdapter;
    public String Type = "REQUEST";

    @Override
    public int getLayoutId() {
        return R.layout.activity_favorites;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("收藏夹");
        mBarRight.setVisibility(View.VISIBLE);
        mBarRight.setText("清空");
        mBarRight.setTextColor(getResources().getColor(R.color.Color_Theme_Text_Label));

        initIData();
        initRecyclerview();


    }

    @Override
    public void onClick(View view) {

    }

    private void initRecyclerview() {
        mActivityFavoitesRecy.setLayoutManager(new LinearLayoutManager(this));
        mFavoritesAdapter = new FavoritesAdapter(R.layout.item_favorites_rv, this);
        mFavoritesAdapter.getPostin(this);
        mActivityFavoitesRecy.setAdapter(mFavoritesAdapter);
//        mFavoritesAdapter.addData(listData);


    }


    private void initIData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("pageSize", "20");

        RetrofitHelper.getInstance().getCollection(new Subscriber<FavoritesModel>() {
            @Override
            public void onCompleted() {

                T.success("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                T.success("onError");

                setEmpty();


            }

            @Override
            public void onNext(FavoritesModel httpBaseResult) {

//                设置数据
                setData(httpBaseResult);


            }
        }, map);


    }

    private void setEmpty() {

        mBarRight.setEnabled(false);
        mFavoritesAdapter.setNewData(null);
        mFavoritesAdapter.bindToRecyclerView(mActivityFavoitesRecy);
        mFavoritesAdapter.setEmptyView(R.layout.item_favorites_empty_view);
    }


    private void setData(FavoritesModel httpBaseResult) {


        List<FavoritesModel.ResultBean> result = httpBaseResult.getResult();

        if (httpBaseResult.getCode().equals("1002")) {

            mFavoritesAdapter.setNewData(null);
            mFavoritesAdapter.bindToRecyclerView(mActivityFavoitesRecy);
            mFavoritesAdapter.setEmptyView(R.layout.item_favorites_empty_view);


        } else if (httpBaseResult.getCode().equals("1000")) {

            mFavoritesAdapter.addData(result);
            mFavoritesAdapter.notifyDataSetChanged();

        } else {
            T.success(httpBaseResult.getMsg());
        }


    }


    @OnClick({R.id.bar_left_img, R.id.bar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_left_img:
                this.finish();
                break;
            case R.id.bar_right:

                mFavoritesAdapter.setNewData(null);
                mFavoritesAdapter.bindToRecyclerView(mActivityFavoitesRecy);
                mFavoritesAdapter.setEmptyView(R.layout.item_favorites_empty_view);
                getCollectionCancel();
                mBarRight.setEnabled(false);
                break;
        }
    }

    @Override
    public void getPostion(int po) {

        mFavoritesAdapter.remove(po);


    }

    private void getCollectionCancel() {

        HashMap<String, String> map = new HashMap<>();
        map.put("productId", "-1");

        RetrofitHelper.getInstance().getCollectionCancel(new Subscriber<FavoritesModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FavoritesModel favoritesModel) {
                if (favoritesModel.getCode().equals("1000")) {
                    Toast.makeText(FavoritesActivity.this, "已清除", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(FavoritesActivity.this, favoritesModel.getMsg(), Toast.LENGTH_SHORT).show();
                }


            }
        }, map);

    }
}
