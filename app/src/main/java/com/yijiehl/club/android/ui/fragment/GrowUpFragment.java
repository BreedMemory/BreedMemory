package com.yijiehl.club.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.uuzz.android.ui.view.pinnedlistview.PinnedHeaderListView;
import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrHandler;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: GrowUpFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/6 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.fragment_growup)
public class GrowUpFragment extends BaseHostFragment {

    @ViewInject(R.id.tv_all)
    private TextView mAll;
    @ViewInject(R.id.tv_health)
    private TextView mHealth;
    @ViewInject(R.id.tv_education)
    private TextView mEducation;

    /** 内容列表 */
    @ViewInject(R.id.lv_listview)
    protected PtrListView mListView;
    /** 下拉刷新容器 */
    @ViewInject(R.id.load_more_list_view_ptr_frame)
    protected PtrClassicFrameLayout mPtrFrameLayout;

    private List<String> data=new ArrayList<String>();//数据源

    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return null;
    }

    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        return false;
    }

    @Override
    protected boolean isRightBtnVisible() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: 2016/9/7 设置自己的适配器
        //GrowUpContentAdapter growUpContentAdapter=new GrowUpContentAdapter(getActivity(),data);
       // mListView.setAdapter(growUpContentAdapter);
        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener(){

            @Override
            public void onLoadMore() {
                // TODO: 2016/9/7 分页请求网络并刷新数据，网络请求结束后关闭加载动画 mListView.loadComplete();
            }
        });
        mPtrFrameLayout.setPtrHandler(new PtrHandler(){

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // TODO: 2016/9/7 请求网络并刷新数据 ，网络请求完毕后记着关了下拉刷新动画mPtrFrameLayout.refreshComplete();
            }
        });

        // TODO: 2016/9/9 成长文章item单击事件
        mListView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });
    }

    @OnClick({R.id.tv_all, R.id.tv_health, R.id.tv_education})
    private void switchTitle(View v){
        switch (v.getId()){
            case R.id.tv_all :
                mAll.setTextColor(getResources().getColor(R.color.white));
                mAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_left_pink));
                mHealth.setTextColor(getResources().getColor(R.color.colorPrimary));
                mHealth.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_center_white));
                mEducation.setTextColor(getResources().getColor(R.color.colorPrimary));
                mEducation.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_center_white));
                break;
            case R.id.tv_health :
                mHealth.setTextColor(getResources().getColor(R.color.white));
                mHealth.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_left_pink));
                mAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                mAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_center_white));
                mEducation.setTextColor(getResources().getColor(R.color.colorPrimary));
                mEducation.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_center_white));
                break;
            case R.id.tv_education :
                mEducation.setTextColor(getResources().getColor(R.color.white));
                mEducation.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_left_pink));
                mHealth.setTextColor(getResources().getColor(R.color.colorPrimary));
                mHealth.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_center_white));
                mAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                mAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_center_white));
                break;
        }
    }
}
