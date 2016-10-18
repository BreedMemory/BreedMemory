/**
 * 项目名称：手机大管家
 * 文件名称: EditTabActivity.java
 * Created by 谌珂 on 2016/10/15.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: EditTabActivity<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_edit_tab_layout)
public class EditTabActivity extends BmActivity implements View.OnClickListener {

    public static final int ADD_EDIT = 222;
    public static final String TAB = "TAB";

    @ViewInject(R.id.et_tab)
    private EditText mEditText;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.edit_tab);
    }

    @Override
    protected void configHeadRightView() {
        super.configHeadRightView();
        mRightBtn.setModle(IconTextView.MODULE_TEXT);
        mRightBtn.setText(R.string.complete);
        mHeadRightContainer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(mEditText.getText())) {
            finish();
            return;
        }
        // TODO: 谌珂 2016/10/15 上传标签

        Intent intent = new Intent();
        intent.putExtra(TAB, mEditText.getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
