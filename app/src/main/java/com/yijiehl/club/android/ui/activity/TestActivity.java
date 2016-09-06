/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: TestActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/6.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.view.NumberPickerView;


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

    @ViewInject(R.id.npv_number_picker_view)
    private NumberPickerView mNumberPicker;

    @Override
    protected void configHeadLeftView() {
        mHeadLeftContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));
        IconTextView lReturnBtn = new IconTextView(this);
        mHeadLeftContainer.addView(lReturnBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lReturnBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        lReturnBtn.setText(getString(R.string.icon_return));

        mHeadLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberPicker.setMaxValue(2010);
            }
        });
    }

    @Override
    protected String getHeadTitle() {
        return "测试";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
