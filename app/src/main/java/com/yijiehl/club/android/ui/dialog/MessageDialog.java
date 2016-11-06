/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: MessageDialog.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/9.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijiehl.club.android.R;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: MessageDialog <br/>
 * 类描述: 提示性对话框<br/>
 * 可设置提醒内容 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/9 <br/>
 * @author 谌珂 <br/>
 */
public class MessageDialog extends BaseDialog {

    private static MessageDialog mMessageDialog;

    /** 消息 */
    private TextView messageView;

    private MessageDialog(Context context) {
        super(context);
    }

    public synchronized static MessageDialog getInstance(Context context) {
        if (mMessageDialog == null || !mMessageDialog.getAd().getContext().equals(context)) {
            mMessageDialog = new MessageDialog(context);
        }
        return mMessageDialog;
    }

    @Override
    protected void setContentView(Context context, LinearLayout container, LayoutInflater inflater) {
        inflater.inflate(R.layout.dialog_text_content_layout, container);
        messageView = (TextView) container.findViewById(R.id.tv_dialog_message);
    }

    /**
     * 描 述：设置消息<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param resId 字符串id
     */
    public MessageDialog setMessage(int resId) {
        messageView.setText(resId);
        return this;
    }
    /**
     * 描 述：设置消息<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/10 注释 <br/>
     * @param message 标题字符串
     */
    public MessageDialog setMessage(String message) {
        messageView.setText(message);
        return this;
    }

    /**
     * 描 述：设置文字对齐方式<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/3/14 注释 <br/>
     * @param gravity 对齐方式
     * @see android.view.Gravity
     */
    public void setMessageGravity(int gravity) {
        messageView.setGravity(gravity);
    }
}
