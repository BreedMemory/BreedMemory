/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: TestActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/6.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import com.uuzz.android.util.ioc.annotation.ContentView;
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


    @Override
    protected String getHeadTitle() {
        return "测试";
    }
}
