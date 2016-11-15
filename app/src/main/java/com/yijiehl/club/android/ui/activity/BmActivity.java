/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: BmActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/1.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.uuzz.android.ui.activity.CkActivity;
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.ObservableTag;
import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.request.ReqSensitize;
import com.yijiehl.club.android.network.response.RespSensitize;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.user.LoginActivity;
import com.yijiehl.club.android.ui.dialog.MessageDialog;

import java.util.Observable;
import java.util.Observer;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: BmActivity <br/>
 * 类描述: https://www.processon.com/view/link/57cccb4de4b0942d7a360ae6<br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/1 <br/>
 * @author 谌珂 <br/>
 */
public abstract class BmActivity extends CkActivity implements Observer {

    /** 标题栏左侧按钮 */
    protected IconTextView mLeftBtn;
    /** 标题栏右侧按钮 */
    protected IconTextView mRightBtn;

    public IconTextView getmLeftBtn() {
        return mLeftBtn;
    }

    public IconTextView getmRightBtn() {
        return mRightBtn;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认根布局背景色
        getmRootLayout().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mHeader.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.primary_text_size));
        CacheDataDAO.getInstance(this).addObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSensitize();
    }

    /**
     * 描 述：检查是否需要重新激活<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/4 <br/>
     */
    protected void checkSensitize() {
        if(!isSigned()) {
            switchActivity();
        } else if(!Common.isSigned) {
            sign();
        }
    }

    /**
     * 描 述：返回今天是否已经签到，如果未签到则保存当前时间戳到静态文件<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/3 <br/>
     */
    private boolean isSigned () {
        if(Common.isSensitize) {
            return true;
        }
        long todayTime = System.currentTimeMillis();
        String today = TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD);
        String savedTime = ContextUtils.getSharedString(this, R.string.shared_preference_sign_time);
        if(TextUtils.isEmpty(savedTime)) {
            SharedPreferences.Editor editor = ContextUtils.getEditor(this);
            editor.putString(getString(R.string.shared_preference_sign_time), String.valueOf(todayTime));
            editor.commit();
            return false;
        }
        long signTime = Long.valueOf(ContextUtils.getSharedString(this, R.string.shared_preference_sign_time));
        String signedDate = TimeUtil.getTime(signTime, TimeUtil.DEFAULT_FORMAT_YYYYMMDD);
        boolean result = TextUtils.equals(signedDate, today);
        if(!result) {
            SharedPreferences.Editor editor = ContextUtils.getEditor(this);
            editor.putString(getString(R.string.shared_preference_sign_time), String.valueOf(todayTime));
            editor.commit();
        }
        Common.isSensitize = result;
        return result;
    }

    /**
     * 描 述：判定激活还是登录<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/4 <br/>
     */
    protected void switchActivity() {
        if(TextUtils.isEmpty(ContextUtils.getSharedString(BmActivity.this, R.string.shared_preference_user_id))) {   //本地保存的userid为空，说明用户没有登录过，跳转到登录页面
            startActivity(new Intent(BmActivity.this, LoginActivity.class));
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
    protected void sensitize() {
        ReqSensitize req = new ReqSensitize(this);
        if(TextUtils.isEmpty(req.getUcid()) || TextUtils.isEmpty(req.getSecode())){
            ActivitySvc.startLoginActivity(this);
            finish();
            return;
        }
        NetHelper.getDataFromNet(this, req, new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSensitize data = (RespSensitize) pResponse;
                ActivitySvc.saveClientInfoNative(BmActivity.this, data, null);
                ActivitySvc.loginSuccess(BmActivity.this, data);
                Common.isSensitize = true;
                onSensitizeSuccess(data);
            }

            @Override
            public void onFailed(String msg) {
                // DONE: 谌珂 2016/9/19 激活失败后的处理
                final MessageDialog instance = MessageDialog.getInstance(BmActivity.this);
                instance.setMessage(R.string.net_error_please_retry);
                instance.showSimpleDialog(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        instance.dismiss();
                        sensitize();
                    }
                });
            }
        }, false);
    }

    /**
     * 描 述：激活成功后回调<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/4 <br/>
     */
    protected void onSensitizeSuccess(RespSensitize data) {
        if(data.getCfgParams() != null && !TextUtils.isEmpty(data.getCfgParams().getSigninInfo())) {
            sign();
        }

    }

    /**
     * 描 述：弹出签到页面<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/4 <br/>
     */
    protected void sign() {
        Common.isSigned = true;
        AlertDialog alertDialog;
        View mAlertLayout = LayoutInflater.from(this).inflate(R.layout.sign_dialog, null);
        alertDialog = new AlertDialog.Builder(this).setView(mAlertLayout).create();
        alertDialog.getWindow().setBackgroundDrawable((new ColorDrawable()));
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        CacheDataDAO.getInstance(this).deleteObserver(this);
        super.onDestroy();
    }

    @Override
    protected void configHeadLeftView() {
        mHeadLeftContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));
        mLeftBtn = new IconTextView(this);
        mHeadLeftContainer.addView(mLeftBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLeftBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mLeftBtn.setText(getString(R.string.icon_return));

        mHeadLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void configHeadRightView() {
        mHeadRightContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));
        mHeadRightContainer.setVisibility(View.GONE);
        mRightBtn = new IconTextView(this);
        mHeadRightContainer.addView(mRightBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mRightBtn.setText(getString(R.string.icon_return));
    }

    @Override
    public void update(Observable observable, Object data) {
        if(observable instanceof CacheDataDAO) {
            final Message msg = (Message) data;
            switch (msg.what) {
                case ObservableTag.CACHE_DATA:                               //数据缓存被成功取到
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onReceiveCacheData((CacheDataEntity) msg.obj);
                        }
                    });
                    break;
            }
        }
    }

    /**
     * 描 述：当缓存数据被取出时调用<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/1 <br/>
     */
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {}

    @Override
    protected void onStop() {
        super.onStop();
        Glide.get(this).clearMemory();
    }
}
