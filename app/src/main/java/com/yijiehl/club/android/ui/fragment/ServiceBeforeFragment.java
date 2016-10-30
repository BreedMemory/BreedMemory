/**
 * 项目名称：手机在线 <br/>
 * 文件名称: ServiceBeforeFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/25.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchMotherData;
import com.yijiehl.club.android.network.response.RespSearchHealthData;
import com.yijiehl.club.android.ui.activity.ActivitysActivity;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.health.HealthInfoBeforeActivity;
import com.yijiehl.club.android.ui.activity.question.KnowledgeActivity;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: ServiceBeforeFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/25 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.fragment_health_service_before_layout)
public class ServiceBeforeFragment extends HealthInfoFragment {
    /** 总信息 */
    @ViewInject(R.id.tv_health_desc)
    private TextView mInfo;
    /** 显示更多的图标 */
    @ViewInject(R.id.im_more)
    private View mMore;
    /** 显示更多的文字 */
    @ViewInject(R.id.tv_more)
    private View mIcMore;
    /** 图表的大容器 */
    @ViewInject(R.id.ll_form_container)
    private View mFormContainer;
    /** 图表选择器 */
    @ViewInject(R.id.rg_selector)
    private RadioGroup mFormSelector;
    /** 图表小容器 */
    @ViewInject(R.id.fl_form_container)
    private FrameLayout mFormFrameLayout;
    /** 母亲统计图容器 */
    @ViewInject(R.id.form_mother)
    private View mFormMother;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherData(getActivity()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherData = (RespSearchHealthData) pResponse;
                mInfo.setText(mMotherData.getResultList().get(0).getDataInfo1());
            }
        });
    }

    @OnClick({R.id.im_more, R.id.tv_more})
    private void showForm() {
        mFormContainer.setVisibility(View.VISIBLE);
        mMore.setVisibility(View.GONE);
        mIcMore.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_retract)
    private void hideForm() {
        mFormContainer.setVisibility(View.GONE);
        mMore.setVisibility(View.VISIBLE);
        mIcMore.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.lcv_mother_chest,
            R.id.lcv_mother_weight,
            R.id.lcv_mother_waist,
            R.id.lcv_mother_hips})
    private void startHealthData() {
        Intent intent = new Intent(getActivity(), HealthInfoBeforeActivity.class);
        startActivity(intent);
    }

    @Override
    public int getCheckId() {
        return 0;
    }

    @OnClick(R.id.ll_activity)
    private void startActivitys() {
        startActivity(new Intent(getActivity(), ActivitysActivity.class));
    }

    @OnClick(R.id.ll_food)
    private void startFood() {
        if(mUserInfo == null) {
            return;
        }
        Intent intent=new Intent(getActivity(),ArticalDetailActivity.class);
        intent.putExtra(ArticalDetailActivity.URL,mUserInfo.getFoodUrl(getActivity()));
        startActivity(intent);
    }

    @OnClick(R.id.ll_knowledge)
    private void startKnowledge(){
        startActivity(new Intent(getActivity(), KnowledgeActivity.class));
    }
}
