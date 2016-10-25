package com.yijiehl.club.android.ui.activity.question;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
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
import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.request.search.ReqSearchQuestion;
import com.yijiehl.club.android.network.response.RespSearchQuestion;
import com.yijiehl.club.android.network.response.innerentity.Answer;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.QuestionListAdapter;

import java.util.List;

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
public class QuestionListActivity extends BmActivity {

    /**
     * 搜索栏
     */
    @ViewInject(R.id.layout_search)
    private RelativeLayout mSearch;
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

    private List<Answer> data;

    @Override
    protected String getHeadTitle() {
        return getIntent().getStringExtra("type").equals("my")?"我的问题":getString(R.string.question_list);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String type = getIntent().getStringExtra("type");
        NetHelper.getDataFromNet(this, new ReqSearchQuestion(this, type), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchQuestion respSearchQuestion = (RespSearchQuestion) pResponse;
                data = respSearchQuestion.getResultList();

                QuestionListAdapter questionListAdapter = new QuestionListAdapter(QuestionListActivity.this, data);
                mListView.setAdapter(questionListAdapter);
            }
        });
        mListView.setEmptyView(mEmpty);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // DONE: 2016/10/4 暂时跳转文章详情页面。。。
                Intent intent = new Intent(QuestionListActivity.this, ArticalDetailActivity.class);
                intent.putExtra(ArticalDetailActivity.URL, ActivitySvc.createWebUrl(data.get(position).getDataShowUrl()));
                Log.d("===",ActivitySvc.createWebUrl(data.get(position).getDataShowUrl()));
                startActivity(intent);
            }
        });
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrameLayout.refreshComplete();
            }
        });

    }

    @OnClick(R.id.layout_search)
    private void searchQuestion() {
        // TODO: 2016/10/4 需要完善搜索页面再跳转
        Toaster.showShortToast(this, "此搜索功能暂未实现");
    }

    @OnClick(R.id.layout_ask)
    private void ask(){
        startActivity(new Intent(this, AskQuestionActivity.class));
    }
}
