package com.yijiehl.club.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrHandler;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchArticle;
import com.yijiehl.club.android.network.response.RespSearchArticle;
import com.yijiehl.club.android.network.response.innerentity.Article;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.user.MineActivity;
import com.yijiehl.club.android.ui.adapter.GrowUpContentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: GrowUpFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/6 <br/>
 *
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

    private List<Article> allData = new ArrayList<Article>();//数据源
    private List<Article> healthData = new ArrayList<Article>();
    private List<Article> educationData = new ArrayList<Article>();

    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DONE: 谌珂 2016/9/5 跳转到个人账户
                startActivity(new Intent(getActivity(), MineActivity.class));
            }
        };
    }


    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        ((MainActivity)getActivity()).getmLeftBtn().setText(R.string.icon_me);
        return true;
    }

    @Override
    protected boolean isRightBtnVisible() {
        return false;
    }

    @Override
    protected int getTitle() {
        return R.string.grow_up;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: 2016/10/2 此处填充假数据
       /* for (int i = 0; i < 2; i++) {
            allData.add("孩子的健康，你如何负责?");
            allData.add("穷宝宝，富宝宝");
            allData.add("幼儿重疾知多少?");
            allData.add("海外游学规划何时开始?");
        }*/

        NetHelper.getDataFromNet(getActivity(), new ReqSearchArticle(getActivity()), new AbstractCallBack(getActivity()) {
            @Override
                public void onSuccess(AbstractResponse pResponse) {
                RespSearchArticle respSearchArticle=(RespSearchArticle)pResponse;
                allData=respSearchArticle.getResultList();
                }
        },false);

        GrowUpContentAdapter growUpContentAdapter = new GrowUpContentAdapter(getActivity(), allData);
        mListView.setAdapter(growUpContentAdapter);
        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {

            @Override
            public void onLoadMore() {
                // TODO: 2016/9/7 分页请求网络并刷新数据，网络请求结束后关闭加载动画 mListView.loadComplete();
                mListView.loadComplete();
            }
        });
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // TODO: 2016/9/7 请求网络并刷新数据 ，网络请求完毕后记着关了下拉刷新动画mPtrFrameLayout.refreshComplete();
                mPtrFrameLayout.refreshComplete();
            }
        });

        // TODO: 2016/9/9 成长文章item单击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ArticalDetailActivity.class);
                intent.putExtra("url","http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=kb_growup_main&dd=XXXXXXXXX&bd=showdetail");
                startActivity(intent);
            }
        });

        mSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mSearchLogo.setVisibility(View.GONE);
                    mSearchShow.setVisibility(View.VISIBLE);
                }else{
                    mSearchLogo.setVisibility(View.VISIBLE);
                    mSearchShow.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.tv_all, R.id.tv_health, R.id.tv_education})
    private void switchTitle(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                mAll.setTextColor(getResources().getColor(R.color.white));
                mAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_left_pink));
                mHealth.setTextColor(getResources().getColor(R.color.colorPrimary));
                mHealth.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_center_white));
                mEducation.setTextColor(getResources().getColor(R.color.colorPrimary));
                mEducation.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_right_white));
                break;
            case R.id.tv_health:
                mHealth.setTextColor(getResources().getColor(R.color.white));
                mHealth.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_center_pink));
                mAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                mAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_left_white));
                mEducation.setTextColor(getResources().getColor(R.color.colorPrimary));
                mEducation.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_right_white));
                break;
            case R.id.tv_education:
                mEducation.setTextColor(getResources().getColor(R.color.white));
                mEducation.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_right_pink));
                mHealth.setTextColor(getResources().getColor(R.color.colorPrimary));
                mHealth.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_center_white));
                mAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                mAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_left_white));
                break;
        }
    }
}
