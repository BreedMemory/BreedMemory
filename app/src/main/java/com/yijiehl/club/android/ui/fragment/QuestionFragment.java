/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: QuestionFragment.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.QuestionListActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: QuestionFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 *
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.fragment_question)
public class QuestionFragment extends BaseHostFragment {

    /**
     * 我的问题
     */
    @ViewInject(R.id.btn_my_question)
    private Button mButton;
    /**
     * 母亲专区
     */
    @ViewInject(R.id.layout_mother_zone)
    private RelativeLayout motherZone;
    /**
     * 婴儿专区
     */
    @ViewInject(R.id.layout_baby_zone)
    private RelativeLayout babyZone;
    /**
     * 母亲专区指示图标
     */
    @ViewInject(R.id.iv_mother_question_show)
    private ImageView ivMotherShow;
    /**
     * 婴儿专区指示图标
     */
    @ViewInject(R.id.iv_baby_question_show)
    private ImageView ivBabyShow;
    // TODO: 2016/10/4 后期需要添加母亲下拉展示内容及详细选项
    /**
     * 婴儿下拉展示内容
     */
    @ViewInject(R.id.layout_baby_zone_context)
    private LinearLayout babyContextList;
    /**
     * 0-3个月
     */
    @ViewInject(R.id.layout_zero_month)
    private RelativeLayout zeroMonth;
    /**
     * 3个月-1岁
     */
    @ViewInject(R.id.layout_three_months)
    private RelativeLayout threeMonth;
    /**
     * 1岁-1岁半
     */
    @ViewInject(R.id.layout_one_year)
    private RelativeLayout oneYear;
    /**
     * 1岁半-3岁
     */
    @ViewInject(R.id.layout_one_half_years)
    private RelativeLayout oneHalfYears;

    private boolean isBabyShow;


    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return null;
    }

    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        return false;
    }

    @Override
    protected boolean isRightBtnVisible() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: 2016/10/4 此页面的所有的方法都需要完善单击事件；暂时跳转预览页面。。。

    @OnClick(R.id.btn_my_question)
    private void myQuestion(){
            startActivity(new Intent(getActivity(), QuestionListActivity.class));
    }

    @OnClick(R.id.layout_mother_zone)
    private void motherZone(){

    }

    @OnClick(R.id.layout_baby_zone)
    private void babyZone(){
        if(!isBabyShow){
            babyContextList.setVisibility(View.GONE);
            isBabyShow=true;
        }else{
            babyContextList.setVisibility(View.VISIBLE);
            isBabyShow=false;
        }
    }

    @OnClick(R.id.layout_zero_month)
    private void zeroMonth(){
        startActivity(new Intent(getActivity(), ArticalDetailActivity.class));
    }

    @OnClick(R.id.layout_three_months)
    private void threeMonth(){
        startActivity(new Intent(getActivity(), ArticalDetailActivity.class));
    }

    @OnClick(R.id.layout_one_year)
    private void oneYear(){
        startActivity(new Intent(getActivity(), ArticalDetailActivity.class));
    }
    @OnClick(R.id.layout_one_half_years)
    private void oneHalfYear(){
        startActivity(new Intent(getActivity(), ArticalDetailActivity.class));
    }
}
