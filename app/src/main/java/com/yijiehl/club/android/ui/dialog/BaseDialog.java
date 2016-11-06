/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: BaseDialog.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/23.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.dialog;

import android.app.ActivityManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzz.android.ui.view.MyButton;
import com.uuzz.android.util.ScreenTools;
import com.yijiehl.club.android.R;

import java.util.List;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: BaseDialog <br/>
 * 类描述: 自定义对话框辅助类<br/>
 * 1.封装了单按钮和双按钮的弹出方式 <br/>
 * 2.封装了按钮点击的回调 <br/>
 * 3.封装了基础视图层 <br/>
 * 4.提供下层接口以填充对话框内容 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/23 <br/>
 * @author 谌珂 <br/>
 */
public abstract class BaseDialog {
    private Context context;
    private CustomerDialog ad;
    /** 标题 */
    private TextView titleView;
    /** 单按钮 */
    private MyButton mSingleBtn;
    /** 双按钮的外层布局 */
    private LinearLayout buttonLayout;
    /** 左侧按钮 */
    private MyButton btnLeft;
    /** 右侧按钮 */
    private MyButton btnRight;

    public BaseDialog(Context context) {
        this.context=context;
        LayoutInflater inflater = LayoutInflater.from(context);
        /** 对话框整体布局 */
        LinearLayout mContentView = (LinearLayout) inflater.inflate(R.layout.dialog_layout, null);
        ad = new CustomerDialog(context, mContentView);
        titleView = (TextView)mContentView.findViewById(R.id.tv_dialog_title);
        /** 消息 */
        LinearLayout contentLayout = (LinearLayout)mContentView.findViewById(R.id.ll_dialog_content);
        mSingleBtn = (MyButton) mContentView.findViewById(R.id.bt_signle_btn);
        buttonLayout = (LinearLayout) mContentView.findViewById(R.id.ll_double_btn_layout);
        btnLeft = (MyButton) mContentView.findViewById(R.id.bt_left_btn);
        btnRight = (MyButton) mContentView.findViewById(R.id.bt_right_btn);
        setContentView(context, contentLayout, inflater);
    }

    public CustomerDialog getAd() {
        return ad;
    }

    /**
     * 描 述：设置对话框内容布局<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/23 注释 <br/>
     * @param container 布局容器
     * @param inflater LayoutInflater
     */
    protected abstract void setContentView(Context context, LinearLayout container, LayoutInflater inflater);

    private void show() {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (int i = 0; i < services.size(); i++) {
            if(services.get(i).topActivity.toString().contains(context.getClass().getName())) {
                ad.show();
                //设置对话框宽度
                Window window = ad.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = (int) (ScreenTools.getScreenPixel(context)[0]*0.79);
                window.setAttributes(lp);
                ad.setCancelable(false);
            }
        }
    }

    /**
     * 描 述：设置标题<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param resId 字符串id
     */
    public void setTitle(int resId) {
        titleView.setText(resId);
    }

    /**
     * 描 述：设置标题<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param title 标题字符串
     */
    public void setTitle(String title) {
        titleView.setText(title);
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        if(ad != null && ad.isShowing()){
            ad.dismiss();
        }
    }

    /**
     * 描 述：初始化单按钮对话框<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param listener 按钮点击回调
     */
    private void initSignleBtn(View.OnClickListener listener) {
        mSingleBtn.setOnClickListener(listener);
        mSingleBtn.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.GONE);
        if(ad.isShowing()) {
            ad.dismiss();
        }
        show();
    }

    /**
     * 描 述：弹出单按钮对话框<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param btnContent 按钮文字内容
     * @param listener 按钮点击回调
     */
    public void showSimpleDialog(String btnContent, View.OnClickListener listener) {
        mSingleBtn.setText(btnContent);
        initSignleBtn(listener);
    }

    /**
     * 描 述：弹出单按钮对话框<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param resId 按钮文字内容id
     * @param listener 按钮点击回调
     */
    public void showSimpleDialog(int resId, View.OnClickListener listener) {
        mSingleBtn.setText(resId);
        initSignleBtn(listener);
    }

    /**
     * 描 述：弹出单按钮对话框<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     */
    public void showSimpleDialog() {
        showSimpleDialog(R.string.commit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 描 述：初始化双按钮对话框<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param listener 左右按钮点击的回调
     */
    private void initDoubleBtnDialog(final OnBtnsClickListener listener) {
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLeftClickListener(v, BaseDialog.this);
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRightClickListener(v, BaseDialog.this);
            }
        });
        mSingleBtn.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.VISIBLE);
        ad.dismiss();
        show();
    }

    /**
     * 描 述：弹出双按钮对话框<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param leftBtnContent 左侧按钮文字内容
     * @param rightBtnContent 右侧按钮文字内容
     * @param listener 点击按钮回调
     */
    public void showDoubleBtnDialog(String leftBtnContent, String rightBtnContent,
                                    OnBtnsClickListener listener) {
        btnLeft.setText(leftBtnContent);
        btnRight.setText(rightBtnContent);
        initDoubleBtnDialog(listener);
    }

    /**
     * 描 述：弹出双按钮对话框<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param leftResId 左侧按钮文字内容
     * @param rightResId 右侧按钮文字内容
     * @param listener 点击按钮回调
     */
    public void showDoubleBtnDialog(int leftResId, int rightResId,
                                    OnBtnsClickListener listener) {
        btnLeft.setText(leftResId);
        btnRight.setText(rightResId);
        initDoubleBtnDialog(listener);
    }

    /**
     * 描 述：弹出双按钮对话框,默认左边取消，右边确认<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param listener 点击按钮回调
     */
    public void showDoubleBtnDialog(OnBtnsClickListener listener) {
        showDoubleBtnDialog(R.string.cancel, R.string.commit, listener);
    }

    public interface OnBtnsClickListener {
        // DONE: 谌珂 2016/2/22 参数里面加上对话框
        void onLeftClickListener(View v, BaseDialog dialog);
        void onRightClickListener(View v, BaseDialog dialog);
    }

}
