package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.adapter.MenuAdapter;
import com.yijiehl.club.android.ui.popupwindow.MenuPopupWindow;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ArticleDetailActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/9 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_articaldetail)
public class ArticleDetailActivity extends BmActivity {

    public static final String URL = "URL";

    @ViewInject(R.id.webview_detail)
    private WebView webView;

    private MenuPopupWindow mMenu;
    private MenuAdapter mAdapter;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.artical_content);
    }

    @Override
    protected void configHeadRightView() {
        mRightBtn = new IconTextView(this);
        mHeadRightContainer.addView(mRightBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mRightBtn.setText(getString(R.string.icon_add));
        mHeadRightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;
                lp.dimAmount = 0.5f;
                getWindow().setAttributes(lp);
                if(mMenu == null) {
                    mAdapter = new MenuAdapter(ArticleDetailActivity.this);
                    mMenu = new MenuPopupWindow(ArticleDetailActivity.this, mAdapter);
                    mMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.alpha = 1f;
                            lp.dimAmount = 1f;
                            getWindow().setAttributes(lp);
                        }
                    });
                }
                mMenu.showAsDropDown(mHeadRightContainer);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DONE: 2016/9/11 此处需要获取url
        String url = getIntent().getStringExtra(URL);
        if (TextUtils.isEmpty(url)) {
            webView.setVisibility(View.GONE);
            return;
        }
        webView.loadUrl(url);
        mAdapter.setUrl(url);

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
