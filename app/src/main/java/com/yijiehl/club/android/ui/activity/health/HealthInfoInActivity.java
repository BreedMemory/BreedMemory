/**
 * 项目名称：手机大管家
 * 文件名称: HealthInfoInActivity.java
 * Created by 谌珂 on 2016/10/4.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity.health;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchBabyData;
import com.yijiehl.club.android.network.request.search.ReqSearchMotherData;
import com.yijiehl.club.android.network.response.RespSearchHealthData;
import com.yijiehl.club.android.network.response.innerentity.HealthData;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.view.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: HealthInfoInActivity<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_health_data_in_frame)
public class HealthInfoInActivity extends BmActivity {
    public static final String ROLE = "role";

    /** 图表选择器 */
    @ViewInject(R.id.rg_selector)
    private RadioGroup mFormSelector;
    @ViewInject(R.id.data_mother)
    private View mDataMother;
    /** 时间显示 */
    @ViewInject(R.id.tv_time)
    private TextView mTimeView;
    /** 妈妈体重 */
    @ViewInject(R.id.tv_mother_weight)
    private TextView mMotherWeight;
    /** 妈妈体温 */
    @ViewInject(R.id.tv_mother_temperature)
    private TextView mMotherTemperature;
    /** 妈妈血糖 */
    @ViewInject(R.id.tv_mother_blood_sugar)
    private TextView mMotherBloodSugar;
    /** 妈妈血压 */
    @ViewInject(R.id.tv_mother_blood_pressure)
    private TextView mMotherBloodPressure;
    /** 妈妈睡眠 */
    @ViewInject(R.id.tv_mother_sleep)
    private TextView mMotherSleep;
    /** 妈妈基本信息 */
    @ViewInject(R.id.tv_mother_info)
    private TextView mMotherInfo;


    /** 宝宝生日 */
    @ViewInject(R.id.tv_baby_birthday)
    private TextView mBabyBirthday;
    /** 宝宝体重 */
    @ViewInject(R.id.tv_baby_weight)
    private TextView mBabyWeight;
    /** 宝宝头围 */
    @ViewInject(R.id.tv_baby_head)
    private TextView mBabyHead;
    /** 宝宝胸围 */
    @ViewInject(R.id.tv_baby_chest)
    private TextView mBabyChest;
    /** 宝宝身高 */
    @ViewInject(R.id.tv_baby_height)
    private TextView mBabyHeight;
    /** 宝宝食物种类 */
    @ViewInject(R.id.tv_foot_kind)
    private TextView mBabyFoodKind;
    /** 宝宝食量 */
    @ViewInject(R.id.tv_foot_count)
    private TextView mBabyFoodCount;
    /** 宝宝排泄 */
    @ViewInject(R.id.tv_excretion)
    private TextView mBabyExcretion;
    /** 宝宝黄恒 */
    @ViewInject(R.id.tv_yellow)
    private TextView mBabyYellow;
    /** 宝宝湿疹 */
    @ViewInject(R.id.tv_wet)
    private TextView mBabyWet;
    /** 宝宝红臀 */
    @ViewInject(R.id.tv_red)
    private TextView mBabyRed;

            ;
    /** 时间选择容器 */
    @ViewInject(R.id.ll_time_picker_container)
    private View mPickerContainer;
    /** 婴儿统计图容器 */
    @ViewInject(R.id.data_baby)
    private View mDataBaby;
    /** 预产期选择控件 */
    @ViewInject(R.id.tp_choose_date)
    private TimePicker mTimePicker;

    private String mTime;

    private UserInfo mUserInfo;
    private AsyncTask mMotherTask;
    private AsyncTask mBabyTask;
    private int flag = 0;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.health_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTime = TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD);
        mTimeView.setText(createTime(System.currentTimeMillis()));
        CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(this,
                R.string.shared_preference_user_id), getString(R.string.shared_preference_user_info));
        mFormSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother:
                        if(mMotherTask != null) {
                            mMotherTask.cancel(true);
                        }
                        mMotherTask = getMotherData();
                        mDataMother.setVisibility(View.VISIBLE);
                        mDataBaby.setVisibility(View.GONE);
                        flag = 0;
                        break;
                    case R.id.rb_baby0:
                    case R.id.rb_baby1:
                    case R.id.rb_baby2:
                    case R.id.rb_baby3:
                        mDataMother.setVisibility(View.GONE);
                        mDataBaby.setVisibility(View.VISIBLE);
                        if(mBabyTask != null) {
                            mBabyTask.cancel(true);
                        }
                        mBabyTask = getBabyData((String) group.findViewById(checkedId).getTag(checkedId));
                        flag = Integer.parseInt((String) group.findViewById(checkedId).getTag(checkedId));
                        break;
                }
            }
        });
        int resId = getIntent().getIntExtra(ROLE, R.id.rb_mother);
        mFormSelector.check(resId);
        mMotherTask = getMotherData();
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            mUserInfo = JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class);
            //计算应当显示的宝宝视图按钮
            if(mUserInfo.getChildrenInfo() != null) {
                for (int i = 0; i < mUserInfo.getChildrenInfo().size(); i++) {
                    View v = mFormSelector.getChildAt(4-i);
                    v.setVisibility(View.VISIBLE);
                    v.setTag(v.getId(), mUserInfo.getChildrenInfo().get(i).getValue());
                }
            }
        }
    }

    /**
     * 描 述：获取母亲某一天数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/26 <br/>
     */
    private AsyncTask getMotherData() {
        clearMotherData();
        return NetHelper.getDataFromNet(this, new ReqSearchMotherData(this, mTime), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchHealthData lData = (RespSearchHealthData) pResponse;
                if(lData.getResultList() == null || lData.getResultList().size() == 0) {
                    if(!mTimeView.getText().toString().isEmpty()&&mTimeView.getText().toString().equals(createTime(System.currentTimeMillis()))){
                        Toaster.showShortToast(HealthInfoInActivity.this, getString(R.string.please_wait_club_upload_data));
                    }
                    return;
                }
                mMotherWeight.setText(lData.getResultList().get(0).getStatValue01());
                mMotherTemperature.setText(lData.getResultList().get(0).getStatValue02());
                mMotherBloodSugar.setText(RespSearchHealthData.transformString(lData.getResultList().get(0).getStatValue05()));
                mMotherBloodPressure.setText(RespSearchHealthData.transformString(lData.getResultList().get(0).getStatValue06()));
                mMotherSleep.setText(RespSearchHealthData.transformString(lData.getResultList().get(0).getStatValue07()));
                mMotherInfo.setText(lData.getResultList().get(0).getDataInfo1());
            }
        });
    }

    /**
     * 描 述：清除母亲健康数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/27 <br/>
     */
    private void clearMotherData() {
        mMotherWeight.setText(null);
        mMotherTemperature.setText(null);
        mMotherBloodSugar.setText(null);
        mMotherBloodPressure.setText(null);
        mMotherSleep.setText(null);
        mMotherInfo.setText(null);
    }

    /**
     * 描 述：获取母亲某一天数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/26 <br/>
     */
    private AsyncTask getBabyData(String babyId) {
        clearBabyData();
        return NetHelper.getDataFromNet(this, new ReqSearchBabyData(this, mTime, babyId), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                if(((RespSearchHealthData) pResponse).getResultList() == null || ((RespSearchHealthData) pResponse).getResultList().size() == 0) {
                    if(!mTimeView.getText().toString().isEmpty()&&mTimeView.getText().toString().equals(createTime(System.currentTimeMillis()))){
                        Toaster.showShortToast(HealthInfoInActivity.this, getString(R.string.please_wait_club_upload_data));
                    }
                    return;
                }
                HealthData lData = ((RespSearchHealthData) pResponse).getResultList().get(0);
                if(TextUtils.isEmpty(lData.getBirthdate())) {
                    mBabyBirthday.setVisibility(View.GONE);
                } else {
                    mBabyBirthday.setVisibility(View.VISIBLE);
                    mBabyBirthday.setText(lData.getBirthdate());
                }
                mBabyWeight.setText(lData.getStatValue01());
                mBabyHead.setText(lData.getStatValue11());
                mBabyChest.setText(lData.getStatValue10());
                mBabyHeight.setText(lData.getStatValue03());
                mBabyFoodKind.setText(RespSearchHealthData.transformString(lData.getStatValue20()));
                mBabyFoodCount.setText(RespSearchHealthData.transformString(lData.getStatValue21()));
                mBabyExcretion.setText("大便" + RespSearchHealthData.transformString(lData.getStatValue25()) + "，小便" + RespSearchHealthData.transformString(lData.getStatValue26()));
                mBabyYellow.setText(RespSearchHealthData.transformString(lData.getStatValue30()));
                mBabyWet.setText(RespSearchHealthData.transformString(lData.getStatValue31()));
                mBabyRed.setText(RespSearchHealthData.transformString(lData.getStatValue32()));
            }
        });
    }

    private void clearBabyData() {
        mBabyBirthday.setText(null);
        mBabyWeight.setText(null);
        mBabyHead.setText(null);
        mBabyChest.setText(null);
        mBabyHeight.setText(null);
        mBabyFoodKind.setText(null);
        mBabyFoodCount.setText(null);
        mBabyExcretion.setText(null);
        mBabyYellow.setText(null);
        mBabyWet.setText(null);
        mBabyRed.setText(null);
    }

    /**
     * 描 述：构建时间<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/26 <br/>
     */
    private String createTime(long timestamp) {
        String text = mTime.replaceFirst("-", getString(R.string.year));
        text = text.replaceFirst("-", getString(R.string.month));
        text += "日 " + TimeUtil.getWeekStr(timestamp);
        return text;
    }

    /**
     * 描 述：确认时间和会所<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/12 <br/>
     */
    @OnClick(R.id.btn_choose_commit)
    private void commitPick() {
        mTime = mTimePicker.getDate();
        String newTime = createTime(mTimePicker.getDateTimeStamp());
        mPickerContainer.setVisibility(View.GONE);
        if(TextUtils.equals(mTimeView.getText().toString(), newTime)){
            return;
        }
        mTimeView.setText(newTime);
        if(flag == 0){
            mMotherTask = getMotherData();
        }
        mBabyTask = getBabyData(String.valueOf(flag));
    }

    @OnClick(R.id.tv_time)
    private void chooseTime() {
        mTimePicker.setDate(TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD));
        mPickerContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 描 述：逆转时间<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/11/22 <br/>
     */
    private long createNextTime(String time) {
        String tempTime = time.substring(0,time.indexOf("日"));
        tempTime = tempTime.replace(getString(R.string.year),"-");
        tempTime = tempTime.replace(getString(R.string.month),"-");
        return createLongTime(tempTime,"yyyy-MM-dd");
    }

    private long createLongTime(String timeStr,String formatString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    formatString);
            Date date = format.parse(timeStr);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }
    @OnClick(R.id.itv_left)
    private void yesterdayData(){
        String nowTime = mTimeView.getText().toString();
        Long newTime = createNextTime(nowTime) - 24*60*60*1000;
        mTime = TimeUtil.getTime(newTime, TimeUtil.DEFAULT_FORMAT_YYYYMMDD);
        mTimeView.setText(createTime(createLongTime(TimeUtil.getTime(newTime, TimeUtil.DEFAULT_FORMAT_YYYYMMDD),"yyyy-MM-dd")));
        if(flag == 0){
            mMotherTask = getMotherData();
        }
        mBabyTask = getBabyData(String.valueOf(flag));
    }

    @OnClick(R.id.itv_right)
    private void tomorrowData(){
        String nowTime = mTimeView.getText().toString();
        Long newTime = createNextTime(nowTime) + 24*60*60*1000;
        mTime = TimeUtil.getTime(newTime, TimeUtil.DEFAULT_FORMAT_YYYYMMDD);
        mTimeView.setText(createTime(createLongTime(TimeUtil.getTime(newTime, TimeUtil.DEFAULT_FORMAT_YYYYMMDD),"yyyy-MM-dd")));
        if(flag == 0){
            mMotherTask = getMotherData();
        }
        mBabyTask = getBabyData(String.valueOf(flag));
    }


}
