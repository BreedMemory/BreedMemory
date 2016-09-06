/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: LoginActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/6.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity;

import android.widget.EditText;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: LoginActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/6 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BmActivity {

    /** 电话号码输入框 */
    @ViewInject(R.id.et_phone_number)
    private EditText mPhoneNumber;
    /** 验证码输入框 */
    @ViewInject(R.id.et_identifying_code)
    private EditText mIdentifyingCode;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.login);
    }
}
