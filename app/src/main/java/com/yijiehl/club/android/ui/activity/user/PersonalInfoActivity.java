/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PersonalInfoActivity.java <br/>
 * Created by 张志新 on 2016/9/11.  <br/>
 */
package com.yijiehl.club.android.ui.activity.user;/**
 * Created by asus on 2016/9/11.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.activity.photo.PhoneNumChangeActivity;

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

    private UserInfo userInfo;
    private boolean isMale;


    @Override
    protected String getHeadTitle() {
        return getString(R.string.person_info);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(this, R.string.shared_preference_user_id),
                getString(R.string.shared_preference_user_info));

        String phone = ContextUtils.getSharedString(this, R.string.shared_preference_user_id);
        if (!TextUtils.isEmpty(phone)) {
            mPhone.setText(phone);
        }
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
        mName.setText(info.getAcctName());
        mNick.setText(info.getShortName());
        mAddress.setText(info.getAreaInfo());
        String sex=info.getGenderCode();
        if(!TextUtils.isEmpty(sex)&&sex.equals("m")){
            mSex.setText("男");
            isMale=true;
        }else{
            mSex.setText("女");
            isMale=false;
        }
    }

    // TODO: 2016/9/11 张志新  此处需要根据需求再写跳转；没有传值 也没有返回值；
    @OnClick(R.id.layout_update_headpic)
    private void upDateHeadPic() {
        Toaster.showShortToast(this, "暂未实现");
    }

    @OnClick(R.id.layout_update_nickname)
    private void upDateNickName() {
        startActivity(new Intent(PersonalInfoActivity.this, NickChangeActivity.class));
    }

    @OnClick(R.id.layout_update_name)
    private void upDateName() {
        // startActivity(new Intent(PersonalInfoActivity.this,NickChangeActivity.class));
    }

    @OnClick(R.id.layout_update_sex)
    private void upDateSex() {
        Intent intent=new Intent(PersonalInfoActivity.this, SexChangeActivity.class);
        intent.putExtra("user",userInfo);
        intent.putExtra("isMale",isMale);
        startActivity(intent);
    }

    @OnClick(R.id.layout_update_phonenum)
    private void upDatePhoneNum() {
       // startActivity(new Intent(PersonalInfoActivity.this, PhoneNumChangeActivity.class));
    }

    @OnClick(R.id.layout_update_address)
    private void upDatePhoneAddress() {
        startActivity(new Intent(PersonalInfoActivity.this, AddressChangeActivity.class));
    }

    @OnClick(R.id.layout_update_relaccount)
    private void upDatePhoneRelAccount() {
        startActivity(new Intent(PersonalInfoActivity.this, AddRelativesAccount.class));
    }

    @OnClick(R.id.tv_club_phone)
    private void callClub() {
        // TODO: 2016/10/9 拨打会所联系电话功能；相应的权限未加以及未获取
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "01066616662"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
