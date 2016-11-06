/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: CustomerDialog.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/23.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yijiehl.club.android.R;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: CustomerDialog <br/>
 * 类描述: 自定义对话框<br/>
 * 对视图进行改造 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/23 <br/>
 * @author 谌珂 <br/>
 */
public class CustomerDialog extends Dialog {

    private View contentView;

    public CustomerDialog(Context context, View v) {
//        super(context);
        super(context, R.style.MyDialog);
        contentView = v;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView);
    }
}
