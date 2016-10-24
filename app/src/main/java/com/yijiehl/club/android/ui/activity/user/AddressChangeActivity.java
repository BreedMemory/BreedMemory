/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: AddressChangeActivity.java <br/>
 * Created by 张志新 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.activity.user;/**
 * Created by asus on 2016/9/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.UpdateUserInfo;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: AddressChangeActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_address_change)
public class AddressChangeActivity extends BmActivity implements OnClickListener {

    @ViewInject(R.id.et_address_change)
    private EditText editText;

    private UserInfo mUserInfo;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.address_change);
    }

    @Override
    protected void configHeadRightView() {
        mRightBtn = new IconTextView(this);
        mHeadRightContainer.addView(mRightBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mRightBtn.setText(getString(R.string.save));

        mRightBtn.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(PersonalInfoActivity.USER_INFO);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            Toaster.showShortToast(AddressChangeActivity.this, getString(R.string.please_input_content));
            return;
        }
        UpdateUserInfo info = new UpdateUserInfo(mUserInfo);
        info.setAreaInfo(editText.getText().toString());
        NetHelper.getDataFromNet(this, new ReqBaseDataProc(AddressChangeActivity.this, info), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mUserInfo.setAreaInfo(editText.getText().toString());
                ActivitySvc.saveUserInfoNative(AddressChangeActivity.this, mUserInfo);
                Intent intent = new Intent();
                intent.putExtra(PersonalInfoActivity.USER_INFO, mUserInfo);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}

