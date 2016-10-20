package com.yijiehl.club.android.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.ActivitysAdapter;
import com.yijiehl.club.android.ui.adapter.SignInAdapter;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: SignInActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/19 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_sign_in)
public class SignInActivity extends BmActivity {

    /**
     * 签到列表
     */
    @ViewInject(R.id.lv_listview)
    protected PtrListView mListView;
    /**
     * 无数据提示
     */
    @ViewInject(R.id.tv_empty)
    protected TextView mEmptyView;

    private SignInAdapter mAdapter;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.sign_in);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {

            @Override
            public void onLoadMore() {
                // TODO: 2016/9/7 分页请求网络并刷新数据，网络请求结束后关闭加载动画 mListView.loadComplete();
                mListView.loadComplete();
            }
        });
        mAdapter = new SignInAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyView);
    }
}
