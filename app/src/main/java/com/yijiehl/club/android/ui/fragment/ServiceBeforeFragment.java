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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uuzz.android.ui.view.LineChatView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ActivitysActivity;
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
    /** 母亲体重值 */
    @ViewInject(R.id.tv_mother_value_weight)
    private TextView mMotherWeightValue;
    /** 母亲体重日期 */
    @ViewInject(R.id.tv_mother_date_weight)
    private TextView mMotherWeightDate;
    /** 母亲体重图表密度转换 */
    @ViewInject(R.id.rg_mother_selector_weight)
    private RadioGroup mMotherWeightSelector;
    /** 母亲体重图表 */
    @ViewInject(R.id.lcv_mother_weight)
    private LineChatView mMotherWeightChat;
    /** 母亲胸围值 */
    @ViewInject(R.id.tv_mother_value_chest)
    private TextView mMotherChestValue;
    /** 母亲胸围日期 */
    @ViewInject(R.id.tv_mother_date_chest)
    private TextView mMotherChestDate;
    /** 母亲胸围图表密度转换 */
    @ViewInject(R.id.rg_mother_selector_chest)
    private RadioGroup mMotherChestSelector;
    /** 母亲胸围图表 */
    @ViewInject(R.id.lcv_mother_chest)
    private LineChatView mMotherChestChat;
    /** 母亲腰围值 */
    @ViewInject(R.id.tv_mother_value_waist)
    private TextView mMotherWaistValue;
    /** 母亲腰围日期 */
    @ViewInject(R.id.tv_mother_date_waist)
    private TextView mMotherWaistDate;
    /** 母亲腰围图表密度转换 */
    @ViewInject(R.id.rg_mother_selector_waist)
    private RadioGroup mMotherWaistSelector;
    /** 母亲腰围图表 */
    @ViewInject(R.id.lcv_mother_waist)
    private LineChatView mMotherWaistChat;
    /** 母亲臀围值 */
    @ViewInject(R.id.tv_mother_value_hips)
    private TextView mMotherHipValue;
    /** 母亲臀围日期 */
    @ViewInject(R.id.tv_mother_date_hips)
    private TextView mMotherHipDate;
    /** 母亲臀围图表密度转换 */
    @ViewInject(R.id.rg_mother_selector_hips)
    private RadioGroup mMotherHipSelector;
    /** 母亲臀围图表 */
    @ViewInject(R.id.lcv_mother_hips)
    private LineChatView mMotherHipChat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMotherDataListWeight();
        getMotherDataListChest();
        getMotherDataListWaist();
        getMotherDataListHip();
    }

    @Override
    protected void onViewInjected() {
        super.onViewInjected();
        initMotherChat();
    }

    /**
     * 描 述：配置母亲图表监听事件<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    private void initMotherChat() {
        mMotherWeightChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mMotherWeightValue.setText(String.format(getString(R.string.weight_string), mMotherDataListWeight.getResultList().get(position).getStatValue()));
                mMotherWeightDate.setText(mMotherDataListWeight.getResultList().get(position).getStatTime());
            }
        });
        mMotherWeightSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother_weight_date_week:
                        mMotherWeightChat.setPointCount(CHAT_STEP_WEEK);
                        break;
                    case R.id.rb_mother_weight_date_month:
                        mMotherWeightChat.setPointCount(CHAT_STEP_MONTH);
                        break;
                }
            }
        });

        mMotherChestChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mMotherChestValue.setText(String.format(getString(R.string.length_string), mMotherDataListChest.getResultList().get(position).getStatValue()));
                mMotherChestDate.setText(mMotherDataListChest.getResultList().get(position).getStatTime());
            }
        });
        mMotherChestSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother_chest_date_week:
                        mMotherChestChat.setPointCount(CHAT_STEP_WEEK);
                        break;
                    case R.id.rb_mother_chest_date_month:
                        mMotherChestChat.setPointCount(CHAT_STEP_MONTH);
                        break;
                }
            }
        });

        mMotherWaistChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mMotherWaistValue.setText(String.format(getString(R.string.length_string), mMotherDataListWaist.getResultList().get(position).getStatValue()));
                mMotherWaistDate.setText(mMotherDataListWaist.getResultList().get(position).getStatTime());
            }
        });
        mMotherWaistSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother_waist_date_week:
                        mMotherWaistChat.setPointCount(CHAT_STEP_WEEK);
                        break;
                    case R.id.rb_mother_waist_date_month:
                        mMotherWaistChat.setPointCount(CHAT_STEP_MONTH);
                        break;
                }
            }
        });

        mMotherHipChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mMotherHipValue.setText(String.format(getString(R.string.length_string), mMotherDataListHip.getResultList().get(position).getStatValue()));
                mMotherHipDate.setText(mMotherDataListHip.getResultList().get(position).getStatTime());
            }
        });
        mMotherHipSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother_hips_date_week:
                        mMotherHipChat.setPointCount(CHAT_STEP_WEEK);
                        break;
                    case R.id.rb_mother_hips_date_month:
                        mMotherHipChat.setPointCount(CHAT_STEP_MONTH);
                        break;
                }
            }
        });
    }

    @Override
    protected void onMotherHealthDataReceived() {
        mInfo.setText(mMotherData.getResultList().get(0).getDataInfo1());
    }

    @Override
    protected void onMotherDataListWeightReceived() {
        fillChatData(mMotherWeightChat, mMotherDataListWeight);
    }

    @Override
    protected void onMotherDataListChestReceived() {
        fillChatData(mMotherChestChat, mMotherDataListChest);
    }

    @Override
    protected void onMotherDataListWaistReceived() {
        fillChatData(mMotherWaistChat, mMotherDataListWaist);
    }

    @Override
    protected void onMotherDataListHipReceived() {
        fillChatData(mMotherHipChat, mMotherDataListHip);
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

    @OnClick({R.id.tv_health_desc,
            R.id.ll_mother_chest_form_container,
            R.id.ll_mother_weight_form_container,
            R.id.ll_mother_waist_form_container,
            R.id.ll_mother_hips_form_container})
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
        /*Intent intent=new Intent(getActivity(),ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.URL,mUserInfo.getFoodUrl(getActivity()));
        startActivity(intent);*/
        ActivitySvc.startArticle(getActivity(),true,mUserInfo.getFoodUrl(getActivity()),getString(R.string.food),null,null,null);
    }

    @OnClick(R.id.ll_knowledge)
    private void startKnowledge(){
        startActivity(new Intent(getActivity(), KnowledgeActivity.class));
    }

    @Override
    protected void onUserInfoAvailable() {
        super.onUserInfoAvailable();
        mInfo.setText(mUserInfo.getBaseInfo());
    }
}
