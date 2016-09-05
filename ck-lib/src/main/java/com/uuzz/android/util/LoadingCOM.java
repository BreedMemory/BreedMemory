package com.uuzz.android.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.uuzz.android.R;


/**
 * Project:Client
 * Class:com.lottery.client.component
 * Description: 加载框组件，适用于网络加载时阻塞窗口
 * Created by lijingwei on 15/8/5
 * Version:1.0.0
 * Update:15/8/5
 */
public class LoadingCOM {
    private static Context mContext;
    private static LoadingCOM ourInstance = new LoadingCOM();
    private Dialog loadingDialog; // Loading窗口
    public static LoadingCOM getInstance(Context pContext) {
        mContext=pContext;
        return ourInstance;
    }

    private LoadingCOM() {
    }

    /**
     * ShowLoadingDialog
     * @param enableManualCancel 是否可以手动关闭
     */
    public void showLoading(boolean enableManualCancel){
        if(loadingDialog != null && loadingDialog.isShowing() && loadingDialog.getContext() == mContext) {
            return;
        } else {
            dismissLoading();
        }
        loadingDialog = new Dialog(mContext, R.style.flat_style_loading_dialog);
        if(!enableManualCancel){ // 禁用取消
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            });
        }
        loadingDialog.setContentView(createLodingView());
        loadingDialog.show();
    }


    /**
     * createLodingView
     * @return
     */
    private View createLodingView(){
        return LayoutInflater.from(mContext).inflate(R.layout.flat_loading_layout, null);
    }

    /**
     * dismissLoading
     */
    public void dismissLoading(){
        if(isLoadingShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * dismissLoading
     */
    public void dismissLoading(DialogInterface.OnDismissListener listener){
        if(isLoadingShowing()) {
            if(listener != null) {
                loadingDialog.setOnDismissListener(listener);
            }
            loadingDialog.dismiss();
        }
    }

    /**
     * isLoadingShowing
     * @return
     */
    public boolean isLoadingShowing() {
        return loadingDialog != null && loadingDialog.isShowing();
    }
}
