/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: SplashActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/8.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.ReqSensitize;
import com.yijiehl.club.android.network.response.RespLogin;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.user.LoginActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: SplashActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/8 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activtiy_splash)
public class SplashActivity extends BmActivity {
    @Override
    protected String getHeadTitle() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeader.setVisibility(View.GONE);
        if(TextUtils.isEmpty(ContextUtils.getSharedString(this, R.string.shared_preference_user_id))) {   //本地保存的userid为空，说明用户没有登录过，跳转到登录页面
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {   //走激活接口
            sensitize();
        }
    }

    /**
     * 描 述：激活成功后根据用户状态跳转到相应页面，失败后重新激活<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/8 <br/>
     */
    private void sensitize() {
        NetHelper.getDataFromNet(this, new ReqSensitize(this), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespLogin data = (RespLogin) pResponse;
                ActivitySvc.loginSuccess(SplashActivity.this, data);
                ActivitySvc.saveClientInfoNative(SplashActivity.this, data, null);
                finish();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                // TODO: 谌珂 2016/9/19 激活失败后的处理
//                sensitize();
            }
        }, false);
    }
}
