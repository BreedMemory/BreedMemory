/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ActivitySvc.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.svc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.ContextUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.RespLogin;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.ui.activity.LoginActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.SupplementInfoActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ActivitySvc <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
public class ActivitySvc {

    /**
     * 描 述：跳转到主页<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     */
    public static void startMainActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
    /**
     * 描 述：跳转到登录页<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     */
    public static void startLoginActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    /**
     * 描 述：根据状态跳转到不同的页面<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/8 <br/>
     * @param context 上下文
     * @param data 登录或激活后的数据
     */
    public static void loginSuccess(Context context, RespLogin data) {
        switch (data.getAccountStatus()) {
            case INIT:
            case GENERAL:
                // DONE: 谌珂 2016/9/7 跳转到完善信息页面
                Intent intent = new Intent(context, SupplementInfoActivity.class);
                intent.putExtra(UserInfo.class.getName(), data.getCfgParams());
                context.startActivity(intent);
                break;
            default:
                ActivitySvc.startMainActivity(context);
                break;
        }
    }

    /**
     * 描 述：保存登录返回数据到本地<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/8 <br/>
     * @param context 上下文
     * @param data 登录或激活后的数据
     * @param phoneNumber 用户电话号码，被用来标识userid  为空时不会覆盖原有的userid
     */
    public static void saveClientInfoNative(Context context, RespLogin data, @Nullable String phoneNumber) {
        // DONE: 谌珂 2016/9/7 保存基本参数
        SharedPreferences.Editor editor = ContextUtils.getEditor(context);
        editor.putString(context.getString(R.string.shared_preference_uccode), data.getUccode());
        editor.putString(context.getString(R.string.shared_preference_ucid), data.getUcid());
        editor.putString(context.getString(R.string.shared_preference_secode), data.getSecode());
        editor.putString(context.getString(R.string.shared_preference_acctStatus), data.getAcctStatus());
        editor.putString(context.getString(R.string.shared_preference_msgUrl), data.getMsgUrl());
        editor.putString(context.getString(R.string.shared_preference_resourceUrl), data.getResourceUrl());
        if(!TextUtils.isEmpty(phoneNumber)) {
            editor.putString(context.getString(R.string.shared_preference_user_id), phoneNumber);
        }
        editor.commit();
        saveUserInfoNative(context, data.getCfgParams());
    }

    /**
     * 描 述：保存用户信息数据到本地<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/8 <br/>
     * @param context 上下文
     * @param data 用户信息
     */
    public static void saveUserInfoNative(Context context, UserInfo data) {
        SharedPreferences.Editor editor = ContextUtils.getEditor(context);
        editor.putString(context.getString(R.string.shared_preference_user_info), JSON.toJSONString(data));
        editor.commit();
    }

    /**
     * 描 述：拼接资源url路径<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/4 <br/>
     */
    public static String createResourceUrl(Context context, String path) {
        return ContextUtils.getSharedString(context, R.string.shared_preference_resourceUrl) + path;
    }
}
