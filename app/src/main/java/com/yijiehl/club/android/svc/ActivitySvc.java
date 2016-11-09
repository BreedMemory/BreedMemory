/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ActivitySvc.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.svc;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.ui.activity.CkActivity;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.response.RespLogin;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.ui.activity.ArticleDetailActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.SplashActivity;
import com.yijiehl.club.android.ui.activity.growup.GrowUpGasStationAvtivity;
import com.yijiehl.club.android.ui.activity.growup.NotSignUpGasStationActivity;
import com.yijiehl.club.android.ui.activity.photo.ImageViewerActivity;
import com.yijiehl.club.android.ui.activity.photo.PhotoPickerActivity;
import com.yijiehl.club.android.ui.activity.photo.UploadPhotoActivity;
import com.yijiehl.club.android.ui.activity.user.LoginActivity;
import com.yijiehl.club.android.ui.activity.user.SupplementInfoActivity;

import java.io.File;
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
                // DONE: 谌珂 2016/9/7 跳转到完善信息页面
                Intent intent = new Intent(context, SupplementInfoActivity.class);
                intent.putExtra(UserInfo.class.getName(), data.getCfgParams());
                context.startActivity(intent);
                break;
            default:
                if(context instanceof SplashActivity || context instanceof LoginActivity) {
                    ActivitySvc.startMainActivity(context);
                }
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
        UserInfo userInfo = data.getCfgParams();
        userInfo.setStatus(data.getAccountStatus());
        saveUserInfoNative(context, userInfo);
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
        if(path.startsWith("/")) {
            path = path.substring(1, path.length());
        }
        return "http://" + Common.SERVICE_URL + path;
    }

    /**
     * 描 述：启动照片选择<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param activity Activity
     * @param path 图片路径集合
     */
    public static void startImagePicker(Activity activity, @Nullable ArrayList<String> path) {
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
    public static void startImagePicker(Fragment fragment, @Nullable ArrayList<String> path) {
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
    public static void startImageViewer(Context context, ArrayList<String> path, boolean isNative, int index) {
        if (path == null || path.size() == 0) {
            return;
        }
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(ImageViewerActivity.NATIVE, isNative);
        intent.putStringArrayListExtra(UploadPhotoActivity.PATH, path);
        intent.putExtra(ImageViewerActivity.INDEX, index);
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
    public static void startImageViewer(Context context, ArrayList<String> path, ArrayList<String> codes, ArrayList<String> descs, boolean isNative, int index) {
        if (path == null || path.size() == 0) {
            return;
        }
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(ImageViewerActivity.NATIVE, isNative);
        intent.putStringArrayListExtra(UploadPhotoActivity.PATH, path);
        intent.putStringArrayListExtra(ImageViewerActivity.CODES, codes);
        intent.putStringArrayListExtra(ImageViewerActivity.DESCS, descs);
        intent.putExtra(ImageViewerActivity.INDEX, index);
        context.startActivity(intent);
    }

    /**
     * 描 述：启动加油站<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param context 上下文
     */
    public static void startGasStation(Context context, @Nullable UserInfo userInfo) {
        if(userInfo == null) {
            userInfo = JSON.parseObject(CacheDataDAO.getInstance(null).getCacheData(ContextUtils.getSharedString(context, R.string.shared_preference_user_id),
                    context.getString(R.string.shared_preference_user_info)).getmData(), UserInfo.class);
        }
        switch (userInfo.getStatus()){
            case GENERAL_BEFORE:
            case GENERAL_AFTER:
                Intent intent=new Intent(context,NotSignUpGasStationActivity.class);
                intent.putExtra(ArticleDetailActivity.URL,NotSignUpGasStationActivity.NOT_SIGN_URL);
                context.startActivity(intent);
                break;
            default:
                context.startActivity(new Intent(context, GrowUpGasStationAvtivity.class));
                break;
        }
    }

    /**
     * 描 述：拨打客服电话<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/14 注释 <br/>
     * @param activity CkActivity
     * @param tel 电话号码
     */
    public static void call(CkActivity activity, String tel) {
        activity.checkPromissions(new String[]{Manifest.permission.CALL_PHONE}, new CallCustomer(activity, tel));
    }

    /**
     * 描 述：拨打电话<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/7/13 注释 <br/>
     */
    private static class CallCustomer implements Runnable {
        private CkActivity activity;
        private String tel;

        public CallCustomer(CkActivity activity, String tel) {
            this.activity = activity;
            this.tel = tel;
        }

        @Override
        public void run() {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            activity.startActivity(intent);
        }
    }

    /**
     * 描 述：开启web浏览页面<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/30 <br/>
     * @param context 上下文
     * @param isShareOrCollect 是否需要分享
     * @param url 文章url，不能为空
     * @param name 名称，非分享模式可以为空
     * @param label 标签，非分享模式可以为空
     * @param imageInfo 图片url，非分享模式可以为空
     * @param dataSummery 简介，非分享模式可以为空
     */
    public static void startArticle(Activity context, boolean isShareOrCollect, String url, String name, String label, String imageInfo, String dataSummery) {
        if (!url.startsWith("http")) {
            url = ActivitySvc.createWebUrl(url);
        }
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.URL, url);
        intent.putExtra(ArticleDetailActivity.NAME, name);
        intent.putExtra(ArticleDetailActivity.LABEL, label);
        intent.putExtra(ArticleDetailActivity.IMAGE_INFO, imageInfo);
        intent.putExtra(ArticleDetailActivity.DATA_SUMMERY, dataSummery);
        intent.putExtra(ArticleDetailActivity.SHARE, isShareOrCollect);
        context.startActivityForResult(intent, ArticleDetailActivity.ARTICL_EDETAIL_ACTIVITY);
    }

    /**
     * 描 述：开启web浏览页面<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/30 <br/>
     * @param context 上下文
     * @param isShareOrCollect 是否需要分享
     * @param url 文章url，不能为空
     * @param name 名称，非分享模式可以为空
     * @param label 标签，非分享模式可以为空
     * @param imageInfo 图片url，非分享模式可以为空
     * @param dataSummery 简介，非分享模式可以为空
     */
    public static void startArticle(Activity context, boolean isShareOrCollect, String url, String name, String label, String imageInfo, String dataSummery, boolean isQuestion) {
        if (!url.startsWith("http")) {
            url = ActivitySvc.createWebUrl(url);
        }
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.URL, url);
        intent.putExtra(ArticleDetailActivity.NAME, name);
        intent.putExtra(ArticleDetailActivity.LABEL, label);
        intent.putExtra(ArticleDetailActivity.IMAGE_INFO, imageInfo);
        intent.putExtra(ArticleDetailActivity.DATA_SUMMERY, dataSummery);
        intent.putExtra(ArticleDetailActivity.SHARE, isShareOrCollect);
        intent.putExtra(ArticleDetailActivity.TYPE, isQuestion);
        context.startActivityForResult(intent, ArticleDetailActivity.ARTICL_EDETAIL_ACTIVITY);
    }

    /**
     * 描 述：开启web浏览页面<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/30 <br/>
     * @param context 上下文
     * @param isShareOrCollect 是否需要分享
     * @param url 文章url，不能为空
     * @param name 名称，非分享模式可以为空
     * @param label 标签，非分享模式可以为空
     * @param imageInfo 图片url，非分享模式可以为空
     * @param dataSummery 简介，非分享模式可以为空
     */
    public static void startArticle(Fragment context, boolean isShareOrCollect, String url, String name, String label, String imageInfo, String dataSummery) {
        if(!url.startsWith("http")) {
            url = ActivitySvc.createWebUrl(url);
        }
        Intent intent=new Intent(context.getActivity(),ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.URL, url);
        intent.putExtra(ArticleDetailActivity.NAME, name);
        intent.putExtra(ArticleDetailActivity.LABEL, label);
        intent.putExtra(ArticleDetailActivity.IMAGE_INFO, imageInfo);
        intent.putExtra(ArticleDetailActivity.DATA_SUMMERY, dataSummery);
        intent.putExtra(ArticleDetailActivity.SHARE, isShareOrCollect);
        context.startActivityForResult(intent, ArticleDetailActivity.ARTICL_EDETAIL_ACTIVITY);
    }

    /**
     * 描 述：开启web浏览页面<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/30 <br/>
     * @param context 上下文
     * @param isShareOrCollect 是否需要分享
     * @param url 文章url，不能为空
     * @param name 名称，非分享模式可以为空
     * @param label 标签，非分享模式可以为空
     * @param imageInfo 图片url，非分享模式可以为空
     * @param dataSummery 简介，非分享模式可以为空
     */
    public static void startArticle(Context context, boolean isShareOrCollect, String url, String name, String label, String imageInfo, String dataSummery) {
        if(!url.startsWith("http")) {
            url = ActivitySvc.createWebUrl(url);
        }
        Intent intent=new Intent(context,ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.URL, url);
        intent.putExtra(ArticleDetailActivity.NAME, name);
        intent.putExtra(ArticleDetailActivity.LABEL, label);
        intent.putExtra(ArticleDetailActivity.IMAGE_INFO, imageInfo);
        intent.putExtra(ArticleDetailActivity.DATA_SUMMERY, dataSummery);
        intent.putExtra(ArticleDetailActivity.SHARE, isShareOrCollect);
        context.startActivity(intent);
    }

    public static void installApk(Context ctx,String filePath){
        installApk(ctx, new File(filePath));
    }

    public static void installApk(Context ctx,File apkfile){
        if (!apkfile.exists()) {
            return;
        } else {
            ctx.startActivity(getInstallApkIntent(apkfile));
        }
    }

    public static Intent getInstallApkIntent(String filePath){
        return getInstallApkIntent(new File(filePath));
    }

    public static Intent getInstallApkIntent(File apkfile){
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(android.content.Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        return i;
    }
}
