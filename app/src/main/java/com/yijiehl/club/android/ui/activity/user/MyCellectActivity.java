package com.yijiehl.club.android.ui.activity.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    private List<Collection> data;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.my_collect);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetHelper.getDataFromNet(this, new ReqSearchCollect(this), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchCollect respSearchCollect = (RespSearchCollect) pResponse;
                data = respSearchCollect.getResultList();
                Log.d("===",data.size()+"");
                if(data.size()!=0){
                    CollectionAdapter collectionAdapter=new CollectionAdapter(MyCellectActivity.this,data);
                    mListView.setAdapter(collectionAdapter);
                }
            }
        }, false);


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
    private void search() {
        // TODO: 2016/10/24  搜索的都没有实现
        Toaster.showShortToast(this, "此搜索功能暂未实现");
    }
}
