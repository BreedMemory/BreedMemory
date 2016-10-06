package com.yijiehl.club.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: KnowledgeActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/6 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_knowledge)
public class KnowledgeActivity extends BmActivity{

    /**产后下拉内容展示*/
    @ViewInject(R.id.layout_after_child_birth_context)
    private LinearLayout afterBirthContent;

    private boolean isAfterShow;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.knowledge);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // TODO: 2016/10/6 此页面的所有的方法都需要完善单击事件；暂时跳转预览页面。。。
    @OnClick(R.id.layout_search)
    private void search(){
        Toaster.showShortToast(this,"此搜索功能暂未实现");
    }

    @OnClick(R.id.layout_confinement)
    private void confinementInKnowledge(){
        startActivity(new Intent(this,KnowledgeListActivity.class));
    }

    @OnClick(R.id.layout_confinement_after)
    private void confinementAfterInKnowledge(){
        startActivity(new Intent(this,KnowledgeListActivity.class));
    }

    @OnClick(R.id.layout_pregnancy)
    private void pregnancy(){
        Toaster.showShortToast(this,"暂未实现");
    }

    @OnClick(R.id.layout_after_child_birth)
    private void afterChildBirth(){
        if (!isAfterShow) {
            afterBirthContent.setVisibility(View.GONE);
            isAfterShow = true;
        } else {
            afterBirthContent.setVisibility(View.VISIBLE);
            isAfterShow = false;
        }
    }
}
