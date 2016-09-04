/**
 * 项目名称：工具库
 * 文件名称: AbstractCallBack.java
 * Created by 谌珂 on 2016/1/10.
 */
package com.uuzz.android.util.net.task;

import android.content.Context;

import com.uuzz.android.util.Toaster;

/**
 * 项目名称：工具库<br/>
 * 类  名: AbstractCallBack<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public abstract class AbstractCallBack implements AbstractTask.HttpCallBack {

    private Context mContext;

    public AbstractCallBack(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onFailed(String msg) {
        Toaster.showShortToast(mContext, msg);
    }

    @Override
    public void onCancelled() {}

    @Override
    public void updateProgress(int progress) {}
}