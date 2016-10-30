/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: TestActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/6.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;

import com.uuzz.android.ui.view.LineChatView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;


/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: TestActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/6 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activity_test)
public class TestActivity extends BmActivity {
    @ViewInject(R.id.lcv)
    private LineChatView view;

    @Override
    protected String getHeadTitle() {
        return "测试";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view.setValues(new float[]{5, 8,11,18,2,9,4,7,3,7,8,2,4});
    }
}
