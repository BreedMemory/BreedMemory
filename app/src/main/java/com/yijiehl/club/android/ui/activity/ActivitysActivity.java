/**
 * 项目名称：手机大管家
 * 文件名称: ActivitysActivity.java
 * Created by 谌珂 on 2016/10/4.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrDefaultHandler;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrHandler;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchActivitys;
import com.yijiehl.club.android.network.response.RespSearchActivitys;
import com.yijiehl.club.android.network.response.innerentity.ActivityInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.adapter.ActivitysAdapter;

import java.util.List;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: ActivitysActivity<br/>
 * 类描述: <br/>
 *
 * @author 谌珂 <br/>
 *         实现的主要功能<br/>
 *         版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_activitys_layout)
public class ActivitysActivity extends BmActivity implements TextWatcher {

    /**
     * 搜索栏
     */
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

    private List<ActivityInfo> data;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ActivitysAdapter(this);
        obtainData(true);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mAdapter);
        //设置下拉回调
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                View v = mListView.getChildAt(0);
                return v == null || mListView.getFirstVisiblePosition() == 0 && v.getTop() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                obtainData(true);
            }
        });
        //设置上拉回调
        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                obtainData(false);
            }
        });

        mSearch.addTextChangedListener(this);
        mSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSearchLogo.setVisibility(View.GONE);
                    mSearchShow.setVisibility(View.VISIBLE);
                } else {
                    mSearchLogo.setVisibility(View.VISIBLE);
                    mSearchShow.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * 描 述：<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/10/27 <br/>
     *
     * @param isRefresh 是否是刷新任务
     */
    private void obtainData(boolean isRefresh) {
        obtainData(isRefresh, null);
    }

    /**
     * 描 述：<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/10/27 <br/>
     *
     * @param isRefresh 是否是刷新任务
     * @param keyWord   搜索关键词，如果此参数不为空则忽略isRefresh
     */
    private void obtainData(final boolean isRefresh, final String keyWord) {
        NetHelper.getDataFromNet(this, new ReqSearchActivitys(this, false, keyWord, (isRefresh || !TextUtils.isEmpty(keyWord)) ? 0 : mAdapter.getDatas().size()), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchActivitys data = (RespSearchActivitys) pResponse;
                if (isRefresh || !TextUtils.isEmpty(keyWord)) {   //如果是刷新或者搜索则完全替换数据
                    mAdapter.clear();
                    mAdapter.setDatas(data.getResultList());
                } else {
                    mAdapter.addDatas(data.getResultList());
                }
                mListView.loadComplete();
                mPtrFrameLayout.refreshComplete();
            }
        }, false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        obtainData(true, s.toString());
    }
}
