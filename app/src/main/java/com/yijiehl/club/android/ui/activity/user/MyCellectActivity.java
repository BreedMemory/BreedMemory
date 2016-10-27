package com.yijiehl.club.android.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
public class MyCellectActivity extends BmActivity {

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
    private CollectionAdapter collectionAdapter;
    private List<Collection> data;

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
        NetHelper.getDataFromNet(this, new ReqSearchCollect(this, keyWord, (isRefresh || !TextUtils.isEmpty(keyWord)) ? 0 : collectionAdapter.getCount()), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchCollect data = (RespSearchCollect) pResponse;
                if (isRefresh || !TextUtils.isEmpty(keyWord)) {   //如果是刷新或者搜索则完全替换数据
                    collectionAdapter.setDatas(data.getResultList());
                } else {
                    collectionAdapter.addDatas(data.getResultList());
                }
                mListView.loadComplete();
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

    @OnClick(R.id.layout_search)
    private void search() {
        // TODO: 2016/10/24  搜索的都没有实现
        Toaster.showShortToast(this, "此搜索功能暂未实现");
    }
}
