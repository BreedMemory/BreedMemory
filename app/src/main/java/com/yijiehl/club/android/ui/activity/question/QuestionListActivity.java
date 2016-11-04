package com.yijiehl.club.android.ui.activity.question;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrDefaultHandler;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchQuestion;
import com.yijiehl.club.android.network.response.RespSearchQuestion;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.ui.activity.ArticleDetailActivity;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.QuestionListAdapter;


/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: QuestionListActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/4 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_question_list)
public class QuestionListActivity extends BmActivity implements TextWatcher {

    public static final String TYPE = "type";
    public static final String MY = "my";
    public static final String MOTHER = "mother";
    public static final String BABY = "month";
    public static final String MONTH0 = "0_3month";
    public static final String MONTH3 = "3_12month";
    public static final String MONTH12 = "12_18month";
    public static final String MONTH18 = "18-36month";
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


    @ViewInject(R.id.tv_empty)
    private TextView mEmpty;

    private UserInfo mUserInfo;
    private AlertDialog mAlertDialog;

    private QuestionListAdapter questionListAdapter;
    private String type;
    private boolean isNoMore;

    @Override
    protected String getHeadTitle() {
        return getIntent().getStringExtra(QuestionListActivity.TYPE).equals(QuestionListActivity.MY) ? "我的问题" : getString(R.string.question_list);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(this, R.string.shared_preference_user_id),
                getString(R.string.shared_preference_user_info));

        type = getIntent().getStringExtra(QuestionListActivity.TYPE);
        questionListAdapter = new QuestionListAdapter(this);
        obtainData(true);
        mListView.setAdapter(questionListAdapter);
        mListView.setEmptyView(mEmpty);

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

        mListView.setOnItemClickListener(questionListAdapter);

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

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            mUserInfo = JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class);
        }
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
        /*if(isRefresh){
            questionListAdapter.clear();
        }*/
        NetHelper.getDataFromNet(this, new ReqSearchQuestion(this, type, keyWord, (isRefresh || !TextUtils.isEmpty(keyWord)) ? 0 : questionListAdapter.getCount()), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchQuestion data = (RespSearchQuestion) pResponse;
                if (isRefresh || !TextUtils.isEmpty(keyWord)) {//如果是刷新或者搜索则完全替换数据
                    isNoMore=true;
                   // questionListAdapter.clear();
                    questionListAdapter.setDatas(data.getResultList());
                    mEmpty.setVisibility(View.GONE);
                } else {
                    questionListAdapter.addDatas(data.getResultList());
                }
                if(data.getResultList().size()<10){
                    isNoMore=true;
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

    @OnClick(R.id.layout_ask)
    private void ask() {
        switch (mUserInfo.getStatus()) {
            case GENERAL_BEFORE:
            case GENERAL_AFTER:
                View mAlertLayout = LayoutInflater.from(this).inflate(R.layout.can_not_ask_dialog, null);
                TextView tvPhone = (TextView) mAlertLayout.findViewById(R.id.tv_dialog_phone);
                if (!TextUtils.isEmpty(mUserInfo.getCustServicePhone())) {
                    tvPhone.setText(mUserInfo.getCustServicePhone());
                }
                mAlertDialog = new AlertDialog.Builder(this).setView(mAlertLayout).show();
                break;
            default:
                startActivity(new Intent(this, AskQuestionActivity.class));
                break;
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ArticleDetailActivity.ARTICL_EDETAIL_ACTIVITY && resultCode == RESULT_OK) {
            questionListAdapter.setCollected(data.getStringExtra(ArticleDetailActivity.URL));
        }
    }

}
