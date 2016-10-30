package com.yijiehl.club.android.ui.activity.growup;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.ArticleDetailActivity;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: NotSignUpGasStationActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/27 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_not_sign_up_gas)
public class NotSignUpGasStationActivity extends BmActivity{

    // DONE: 2016/10/27 需要添加url，且不变
    public static final String NOT_SIGN_URL="http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=demo_growup_info&dd=XXXXXXXXX&bd=showdetail";

    @ViewInject(R.id.webview_detail)
    private WebView webView;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.growup_comeon);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(ArticleDetailActivity.URL);
        if (TextUtils.isEmpty(url)) {
            webView.setVisibility(View.GONE);
            return;
        }
        webView.loadUrl(url);

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
