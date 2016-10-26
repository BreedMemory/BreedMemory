/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PhoneNumChangeActivity.java <br/>
 * Created by 张志新 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.activity.photo;/**
 * Created by asus on 2016/9/18.
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PhoneNumChangeActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_phone_num_change)
public class PhoneNumChangeActivity extends BmActivity {

    @ViewInject(R.id.et_phone_change)
    private EditText editText;
    @ViewInject(R.id.iv_cancel_phone)
    private ImageView imageView;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.phone_num);
    }

    // DONE: 2016/9/18 标题栏的取消和保存功能还没有实现;布局大小也是暂时的；地址，昵称类似问题需要解决


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(editText.getText())) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_cancel_phone)
    private void clearPhoneNum() {
        if (!TextUtils.isEmpty(editText.getText())) {
            editText.getText().clear();
        }
    }

    /**
     * 描 述：检测电话号码是否合法<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/9/18 <br/>
     *
     * @param phoneNumber 电话号码
     * @return true代表验证通过
     */
    private boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.startsWith("1") && phoneNumber.length() == 11;
    }
}
