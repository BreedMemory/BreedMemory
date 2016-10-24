package com.yijiehl.club.android.ui.activity.user;

import android.os.Bundle;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: MyMessageActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/22 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_my_message)
public class MyMessageActivity extends BmActivity{
    @Override
    protected String getHeadTitle() {
        return getString(R.string.my_message);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
