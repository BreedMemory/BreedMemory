package com.yijiehl.club.android.ui.activity.user;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchMyMessage;
import com.yijiehl.club.android.network.response.RespSearchMyMessage;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.MyMessageAdapter;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: MyMessageActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/22 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_my_message)
public class MyMessageActivity extends BmActivity{

    @ViewInject(R.id.lv_listview)
    private ListView mListView;
    @ViewInject(R.id.tv_no_data)
    private TextView noData;

    private MyMessageAdapter mAdapter;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.my_message);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MyMessageAdapter(this);
        NetHelper.getDataFromNet(this, new ReqSearchMyMessage(this,false), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchMyMessage messages = (RespSearchMyMessage) pResponse;
                mAdapter.setDatas(messages.getResultList());
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(noData);

        mListView.setOnItemClickListener(mAdapter);
    }
}
