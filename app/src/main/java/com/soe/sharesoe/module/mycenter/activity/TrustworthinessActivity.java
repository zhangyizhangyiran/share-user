package com.soe.sharesoe.module.mycenter.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.module.mycenter.adapter.TrustworthinessAdapter;
import com.soe.sharesoe.module.mycenter.bean.CreditChangeModel;
import com.soe.sharesoe.module.mycenter.bean.CreditInfoModel;
import com.soe.sharesoe.module.mycenter.view.SeekArc;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.utils.Q;
import com.soe.sharesoe.utils.S;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 信用积分
 * @encoding UTF-8
 * @date 17/11/7
 * @time 上午11:30
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class TrustworthinessActivity extends RxBaseActivity {


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

    @BindView(R.id.activity_trustworthiness_seekarc_recy)
    RecyclerView mActivityTrustworthinessSeekarcRecy;
    private SeekArc mActivity_trustworthiness_seekarc;
    private TextView mActivity_trustworthiness_seekarc_data;
    private TextView mActivity_trustworthiness_seekarc_number;
    private TextView mActivity_trustworthiness_seekarc_rule;

    private TrustworthinessAdapter mTrustworthinessAdapter;
    private int hasData = 150;
    //记录progressBar的完成程度
    private int status = 0;
    private List<String> listData;
    private CreditInfoModel mCreditInfoModel;
    //加载类型
    public String LoadType = "Request";
    //分页
    public int page = 1;


    //创建一个负责更新进度的hander
    Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
            status += hasData;
            if (!Q.isEmpty(mCreditInfoModel)) {

                if (!Q.isEmpty(mCreditInfoModel.getResult().getCredit())) {


                    if (status < Integer.parseInt(mCreditInfoModel.getResult().getCredit()) + 9999 / 2 + 100) {

                        mActivity_trustworthiness_seekarc.setProgress(status);
                        mHandler.postDelayed(this, 80);

                    }
                }
            }


        }
    };
    private String mUid;


    @Override
    public int getLayoutId() {
        return R.layout.activity_trustworthiness;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initVIew();
        mUid = (String) S.get(TrustworthinessActivity.this, "uid", "");
        getCreditInfo(mUid);
        getCreditChange(mUid, "20", "1");
    }

    //初始化view
    private void initVIew() {
        mBarTitle.setText("信用积分");
        mActivityTrustworthinessSeekarcRecy.setLayoutManager(new LinearLayoutManager(this));
        mTrustworthinessAdapter = new TrustworthinessAdapter(R.layout.item_trustwortthiness);
        View inflate = getLayoutInflater().inflate(R.layout.item_trustworthiness_head, null);
        mTrustworthinessAdapter.addHeaderView(inflate);
        mActivity_trustworthiness_seekarc = (SeekArc) inflate.findViewById(R.id.activity_trustworthiness_seekarc);
        mActivity_trustworthiness_seekarc.setMax(9999);
        mActivity_trustworthiness_seekarc_data = (TextView) inflate.findViewById(R.id.activity_trustworthiness_seekarc_data);
        mActivity_trustworthiness_seekarc_number = (TextView) inflate.findViewById(R.id.activity_trustworthiness_seekarc_number);
        mActivity_trustworthiness_seekarc_rule = (TextView) inflate.findViewById(R.id.activity_trustworthiness_seekarc_rule);
        mActivityTrustworthinessSeekarcRecy.setAdapter(mTrustworthinessAdapter);
        mTrustworthinessAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                getLoadMore();

            }
        }, mActivityTrustworthinessSeekarcRecy);


    }

    private void getCreditChange(String uid, String pageSize, String page) {
        RetrofitHelper.getInstance().getCreditChange(new Subscriber<CreditChangeModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CreditChangeModel creditChangeModel) {
                if (LoadType.equals("Request")) {
                    setTextInfo(creditChangeModel);
                    List<CreditChangeModel.ResultBean.ListBean> list = creditChangeModel.getResult().getList();
                    mTrustworthinessAdapter.setNewData(list);

                } else if (LoadType.equals("LOAD")) {


                    List<CreditChangeModel.ResultBean.ListBean> list = creditChangeModel.getResult().getList();
                    if (list.size() > 0) {

                        mTrustworthinessAdapter.addData(list);


                    } else {
                        mTrustworthinessAdapter.loadMoreEnd(false);

                    }


                }


            }
        }, uid, pageSize, page);
    }


    private void getCreditInfo(String uid) {

        RetrofitHelper.getInstance().getCreditInfo(new Subscriber<CreditInfoModel>() {


            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CreditInfoModel creditInfoModel) {

                mCreditInfoModel = creditInfoModel;
                setTextNumberInfo(creditInfoModel);


            }
        }, uid);


    }

    //信用积分变动

    private void setTextInfo(CreditChangeModel creditChangeModel) {
        if (!Q.isEmpty(creditChangeModel.getMsg())) {
            mActivity_trustworthiness_seekarc_data.setText(creditChangeModel.getMsg());


        }


    }

    //积分信息查询

    private void setTextNumberInfo(CreditInfoModel creditInfoModel) {

        initThread();
        mActivity_trustworthiness_seekarc_number.setText(creditInfoModel.getResult().getCredit());
    }

    //子线程开启动画
    private void initThread() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                mHandler.postDelayed(runnable, 80);
            }
        };

        thread.start();

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);

    }


    @OnClick(R.id.bar_left_img)
    public void onViewClicked() {
        this.finish();
    }

    void getLoadMore() {

        LoadType = "LOAD";
        page++;
        getCreditChange(mUid, "20", page + "");

    }
}
