/**
 * 项目名称：手机大管家
 * 文件名称: HealthInfoInActivity.java
 * Created by 谌珂 on 2016/10/4.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity.health;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.FileUtil;
import com.uuzz.android.util.ObservableTag;
import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.Utils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.entity.UploadPictureMessage;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.AddBabyHealthData;
import com.yijiehl.club.android.network.request.dataproc.AddMotherHealthData;
import com.yijiehl.club.android.network.request.dataproc.BabyHealthData;
import com.yijiehl.club.android.network.request.dataproc.EditBabyHealthData;
import com.yijiehl.club.android.network.request.dataproc.EditMotherHealthData;
import com.yijiehl.club.android.network.request.dataproc.MotherHealthData;
import com.yijiehl.club.android.network.request.search.ReqSearchBabyData;
import com.yijiehl.club.android.network.request.search.ReqSearchExtraFile;
import com.yijiehl.club.android.network.request.search.ReqSearchMotherData;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;
import com.yijiehl.club.android.network.response.RespSearchExtraFile;
import com.yijiehl.club.android.network.response.RespSearchHealthData;
import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.ExtraFile;
import com.yijiehl.club.android.network.response.innerentity.HealthData;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.UploadPictureSvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.activity.photo.PhotoPickerActivity;
import com.yijiehl.club.android.ui.activity.photo.UploadPhotoActivity;
import com.yijiehl.club.android.ui.adapter.UploadImageAdapter;
import com.yijiehl.club.android.ui.dialog.BaseDialog;
import com.yijiehl.club.android.ui.dialog.MessageDialog;
import com.yijiehl.club.android.ui.view.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: HealthInfoInActivity<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_health_data_after_frame)
public class HealthInfoAfterActivity extends BmActivity implements AdapterView.OnItemClickListener {
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


    /** 宝宝身高 */
    @ViewInject(R.id.et_baby_height)
    private EditText mBabyHeight;
    /** 宝宝体重 */
    @ViewInject(R.id.et_baby_weight)
    private EditText mBabyWeight;
    /** 疾病名称 */
    @ViewInject(R.id.et_illness_name)
    private EditText mIllnessName;
    /** 生病开始日期 */
    @ViewInject(R.id.tv_date)
    private TextView mIllDate;
    /** 生病天数 */
    @ViewInject(R.id.et_illness_day)
    private TextView mIllnessDay;
    /** 病例图片 */
    @ViewInject(R.id.gv_photos)
    private GridView mIllnessPhotos;

    /** 时间选择容器 */
    @ViewInject(R.id.ll_time_picker_container)
    private View mPickerContainer;
    /** 婴儿统计图容器 */
    @ViewInject(R.id.data_baby)
    private View mDataBaby;
    /** 预产期选择控件 */
    @ViewInject(R.id.tp_choose_date)
    private TimePicker mTimePicker;
    /** 确认时间按钮 */
    @ViewInject(R.id.btn_choose_commit)
    private View mChooseTimeCommit;
    /** 确认生病时间按钮 */
    @ViewInject(R.id.btn_choose_ill_date_commit)
    private View mChooseIllTimeCommit;

    private String mTime;

    private UserInfo mUserInfo;
    private AsyncTask mMotherTask;
    private AsyncTask mBabyTask;

    private HealthData mBabyHealthData;
    private HealthData mMotherHealthData;
    private UploadImageAdapter mUploadImageAdapter;
    private ReadMediaTask mReadMediaTask = new ReadMediaTask();
    private long mTaskId;

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
                switch (mFormSelector.getCheckedRadioButtonId()) {
                    case R.id.rb_mother:
                        if(!checkMotherData()) {
                            Toaster.showShortToast(HealthInfoAfterActivity.this, getString(R.string.please_input_real_data));
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
                        NetHelper.getDataFromNet(HealthInfoAfterActivity.this,
                                new ReqBaseDataProc(HealthInfoAfterActivity.this, request),
                                new AbstractCallBack(HealthInfoAfterActivity.this) {
                                    @Override
                                    public void onSuccess(AbstractResponse pResponse) {
                                        Toaster.showShortToast(HealthInfoAfterActivity.this, getString(R.string.save_data_success));
                                        getMotherData();
                                    }

                                    @Override
                                    public void onFailed(String msg) {
                                        super.onFailed(msg);
                                        Toaster.showShortToast(HealthInfoAfterActivity.this, getString(R.string.save_data_failed));
                                    }
                                });
                        break;
                    case R.id.rb_baby0:
                    case R.id.rb_baby1:
                    case R.id.rb_baby2:
                    case R.id.rb_baby3:
                        if(mTaskId != 0) {
                            Toaster.showShortToast(HealthInfoAfterActivity.this, R.string.uploading_picture);
                        }
                        if(!checkBabyData()) {
                            Toaster.showShortToast(HealthInfoAfterActivity.this, getString(R.string.please_input_real_data));
                            return;
                        }
                        // DONE: 谌珂 2016/10/26 接口文档没有传宝宝id的字段  等待添加
                        if(mBabyTask != null) {
                            mBabyTask.cancel(true);
                        }
                        BabyHealthData babyRequest;
                        int childId = Integer.valueOf((String)mFormSelector.findViewById(mFormSelector.getCheckedRadioButtonId()).getTag(mFormSelector.getCheckedRadioButtonId()));
                        if(mBabyHealthData == null) {
                            babyRequest = new AddBabyHealthData(childId, mTime, mBabyHeight.getText().toString(), mBabyWeight.getText().toString(), mIllnessName.getText().toString(), mIllDate.getText().toString(), mIllnessDay.getText().toString(), mUploadImageAdapter.getCount() - 1);
                        } else {
                            babyRequest = new EditBabyHealthData(mBabyHealthData.getRelateCode(), childId, mTime, mBabyHeight.getText().toString(), mBabyWeight.getText().toString(), mIllnessName.getText().toString(), mIllDate.getText().toString(), mIllnessDay.getText().toString(), mUploadImageAdapter.getCount() - 1);
                        }
                        NetHelper.getDataFromNet(HealthInfoAfterActivity.this,
                                new ReqBaseDataProc(HealthInfoAfterActivity.this, babyRequest),
                                new AbstractCallBack(HealthInfoAfterActivity.this) {
                                    @Override
                                    public void onSuccess(AbstractResponse pResponse) {
                                        String relateCode = ((BaseResponse)pResponse).getReturnMsg().getResultCode();
                                        if(mUploadImageAdapter.getCount() <= 1 || TextUtils.isEmpty(relateCode)) {
                                            getBabyData();
                                            Toaster.showShortToast(HealthInfoAfterActivity.this, getString(R.string.save_data_success));
                                            return;
                                        }
                                        mTaskId = System.currentTimeMillis();
                                        UploadPictureSvc.getInstance().addObserver(HealthInfoAfterActivity.this);
                                        UploadPictureSvc.getInstance().uploadMultiplePicture(HealthInfoAfterActivity.this, ReqUploadFile.UploadType.CRM_HLDATA_ITEM_MY, null, mUploadImageAdapter.getDatas(), mTaskId, relateCode);
                                    }

                                    @Override
                                    public void onFailed(String msg) {
                                        super.onFailed(msg);
                                        Toaster.showShortToast(HealthInfoAfterActivity.this, getString(R.string.save_data_failed));
                                    }
                                });
                }
            }
        });
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        Message msg = (Message) data;
        if(observable != UploadPictureSvc.getInstance()){
            return;
        }
        UploadPictureMessage result = (UploadPictureMessage) msg.obj;
        if (mTaskId == result.getTimestamp() && msg.what == ObservableTag.UPLOAD_COMPLETE) {
            UploadPictureSvc.getInstance().deleteObserver(this);
            mTaskId = 0;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toaster.showShortToast(HealthInfoAfterActivity.this, getString(R.string.save_data_success));
                    if(mBabyTask != null) {
                        mBabyTask.cancel(true);
                    }
                    mBabyTask = getBabyData();
                }
            });
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUploadImageAdapter = new UploadImageAdapter(this);
        mIllnessPhotos.setAdapter(mUploadImageAdapter);
        mIllnessPhotos.setOnItemClickListener(this);
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
                        mBabyTask = getBabyData();
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
            mBabyTask = getBabyData();
        }
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
     * 描 述：获取母亲某一天数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/26 <br/>
     */
    private AsyncTask getBabyData() {

        clearBabyData();
        return NetHelper.getDataFromNet(this, new ReqSearchBabyData(this, mTime, (String) mFormSelector.findViewById(mFormSelector.getCheckedRadioButtonId()).getTag(mFormSelector.getCheckedRadioButtonId())), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                if(((RespSearchHealthData) pResponse).getResultList() == null || ((RespSearchHealthData) pResponse).getResultList().size() == 0) {
                    return;
                }
                mBabyHealthData = ((RespSearchHealthData) pResponse).getResultList().get(0);
                mBabyWeight.setText(mBabyHealthData.getStatValue01());
                mBabyHeight.setText(mBabyHealthData.getStatValue03());
                mIllnessName.setText(mBabyHealthData.getStatValue35());
                mIllDate.setText(mBabyHealthData.getStatValue36());
                mIllnessDay.setText(mBabyHealthData.getStatValue37());
                if(mBabyHealthData.getFileFlag() > 0) {
                    obtainExtraPhoto(((RespSearchHealthData) pResponse).getResultList().get(0).getRelateCode());
                }
            }
        });
    }

    private void clearBabyData() {
        // DONE: 谌珂 2016/10/27 清除宝宝健康数据
        mBabyWeight.setText(null);
        mBabyHeight.setText(null);
        mIllnessName.setText(null);
        mIllDate.setText(null);
        mIllnessDay.setText(null);
        mUploadImageAdapter.clear();
        mUploadImageAdapter.setMode(UploadImageAdapter.MODE_NATIVE);
    }

    /**
     * 描 述：获取病例图片<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/27 <br/>
     */
    private void obtainExtraPhoto(final String relateCode) {
        NetHelper.getDataFromNet(this, new ReqSearchExtraFile(this, relateCode), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchExtraFile data = (RespSearchExtraFile) pResponse;
                if(data.getResultList() == null || data.getResultList().size() == 0) {
                    return;
                }
                List<String> urls = new ArrayList<>();
                for (ExtraFile lExtraFile: data.getResultList()) {
                    urls.add(lExtraFile.getDataUrl());
                }
                mUploadImageAdapter.setMode(UploadImageAdapter.MODE_REMOTE);
                mUploadImageAdapter.setDatas(urls);
            }
        });
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
        if(mMotherTask != null) {
            mMotherTask.cancel(true);
        }
        mMotherHealthData = null;
        mMotherTask = getMotherData();
        if(mBabyTask != null) {
            mBabyTask.cancel(true);
        }
        mBabyHealthData = null;
        mBabyTask = getBabyData();
    }

    /**
     * 描 述：确认生病时间<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/12 <br/>
     */
    @OnClick(R.id.btn_choose_ill_date_commit)
    private void commitIllDate() {
        mPickerContainer.setVisibility(View.GONE);
        mIllDate.setText(mTimePicker.getDate());
    }

    @OnClick(R.id.tv_time)
    private void chooseTime() {
        mTimePicker.setDate(TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD));
        mPickerContainer.setVisibility(View.VISIBLE);
        mChooseIllTimeCommit.setVisibility(View.GONE);
        mChooseTimeCommit.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.rl_choose_ill_time)
    private void chooseIllTime() {
        Utils.hideKeyBoard(mFormSelector);
        mTimePicker.setDate(TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD));
        mPickerContainer.setVisibility(View.VISIBLE);
        mChooseIllTimeCommit.setVisibility(View.VISIBLE);
        mChooseTimeCommit.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mUploadImageAdapter.getItemViewType(position) != UploadImageAdapter.TYPE_ADD) {
            return;
        }
        if (mTaskId != 0) {
            Toaster.showShortToast(this, getString(R.string.uploading_picture));
            return;
        }
        checkPromissions(FileUtil.createPermissions(), mReadMediaTask);
    }

    /**
     * 描 述：请求权限成功后打开图片选择<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/8 <br/>
     */
    private class ReadMediaTask implements Runnable {
        @Override
        public void run() {
            ArrayList<String> list = new ArrayList<>();
            if(mUploadImageAdapter.getDatas() != null) {
                list.addAll(mUploadImageAdapter.getDatas());
            }
            ActivitySvc.startImagePicker(HealthInfoAfterActivity.this, list);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoPickerActivity.PHOTO_PICKER_ACTIVITY && resultCode == Activity.RESULT_OK) {
            //获取选择的图片
            ArrayList<String> filePaths = data.getStringArrayListExtra(UploadPhotoActivity.PATH);
            if (filePaths == null || filePaths.size() == 0) {   //选择的图片为空终止
                return;
            }
            mUploadImageAdapter.setDatas(filePaths);
        }
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

    /**
     * 描 述：检测婴儿健康数据是否合理<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/3 <br/>
     */
    private boolean checkBabyData() {
        if(TextUtils.isEmpty(mBabyWeight.getText()) || TextUtils.isEmpty(mBabyHeight.getText())) {
            return false;
        }
        float weight = Float.valueOf(mBabyWeight.getText().toString());
        if(weight < 1 || weight > 30) {
            return false;
        }
        float height = Float.valueOf(mBabyHeight.getText().toString());
        if(height < 20 || height > 100) {
            return false;
        }
        return true;
    }

    /**
     * 描 述：检查数据是否已经保存<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/11/5 <br/>
     */
    private boolean isSaveData() {
        switch (mFormSelector.getCheckedRadioButtonId()) {
            case R.id.rb_mother:
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
            case R.id.rb_baby0:
            case R.id.rb_baby1:
            case R.id.rb_baby2:
            case R.id.rb_baby3:
                if(mBabyHealthData == null) {
                    return true;
                }
                if(TextUtils.isEmpty(mBabyWeight.getText().toString()) ||
                        TextUtils.isEmpty(mBabyHeight.getText().toString())) {
                    return false;
                }
                return (Float.valueOf(mBabyWeight.getText().toString()).equals(Float.valueOf(mBabyHealthData.getStatValue01())) &&
                        Float.valueOf(mBabyHeight.getText().toString()).equals(Float.valueOf(mBabyHealthData.getStatValue03())));
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(mTaskId != 0 || (mUploadImageAdapter.getMode() == UploadImageAdapter.MODE_NATIVE && mUploadImageAdapter.getCount() > 1) || !isSaveData()) {
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
                    HealthInfoAfterActivity.super.onBackPressed();
                }
            });
        } else {
            super.onBackPressed();
        }
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
        if(mMotherTask != null) {
            mMotherTask.cancel(true);
        }
        mMotherHealthData = null;
        mMotherTask = getMotherData();
        if(mBabyTask != null) {
            mBabyTask.cancel(true);
        }
        mBabyHealthData = null;
        mBabyTask = getBabyData();
    }

    @OnClick(R.id.itv_right)
    private void tomorrowData(){
        String nowTime = mTimeView.getText().toString();
        Long newTime = createNextTime(nowTime) + 24*60*60*1000;
        mTime = TimeUtil.getTime(newTime, TimeUtil.DEFAULT_FORMAT_YYYYMMDD);
        mTimeView.setText(createTime(createLongTime(TimeUtil.getTime(newTime, TimeUtil.DEFAULT_FORMAT_YYYYMMDD),"yyyy-MM-dd")));
        if(mMotherTask != null) {
            mMotherTask.cancel(true);
        }
        mMotherHealthData = null;
        mMotherTask = getMotherData();
        if(mBabyTask != null) {
            mBabyTask.cancel(true);
        }
        mBabyHealthData = null;
        mBabyTask = getBabyData();
    }
}
