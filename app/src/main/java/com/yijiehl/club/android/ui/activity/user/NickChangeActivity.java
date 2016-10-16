/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: NickChangeActivity.java <br/>
 * Created by 张志新 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.activity.user;/**
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
 * 类  名: NickChangeActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_nickname_change)
public class NickChangeActivity extends BmActivity {

    @ViewInject(R.id.et_nickname_change)
    private EditText editText;

    @ViewInject(R.id.iv_cancel_newnick)
    private ImageView imageView;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.nickname);
    }

    // TODO: 2016/9/18 标题栏的取消和保存功能还没有实现

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(editText.getText())) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_cancel_newnick)
    private void clearNickName() {
        if (!TextUtils.isEmpty(editText.getText())) {
            editText.getText().clear();
        }
    }
}
