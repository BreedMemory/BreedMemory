package com.yijiehl.club.android.ui.activity.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

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
import com.yijiehl.club.android.network.request.search.ReqSearchActivitys;
import com.yijiehl.club.android.network.request.search.ReqSearchKnowledge;
import com.yijiehl.club.android.network.response.RespSearchActivitys;
import com.yijiehl.club.android.network.response.RespSearchArticle;
import com.yijiehl.club.android.network.response.innerentity.Article;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.ActivitysAdapter;
import com.yijiehl.club.android.ui.adapter.KnowledgeListAdapter;

import java.util.ArrayList;
import java.util.List;

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
public class KnowledgeListActivity extends BmActivity {

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

    private List<Article> data;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.knowledge_list);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra(KnowledgeActivity.TYPE);
        NetHelper.getDataFromNet(this, new ReqSearchKnowledge(this, type), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchArticle respSearchActivitys=(RespSearchArticle)pResponse;
                data=respSearchActivitys.getResultList();
                if(data.size()!=0){
                    KnowledgeListAdapter knowledgeListAdapter=new KnowledgeListAdapter(KnowledgeListActivity.this,data);
                    mListView.setAdapter(knowledgeListAdapter);
                }
            }
        }, false);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // DONE: 2016/10/4 暂时跳转文章详情页面。。。
                Intent intent = new Intent(KnowledgeListActivity.this, ArticalDetailActivity.class);
                intent.putExtra(ArticalDetailActivity.URL, ActivitySvc.createWebUrl(data.get(position).getDataShowUrl()));
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
        // TODO: 2016/10/6 需要完善搜索页面再跳转
        Toaster.showShortToast(this, "此搜索功能暂未实现");
    }
}
