/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: AddRelativesAccount.java <br/>
 * Created by 张志新 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.activity;/**
 * Created by asus on 2016/9/18.
 */

import android.os.Bundle;
import android.widget.EditText;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: AddRelativesAccount <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_add_relatives_account)
public class AddRelativesAccount extends BmActivity {

    @ViewInject(R.id.et_relatives_input)
    private EditText relEditText;

    @ViewInject(R.id.et_name_input)
    private EditText nameEditText;

    @ViewInject(R.id.et_phone_input)
    private EditText phoneEditText;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.relaccount);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 2016/9/18 点击保存记得判断手机号合法以及信息是否完全
    }


    /**
     * 描 述：检测电话号码是否合法<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/9/18 <br/>
     *
     * @param phoneNumber 电话号码
     * @return true代表验证通过
     */
    private boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.startsWith("1") && phoneNumber.length() == 11;
    }
}
