/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: LoginActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/6.  <br/>
 */
package com.yijiehl.club.android.ui.activity.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.uuzz.android.ui.view.MyButton;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.request.ReqLogin;
import com.yijiehl.club.android.network.request.ReqSendVerifyCode;
import com.yijiehl.club.android.network.response.RespLogin;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: LoginActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/6 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BmActivity {

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case Common.TIME_TASK_IDENTIFYING_CODE:
                    int second = (int) msg.obj;
                    // DONE: 谌珂 2016/9/7 按钮样式及字体颜色变换
                    if(second > 0) {          //验证码间隔时间大于零，修改按钮上的读秒并延时一秒再发送任务
                        mSendVerifyCode.setText(String.format(getString(R.string.resend_identifying_code), second));
                        msg = Message.obtain();
                        msg.what = Common.TIME_TASK_IDENTIFYING_CODE;
                        msg.obj = --second;
                        mHandler.sendMessageDelayed(msg, 1000);
                    } else {                  //验证码倒计时完成，恢复按钮初始状态
                        mSendVerifyCode.setText(R.string.get_identifying_code);
                    }

                    break;
                default:
                    break;
            }
            return false;
        }
    });

    /** 电话号码输入框 */
    @ViewInject(R.id.et_phone_number)
    private EditText mPhoneNumber;
    /** 验证码输入框 */
    @ViewInject(R.id.et_identifying_code)
    private EditText mVerifyCode;
    /** 发送验证码的按钮 */
    @ViewInject(R.id.mb_get_verify_code)
    private MyButton mSendVerifyCode;
    /** 客户端会话标识 */
    private final String CLIET_SECODE = String.valueOf(System.currentTimeMillis());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeader.setVisibility(View.GONE);
        String phone = ContextUtils.getSharedString(this, R.string.shared_preference_user_id);
        if(!TextUtils.isEmpty(phone)) {
            mPhoneNumber.setText(phone);
        }
    }

    @Override
    protected String getHeadTitle() {
        return getString(R.string.login);
    }

    @OnClick(R.id.mb_get_verify_code)
    private void getVerifyCode() {
        if(!TextUtils.equals(mSendVerifyCode.getText(), getString(R.string.get_identifying_code))) {
            return;
        }
        String phoneNumber = mPhoneNumber.getText().toString();
        if(!checkPhoneNumber(phoneNumber)) {
            Toaster.showShortToast(this, getString(R.string.input_correct_phone_number));
            return;
        }
        Message msg = Message.obtain();
        msg.what = Common.TIME_TASK_IDENTIFYING_CODE;
        msg.obj = 60;
        mHandler.sendMessage(msg);

        NetHelper.getDataFromNet(this, new ReqSendVerifyCode(this, CLIET_SECODE, phoneNumber), null, false);
    }

    @OnClick(R.id.mb_login)
    private void login() {
        String phoneNumber = mPhoneNumber.getText().toString();
        String code = mVerifyCode.getText().toString();
        if(!checkPhoneNumber(phoneNumber) || !checkVerifyCode(code)) {
            return;
        }
        NetHelper.getDataFromNet(this, new ReqLogin(this, CLIET_SECODE, phoneNumber, code), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespLogin data = (RespLogin) pResponse;
                ActivitySvc.loginSuccess(LoginActivity.this, data);
                ActivitySvc.saveClientInfoNative(LoginActivity.this, data, mPhoneNumber.getText().toString());
                finish();
            }
        }, false);
    }

    /**
     * 描 述：检测电话号码是否合法<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     * @param phoneNumber 电话号码
     * @return true代表验证通过
     */
    private boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.startsWith("1") && phoneNumber.length() == 11;
    }

    /**
     * 描 述：检测验证码是否合法<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     * @param code 验证码
     * @return true代表验证通过
     */
    private boolean checkVerifyCode(String code) {
        return !TextUtils.isEmpty(code);
    }

    @Override
    protected void checkSensitize() {
    }
}
