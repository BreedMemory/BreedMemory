/**
 * 项目名称：手机大管家
 * 文件名称: HealthInfoActivity.java
 * Created by 谌珂 on 2016/10/4.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity.health;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: HealthInfoActivity<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_health_data_frame)
public class HealthInfoActivity extends BmActivity {
    public static final String ROLE = "role";

    /** 图表选择器 */
    @ViewInject(R.id.rg_selector)
    private RadioGroup mFormSelector;
    @ViewInject(R.id.data_mother)
    private View mDataMother;
    /** 婴儿统计图容器 */
    @ViewInject(R.id.data_baby)
    private View mDataBaby;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.health_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFormSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother:
                        mDataMother.setVisibility(View.VISIBLE);
                        mDataBaby.setVisibility(View.GONE);
                        break;
                    case R.id.rb_baby:
                        mDataMother.setVisibility(View.GONE);
                        mDataBaby.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        int resId = getIntent().getIntExtra(ROLE, R.id.rb_mother);
        mFormSelector.check(resId);
    }
}
