package com.yijiehl.club.android.ui.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrDefaultHandler;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchCollect;
import com.yijiehl.club.android.network.response.RespSearchCollect;
import com.yijiehl.club.android.network.response.innerentity.Collection;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.CollectionAdapter;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: MyCellectActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/22 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_my_collect)
public class MyCellectActivity extends BmActivity implements TextWatcher {

    public static final String PHOTO = "photo";
    public static final String QUESTION = "question";
    public static final String ARTICLE = "article";

    /**
     * 收藏列表
     */
    @ViewInject(R.id.lv_listview)
    protected PtrListView mListView;


    /**
     * 下拉刷新容器
     */
    @ViewInject(R.id.load_more_list_view_ptr_frame)
    protected PtrClassicFrameLayout mPtrFrameLayout;
    /**
     * 无数据显示
     */
    @ViewInject(R.id.tv_sign_no_data)
    private TextView noData;

    /**
     * 搜索框
     */
    @ViewInject(R.id.et_search)
    private EditText mSearch;
    @ViewInject(R.id.iv_search_show)
    private ImageView mSearchShow;
    @ViewInject(R.id.layout_search_logo)
    private LinearLayout mSearchLogo;

    private CollectionAdapter collectionAdapter;

    private boolean isNoMore;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.my_collect);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化适配器
        collectionAdapter = new CollectionAdapter(this);
        obtainSignUp(true);

        mListView.setAdapter(collectionAdapter);
        mListView.setOnItemClickListener(collectionAdapter);
        mListView.setOnItemLongClickListener(collectionAdapter);

        mListView.setEmptyView(noData);

        //设置下拉回调
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                View v = mListView.getChildAt(0);
                return v == null || mListView.getFirstVisiblePosition() == 0 && v.getTop() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                obtainSignUp(true);
            }
        });
        //设置上拉回调
        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                obtainSignUp(false);
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
    private void obtainSignUp(boolean isRefresh) {
        obtainSignUp(isRefresh, null);
    }

    /**
     * 描 述：<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/10/27 <br/>
     *
     * @param isRefresh 是否是刷新任务
     * @param keyWord   搜索关键词，如果此参数不为空则忽略isRefresh
     */
    private void obtainSignUp(final boolean isRefresh, final String keyWord) {
        NetHelper.getDataFromNet(this, new ReqSearchCollect(this, keyWord, (isRefresh || !TextUtils.isEmpty(keyWord)) ? 0 : collectionAdapter.getDatas().size()), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchCollect data = (RespSearchCollect) pResponse;
                if (isRefresh || !TextUtils.isEmpty(keyWord)) {   //如果是刷新或者搜索则完全替换数据
                    isNoMore = true;
                    // collectionAdapter.clear();
                    collectionAdapter.setDatas(data.getResultList());
                } else {
                    collectionAdapter.addDatas(data.getResultList());
                }
                if (data.getResultList().size() < 10) {
                    isNoMore = true;
                }
                mListView.loadComplete();
                mListView.lockLoad(isNoMore);
                mPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
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
        obtainSignUp(true, s.toString());
    }
}
