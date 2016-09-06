/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: SimplePtrListViewActivity.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/30.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrHandler;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;


/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: SimplePtrListViewActivity <br/>
 * 类描述: 简单的下拉刷新和上拉加载的ListView<br/>
 * 封装了下拉刷新和上拉加载模块，提供获取回调事件的方法 <br/>
 * 结束下拉刷新调用mPtrFrameLayout.refreshComplete();
 * 结束上拉加载调用mListView.loadComplete();
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/30 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.simple_ptr_listview_layout)
public abstract class SimplePtrListViewActivity extends BmActivity {

    /** 内容列表 */
    @ViewInject(R.id.lv_listview)
    protected PtrListView mListView;
    /** 下拉刷新容器 */
    @ViewInject(R.id.load_more_list_view_ptr_frame)
    protected PtrClassicFrameLayout mPtrFrameLayout;
    /** 加载进度条 */
    @ViewInject(R.id.pgb_loading)
    protected ProgressBar mProgressBar;
    /** 无数据视图 */
    @ViewInject(R.id.ll_empty)
    private LinearLayout mEmptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView.setLoadMoreListener(getLoadMoreListener());
        mPtrFrameLayout.setPtrHandler(getRefreshListener());
        if(isLoadMoreSupported()) {
            mListView.removeFootView();
        }
    }

    /**
     * 描 述：设置是否是空数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/6/6 注释 <br/>
     * @param isEmpty 是否是空数据
     */
    public void setIsEmpty(boolean isEmpty) {
        complete();
        if(isEmpty) {
            mEmptyView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 描 述：请求数据完成<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/6/21 注释 <br/>
     */
    public void complete() {
        mPtrFrameLayout.refreshComplete();
        mListView.loadComplete();
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * 描 述：下拉刷新的回调事件<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/30 注释 <br/>
     */
    protected abstract PtrHandler getRefreshListener();

    /**
     * 描 述：上拉加载的回调事件<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/30 注释 <br/>
     */
    protected abstract @Nullable PtrListView.LoadMoreListener getLoadMoreListener();

    /**
     * 描 述：是否支持上拉加载<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     */
    protected abstract boolean isLoadMoreSupported();
}
