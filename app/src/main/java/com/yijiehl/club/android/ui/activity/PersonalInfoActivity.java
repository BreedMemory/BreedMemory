/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PersonalInfoActivity.java <br/>
 * Created by 张志新 on 2016/9/11.  <br/>
 */
package com.yijiehl.club.android.ui.activity;/**
 * Created by asus on 2016/9/11.
 */

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;

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

    @Override
    protected String getHeadTitle() {
        return getString(R.string.person_info);
    }

    // TODO: 2016/9/11 张志新  此处需要根据需求再写跳转；没有传值 也没有返回值；
    @OnClick(R.id.layout_update_headpic)
    private void upDateHeadPic(){
        Toaster.showShortToast(this,"暂未实现");
    }
    @OnClick(R.id.layout_update_nickname)
    private void upDateNickName(){
        startActivity(new Intent(PersonalInfoActivity.this,NickChangeActivity.class));
    }
    @OnClick(R.id.layout_update_name)
    private void upDateName(){
       // startActivity(new Intent(PersonalInfoActivity.this,NickChangeActivity.class));
    }
    @OnClick(R.id.layout_update_sex)
    private void upDateSex(){
        startActivity(new Intent(PersonalInfoActivity.this,SexChangeActivity.class));
    }
    @OnClick(R.id.layout_update_phonenum)
    private void upDatePhoneNum(){
        startActivity(new Intent(PersonalInfoActivity.this,PhoneNumChangeActivity.class));
    }
    @OnClick(R.id.layout_update_address)
    private void upDatePhoneAddress(){
        startActivity(new Intent(PersonalInfoActivity.this,AddressChangeActivity.class));
    }
    @OnClick(R.id.layout_update_relaccount)
    private void upDatePhoneRelAccount(){
        startActivity(new Intent(PersonalInfoActivity.this,AddRelativesAccount.class));
    }
}
