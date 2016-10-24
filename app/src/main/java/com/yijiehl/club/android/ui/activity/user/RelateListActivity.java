/**
 * 项目名称：手机在线 <br/>
 * 文件名称: RelateListActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/24.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchRelateAccount;
import com.yijiehl.club.android.network.response.RespSearchRelateAccount;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.RelateListAdapter;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: RelateListActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/24 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.simple_listview_layout)
public class RelateListActivity extends BmActivity {

    @ViewInject(R.id.lv_listview)
    private ListView mListView;

    private RelateListAdapter mAdapter;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.relaccount);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_relate_foot_layout, null));
        mAdapter = new RelateListAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetHelper.getDataFromNet(this, new ReqSearchRelateAccount(this), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchRelateAccount relateAccount = (RespSearchRelateAccount) pResponse;
                mAdapter.setDatas(relateAccount.getResultList());
            }
        });
    }
}
