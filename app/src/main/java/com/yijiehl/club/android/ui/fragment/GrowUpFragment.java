package com.yijiehl.club.android.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrHandler;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchGrowUpArticle;
import com.yijiehl.club.android.network.request.search.ReqSearchEducationArticle;
import com.yijiehl.club.android.network.request.search.ReqSearchHealthArticle;
import com.yijiehl.club.android.network.response.RespSearchArticle;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ArticleDetailActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.user.MineActivity;
import com.yijiehl.club.android.ui.adapter.GrowUpContentAdapter;

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
public class GrowUpFragment extends BaseHostFragment implements RadioGroup.OnCheckedChangeListener, TextWatcher {

    @ViewInject(R.id.layout_title)
    private RadioGroup mTitle;
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

    private GrowUpContentAdapter mGrowUpContentAdapter;

    private boolean isHealthNoMore;
    private boolean isEducateNoMore;
    private boolean isAllNoMore;

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
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySvc.startGasStation(getActivity(), null);
            }
        };
    }

    @Override
    protected boolean isLeftBtnVisible() {
        ((MainActivity)getActivity()).getmLeftBtn().setText(R.string.icon_me);
        return true;
    }

    @Override
    protected boolean isRightBtnVisible() {
        ((MainActivity)getActivity()).getmRightBtn().setModle(IconTextView.MODULE_ICON);
        ((MainActivity)getActivity()).getmRightBtn().setText(R.string.icon_gas_station);
        return true;
    }

    @Override
    protected int getTitle() {
        return R.string.grow_up;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitle.setOnCheckedChangeListener(this);
        mGrowUpContentAdapter = new GrowUpContentAdapter(this, getMode());
        obtainData(true);
        mListView.setAdapter(mGrowUpContentAdapter);
        mSearch.addTextChangedListener(this);

        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {

            @Override
            public void onLoadMore() {
                // DONE: 2016/9/7 分页请求网络并刷新数据，网络请求结束后关闭加载动画 mListView.loadComplete();
                obtainData(false);
            }
        });
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {

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

        // DONE: 2016/9/9 成长文章item单击事件
        mListView.setOnItemClickListener(mGrowUpContentAdapter);

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

    /**
     * 描 述：判断是否可以加载更多<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/28 <br/>
     */
    private void awardLoadMore() {
        switch (mTitle.getCheckedRadioButtonId()) {
            case R.id.gb_all:
                mListView.lockLoad(isAllNoMore);
                break;
            case R.id.gb_health:
                mListView.lockLoad(isHealthNoMore);
                break;
            case R.id.gb_education:
                mListView.lockLoad(isEducateNoMore);
                break;
        }
    }

    /**
     * 描 述：获取数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
     * @param isRefresh 是否是完全刷新
     */
    private void obtainData(boolean isRefresh) {
        obtainData(isRefresh, null);
    }

    /**
     * 描 述：获取数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
     * @param isRefresh 是否是完全刷新
     * @param keyWord 搜索关键词
     */
    private void obtainData(boolean isRefresh, String keyWord) {
        if(isRefresh) {
            mGrowUpContentAdapter.clear();
        }
        switch (getMode()) {
            case GrowUpContentAdapter.EDUCATION_DATA:
                obtainEducationArticle(isRefresh, keyWord);
                break;
            case GrowUpContentAdapter.HEALTH_DATA:
                obtainHealthArticle(isRefresh, keyWord);
                break;
            default:
                obtainGrowUpArticle(isRefresh, keyWord);
                break;
        }
    }

    /**
     * 描 述：获取教育文章<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
     * @param isRefresh 是否是完全刷新
     */
    private void obtainGrowUpArticle(final boolean isRefresh, final String keyWord) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchGrowUpArticle(getActivity(), (isRefresh||!TextUtils.isEmpty(keyWord))?0:mGrowUpContentAdapter.getDatas(GrowUpContentAdapter.ALL_DATA).size(), keyWord), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchArticle respSearchArticle=(RespSearchArticle)pResponse;
                if(isRefresh) {
                    isAllNoMore = false;
                    mGrowUpContentAdapter.setDatas(GrowUpContentAdapter.ALL_DATA, respSearchArticle.getResultList());
                } else {
                    mGrowUpContentAdapter.addDatas(GrowUpContentAdapter.ALL_DATA, respSearchArticle.getResultList());
                }
                if(respSearchArticle.getResultList() == null || respSearchArticle.getResultList().size() < 10) {
                    isAllNoMore = true;
                }
                mListView.loadComplete();
                awardLoadMore();
                mPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                mListView.loadComplete();
                mPtrFrameLayout.refreshComplete();
            }
        },false);
    }

    /**
     * 描 述：获取教育文章<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
     * @param isRefresh 是否是完全刷新
     */
    private void obtainEducationArticle(final boolean isRefresh, final String keyWord) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchEducationArticle(getActivity(), (isRefresh||!TextUtils.isEmpty(keyWord))?0:mGrowUpContentAdapter.getDatas(GrowUpContentAdapter.EDUCATION_DATA).size(), keyWord), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchArticle respSearchArticle=(RespSearchArticle)pResponse;
                if(isRefresh) {
                    isEducateNoMore = false;
                    mGrowUpContentAdapter.setDatas(GrowUpContentAdapter.EDUCATION_DATA, respSearchArticle.getResultList());
                } else {
                    mGrowUpContentAdapter.addDatas(GrowUpContentAdapter.EDUCATION_DATA, respSearchArticle.getResultList());
                }
                if(respSearchArticle.getResultList() == null || respSearchArticle.getResultList().size() < 10) {
                    isEducateNoMore = true;
                }
                mListView.loadComplete();
                awardLoadMore();
                mPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                mListView.loadComplete();
                mPtrFrameLayout.refreshComplete();
            }
        },false);
    }

    /**
     * 描 述：获取教育文章<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
     */
    private void obtainHealthArticle(final boolean isRefresh, final String keyWord) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchHealthArticle(getActivity(), (isRefresh||!TextUtils.isEmpty(keyWord))?0:mGrowUpContentAdapter.getDatas(GrowUpContentAdapter.EDUCATION_DATA).size(), keyWord), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchArticle respSearchArticle=(RespSearchArticle)pResponse;
                if(isRefresh) {
                    isHealthNoMore = false;
                    mGrowUpContentAdapter.setDatas(GrowUpContentAdapter.HEALTH_DATA, respSearchArticle.getResultList());
                } else {
                    mGrowUpContentAdapter.addDatas(GrowUpContentAdapter.HEALTH_DATA, respSearchArticle.getResultList());
                }
                if(respSearchArticle.getResultList() == null ||respSearchArticle.getResultList().size() < 10) {
                    isHealthNoMore = true;
                }
                mListView.loadComplete();
                awardLoadMore();
                mPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                mListView.loadComplete();
                mPtrFrameLayout.refreshComplete();
            }
        },false);
    }

    /**
     * 描 述：获取显示类型 {@link GrowUpContentAdapter#ALL_DATA}, {@link GrowUpContentAdapter#HEALTH_DATA}, {@link GrowUpContentAdapter#EDUCATION_DATA}<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
     */
    private int getMode() {
        switch (mTitle.getCheckedRadioButtonId()) {
            case R.id.gb_health:
                return GrowUpContentAdapter.HEALTH_DATA;
            case R.id.gb_education:
                return GrowUpContentAdapter.EDUCATION_DATA;
            default:
                return GrowUpContentAdapter.ALL_DATA;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.gb_all:
                mGrowUpContentAdapter.setMode(GrowUpContentAdapter.ALL_DATA);
                break;
            case R.id.gb_health:
                mGrowUpContentAdapter.setMode(GrowUpContentAdapter.HEALTH_DATA);
                break;
            case R.id.gb_education:
                mGrowUpContentAdapter.setMode(GrowUpContentAdapter.EDUCATION_DATA);
                break;
        }
        awardLoadMore();
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
            mGrowUpContentAdapter.setCollected(data.getStringExtra(ArticleDetailActivity.URL));
        }
    }

}
