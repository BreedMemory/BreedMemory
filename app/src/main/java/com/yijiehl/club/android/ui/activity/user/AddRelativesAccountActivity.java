/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: AddRelativesAccountActivity.java <br/>
 * Created by 张志新 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.activity.user;/**
 * Created by asus on 2016/9/18.
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.AddRelationAccount;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.view.NumberPickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: AddRelativesAccountActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_add_relatives_account)
public class AddRelativesAccountActivity extends BmActivity {

    @ViewInject(R.id.tv_relatives_input)
    private TextView relEditText;

    @ViewInject(R.id.et_name_input)
    private EditText nameEditText;

    @ViewInject(R.id.et_phone_input)
    private EditText phoneEditText;

    @ViewInject(R.id.npv_relation_ship)
    private NumberPickerView relationShipPicker;
    @ViewInject(R.id.ll_ship_picker_container)
    private View relationShipContainer;

    private int relationIndex;
    List<String> mExtras = new ArrayList<>();

    @Override
    protected String getHeadTitle() {
        return getString(R.string.relaccount);
    }

    @Override
    protected void configHeadRightView() {
        super.configHeadRightView();
        mHeadRightContainer.setVisibility(View.VISIBLE);
        mRightBtn.setModle(IconTextView.MODULE_TEXT);
        mRightBtn.setText(R.string.save);
        mHeadRightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkData()) {
                    return;
                }
                AddRelationAccount req = new AddRelationAccount(nameEditText.getText().toString(), phoneEditText.getText().toString(), TextUtils.equals("0", String.valueOf(relationIndex))?"couple":"kith");
                NetHelper.getDataFromNet(AddRelativesAccountActivity.this, new ReqBaseDataProc(AddRelativesAccountActivity.this, req), new AbstractCallBack(AddRelativesAccountActivity.this) {
                    @Override
                    public void onSuccess(AbstractResponse pResponse) {
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExtras.add(getString(R.string.couple));
        mExtras.add(getString(R.string.kith));
        relationShipPicker.setExtras(mExtras);
        relEditText.setText(mExtras.get(relationIndex));
    }

    @OnClick(R.id.ll_ship_container)
    private void chooseRelationShip() {
        relationShipContainer.setVisibility(View.VISIBLE);
        relationShipPicker.setValue(relationIndex);
    }

    @OnClick(R.id.btn_choose_commit)
    private void saveRelationShip() {
        relationShipContainer.setVisibility(View.GONE);
        relationIndex = relationShipPicker.getValue();
        relEditText.setText(mExtras.get(relationIndex-1));
    }


    /**
     * 描 述：检测电话号码是否合法<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/9/18 <br/>
     *
     * @return true代表验证通过
     */
    private boolean checkData() {
        return (phoneEditText.getText().toString().startsWith("1") && phoneEditText.getText().toString().length() == 11) && !TextUtils.isEmpty(nameEditText.getText().toString());
    }
}
