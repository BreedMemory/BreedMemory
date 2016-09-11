package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ArticalDetailActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/9 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_articaldetail)
public class ArticalDetailActivity extends BmActivity {

    @ViewInject(R.id.webview_detail)
    private WebView webView;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.artical_content);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 2016/9/11 此处需要获取url
        String url = getIntent().getStringExtra("url");

        if (TextUtils.isEmpty(url)) {
            webView.setVisibility(View.GONE);
            return;
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
