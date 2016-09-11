/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PersonalInfoActivity.java <br/>
 * Created by 张志新 on 2016/9/11.  <br/>
 */
package com.yijiehl.club.android.ui.activity;/**
 * Created by asus on 2016/9/11.
 */

import android.view.View;
import android.widget.RelativeLayout;

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

    // TODO: 2016/9/11 张志新  此处需要根据需求再写跳转。。。
    @OnClick({R.id.layout_update_headpic, R.id.layout_update_nickname, R.id.layout_update_name
            , R.id.layout_update_sex, R.id.layout_update_phonenum,
            R.id.layout_update_address, R.id.layout_update_relaccount})
    private void upDatePersonalInfo(View v) {
        switch (v.getId()){
            case R.id.layout_update_headpic:
                break;
            case R.id.layout_update_nickname:
                break;
            case R.id.layout_update_name:
                break;
            case R.id.layout_update_sex:
                break;
            case R.id.layout_update_phonenum:
                break;
            case R.id.layout_update_address:
                break;
            case R.id.layout_update_relaccount:
                break;
        }
    }
}
