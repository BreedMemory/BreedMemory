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

import com.uuzz.android.ui.view.LineChatView;
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
    /** 母亲体温值 */
    @ViewInject(R.id.tv_mother_value_temperature)
    private TextView mMotherTemperatureValue;
    /** 母亲体温日期 */
    @ViewInject(R.id.tv_mother_date_temperature)
    private TextView mMotherTemperatureDate;
    /** 母亲体温图表密度转换 */
    @ViewInject(R.id.rg_mother_selector_temperature)
    private RadioGroup mMotherTemperatureSelector;
    /** 母亲体温图表 */
    @ViewInject(R.id.lcv_mother_temperature)
    private LineChatView mMotherTemperatureChat;
    /** 婴儿体重值 */
    @ViewInject(R.id.tv_baby_value_weight)
    private TextView mBabyWeightValue;
    /** 婴儿体重日期 */
    @ViewInject(R.id.tv_baby_date_weight)
    private TextView mBabyWeightDate;
    /** 婴儿体重图表密度转换 */
    @ViewInject(R.id.rg_baby_selector_weight)
    private RadioGroup mBabyWeightSelector;
    /** 婴儿体重图表 */
    @ViewInject(R.id.lcv_baby_weight)
    private LineChatView mBabyWeightChat;
    /** 婴儿身高值 */
    @ViewInject(R.id.tv_baby_value_height)
    private TextView mBabyHeightValue;
    /** 婴儿身高日期 */
    @ViewInject(R.id.tv_baby_date_height)
    private TextView mBabyHeightDate;
    /** 婴儿身高图表密度转换 */
    @ViewInject(R.id.rg_baby_selector_height)
    private RadioGroup mBabyHeightSelector;
    /** 婴儿身高图表 */
    @ViewInject(R.id.lcv_baby_height)
    private LineChatView mBabyHeightChat;
    /** 婴儿头围值 */
    @ViewInject(R.id.tv_baby_value_head)
    private TextView mBabyHeadValue;
    /** 婴儿头围日期 */
    @ViewInject(R.id.tv_baby_date_head)
    private TextView mBabyHeadDate;
    /** 婴儿头围图表密度转换 */
    @ViewInject(R.id.rg_baby_selector_head)
    private RadioGroup mBabyHeadSelector;
    /** 婴儿头围图表 */
    @ViewInject(R.id.lcv_baby_head)
    private LineChatView mBabyHeadChat;
    /** 婴儿胸围值 */
    @ViewInject(R.id.tv_baby_value_chest)
    private TextView mBabyChestValue;
    /** 婴儿胸围日期 */
    @ViewInject(R.id.tv_baby_date_chest)
    private TextView mBabyChestDate;
    /** 婴儿胸围图表密度转换 */
    @ViewInject(R.id.rg_baby_selector_chest)
    private RadioGroup mBabyChestSelector;
    /** 婴儿胸围图表 */
    @ViewInject(R.id.lcv_baby_chest)
    private LineChatView mBabyChestChat;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMotherDataListTemperature();
        getMotherDataListWeight();

        initAllChat();

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
                        fillBabyData();
                        break;
                }
            }
        });
    }

    /**
     * 描 述：配置母亲图表监听事件<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    private void initAllChat() {
        mMotherWeightChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mMotherWeightValue.setText(mMotherDataListWeight.getResultList().get(position).getStatValue());
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

        mMotherTemperatureChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mMotherTemperatureValue.setText(mMotherDataListTemperature.getResultList().get(position).getStatValue());
                mMotherTemperatureDate.setText(mMotherDataListTemperature.getResultList().get(position).getStatTime());
            }
        });
        mMotherTemperatureSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother_temperature_date_week:
                        mMotherTemperatureChat.setPointCount(CHAT_STEP_WEEK);
                        break;
                    case R.id.rb_mother_temperature_date_month:
                        mMotherTemperatureChat.setPointCount(CHAT_STEP_MONTH);
                        break;
                }
            }
        });

        mBabyWeightChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mBabyWeightValue.setText(mBabyDataListWeight.get(getBabyDataIndex()).getResultList().get(position).getStatValue());
                mBabyWeightDate.setText(mBabyDataListWeight.get(getBabyDataIndex()).getResultList().get(position).getStatTime());
            }
        });
        mBabyWeightSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_baby_weight_date_week:
                        mBabyWeightChat.setPointCount(CHAT_STEP_WEEK);
                        break;
                    case R.id.rb_baby_weight_date_month:
                        mBabyWeightChat.setPointCount(CHAT_STEP_MONTH);
                        break;
                }
            }
        });

        mBabyHeightChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mBabyHeightValue.setText(mBabyDataListHeight.get(getBabyDataIndex()).getResultList().get(position).getStatValue());
                mBabyHeightDate.setText(mBabyDataListHeight.get(getBabyDataIndex()).getResultList().get(position).getStatTime());
            }
        });
        mBabyHeightSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_baby_height_date_week:
                        mBabyHeightChat.setPointCount(CHAT_STEP_WEEK);
                        break;
                    case R.id.rb_baby_height_date_month:
                        mBabyHeightChat.setPointCount(CHAT_STEP_MONTH);
                        break;
                }
            }
        });

        mBabyHeadChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mBabyHeadValue.setText(mBabyDataListHead.get(getBabyDataIndex()).getResultList().get(position).getStatValue());
                mBabyHeadDate.setText(mBabyDataListHead.get(getBabyDataIndex()).getResultList().get(position).getStatTime());
            }
        });
        mBabyHeadSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_baby_head_date_week:
                        mBabyHeadChat.setPointCount(CHAT_STEP_WEEK);
                        break;
                    case R.id.rb_baby_head_date_month:
                        mBabyHeadChat.setPointCount(CHAT_STEP_MONTH);
                        break;
                }
            }
        });

        mBabyChestChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mBabyChestValue.setText(mBabyDataListChest.get(getBabyDataIndex()).getResultList().get(position).getStatValue());
                mBabyChestDate.setText(mBabyDataListChest.get(getBabyDataIndex()).getResultList().get(position).getStatTime());
            }
        });
        mBabyChestSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_baby_chest_date_week:
                        mBabyChestChat.setPointCount(CHAT_STEP_WEEK);
                        break;
                    case R.id.rb_baby_chest_date_month:
                        mBabyChestChat.setPointCount(CHAT_STEP_MONTH);
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
    protected void onMotherDataListWeightReceived() {
        super.onMotherDataListWeightReceived();
        fillChatData(mMotherWeightChat, mMotherDataListWeight);
    }

    @Override
    protected void onMotherDataListTemperatureReceived() {
        super.onMotherDataListWeightReceived();
        fillChatData(mMotherTemperatureChat, mMotherDataListTemperature);
    }

    @Override
    protected void onBabyDataListHeightReceived(int index) {
        super.onBabyDataListHeightReceived(index);
        if(index != getBabyDataIndex()) {
            fillChatData(mBabyHeightChat, mBabyDataListHeight.get(index));
        }
    }

    @Override
    protected void onBabyDataListWeightReceived(int index) {
        super.onBabyDataListWeightReceived(index);
        if(index != getBabyDataIndex()) {
            fillChatData(mBabyWeightChat, mBabyDataListWeight.get(index));
        }
    }

    @Override
    protected void onBabyDataListHeadReceived(int index) {
        if(index != getBabyDataIndex()) {
            fillChatData(mBabyHeadChat, mBabyDataListHead.get(index));
        }
    }

    @Override
    protected void onBabyDataListChestReceived(int index) {
        if(index != getBabyDataIndex()) {
            fillChatData(mBabyChestChat, mBabyDataListChest.get(index));
        }
    }

    /**
     * 描 述：获取被选择的宝宝数据索引<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/3 <br/>
     * @return 宝宝数据索引
     */
    private int getBabyDataIndex() {
        switch (mFormSelector.getCheckedRadioButtonId()) {
            case R.id.rb_baby0:
                return 0;
            case R.id.rb_baby1:
                return 1;
            case R.id.rb_baby2:
                return 2;
            case R.id.rb_baby3:
                return 3;
            default:
                return 0;
        }
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
        return mFormSelector.getCheckedRadioButtonId();
    }

    @Override
    protected void onBabyDataReceived(int index) {
        if(index != getBabyDataIndex()) {
            return;
        }
        fillBabyData();
    }

    private void fillBabyData() {
        if(mBabyDatas == null || mBabyDatas.size() == 0 || mBabyDatas.get(getBabyDataIndex()) == null || mBabyDatas.get(getBabyDataIndex()).getResultList() == null || mBabyDatas.get(getBabyDataIndex()).getResultList().size() > getBabyDataIndex()) {
            return;
        }
        HealthData data = mBabyDatas.get(getBabyDataIndex()).getResultList().get(getBabyDataIndex());
        if(data != null) {
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

        // DONE: 谌珂 2016/11/3 填充统计图数据
        fillChatData(mBabyHeightChat, mBabyDataListHeight.get(getBabyDataIndex()));
        fillChatData(mBabyWeightChat, mBabyDataListWeight.get(getBabyDataIndex()));
        fillChatData(mBabyHeadChat, mBabyDataListHead.get(getBabyDataIndex()));
        fillChatData(mBabyChestChat, mBabyDataListChest.get(getBabyDataIndex()));
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

    @OnClick({R.id.mother_extra_data,
            R.id.baby_extra_data})
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
