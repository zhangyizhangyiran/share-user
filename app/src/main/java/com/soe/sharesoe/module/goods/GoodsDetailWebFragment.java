package com.soe.sharesoe.module.goods;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.soe.sharesoe.R;
import com.soe.sharesoe.base.RxLazyFragment;
import com.soe.sharesoe.widget.ItemWebView;

import butterknife.BindView;
/**
 * 图文详情webview的Fragment
 */
public class GoodsDetailWebFragment extends RxLazyFragment {

    @BindView(R.id.wv_detail)
    ItemWebView wv_detail;
    private WebSettings webSettings;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_item_info_web;
    }

    @Override
    public void finishCreateView(Bundle state) {
        String url = "http://m.okhqb.com/item/description/1000334264.html?fromApp=true";
        wv_detail.setFocusable(false);
        wv_detail.loadUrl(url);
        webSettings = wv_detail.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_detail.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
