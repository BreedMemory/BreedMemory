/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PersonalInfoActivity.java <br/>
 * Created by 张志新 on 2016/9/11.  <br/>
 */
package com.yijiehl.club.android.ui.activity.user;/**
 * Created by asus on 2016/9/11.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.ObservableTag;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.entity.UploadPictureMessage;
import com.yijiehl.club.android.network.request.base.Sex;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.UploadPictureSvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.activity.photo.PhotoPickerActivity;
import com.yijiehl.club.android.ui.activity.photo.UploadPhotoActivity;

import java.util.ArrayList;
import java.util.Observable;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PersonalInfoActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/11 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_personal_info)
public class PersonalInfoActivity extends BmActivity {

    public static final String USER_INFO = "USER_INFO";

    public static final int EDIT_USER_INFO = 0;

    /**
     * 昵称
     */
    @ViewInject(R.id.tv_persion_nick)
    private TextView mNick;
    /**
     * 姓名
     */
    @ViewInject(R.id.tv_persion_name)
    private TextView mName;
    /**
     * 性别
     */
    @ViewInject(R.id.tv_persion_sex)
    private TextView mSex;
    /**
     * 手机号
     */
    @ViewInject(R.id.tv_persion_phone)
    private TextView mPhone;
    /**
     * 通讯地址
     */
    @ViewInject(R.id.tv_persion_address)
    private TextView mAddress;
    /**头像*/
    @ViewInject(R.id.iv_persion_head_pic)
    private ImageView mHeadPic;
    /**头像*/
    @ViewInject(R.id.tv_club_phone)
    private TextView mCustomerPhone;

    @SaveInstance
    private UserInfo userInfo;

    private long mTaskId;


    @Override
    protected String getHeadTitle() {
        return getString(R.string.person_info);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String phone = ContextUtils.getSharedString(this, R.string.shared_preference_user_id);
        if (!TextUtils.isEmpty(phone)) {
            mPhone.setText(phone);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(this, R.string.shared_preference_user_id),
                getString(R.string.shared_preference_user_info));
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            fillInfoList(JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class));
        }
    }

    /**
     * 描 述：填充个人信息列表<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/10/23 <br/>
     */
    private void fillInfoList(UserInfo info) {
        userInfo=info;
        Glide.with(this).load(ActivitySvc.createResourceUrl(this, userInfo.getImageInfo())).dontAnimate().placeholder(R.drawable.bg_loading).into(mHeadPic);
        mName.setText(info.getAcctName());
        mNick.setText(info.getShortName());
        mAddress.setText(info.getAreaInfo());
        mCustomerPhone.setText(info.getCustServicePhone());
        switch (Sex.setValue(info.getGenderCode())) {
            case MALE:
                mSex.setText(R.string.male);
                break;
            case FEMALE:
                mSex.setText(R.string.female);
                break;
        }
    }

    @OnClick(R.id.layout_update_headpic)
    private void upDateHeadPic() {
        if(mTaskId != 0) {
            Toaster.showShortToast(this, R.string.uploading_picture);
            return;
        }
        ActivitySvc.startImagePicker(this, null);
    }

    @OnClick(R.id.layout_update_nickname)
    private void upDateNickName() {
        if(userInfo == null) {
            return;
        }
        Intent intent = new Intent(PersonalInfoActivity.this, NickChangeActivity.class);
        intent.putExtra(USER_INFO, userInfo);
        startActivity(intent);
    }

    @OnClick(R.id.layout_update_name)
    private void upDateName() {
        // startActivity(new Intent(PersonalInfoActivity.this,NickChangeActivity.class));
    }

    @OnClick(R.id.layout_update_sex)
    private void upDateSex() {
        if(userInfo == null) {
            return;
        }
        Intent intent=new Intent(PersonalInfoActivity.this, SexChangeActivity.class);
        intent.putExtra(USER_INFO, userInfo);
        startActivity(intent);
    }

    @OnClick(R.id.layout_update_phonenum)
    private void upDatePhoneNum() {
       // startActivity(new Intent(PersonalInfoActivity.this, PhoneNumChangeActivity.class));
    }

    @OnClick(R.id.layout_update_address)
    private void upDatePhoneAddress() {
        if(userInfo == null) {
            return;
        }
        Intent intent=new Intent(PersonalInfoActivity.this, AddressChangeActivity.class);
        intent.putExtra(USER_INFO, userInfo);
        startActivity(intent);
    }

    @OnClick(R.id.layout_update_relaccount)
    private void upDatePhoneRelAccount() {
        startActivity(new Intent(PersonalInfoActivity.this, RelateListActivity.class));
    }

    @OnClick(R.id.tv_club_phone)
    private void callClub() {
        ActivitySvc.call(this, userInfo.getCustServicePhone());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case EDIT_USER_INFO:
                fillInfoList((UserInfo) data.getSerializableExtra(USER_INFO));
                break;
            case PhotoPickerActivity.PHOTO_PICKER_ACTIVITY:
                //获取选择的图片
                ArrayList<String> paths = data.getStringArrayListExtra(UploadPhotoActivity.PATH);
                if(paths == null || paths.size() == 0) {   //选择的图片为空终止
                    return;
                }
                mTaskId = System.currentTimeMillis();
                UploadPictureSvc.getInstance().addObserver(this);
                UploadPictureSvc.getInstance().uploadSinglePicture(this,
                        ReqUploadFile.UploadType.CUSTOMER_PORTRAIT,
                        null,
                        paths.get(0),
                        mTaskId,
                        null);
                break;
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if(observable != UploadPictureSvc.getInstance()) {
            return;
        }
        observable.deleteObserver(this);
        mTaskId = 0;
        Message msg = (Message) data;
        switch (msg.what) {
            case ObservableTag.UPLOAD_SUCCESS:
                UploadPictureMessage pic = (UploadPictureMessage) msg.obj;
                userInfo.setImageInfo(pic.getUrl());
                ActivitySvc.saveUserInfoNative(this, userInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fillInfoList(userInfo);
                    }
                });
                break;
            case ObservableTag.UPLOAD_FAILED:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toaster.showShortToast(PersonalInfoActivity.this, R.string.upload_failed);
                    }
                });
                break;
        }
    }
}
