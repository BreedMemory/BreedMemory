package com.yijiehl.club.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ScreenTools;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.growup.GrowUpGasStationAvtivity;
import com.yijiehl.club.android.ui.adapter.MenuAdapter;
import com.yijiehl.club.android.ui.popupwindow.MenuPopupWindow;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: GrowUpArticalDetailActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/9 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_grow_up_artical_detail)
public class GrowUpArticalDetailActivity extends BmActivity implements MenuAdapter.OnClick {

    /*public static final int ARTICL_EDETAIL_ACTIVITY = 876;

    public static final String SHARE = "SHARE";
    public static final String URL = "URL";
    public static final String NAME = "NAME";
    public static final String LABEL = "LABEL";
    public static final String IMAGE_INFO = "IMAGE_INFO";
    public static final String DATA_SUMMERY = "DATA_SUMMERY";
    public static final String COLLECTED = "COLLECTED";
    public static final String TYPE = "TYPE";
    public static final String GrowUp = "growup";*/

    @ViewInject(R.id.webview_detail)
    private WebView webView;

    private IconTextView mGasStationBtn;
    private LinearLayout mGasStationContainer;

    private MenuPopupWindow mMenu;
    private MenuAdapter mAdapter;

    private boolean haveCollected;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.artical_content);
    }

    @Override
    protected void configHeadLeftView() {
        super.configHeadLeftView();
        mHeadLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ArticleDetailActivity.COLLECTED, haveCollected);
                intent.putExtra(ArticleDetailActivity.URL, mAdapter.getUrl());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void configHeadRightView() {
        mGasStationContainer = new LinearLayout(this);
        int padding = getResources().getDimensionPixelOffset(R.dimen.primary_List_margin);
        mGasStationContainer.setPadding(padding, 0, padding, 0);
        mGasStationContainer.setGravity(Gravity.CENTER);
        mGasStationContainer.setMinimumWidth(ScreenTools.dip2px(this, 42));
        mGasStationContainer.setMinimumHeight(ScreenTools.dip2px(this, 42));
        mHeader.addView(mGasStationContainer);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mGasStationContainer.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.LEFT_OF, R.id.ll_head_right);
        mGasStationContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));


        mGasStationBtn = new IconTextView(this);
        mGasStationContainer.addView(mGasStationBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mGasStationBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mGasStationBtn.setText(getString(R.string.icon_gas_station));
        mGasStationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // done: 2016/11/27 跳转
                startActivity(new Intent(GrowUpArticalDetailActivity.this, GrowUpGasStationAvtivity.class));
            }
        });




        mRightBtn = new IconTextView(this);
        mHeadRightContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));
        mHeadRightContainer.addView(mRightBtn);
        layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
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
                    mMenu = new MenuPopupWindow(GrowUpArticalDetailActivity.this, mAdapter);
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
                int[] location = new int[2];
                v.getLocationOnScreen(location);

                mMenu.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]+v.getHeight());
//                mMenu.showAtLocation(mRightBtn, Gravity.NO_GRAVITY, 0, 0);
//                mMenu.showAsDropDown(mHeadRightContainer);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DONE: 2016/9/11 此处需要获取url
        String url = getIntent().getStringExtra(ArticleDetailActivity.URL);
        if (TextUtils.isEmpty(url)) {
            webView.setVisibility(View.GONE);
            return;
        }
        mAdapter = new MenuAdapter(GrowUpArticalDetailActivity.this);
        webView.loadUrl(url);
        mAdapter.setUrl(url);
        mAdapter.setName(getIntent().getStringExtra(ArticleDetailActivity.NAME));
        mAdapter.setLabel(getIntent().getStringExtra(ArticleDetailActivity.LABEL));
        mAdapter.setDataSummary(getIntent().getStringExtra(ArticleDetailActivity.DATA_SUMMERY));
        mAdapter.setImageInfo(getIntent().getStringExtra(ArticleDetailActivity.IMAGE_INFO));
        mAdapter.setQuestion(getIntent().getBooleanExtra(ArticleDetailActivity.TYPE, false));
        if(!getIntent().getBooleanExtra(ArticleDetailActivity.SHARE, false)) {
            mHeadRightContainer.setVisibility(View.GONE);
        }

        mAdapter.setListener(this);

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
        Intent intent = new Intent();
        intent.putExtra(ArticleDetailActivity.URL, mAdapter.getUrl());
        if(haveCollected) {
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick() {
        mMenu.dismiss();
    }

    @Override
    public void onCollected() {
        haveCollected = true;
    }
}
