/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: SexChangeActivity.java <br/>
 * Created by 张志新 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.activity.user;/**
 * Created by asus on 2016/9/18.
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.BmActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: SexChangeActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_sex_change)
public class SexChangeActivity extends BmActivity {

    @ViewInject(R.id.layout_choose_male)
    private RelativeLayout layoutMale;

    @ViewInject(R.id.layout_choose_female)
    private RelativeLayout layoutFemale;

    @ViewInject(R.id.iv_male_show)
    private ImageView maleShow;

    @ViewInject(R.id.iv_female_show)
    private ImageView femaleShow;


    @Override
    protected String getHeadTitle() {
        return getString(R.string.sex);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String sex=getIntent().getStringExtra("sex");
        if(TextUtils.isEmpty(sex)||sex.equals("male")){
            maleShow.setVisibility(View.VISIBLE);
        }else{
            femaleShow.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.layout_choose_male)
    private void chooseMale(){
        femaleShow.setVisibility(View.GONE);
        maleShow.setVisibility(View.VISIBLE);
        // TODO: 2016/9/18 需要返回性别
        //setResult();
        finish();
    }

    @OnClick(R.id.layout_choose_female)
    private void chooseFemale(){
        maleShow.setVisibility(View.GONE);
        femaleShow.setVisibility(View.VISIBLE);
        // TODO: 2016/9/18 需要返回性别
        //setResult();
        finish();
    }
}
