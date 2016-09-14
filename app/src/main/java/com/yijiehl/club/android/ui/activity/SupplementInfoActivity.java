/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: SupplementInfoActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.Utils;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.ReqSearchClub;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.base.Sex;
import com.yijiehl.club.android.network.request.dataproc.UpdateUserInfo;
import com.yijiehl.club.android.network.response.RespSearchClubs;
import com.yijiehl.club.android.network.response.innerentity.ClubInfo;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.view.NumberPickerView;
import com.yijiehl.club.android.ui.view.TimePicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: SupplementInfoActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activity_supplement_info)
public class SupplementInfoActivity extends BmActivity {
    /** 姓名编辑框 */
    @ViewInject(R.id.et_name)
    private EditText mName;
    /** 性别选择 */
    @ViewInject(R.id.rg_sex)
    private RadioGroup mSex;
    /** 选择会所 */
    @ViewInject(R.id.tv_club)
    private TextView mClub;
    /** 选择预产期 */
    @ViewInject(R.id.tv_choose_bron_time)
    private TextView mChooseBronTime;
    /** 预产期选择按钮 */
    @ViewInject(R.id.ll_time_picker_container)
    private View mPickerContainer;
    /** 预产期选择控件 */
    @ViewInject(R.id.tp_choose_date)
    private TimePicker mTimePicker;
    /** 预产期选择控件 */
    @ViewInject(R.id.np_club_picker)
    private NumberPickerView mClubPicker;
    /** 蒙版 */
    @ViewInject(R.id.v_masking)
    private View mMasking;
    /** 用户信息 */
    @SaveInstance
    private UserInfo mUserInfo;
    /** 会所信息 */
    private List<ClubInfo> mClubInfos;
    /** 如果会所信息为空代表网络请求还未完成，点击选择会所时记录当前时间，当接口返回数据后跟此时间差距不大于1秒时弹出会所选择 */
    private long mChooseClubTimeStamp;
    /** 已选择的会所索引，初始值为-1 */
    private int mClubIndex = -1;
    private AsyncTask mTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取会所列表数据
        mTask = getClubInfo();
        //从Intent获取用户数据
        Serializable serializableExtra = getIntent().getSerializableExtra(UserInfo.class.getName());
        if(serializableExtra != null) {
            mUserInfo = (UserInfo) serializableExtra;
        }
        //设置名称
        mName.setText(mUserInfo.getAcctName());
        //设置性别
        mSex.check(mUserInfo.isMale()?R.id.rb_male : R.id.rb_female);
        // TODO: 谌珂 2016/9/8 设置预产期
//        mChooseBronTime.setText();
        //设置会所信息
        if(!TextUtils.isEmpty(mUserInfo.getOrgInfo())) {
            mClub.setText(mUserInfo.getOrgInfo());
        }
    }

    @Override
    protected String getHeadTitle() {
        return getString(R.string.supplement_info);
    }

    /**
     * 描 述：获取已选中的性别<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/14 <br/>
     */
    private Sex getSex() {
        switch (mSex.getCheckedRadioButtonId()) {
            case R.id.rb_male:
                return Sex.MALE;
            case R.id.rb_female:
                return Sex.FEMALE;
            default:
                return Sex.UNKONW;
        }
    }

    /**
     * 描 述：请求接口获取会所信息，如果获取失败则无线请求直到成功<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/13 <br/>
     */
    private AsyncTask getClubInfo() {
        return NetHelper.getDataFromNet(this, new ReqSearchClub(this), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mClubInfos = ((RespSearchClubs)pResponse).getResultList();
                //生成会所名称列表
                List<String> lClubNames = new ArrayList<>();
                for (int i = 0; i < mClubInfos.size(); i++) {
                    lClubNames.add(mClubInfos.get(i).getDataName());
                    if(TextUtils.equals(mClub.getText().toString(), mClubInfos.get(i).getDataName())) {
                        mClubIndex = i;
                    }
                }
                mClubPicker.setExtras(lClubNames);
                //判断点击时间是否应该弹出选择框
                if(System.currentTimeMillis() - mChooseClubTimeStamp < 1000) {
                    chooseClub();
                }
            }
        });
    }

    /**
     * 描 述：点击蒙版层隐藏时间和会所选择<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/12 <br/>
     */
    @OnClick(R.id.v_masking)
    private void dismissMarking() {
        mPickerContainer.setVisibility(View.GONE);
        mMasking.setVisibility(View.GONE);
    }

    /**
     * 描 述：选择会所<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     */
    @OnClick(R.id.tv_club)
    private void chooseClub() {
        if(mClubInfos == null || mClubInfos.size() == 0) {
            Toaster.showShortToast(this, getString(R.string.search_club_info));
            mChooseClubTimeStamp = System.currentTimeMillis();
            if(mTask.getStatus() != AsyncTask.Status.FINISHED) {
                mTask.cancel(true);
            }
            mTask = getClubInfo();
            return;
        }

        if(!TextUtils.isEmpty(mChooseBronTime.getText())) {
            mClubPicker.scrollToValue(mClubIndex);
        }
        mPickerContainer.setVisibility(View.VISIBLE);
        mMasking.setVisibility(View.VISIBLE);
        mTimePicker.setVisibility(View.GONE);
        mClubPicker.setVisibility(View.VISIBLE);
        Utils.hideKeyBoard(mMasking);
    }

    /**
     * 描 述：确认时间和会所<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/12 <br/>
     */
    @OnClick(R.id.btn_choose_commit)
    private void commitPick() {
        if(mTimePicker.getVisibility() == View.VISIBLE) {
            mChooseBronTime.setText(mTimePicker.getDate());    //赋值日期选择
        }
        if(mClubPicker.getVisibility() == View.VISIBLE) {
            mClubIndex = mClubPicker.getValue() - 1;           //记录会所索引
            mClub.setText(mClubInfos.get(mClubIndex).getDataName());             //赋值会所选择
        }
        mPickerContainer.setVisibility(View.GONE);
        mMasking.setVisibility(View.GONE);
    }

    /**
     * 描 述：选择时间<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     */
    @OnClick(R.id.tv_choose_bron_time)
    private void chooseTime() {
        if(!TextUtils.isEmpty(mChooseBronTime.getText())) {
            mTimePicker.setDate(mChooseBronTime.getText().toString());
        }
        mPickerContainer.setVisibility(View.VISIBLE);
        mMasking.setVisibility(View.VISIBLE);
        mTimePicker.setVisibility(View.VISIBLE);
        mClubPicker.setVisibility(View.GONE);
        Utils.hideKeyBoard(mMasking);
    }

    /**
     * 描 述：验证信息通过后跳转到主页<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     */
    @OnClick(R.id.btn_commit)
    private void commit() {
        // DONE: 谌珂 2016/9/7 验证信息通过后跳转到主页
        if(!checkInfo()) {
            return;
        }
        final UpdateUserInfo info = new UpdateUserInfo(mName.getText().toString(), getSex().getName(), mUserInfo.getMobileNum(), mClubInfos.get(mClubIndex).getDataId(), mChooseBronTime.getText().toString());
        NetHelper.getDataFromNet(this, new ReqBaseDataProc(this, info), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mUserInfo.setAcctName(info.getDataName());
                mUserInfo.setGenderCode(info.getGenderCode());
                mUserInfo.setOrgId(String.valueOf(info.getOrgId()));
                mUserInfo.setOrgInfo(mClubInfos.get(mClubIndex).getDataName());
                mUserInfo.setBirthday(info.getBirthdate());
                ActivitySvc.saveUserInfoNative(SupplementInfoActivity.this, mUserInfo);
                ActivitySvc.startMainActivity(SupplementInfoActivity.this);
                finish();
            }
        });
    }

    /**
     * 描 述：验证信息是否通过<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     * @return true为通过
     */
    private boolean checkInfo() {
        if(TextUtils.isEmpty(mName.getText().toString())) {
            return false;
        }
        return true;
    }
}
