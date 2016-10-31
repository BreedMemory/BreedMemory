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
import com.yijiehl.club.android.ui.view.TimePicker;

import java.util.ArrayList;
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
                        // DONE: 谌珂 2016/10/26 接口文档没有传宝宝id的字段  等待添加
                        if(mBabyTask != null) {
                            mBabyTask.cancel(true);
                        }
                        BabyHealthData babyRequest;
                        int childId = Integer.valueOf((String)mFormSelector.findViewById(mFormSelector.getCheckedRadioButtonId()).getTag(mFormSelector.getCheckedRadioButtonId()));
                        if(mBabyHealthData == null) {
                            babyRequest = new AddBabyHealthData(childId, mTime, mBabyHeight.getText().toString(), mBabyWeight.getText().toString(), mIllnessName.getText().toString(), mIllDate.getText().toString(), mIllnessDay.getText().toString());
                        } else {
                            babyRequest = new EditBabyHealthData(mBabyHealthData.getRelateCode(), childId, mTime, mBabyHeight.getText().toString(), mBabyWeight.getText().toString(), mIllnessName.getText().toString(), mIllDate.getText().toString(), mIllnessDay.getText().toString());
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
                    obtainExtraPhoto(((RespSearchHealthData) pResponse).getReturnMsg().getResultCode());
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
                    if (TextUtils.equals(relateCode, lExtraFile.getDataCode()))
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
        String text = mTime.replace("-", getString(R.string.year));
        text = text.replace("-", getString(R.string.month));
        text += "日 " + TimeUtil.getWeekStr(timestamp);
        return text;
    }

    private String transformString(String value) {
        switch (value) {
            case "normal":
                return "正常";
            case "abnormal":
                return "不正常";
            case "higher":
                return "偏高";
            case "lower":
                return "偏低";
            case "nochk":
                return "未检测";
            case "good":
                return "良好";
            case "ordinary":
                return "一般";
            case "poorer":
                return "较差";
            case "breast_milk":
                return "母乳";
            case "powdered_milk":
                return "奶粉";
            case "have":
                return "有";
            case "not":
                return "无";
            default:
                return value;
        }
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
        if(mBabyTask != null) {
            mBabyTask.cancel(true);
        }
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

    @OnClick(R.id.rl_choose_time)
    private void chooseTime() {
        mTimePicker.setDate(TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD));
        mPickerContainer.setVisibility(View.VISIBLE);
        mChooseIllTimeCommit.setVisibility(View.GONE);
        mChooseTimeCommit.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.rl_choose_ill_time)
    private void chooseIllTime() {
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

}
