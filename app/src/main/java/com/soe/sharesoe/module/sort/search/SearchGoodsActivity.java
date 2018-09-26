package com.soe.sharesoe.module.sort.search;

import android.content.Intent;
import android.os.Bundle;
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
import com.soe.sharesoe.entity.DataServer;
import com.soe.sharesoe.module.member.edittext.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chengel
 * @version v1.0
 * @project
 * @Description
 * @encoding UTF-8
 * @date 2017/11/6
 * @time 下午3:26
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */

public class SearchGoodsActivity extends RxBaseActivity {

    @BindView(R.id.act_search_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_edittext)
    EditText mSearchEdittext;
    @BindView(R.id.search_clear)
    ImageView mSearchClear;
    @BindView(R.id.search_cancle)
    TextView mSearchCancle;
    private List<MySection> mData;
    private String mSearchResilt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_goods;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mSearchEdittext.setHint("搜索商品");

        initEditListener();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mData = DataServer.getSampleData();
        final SearchGoodsAdapter searchGoodsAdapter = new SearchGoodsAdapter(R.layout.item_searchgoods_section_content, R.layout.item_searchgoods_section_head, mData);


        searchGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                MySection mySection = mData.get(position);


                if (mySection.isHeader) {
                    if (mySection.isMore()) {

                        Toast.makeText(SearchGoodsActivity.this, "删除", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SearchGoodsActivity.this, mySection.header, Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(SearchGoodsActivity.this, mySection.t.getName(), Toast.LENGTH_LONG).show();
                }


            }
        });


        searchGoodsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


                Toast.makeText(SearchGoodsActivity.this, "onItemChildClick" + position, Toast.LENGTH_LONG).show();


            }
        });

        mRecyclerView.setAdapter(searchGoodsAdapter);


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
                mSearchResilt = s.toString().trim();

            }
        });

        mSearchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    setHintKey();

                    String keytag = mSearchEdittext.getText().toString().trim();

                    if (StringUtils.isEmpty(keytag)) {
                        Toast.makeText(SearchGoodsActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();

                        return false;
                    }


                    // 搜索功能主体

                    Intent intent = new Intent(SearchGoodsActivity.this, SearchResultsActivity.class);
                    intent.putExtra("key", mSearchResilt);
                    startActivity(intent);


                    return true;
                }


                return false;

            }
        });


    }


    private void setHintKey() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(
                        SearchGoodsActivity.this
                                .getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View view) {

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
}
