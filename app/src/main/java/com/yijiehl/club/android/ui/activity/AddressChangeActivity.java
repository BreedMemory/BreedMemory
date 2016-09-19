/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: AddressChangeActivity.java <br/>
 * Created by 张志新 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.activity;/**
 * Created by asus on 2016/9/18.
 */

import android.widget.EditText;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: AddressChangeActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_address_change)
public class AddressChangeActivity extends BmActivity {

    @ViewInject(R.id.et_address_change)
    private EditText editText;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.address_change);
    }

}
