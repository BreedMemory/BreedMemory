/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: AddRelativesAccountActivity.java <br/>
 * Created by 张志新 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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

    public static final String  RELATIONCODE  = "relationCode";
    public static final String  DATANAME  = "dataName";
    public static final String  MOBILENUM  = "mobileNum";
    public static final String  DATACODE  = "dataCode";
    public static final String  AUTH1  = "accessAuth1";
    public static final String  AUTH2  = "accessAuth2";
    public static final String  AUTH5  = "accessAuth5";
    public static final String  AUTH6  = "accessAuth6";
    public static final String  MODE  = "mode";

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

    @ViewInject(R.id.switch_mother_data)
    private Switch motherSwitch;

    @ViewInject(R.id.switch_baby_data)
    private Switch babySwitch;

    @ViewInject(R.id.switch_my_answer_data)
    private Switch myAnswerSwitch;

    @ViewInject(R.id.switch_photo_data)
    private Switch photoSwitch;

    private int motherFlag;
    private int babyFlag;
    private int answerFlag;
    private int photoFlag;

    private boolean isChange;
    private String relation;
    private String name;
    private String phoneNum;
    private String dataCode;

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
                AddRelationAccount req = new AddRelationAccount(nameEditText.getText().toString(), phoneEditText.getText().toString(), TextUtils.equals("0", String.valueOf(relationIndex))?"couple":"kith",String.valueOf(motherFlag),String.valueOf(babyFlag),String.valueOf(answerFlag),String.valueOf(photoFlag),isChange,dataCode);
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

        motherFlag = 1;
        babyFlag = 1;
        answerFlag = 1;
        photoFlag = 1;

        isChange = getIntent().getBooleanExtra(AddRelativesAccountActivity.MODE,false);

        if(isChange){
            relation = getIntent().getStringExtra(AddRelativesAccountActivity.RELATIONCODE);
            name = getIntent().getStringExtra(AddRelativesAccountActivity.DATANAME);
            phoneNum = getIntent().getStringExtra(AddRelativesAccountActivity.MOBILENUM);
            motherFlag = Integer.valueOf(getIntent().getStringExtra(AddRelativesAccountActivity.AUTH1));
            babyFlag = Integer.valueOf(getIntent().getStringExtra(AddRelativesAccountActivity.AUTH2));
            answerFlag = Integer.valueOf(getIntent().getStringExtra(AddRelativesAccountActivity.AUTH5));
            photoFlag = Integer.valueOf(getIntent().getStringExtra(AddRelativesAccountActivity.AUTH6));
            dataCode = getIntent().getStringExtra(AddRelativesAccountActivity.DATACODE);

            relEditText.setText(relation);
            nameEditText.setText(name);
            phoneEditText.setText(phoneNum);
        }

        motherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    motherFlag = 1;
                }else{
                    motherFlag = 0;
                }
            }
        });

        babySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    babyFlag = 1;
                }else{
                    babyFlag = 0;
                }
            }
        });

        myAnswerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    answerFlag = 1;
                }else{
                    answerFlag = 0;
                }
            }
        });

        photoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    photoFlag = 1;
                }else{
                    photoFlag = 0;
                }
            }
        });
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
