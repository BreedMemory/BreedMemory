/**
 * 项目名称：手机在线 <br/>
 * 文件名称: SearchQuestionActivity.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/10/27.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity.question;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchQuestion;
import com.yijiehl.club.android.network.response.RespSearchQuestion;
import com.yijiehl.club.android.ui.activity.ArticleDetailActivity;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.QuestionListAdapter;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: SearchQuestionActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/27 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activity_search_question_layout)
public class SearchQuestionActivity extends BmActivity implements TextWatcher {
    /**搜索栏*/
    @ViewInject(R.id.et_search)
    private EditText mEditText;
    /**搜索结果ListView*/
    @ViewInject(R.id.lv)
    private ListView lv;
    /**未搜到数据提示*/
    @ViewInject(R.id.tv_search_no_data)
    private TextView noData;

    private QuestionListAdapter questionListAdapter;

    @Override
    protected String getHeadTitle() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeader.setVisibility(View.GONE);
        mEditText.addTextChangedListener(this);
    }

    @OnClick(R.id.tv_cancel)
    private void cancel() {
        mEditText.getText().clear();
    }

    /**
     * 描 述：<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/10/28 <br/>
     */
    private void obtainData(String keyWord) {
        NetHelper.getDataFromNet(this, new ReqSearchQuestion(this, keyWord), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchQuestion data = (RespSearchQuestion) pResponse;
                questionListAdapter=new QuestionListAdapter(SearchQuestionActivity.this);
                questionListAdapter.addDatas(data.getResultList());
                lv.setAdapter(questionListAdapter);
                if(data.getResultList().size()<=0){
                    noData.setVisibility(View.VISIBLE);
                }
            }
        }, false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!TextUtils.isEmpty(s.toString())){
            obtainData(s.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ArticleDetailActivity.ARTICL_EDETAIL_ACTIVITY && resultCode == RESULT_OK) {
            questionListAdapter.setCollected(data.getStringExtra(ArticleDetailActivity.URL));
        }
    }
}
