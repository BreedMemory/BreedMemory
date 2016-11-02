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
import android.widget.EditText;
import android.widget.TextView;

import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.AddMotherHealthData;
import com.yijiehl.club.android.network.request.dataproc.EditMotherHealthData;
import com.yijiehl.club.android.network.request.dataproc.MotherHealthData;
import com.yijiehl.club.android.network.request.search.ReqSearchMotherData;
import com.yijiehl.club.android.network.response.RespSearchHealthData;
import com.yijiehl.club.android.network.response.innerentity.HealthData;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.view.TimePicker;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: HealthInfoInActivity<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_health_data_before_frame)
public class HealthInfoBeforeActivity extends BmActivity {
    /** 时间显示 */
    @ViewInject(R.id.tv_time)
    private TextView mTimeView;
    /** 妈妈体重 */
    @ViewInject(R.id.et_mother_weight)
    private EditText mMotherWeight;
    /** 妈妈体重 */
    @ViewInject(R.id.et_mother_chest)
    private EditText mMotherChest;
    /** 妈妈体重 */
    @ViewInject(R.id.et_mother_waist)
    private EditText mMotherWaist;
    /** 妈妈体重 */
    @ViewInject(R.id.et_mother_hips)
    private EditText mMotherHips;


    /** 时间选择容器 */
    @ViewInject(R.id.ll_time_picker_container)
    private View mPickerContainer;
    /** 预产期选择控件 */
    @ViewInject(R.id.tp_choose_date)
    private TimePicker mTimePicker;

    private String mTime;

    private AsyncTask mMotherTask;

    private HealthData mMotherHealthData;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.health_data);
    }

    @Override
    protected void configHeadRightView() {
        super.configHeadRightView();
        mHeadRightContainer.setVisibility(View.VISIBLE);
        mRightBtn.setText(R.string.icon_save);
        mHeadRightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMotherTask != null) {
                    mMotherTask.cancel(true);
                }
                MotherHealthData request;
                if(mMotherHealthData == null) {
                    request = new AddMotherHealthData(mTime, mMotherWeight.getText().toString(), mMotherChest.getText().toString(), mMotherWaist.getText().toString(), mMotherHips.getText().toString());
                } else {
                    request = new EditMotherHealthData(mMotherHealthData.getRelateCode(), mTime, mMotherWeight.getText().toString(), mMotherChest.getText().toString(), mMotherWaist.getText().toString(), mMotherHips.getText().toString());

                }
                NetHelper.getDataFromNet(HealthInfoBeforeActivity.this,
                        new ReqBaseDataProc(HealthInfoBeforeActivity.this, request),
                        new AbstractCallBack(HealthInfoBeforeActivity.this) {
                            @Override
                            public void onSuccess(AbstractResponse pResponse) {
                                Toaster.showShortToast(HealthInfoBeforeActivity.this, getString(R.string.save_data_success));
                                getMotherData();
                            }

                            @Override
                            public void onFailed(String msg) {
                                super.onFailed(msg);
                                Toaster.showShortToast(HealthInfoBeforeActivity.this, getString(R.string.save_data_failed));
                            }
                        });
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTime = TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD);
        mTimeView.setText(createTime(System.currentTimeMillis()));
        mMotherTask = getMotherData();
    }


    /**
     * 描 述：获取母亲某一天数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/26 <br/>
     */
    private AsyncTask getMotherData() {
        mMotherHealthData = null;
        clearMotherData();
        return NetHelper.getDataFromNet(this, new ReqSearchMotherData(this, mTime), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchHealthData lData = (RespSearchHealthData) pResponse;
                if(lData.getResultList() == null || lData.getResultList().size() == 0) {
                    return;
                }
                mMotherHealthData = lData.getResultList().get(0);
                mMotherWeight.setText(mMotherHealthData.getStatValue01());
                mMotherChest.setText(mMotherHealthData.getStatValue10());
                mMotherWaist.setText(mMotherHealthData.getStatValue11());
                mMotherHips.setText(mMotherHealthData.getStatValue12());
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
        mMotherChest.setText(null);
        mMotherWaist.setText(null);
        mMotherHips.setText(null);
    }

    /**
     * 描 述：构建时间<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/26 <br/>
     */
    private String createTime(long timestamp) {
        String text = mTime.replace("-", getString(R.string.year));
        text = text.replace("-", getString(R.string.month));
        text += "日 " + TimeUtil.getWeekStr(timestamp);
        return text;
    }


    /**
     * 描 述：确认时间<br/>
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
        if(mMotherTask != null) {
            mMotherTask.cancel(true);
        }
        mMotherTask = getMotherData();
    }

    @OnClick(R.id.rl_choose_time)
    private void chooseTime() {
        mTimePicker.setDate(TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD));
        mPickerContainer.setVisibility(View.VISIBLE);
    }
}
