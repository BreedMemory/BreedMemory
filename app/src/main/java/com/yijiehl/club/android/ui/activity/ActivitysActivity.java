/**
 * 项目名称：手机大管家
 * 文件名称: ActivitysActivity.java
 * Created by 谌珂 on 2016/10/4.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrHandler;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.adapter.ActivitysAdapter;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: ActivitysActivity<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_activitys_layout)
public class ActivitysActivity extends BmActivity {

    @ViewInject(R.id.et_search)
    private EditText mSearch;

    @ViewInject(R.id.iv_search_show)
    private ImageView mSearchShow;

    @ViewInject(R.id.layout_search_logo)
    private LinearLayout mSearchLogo;

    /**
     * 内容列表
     */
    @ViewInject(R.id.lv_listview)
    protected PtrListView mListView;
    /**
     * 下拉刷新容器
     */
    @ViewInject(R.id.load_more_list_view_ptr_frame)
    protected PtrClassicFrameLayout mPtrFrameLayout;

    private ActivitysAdapter mAdapter;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mSearchLogo.setVisibility(View.GONE);
                    mSearchShow.setVisibility(View.VISIBLE);
                }else{
                    mSearchLogo.setVisibility(View.VISIBLE);
                    mSearchShow.setVisibility(View.INVISIBLE);
                }
            }
        });

        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {

            @Override
            public void onLoadMore() {
                // TODO: 2016/9/7 分页请求网络并刷新数据，网络请求结束后关闭加载动画 mListView.loadComplete();
                mListView.loadComplete();
            }
        });
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // TODO: 2016/9/7 请求网络并刷新数据 ，网络请求完毕后记着关了下拉刷新动画mPtrFrameLayout.refreshComplete();
                mPtrFrameLayout.refreshComplete();
            }
        });

        mAdapter = new ActivitysAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ActivitysActivity.this,ArticalDetailActivity.class);
                intent.putExtra("url","http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=activity_main&dd=XXXXXXXXX&bd=showdetail");
                startActivity(intent);
            }
        });
    }
}
