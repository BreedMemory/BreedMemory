/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ContextUtils.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/3/7.  <br/>
 */
package com.uuzz.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.uuzz.android.R;


/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ContextUtils <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/3/7 <br/>
 * @author 谌珂 <br/>
 */
public class ContextUtils {
    /**
     * 描 述：获取SharedPreferences<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/3/7 注释 <br/>
     * @param context 上下文
     * @return SharedPreferences实例
     */
    public static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(context.getString(R.string.shared_preference_main), Activity.MODE_PRIVATE);
    }

    /**
     * 描 述：获取获取SharedPreferences中保存的字符串<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/3/7 注释 <br/>
     * @param context 上下文
     * @param resId 字符串资源id
     * @return 如果查不到则返回""
     */
    public static String getSharedString(@NonNull Context context, @StringRes int resId) {
        return getSharedPreferences(context).getString(context.getString(resId), "");
    }

    /**
     * 描 述：获取获取SharedPreferences中保存的字符串<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/3/7 注释 <br/>
     * @param context 上下文
     * @param key 字符串key
     * @return 如果查不到则返回""
     */
    public static String getSharedString(@NonNull Context context, String key) {
        SharedPreferences lSharedPreferences = getSharedPreferences(context);
        return lSharedPreferences.getString(key, "");
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }
}
