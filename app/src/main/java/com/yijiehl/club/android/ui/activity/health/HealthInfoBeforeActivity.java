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
import com.yijiehl.club.android.ui.dialog.BaseDialog;
import com.yijiehl.club.android.ui.dialog.MessageDialog;
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
                if(!checkMotherData()) {
                    Toaster.showShortToast(HealthInfoBeforeActivity.this, getString(R.string.please_input_real_data));
                    return;
                }
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

    /**
     * 描 述：检测母亲健康数据是否合理<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/3 <br/>
     */
    private boolean checkMotherData() {
        if(TextUtils.isEmpty(mMotherWeight.getText().toString()) ||
                TextUtils.isEmpty(mMotherChest.getText().toString()) ||
                TextUtils.isEmpty(mMotherWaist.getText().toString()) ||
                TextUtils.isEmpty(mMotherHips.getText().toString())) {
            return false;
        }
        float weight = Float.valueOf(mMotherWeight.getText().toString());
        if(weight < 20 || weight > 200) {
            return false;
        }
        float waist = Float.valueOf(mMotherWaist.getText().toString());
        if(waist < 10 || waist > 200) {
            return false;
        }
        float chest = Float.valueOf(mMotherChest.getText().toString());
        if(chest < 30 || chest > 300) {
            return false;
        }
        float hips = Float.valueOf(mMotherHips.getText().toString());
        if(hips < 30 || hips > 300) {
            return false;
        }
        return true;
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
        String text = mTime.replaceFirst("-", getString(R.string.year));
        text = text.replaceFirst("-", getString(R.string.month));
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
        mMotherHealthData = null;
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

    /**
     * 描 述：检查数据是否已经保存<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/11/5 <br/>
     */
    private boolean isSaveData() {
        if(mMotherHealthData == null) {
            return true;
        }
        if(TextUtils.isEmpty(mMotherWeight.getText().toString()) ||
                TextUtils.isEmpty(mMotherChest.getText().toString()) ||
                TextUtils.isEmpty(mMotherWaist.getText().toString()) ||
                TextUtils.isEmpty(mMotherHips.getText().toString())) {
            return false;
        }
        return (Float.valueOf(mMotherWeight.getText().toString()).equals(Float.valueOf(mMotherHealthData.getStatValue01())) &&
                Float.valueOf(mMotherChest.getText().toString()).equals(Float.valueOf(mMotherHealthData.getStatValue10())) &&
                Float.valueOf(mMotherWaist.getText().toString()).equals(Float.valueOf(mMotherHealthData.getStatValue11())) &&
                Float.valueOf(mMotherHips.getText().toString()).equals(Float.valueOf(mMotherHealthData.getStatValue12())));
    }

    @Override
    public void onBackPressed() {
        if(!isSaveData()) {
            MessageDialog dialog = MessageDialog.getInstance(this);
            dialog.setMessage("您当前的数据和图片没有上传完成?要放弃本次上传吗?");
            dialog.showDoubleBtnDialog(R.string.cancel, R.string.give_up, new BaseDialog.OnBtnsClickListener() {
                @Override
                public void onLeftClickListener(View v, BaseDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onRightClickListener(View v, BaseDialog dialog) {
                    dialog.dismiss();
                    HealthInfoBeforeActivity.super.onBackPressed();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
