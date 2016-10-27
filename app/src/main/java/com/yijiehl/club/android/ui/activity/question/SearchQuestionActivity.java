/**
 * 项目名称：手机在线 <br/>
 * 文件名称: SearchQuestionActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/27.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity.question;

import android.os.Bundle;
import android.view.View;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: SearchQuestionActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/27 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activity_search_question_layout)
public class SearchQuestionActivity extends BmActivity {
    @Override
    protected String getHeadTitle() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeader.setVisibility(View.GONE);
    }
}
