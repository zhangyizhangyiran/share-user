package com.soe.sharesoe.module.sort.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxBaseActivity;
import com.soe.sharesoe.module.goods.GoodsDetailActivity;
import com.soe.sharesoe.module.home.ProductList;
import com.soe.sharesoe.module.member.edittext.StringUtils;
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
 * @Description 搜索结果界面
 * @encoding UTF-8
 * @date 17/11/10
 * @time 下午7:29
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class SearchResultsActivity extends RxBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.search_edittext)
    EditText mSearchEdittext;
    @BindView(R.id.search_clear)
    ImageView mSearchClear;
    @BindView(R.id.search_cancle)
    TextView mSearchCancle;
    @BindView(R.id.activity_search_result_recy_flush)
    RecyclerView mActivitySearchResultRecyFlush;
    @BindView(R.id.activity_swipe)
    SwipeRefreshLayout mActivitySwipe;
    private SearchResultsAdapter mSearchResultsAdapter;
    //SearchGoodsActivity输入框结果
    private String mSearchResult;
    //输入框结果
    private String mTrim;
    //页数
    private int page = 1;

    private ProductList mProductList;

    private Intent mIntent;
    //加载类型
    public String LoadType = "Request";

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_results;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mSearchEdittext.setHint("搜索商品");
        mSearchResult = (String) getIntent().getExtras().get("key");
        mIntent = new Intent(this, GoodsDetailActivity.class);
        //请求数据
        initData();
        //Adapter初始化工作
        initAdapter();
        //输入框监听
        initEditListener();

    }


    private void initAdapter() {

        mSearchResultsAdapter = new SearchResultsAdapter(this);
        mSearchResultsAdapter.setOnLoadMoreListener(this);

        mActivitySearchResultRecyFlush.setLayoutManager(new LinearLayoutManager(this));

        mActivitySearchResultRecyFlush.setAdapter(mSearchResultsAdapter);


        mSearchResultsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                String id = mProductList.getList().get(position).getId();
                mIntent.putExtra("pId", id);
                startActivity(mIntent);
                T.success(String.valueOf(position));
            }
        });


    }


    private void initData() {

        LoadType = "Request";
        setData(mSearchResult, String.valueOf(page));

    }

    private void setData(String keyWord, String page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("latitude", "");
        map.put("longitude", "");
        map.put("keyWord", keyWord);
        map.put("categoryId", "");
        map.put("recommend", "");
        map.put("page", page);
        map.put("order", "asc1");
        map.put("pageSize", "20");
        RetrofitHelper.getInstance().getProductList(map, new Subscriber<ProductList>() {


            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onNext(ProductList productList) {

                setData(productList);


            }
        });

    }

    private void setData(ProductList productList) {
        mProductList = productList;
        List<ProductList.ListBean> list = productList.getList();

        if (LoadType.equals("Request")) {
            if (list.size() > 8) {
                mSearchResultsAdapter.setNewData(list);
                mSearchResultsAdapter.setEnableLoadMore(true);
            } else {
                mSearchResultsAdapter.setNewData(list);
                mSearchResultsAdapter.setEnableLoadMore(false);
            }

        } else if (LoadType.equals("More")) {
            if (list.size() > 8) {
                mSearchResultsAdapter.addData(list);
                mSearchResultsAdapter.setEnableLoadMore(true);
            } else {
                mSearchResultsAdapter.addData(list);
                mSearchResultsAdapter.setEnableLoadMore(false);
            }

        }
    }

    private void initEditListener() {
        mSearchEdittext.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTrim = s.toString().trim();

            }
        });

        mSearchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    setHintKey();

                    String keytag = mSearchEdittext.getText().toString().trim();

                    if (StringUtils.isEmpty(keytag)) {
                        Toast.makeText(SearchResultsActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();

                        return false;
                    }

                    // 搜索功能主体
                    setData(mTrim, String.valueOf(page));


                    return true;
                }


                return false;

            }
        });

    }


    //隐藏键盘
    private void setHintKey() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(
                        SearchResultsActivity.this
                                .getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @OnClick({R.id.search_clear, R.id.search_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_clear:
                mSearchEdittext.setText("");
                break;
            case R.id.search_cancle:
                finish();
                break;
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onLoadMoreRequested() {
        getLoadMore();


    }

    public void getLoadMore() {
        LoadType = "More";
        page++;
        initData();

    }
}
