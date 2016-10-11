package com.yijiehl.club.android.ui.activity;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.yijiehl.club.android.R;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: AskQuestionActivity<br/>
 * 类描述: <br/>
 * @author 张志新 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_ask_question)
public class AskQuestionActivity extends BmActivity {
    @Override
    protected String getHeadTitle() {
        return getString(R.string.ask_question);
    }
}
