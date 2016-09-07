/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: SupplementInfoActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.view.TimePicker;

import java.io.Serializable;

/**
 * 项目名称：手机大管家 <br/>
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
    /** 性别选择 */
    @ViewInject(R.id.tp_choose_date)
    private TimePicker mTimePicker;
    /** 用户信息 */
    @SaveInstance
    private UserInfo mUserInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Serializable serializableExtra = getIntent().getSerializableExtra(UserInfo.class.getName());
        if(serializableExtra != null) {            //从Intent获取用户数据
            mUserInfo = (UserInfo) serializableExtra;
        }

        mName.setText(mUserInfo.getAcctName());
//        mSex.check();
    }

    @Override
    protected String getHeadTitle() {
        return getString(R.string.supplement_info);
    }

    /**
     * 描 述：选择时间<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     */
    @OnClick(R.id.btn_choose_time)
    private void chooseTime() {
        Toaster.showShortToast(this, mTimePicker.getDate());
    }

    /**
     * 描 述：验证信息通过后跳转到主页<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     */
    @OnClick(R.id.btn_commit)
    private void commit() {
        // TODO: 谌珂 2016/9/7 验证信息通过后跳转到主页
        if(checkInfo()) {
            ActivitySvc.startMainActivity(this);
        }
    }

    /**
     * 描 述：验证信息是否通过<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     * @return true为通过
     */
    private boolean checkInfo() {
        // TODO: 谌珂 2016/9/7 验证
        return false;
    }
}
