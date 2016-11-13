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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.response.RespCheckVersion;
import com.yijiehl.club.android.network.response.RespSensitize;
import com.yijiehl.club.android.ui.controlversion.AppUpdateWindow;
import com.yijiehl.club.android.ui.controlversion.ReqVersionCheck;
import com.yijiehl.club.android.ui.dialog.MessageDialog;

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

    @ViewInject(R.id.iv_login_bg)
    private ImageView mBackground;

    @Override
    protected String getHeadTitle() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load(R.drawable.login_bg).dontAnimate().into(mBackground);
        mHeader.setVisibility(View.GONE);
        NetHelper.getDataFromNet(this, new ReqVersionCheck(), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespCheckVersion lRespCheckVersion = (RespCheckVersion) pResponse;
                if(lRespCheckVersion.getResultList() == null || lRespCheckVersion.getResultList().size() == 0) {
                    switchActivity();
                    return;
                }
                Intent intent = new Intent(SplashActivity.this, AppUpdateWindow.class);
                intent.putExtra(AppUpdateWindow.VERSION_INFO, lRespCheckVersion.getResultList().get(0));
                startActivityForResult(intent, AppUpdateWindow.APP_UPDATE_WINDOW);
            }

            @Override
            public void onFailed(String msg) {
                // DONE: 谌珂 2016/9/19 激活失败后的处理
                final MessageDialog instance = MessageDialog.getInstance(SplashActivity.this);
                instance.setMessage(R.string.net_error_please_retry);
                instance.showSimpleDialog(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        instance.dismiss();
                        sensitize();
                    }
                });
            }
        });

    }

    @Override
    protected void checkSensitize() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppUpdateWindow.APP_UPDATE_WINDOW) {
            switchActivity();
        }
    }

    @Override
    protected void onSensitizeSuccess(RespSensitize data) {
        if(data.getCfgParams() != null && !TextUtils.isEmpty(data.getCfgParams().getSigninInfo())) {
            Common.isSigned = false;
        }
        finish();
    }
}
