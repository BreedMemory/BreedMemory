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
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uuzz.android.ui.view.LineChatView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ActivitysActivity;
import com.yijiehl.club.android.ui.activity.health.HealthInfoAfterActivity;
import com.yijiehl.club.android.ui.activity.health.HealthInfoInActivity;
import com.yijiehl.club.android.ui.activity.question.KnowledgeActivity;
import com.yijiehl.club.android.ui.adapter.IllnessHistoryAdapter;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: ServiceInFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/25 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.fragment_health_service_after_layout)
public class ServiceAfterFragment extends HealthInfoFragment {
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
    /** 母亲统计图容器 */
    @ViewInject(R.id.form_mother)
    private View mFormMother;
    /** 婴儿统计图容器 */
    @ViewInject(R.id.form_baby)
    private View mFormBaby;

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

    /** 婴儿病历记录标题容器 */
    @ViewInject(R.id.tv_illness_title)
    private TextView mBabyIllnessHistoryTitle;
    /** 婴儿病历记录图容器 */
    @ViewInject(R.id.lv_listview)
    private ListView mBabyIllnessHistory;
    private IllnessHistoryAdapter mIllnessAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIllnessAdapter = new IllnessHistoryAdapter(getActivity());
        mBabyIllnessHistory.setAdapter(mIllnessAdapter);

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

    @Override
    public void onResume() {
        super.onResume();
        getMotherDataListWeight();
        getMotherDataListChest();
        getMotherDataListWaist();
        getMotherDataListHip();
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

        mBabyWeightChat.setOnValueChangedListener(new LineChatView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, float value) {
                mBabyWeightValue.setText(String.format(getString(R.string.weight_string), mBabyDataListWeight.get(getBabyDataIndex()).getResultList().get(position).getStatValue()));
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
                mBabyHeightValue.setText(String.format(getString(R.string.length_string), mBabyDataListHeight.get(getBabyDataIndex()).getResultList().get(position).getStatValue()));
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

    @Override
    protected void onBabyDataListHeightReceived(int index) {
        super.onBabyDataListHeightReceived(index);
        if(index == getBabyDataIndex()) {
            fillChatData(mBabyHeightChat, mBabyDataListHeight.get(index));
        }
    }

    @Override
    protected void onBabyDataListWeightReceived(int index) {
        super.onBabyDataListWeightReceived(index);
        if(index == getBabyDataIndex()) {
            fillChatData(mBabyWeightChat, mBabyDataListWeight.get(index));
        }
    }

    @Override
    protected void onUserInfoAvailable() {
        super.onUserInfoAvailable();
        mInfo.setText(mUserInfo.getBaseInfo());
        getBabyData();

        // TODO: 谌珂 2016/10/26 请求接口查询病例


        //计算应当显示的宝宝视图按钮
        if (mUserInfo.getChildrenInfo() != null) {
            for (int i = 0; i < mUserInfo.getChildrenInfo().size(); i++) {
                mFormSelector.getChildAt(4 - i).setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 描 述：填充婴儿统计图数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/3 <br/>
     */
    private void fillBabyData() {
        if(mBabyDataListHeight.size() > getBabyDataIndex()) {
            fillChatData(mBabyHeightChat, mBabyDataListHeight.get(getBabyDataIndex()));
        }
        if(mBabyDataListWeight.size() > getBabyDataIndex()) {
            fillChatData(mBabyWeightChat, mBabyDataListWeight.get(getBabyDataIndex()));
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
            R.id.ll_mother_hips_form_container,
            R.id.ll_baby_weight_form_container,
            R.id.ll_baby_height_form_container})
    private void startHealthData() {
        Intent intent = new Intent(getActivity(), HealthInfoAfterActivity.class);
        intent.putExtra(HealthInfoInActivity.ROLE, mFormSelector.getCheckedRadioButtonId());
        startActivity(intent);
    }

    @Override
    public int getCheckId() {
        return mFormSelector.getCheckedRadioButtonId();
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
        ActivitySvc.startArticle(getActivity(),true,mUserInfo.getFoodUrl(getActivity()), mUserInfo.getOrgInfo() + getString(R.string.month_food),null,null,null);
    }

    @OnClick(R.id.ll_knowledge)
    private void startKnowledge(){
        startActivity(new Intent(getActivity(), KnowledgeActivity.class));
    }
}
