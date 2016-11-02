/**
 * 项目名称：手机在线 <br/>
 * 文件名称: ServiceInFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/25.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.HealthData;
import com.yijiehl.club.android.ui.activity.ActivitysActivity;
import com.yijiehl.club.android.ui.activity.ArticleDetailActivity;
import com.yijiehl.club.android.ui.activity.health.HealthInfoInActivity;
import com.yijiehl.club.android.ui.activity.question.KnowledgeActivity;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: ServiceInFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/25 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.fragment_health_service_in_layout)
public class ServiceInFragment extends HealthInfoFragment {
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
    /** 婴儿统计图容器 */
    @ViewInject(R.id.form_baby)
    private View mFormBaby;
    /** 母亲血糖背景 */
    @ViewInject(R.id.ll_mother_blood_sugar)
    private View mMotherBloodSugar;
    /** 母亲血压背景 */
    @ViewInject(R.id.ll_mother_blood_pressure)
    private View mMotherBloodPressure;
    /** 母亲睡眠背景 */
    @ViewInject(R.id.ll_mother_sleep)
    private View mMotherSleep;
    /** 宝宝饮食背景 */
    @ViewInject(R.id.ll_baby_food)
    private View mBabyFood;
    /** 宝宝排泄背景 */
    @ViewInject(R.id.ll_baby_excretion)
    private View mBabyExcretion;
    /** 宝宝生理性背景 */
    @ViewInject(R.id.ll_baby_physiology)
    private View mBabyPhysiology;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMotherDataListTemperature();
        getMotherDataListWeight();

        mFormSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother:
                        mFormMother.setVisibility(View.VISIBLE);
                        mFormBaby.setVisibility(View.GONE);
                        break;
                    case R.id.rb_baby0:
                    case R.id.rb_baby1:
                    case R.id.rb_baby2:
                    case R.id.rb_baby3:
                        mFormMother.setVisibility(View.GONE);
                        mFormBaby.setVisibility(View.VISIBLE);
                        fillBabyData(checkedId);
                        break;
                }
            }
        });
    }

    @Override
    protected void onMotherHealthDataReceived() {
        mInfo.setText(mMotherData.getResultList().get(0).getDataInfo1());
        if(!TextUtils.equals(mMotherData.getResultList().get(0).getStatValue05(), "normal")){
            mMotherBloodSugar.setBackgroundColor(getResources().getColor(R.color.unnormal));
        }
        if(!TextUtils.equals(mMotherData.getResultList().get(0).getStatValue06(), "normal")){
            mMotherBloodPressure.setBackgroundColor(getResources().getColor(R.color.unnormal));
        }
        if(!TextUtils.equals(mMotherData.getResultList().get(0).getStatValue07(), "good")){
            mMotherSleep.setBackgroundColor(getResources().getColor(R.color.unnormal));
        }
    }

    @Override
    protected void onBabyDataListHeightReceived(int index) {

    }

    @Override
    protected void onBabyDataListWeightReceived(int index) {

    }

    @Override
    protected void onBabyDataListHeadReceived(int index) {

    }

    @Override
    protected void onBabyDataListChestReceived(int index) {

    }

    @Override
    protected void onUserInfoAvailable() {
        super.onUserInfoAvailable();
        getBabyData();
        //计算应当显示的宝宝视图按钮
        if(mUserInfo.getChildrenInfo() != null) {
            for (int i = 0; i < mUserInfo.getChildrenInfo().size(); i++) {
                mFormSelector.getChildAt(4-i).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getCheckId() {
        return 0;
    }

    private void fillBabyData(int id) {
        HealthData data = null;
        switch (id) {
            case R.id.rb_baby0:
                if(mBabyDatas.size() > 0 && mBabyDatas.get(0).getResultList() != null && mBabyDatas.get(0).getResultList().size() > 0) {
                    data = mBabyDatas.get(0).getResultList().get(0);
                }
                break;
            case R.id.rb_baby1:
                if(mBabyDatas.size() > 1 && mBabyDatas.get(1).getResultList() != null && mBabyDatas.get(1).getResultList().size() > 0) {
                    data = mBabyDatas.get(1).getResultList().get(0);
                }
                break;
            case R.id.rb_baby2:
                if(mBabyDatas.size() > 2 && mBabyDatas.get(1).getResultList() != null && mBabyDatas.get(1).getResultList().size() > 0) {
                    data = mBabyDatas.get(2).getResultList().get(0);
                }
                break;
            case R.id.rb_baby3:
                if(mBabyDatas.size() > 3 && mBabyDatas.get(1).getResultList() != null && mBabyDatas.get(1).getResultList().size() > 0) {
                    data = mBabyDatas.get(3).getResultList().get(0);
                }
                break;
        }
        if(data == null) {
            return;
        }
        if(!TextUtils.equals(data.getStatValue21(), "normal")) {
            mBabyFood.setBackgroundColor(getResources().getColor(R.color.unnormal));
        }
        if(TextUtils.equals(data.getStatValue25(), "abnormal") || TextUtils.equals(data.getStatValue26(), "abnormal")) {
            mBabyExcretion.setBackgroundColor(getResources().getColor(R.color.unnormal));
        }
        if(TextUtils.equals(data.getStatValue30(), "have") || TextUtils.equals(data.getStatValue31(), "have") || TextUtils.equals(data.getStatValue32(), "have")) {
            mBabyPhysiology.setBackgroundColor(getResources().getColor(R.color.unnormal));
        }
    }

    //    protected void getDataList(ReqBaseSearch.StatisticalTarget target, final String fildName) {
//        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), target), new AbstractCallBack(getActivity()) {
//            @Override
//            public void onSuccess(AbstractResponse pResponse) {
//                try {
//                    Field declaredField = ServiceInFragment.class.getDeclaredField(fildName);
//                    declaredField.set(ServiceInFragment.this, pResponse);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

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

    @OnClick({R.id.mother_extra_data,
            R.id.lcv_mother_temperature,
            R.id.lcv_mother_weight,
            R.id.baby_extra_data,
            R.id.lcv_baby_height,
            R.id.lcv_baby_chest,
            R.id.lcv_baby_head,
            R.id.lcv_baby_weight})
    private void startHealthData() {
        Intent intent = new Intent(getActivity(), HealthInfoInActivity.class);
        intent.putExtra(HealthInfoInActivity.ROLE, mFormSelector.getCheckedRadioButtonId());
        startActivity(intent);
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
        Intent intent=new Intent(getActivity(),ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.URL,mUserInfo.getFoodUrl(getActivity()));
        startActivity(intent);
    }

    @OnClick(R.id.ll_knowledge)
    private void startKnowledge(){
        startActivity(new Intent(getActivity(), KnowledgeActivity.class));
    }
}
