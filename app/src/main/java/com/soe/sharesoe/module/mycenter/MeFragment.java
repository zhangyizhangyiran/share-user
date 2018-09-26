package com.soe.sharesoe.module.mycenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxLazyFragment;
import com.soe.sharesoe.manager.UserDataManager;
import com.soe.sharesoe.module.member.activity.MemberActivity;
import com.soe.sharesoe.module.member.edittext.StringUtils;
import com.soe.sharesoe.module.mycenter.activity.FavoritesActivity;
import com.soe.sharesoe.module.mycenter.activity.MyCenterActivity;
import com.soe.sharesoe.module.mycenter.activity.SetActivity;
import com.soe.sharesoe.module.mycenter.activity.TrustworthinessActivity;
import com.soe.sharesoe.module.mycenter.bean.UserInfoModel;
import com.soe.sharesoe.module.mycenter.order.SlidingTabOrderActivity;
import com.soe.sharesoe.module.mycenter.reserve.ProductReserveActivity;
import com.soe.sharesoe.module.mycenter.view.CircleImageView;
import com.soe.sharesoe.module.mycenter.wallet.WalletActivity;
import com.soe.sharesoe.net.RetrofitHelper;
import com.soe.sharesoe.thirdparty.share.Share;
import com.soe.sharesoe.utils.S;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;


/**
 * @author zhangyi<zhangyi, 1104745049@qq.com>
 * @version v1.0
 * @project
 * @Description 我的界面
 * @encoding UTF-8
 * @date 17/10/23
 * @time 下午8:48
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class MeFragment extends RxLazyFragment {

    @BindView(R.id.fragment_me_img_head)
    CircleImageView mFragmentMeImgHead;

    @BindView(R.id.fragment_me_lin_favorites)
    LinearLayout mFragmentMeLinFavorites;
    @BindView(R.id.fragment_me_tv_status_madebill)
    TextView mFragmentMeTvStatusMadebill;
    @BindView(R.id.fragment_me_tv_status_use)
    TextView mFragmentMeTvStatusUse;
    @BindView(R.id.fragment_me_tv_status_exchange)
    TextView mFragmentMeTvStatusExchange;
    @BindView(R.id.fragment_me_tv_money)
    LinearLayout mFragmentMeTvMoney;
    @BindView(R.id.fragment_me_lin_contact)
    LinearLayout mFragmentMeLinContact;
    @BindView(R.id.fragment_me_lin_help)
    LinearLayout mFragmentMeLinHelp;
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
    @BindView(R.id.fragment_me_lin_appointment)
    LinearLayout mFragmentMeLinAppointment;
    @BindView(R.id.fragment_me_tv_indent)
    LinearLayout mFragmentMeTvIndent;
    @BindView(R.id.fragment_me_rela_set)
    RelativeLayout mFragmentMeRelaSet;
    @BindView(R.id.fragment_me_tv_no_login)
    TextView mFragmentMeTvNoLogin;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.fragment_me_tv_no_integral)
    TextView mFragmentMeTvNoIntegral;
    @BindView(R.id.fragment_me_lin_state)
    LinearLayout mFragmentMeLinState;
    @BindView(R.id.fragment_me_img_v)
    ImageView mFragmentMeImgV;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public void finishCreateView(Bundle state) {

        mBarLeftImg.setVisibility(View.INVISIBLE);
        mBarTitle.setText("我的");
        mLayoutRightImg.setVisibility(View.VISIBLE);
        mBarRightMsgImg.setVisibility(View.GONE);
        mBarRightSerchImg.setVisibility(View.INVISIBLE);
        mBarRightMsgImg.setVisibility(View.VISIBLE);
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_set);
        mBarRightMsgImg.setImageDrawable(drawable);


        setState();

    }

    @OnClick({R.id.fragment_me_tv_no_login, R.id.fragment_me_img_head, R.id.fragment_me_lin_favorites, R.id.fragment_me_tv_status_madebill, R.id.fragment_me_tv_status_use, R.id.fragment_me_tv_status_exchange, R.id.fragment_me_tv_money, R.id.fragment_me_lin_contact, R.id.fragment_me_lin_help, R.id.fragment_me_lin_appointment, R.id.fragment_me_rela_set, R.id.bar_right_msg_img, R.id.fragment_me_tv_indent, R.id.fragment_me_tv_no_integral})
    public void onClick(View view) {
        switch (view.getId()) {
//            设置
            case R.id.bar_right_msg_img:

                setLogin(new Intent(getActivity(), SetActivity.class).putExtra("position", 0));

                break;
//            未登录
            case R.id.fragment_me_tv_no_login:
                startActivity(new Intent(getContext(), MemberActivity.class));

                break;
//            头像
            case R.id.fragment_me_img_head:

                break;
//            收藏夹
            case R.id.fragment_me_lin_favorites:

                setLogin(new Intent(getActivity(), FavoritesActivity.class));
                break;

//            我的订单
            case R.id.fragment_me_tv_indent:

                setLogin(new Intent(getActivity(), SlidingTabOrderActivity.class).putExtra("position", 0));
                break;

            //            待付款
            case R.id.fragment_me_tv_status_madebill:

                setLogin(new Intent(getActivity(), SlidingTabOrderActivity.class).putExtra("position", 1));
                break;

//            使用中
            case R.id.fragment_me_tv_status_use:
                setLogin(new Intent(getActivity(), SlidingTabOrderActivity.class).putExtra("position", 2));
                break;

//            已退换
            case R.id.fragment_me_tv_status_exchange:
                setLogin(new Intent(getActivity(), SlidingTabOrderActivity.class).putExtra("position", 3));
                break;

//            我的钱包
            case R.id.fragment_me_tv_money:
                setLogin(new Intent(getActivity(), WalletActivity.class));
                break;

//            联系我们
            case R.id.fragment_me_lin_contact:

                toCallPhoneActivity(getActivity(), "10086");
                Toast.makeText(getContext(), "联系我们", Toast.LENGTH_SHORT).show();
                break;

//            我的帮助
            case R.id.fragment_me_lin_help:
                new Share().Share(getActivity());
                Toast.makeText(getContext(), "我的帮助", Toast.LENGTH_SHORT).show();

                break;

//            已预约
            case R.id.fragment_me_lin_appointment:

                setLogin(new Intent(getActivity(), ProductReserveActivity.class));

                break;

//            个人信息设置
            case R.id.fragment_me_rela_set:

                setLogin(new Intent(getActivity(), MyCenterActivity.class));

                break;

//            信用积分
            case R.id.fragment_me_tv_no_integral:

                setLogin(new Intent(getActivity(), TrustworthinessActivity.class));
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setState();
        setPathPhoto();

    }

    private void setPathPhoto() {
        String photo = (String) S.get(getActivity(), "photo", "");
        String isLogin = (String) S.get(getActivity(), "isLogin", "false");
        boolean empty = StringUtils.isEmpty(photo);

        if (isLogin.equals("true")) {

            if (empty) {

            } else {
                Glide.with(this).load(new File(photo)).into(mFragmentMeImgHead);

            }

        }



    }


    //    监听fragment状态
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (getUserVisibleHint()) {
                //界面可见时
                setState();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setState() {


        String attestation = (String) S.get(getActivity(), "attestation", "");
        if (attestation.equals("true")) {

            mFragmentMeImgV.setImageResource(R.mipmap.icon_v);
        } else {
            mFragmentMeImgV.setImageResource(R.mipmap.icon_v_un);
        }

        String isLogin = (String) S.get(getActivity(), "isLogin", "false");
        if (isLogin.equals("true")) {
            mFragmentMeImgV.setVisibility(View.VISIBLE);
            mFragmentMeTvNoLogin.setVisibility(View.GONE);
            mFragmentMeLinState.setVisibility(View.VISIBLE);
            initUserInfo();

        } else {

            mFragmentMeImgV.setVisibility(View.INVISIBLE);
            mFragmentMeTvNoLogin.setVisibility(View.VISIBLE);
            mFragmentMeLinState.setVisibility(View.GONE);

        }


    }

    private void initUserInfo() {
        RetrofitHelper.getInstance().getUserInfo(new Subscriber<UserInfoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoModel userInfoModels) {

                setUserInfo(userInfoModels);

            }
        });
    }


    private void setUserInfo(UserInfoModel userInfo) {

        mTextView.setText(userInfo.getResult().getUserPhone());
        mFragmentMeTvNoIntegral.setText(userInfo.getResult().getCredit());
        UserDataManager.setUid(userInfo.getResult().getUid());

    }

    public static void toCallPhoneActivity(Context mContext, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent call = new Intent(Intent.ACTION_DIAL, uri);
        mContext.startActivity(call);
    }

}
