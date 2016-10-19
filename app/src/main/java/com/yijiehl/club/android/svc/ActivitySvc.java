/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ActivitySvc.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.svc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.response.RespLogin;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.photo.ImageViewerActivity;
import com.yijiehl.club.android.ui.activity.photo.PhotoPickerActivity;
import com.yijiehl.club.android.ui.activity.photo.UploadPhotoActivity;
import com.yijiehl.club.android.ui.activity.user.LoginActivity;
import com.yijiehl.club.android.ui.activity.user.SupplementInfoActivity;

import java.util.ArrayList;

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
     * 描 述：清空本地数据，保留登录手机号<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/8 <br/>
     * @param context 上下文
     */
    public static void clearClientInfoNative(Context context) {
        // DONE: 谌珂 2016/9/7 保存基本参数
        SharedPreferences.Editor editor = ContextUtils.getEditor(context);
        editor.remove(context.getString(R.string.shared_preference_uccode));
        editor.remove(context.getString(R.string.shared_preference_ucid));
        editor.remove(context.getString(R.string.shared_preference_secode));
        editor.remove(context.getString(R.string.shared_preference_acctStatus));
        editor.remove(context.getString(R.string.shared_preference_msgUrl));
        editor.remove(context.getString(R.string.shared_preference_resourceUrl));
        // DONE: 谌珂 2016/10/15 从数据库删除userinfo
        CacheDataDAO.getInstance(null).delete("c_name = ?", new String[]{context.getString(R.string.shared_preference_user_info)});
        editor.commit();
    }

    /**
     * 描 述：保存用户信息数据到本地<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/8 <br/>
     * @param context 上下文
     * @param data 用户信息
     */
    public static void saveUserInfoNative(Context context, UserInfo data) {
        // DONE: 谌珂 2016/10/15 替换为存储到数据库
        CacheDataDAO.getInstance(null).insertCacheDate(context, context.getString(R.string.shared_preference_user_info), JSON.toJSONString(data));
    }

    /**
     * 描 述：拼接资源url路径<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/4 <br/>
     */
    public static String createResourceUrl(Context context, String path) {
        return ContextUtils.getSharedString(context, R.string.shared_preference_resourceUrl) + path;
    }

    /**
     * 描 述：拼接资源url路径<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/4 <br/>
     */
    public static String createWebUrl(String path) {
        return Common.SERVICE_URL + path;
    }

    /**
     * 描 述：启动照片选择<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param activity Activity
     * @param path 图片路径集合
     */
    public static void startImagePicker(Activity activity, ArrayList<String> path) {
        Intent intent = new Intent(activity, PhotoPickerActivity.class);
        intent.putStringArrayListExtra(UploadPhotoActivity.PATH, path);
        activity.startActivityForResult(intent, PhotoPickerActivity.PHOTO_PICKER_ACTIVITY);
    }

    /**
     * 描 述：启动照片选择<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param fragment Fragment
     * @param path 图片路径集合
     */
    public static void startImagePicker(Fragment fragment, ArrayList<String> path) {
        Intent intent = new Intent(fragment.getActivity(), PhotoPickerActivity.class);
        intent.putStringArrayListExtra(UploadPhotoActivity.PATH, path);
        fragment.startActivityForResult(intent, PhotoPickerActivity.PHOTO_PICKER_ACTIVITY);
    }

    /**
     * 描 述：启动照片上传<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param context 上下文
     * @param path 图片路径集合
     */
    public static void startUploadPhoto(Context context, ArrayList<String> path, long taskId) {
        Intent intent = new Intent(context, UploadPhotoActivity.class);
        intent.putStringArrayListExtra(UploadPhotoActivity.PATH, path);
        intent.putExtra(UploadPhotoActivity.TASK, taskId);
        context.startActivity(intent);
    }

    /**
     * 描 述：启动图片预览<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param context 上下文
     * @param path 图片路径集合
     * @param isNative 是否是本地图片
     */
    public static void startImageViewer(Context context, ArrayList<String> path, boolean isNative) {
        if (path == null || path.size() == 0) {
            return;
        }
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(ImageViewerActivity.NATIVE, false);
        intent.putStringArrayListExtra(UploadPhotoActivity.PATH, path);
        context.startActivity(intent);
    }
}
