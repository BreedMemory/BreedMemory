/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: SexChangeActivity.java <br/>
 * Created by 张志新 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.activity.user;/**
 * Created by asus on 2016/9/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.base.Sex;
import com.yijiehl.club.android.network.request.dataproc.UpdateUserInfo;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: SexChangeActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_sex_change)
public class SexChangeActivity extends BmActivity {

    @ViewInject(R.id.iv_male_show)
    private ImageView maleShow;

    @ViewInject(R.id.iv_female_show)
    private ImageView femaleShow;

    private UserInfo mUserInfo;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.sex);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo= (UserInfo) getIntent().getSerializableExtra(PersonalInfoActivity.USER_INFO);
        switch (Sex.setValue(mUserInfo.getGenderCode())) {
            case MALE:
                maleShow.setVisibility(View.VISIBLE);
                break;
            case FEMALE:
                femaleShow.setVisibility(View.VISIBLE);
                break;
        }
    }


    @OnClick({R.id.layout_choose_male, R.id.layout_choose_female})
    private void chooseMale(View v){
        final String genderCode;
        switch (v.getId()) {
            case R.id.layout_choose_male:
                genderCode = Sex.MALE.getName();
                break;
            default:
                genderCode = Sex.FEMALE.getName();
                break;
        }
        femaleShow.setVisibility(View.GONE);
        maleShow.setVisibility(View.VISIBLE);
        UpdateUserInfo info = new UpdateUserInfo(mUserInfo);
        info.setGenderCode(genderCode);
        NetHelper.getDataFromNet(this, new ReqBaseDataProc(this, info), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mUserInfo.setGenderCode(genderCode);
                ActivitySvc.saveUserInfoNative(SexChangeActivity.this, mUserInfo);
                finish();
            }
        });
    }
}
