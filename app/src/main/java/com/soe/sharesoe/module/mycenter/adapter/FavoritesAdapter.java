package com.soe.sharesoe.module.mycenter.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soe.sharesoe.R;
import com.soe.sharesoe.module.mycenter.FavoritesDeleteListener;
import com.soe.sharesoe.module.mycenter.bean.FavoritesModel;
import com.soe.sharesoe.net.RetrofitHelper;

import java.util.HashMap;

import rx.Subscriber;

/**
 * @author zy zhangyi <zhangyi, 1104745049@QQ.com
 * @version v1.0
 * @project
 * @Description 收藏夹
 * @encoding UTF-8
 * @date 17/10/30
 * @time 下午2:40
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class FavoritesAdapter extends BaseQuickAdapter<FavoritesModel.ResultBean, BaseViewHolder> {


    public FavoritesDeleteListener mMFavoritesDeleteListener;
    private Context mContext;


    public FavoritesAdapter(@LayoutRes int layoutResId, Context context) {
        super(layoutResId);

        mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FavoritesModel.ResultBean item) {

        TextView item_favorites_tv_sort = (TextView) helper.getView(R.id.item_favorites_tv_name);
        TextView item_favorites_tv_money = (TextView) helper.getView(R.id.item_favorites_tv_money);
        ImageView item_favorites_tv_imag = (ImageView) helper.getView(R.id.item_favorites_tv_imag);

        item_favorites_tv_sort.setText(item.getProductName());
        item_favorites_tv_money.setText(item.getRent());
        String pic = item.getPic();

        Glide.with(mContext).load(pic).into(item_favorites_tv_imag);


        helper.getView(R.id.item_favorites_tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = helper.getLayoutPosition();
                if (mMFavoritesDeleteListener != null) {
                    mMFavoritesDeleteListener.getPostion(layoutPosition);
//                    请求接口删除收藏
                    getCollectionCancel(layoutPosition, item);
                }
            }
        });

        helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = helper.getLayoutPosition();
                Toast.makeText(mContext, String.valueOf(layoutPosition), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getCollectionCancel(int layoutPosition, FavoritesModel.ResultBean item) {

        HashMap<String, String> map = new HashMap<>();
        map.put("productId", item.getId());

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
                    Toast.makeText(mContext, "已清除", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(mContext, favoritesModel.getMsg(), Toast.LENGTH_SHORT).show();
                }


            }
        }, map);

    }

    public void getPostin(FavoritesDeleteListener mFavoritesDeleteListener) {

        mMFavoritesDeleteListener = mFavoritesDeleteListener;
    }


}
