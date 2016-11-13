package com.yijiehl.club.android.ui.activity.question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrDefaultHandler;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchKnowledge;
import com.yijiehl.club.android.network.response.RespSearchArticle;
import com.yijiehl.club.android.ui.activity.ArticleDetailActivity;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.KnowledgeListAdapter;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: KnowledgeListActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/6 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_knowledge_list)
public class KnowledgeListActivity extends BmActivity implements TextWatcher {

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
     * 问题列表
     */
    @ViewInject(R.id.lv_listview)
    protected PtrListView mListView;


    /**
     * 下拉刷新容器
     */
    @ViewInject(R.id.load_more_list_view_ptr_frame)
    protected PtrClassicFrameLayout mPtrFrameLayout;

    private String type;
    private KnowledgeListAdapter knowledgeListAdapter;
    private boolean isNoMore;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.knowledge_list);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra(KnowledgeActivity.TYPE);

        obtainData(true);
        knowledgeListAdapter = new KnowledgeListAdapter(this);
        mListView.setAdapter(knowledgeListAdapter);

        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {

            @Override
            public void onLoadMore() {
                // DONE: 2016/9/7 分页请求网络并刷新数据，网络请求结束后关闭加载动画 mListView.loadComplete();
                obtainData(false);
            }
        });

        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                View v = mListView.getChildAt(0);
                return v == null || mListView.getFirstVisiblePosition() == 0 && v.getTop() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // DONE: 2016/9/7 请求网络并刷新数据 ，网络请求完毕后记着关了下拉刷新动画mPtrFrameLayout.refreshComplete();
                obtainData(true);
            }
        });

        mListView.setOnItemClickListener(knowledgeListAdapter);
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
     * 描 述：获取数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
     *
     * @param isRefresh 是否是完全刷新
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
        NetHelper.getDataFromNet(this, new ReqSearchKnowledge(this, type, keyWord,
                (isRefresh || !TextUtils.isEmpty(keyWord)) ? 0 : knowledgeListAdapter.getDatas().size()), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {

                RespSearchArticle data = (RespSearchArticle) pResponse;
                Log.d("===",data.getResultList().size()+"");
                if (isRefresh || !TextUtils.isEmpty(keyWord)) {//如果是刷新或者搜索则完全替换数据
                    isNoMore=true;
                    //knowledgeListAdapter.clear();
                    knowledgeListAdapter.setDatas(data.getResultList());
                } else {
                    knowledgeListAdapter.addDatas(data.getResultList());
                }
                if(data.getResultList() == null || data.getResultList().size()<10){
                   isNoMore=true;
                }
                mListView.loadComplete();
                mListView.lockLoad(isNoMore);
                mPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                Log.d("===","失败");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ArticleDetailActivity.ARTICL_EDETAIL_ACTIVITY && resultCode == Activity.RESULT_OK) {
            knowledgeListAdapter.setCollected(data.getStringExtra(ArticleDetailActivity.URL));
        }
    }
}
