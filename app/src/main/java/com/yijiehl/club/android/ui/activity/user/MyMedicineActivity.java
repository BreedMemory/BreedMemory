package com.yijiehl.club.android.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: MyMedicineActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/22 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_my_medicine)
public class MyMedicineActivity extends BmActivity implements View.OnClickListener {

    /**无数据显示*/
    @ViewInject(R.id.tv_no_remind)
    private TextView mNoRemind;
    /**疫苗提醒列表*/
    @ViewInject(R.id.lv_medicine)
    private ListView mLvMedicine;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.vaccine);
    }

    @Override
    protected void configHeadRightView() {
        mRightBtn = new IconTextView(this);
        mHeadRightContainer.addView(mRightBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mRightBtn.setText(getString(R.string.add));
        mRightBtn.setModle(IconTextView.MODULE_TEXT);

        mRightBtn.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLvMedicine.setEmptyView(mNoRemind);
    }

    @Override
    public void onClick(View v) {
        //startActivity(new Intent(this, AddMedicineRemindActivity.class));
    }
}
