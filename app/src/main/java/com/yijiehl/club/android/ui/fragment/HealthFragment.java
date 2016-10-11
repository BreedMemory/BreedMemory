/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: HealthFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.ActivitysActivity;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.HealthInfoActivity;
import com.yijiehl.club.android.ui.activity.KnowledgeActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.MineActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: HealthFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.fragment_health_layout)
public class HealthFragment extends BaseHostFragment {
    /** 显示更多的图标 */
    @ViewInject(R.id.im_more)
    private View mMore;
    /** 显示更多的文字 */
    @ViewInject(R.id.tv_more)
    private View mIcMore;
    /** 图表的大容器 */
    @ViewInject(R.id.ll_form_container)
    private View mFormContainer;
    /** 图表选择器 */
    @ViewInject(R.id.rg_selector)
    private RadioGroup mFormSelector;
    /** 图表小容器 */
    @ViewInject(R.id.fl_form_container)
    private FrameLayout mFormFrameLayout;
    /** 母亲统计图容器 */
    @ViewInject(R.id.form_mother)
    private View mFormMother;
    /** 婴儿统计图容器 */
    @ViewInject(R.id.form_baby)
    private View mFormBaby;

    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DONE: 谌珂 2016/9/5 跳转到个人账户
                startActivity(new Intent(getActivity(), MineActivity.class));
            }
        };
    }

    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        ((MainActivity)getActivity()).getmLeftBtn().setText(R.string.icon_me);
        return true;
    }

    @Override
    protected boolean isRightBtnVisible() {
        return false;
    }

    @Override
    protected int getTitle() {
        return R.string.health;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFormSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother:
                        mFormMother.setVisibility(View.VISIBLE);
                        mFormBaby.setVisibility(View.GONE);
                        break;
                    case R.id.rb_baby:
                        mFormMother.setVisibility(View.GONE);
                        mFormBaby.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.im_more, R.id.tv_more})
    private void showForm() {
        mFormContainer.setVisibility(View.VISIBLE);
        mMore.setVisibility(View.GONE);
        mIcMore.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_retract)
    private void hideForm() {
        mFormContainer.setVisibility(View.GONE);
        mMore.setVisibility(View.VISIBLE);
        mIcMore.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.mother_extra_data,
            R.id.form_mother_temperature,
            R.id.form_mother_weight,
            R.id.baby_extra_data,
            R.id.form_baby_height,
            R.id.form_baby_chest,
            R.id.form_baby_head,
            R.id.form_baby_weight})
    private void startHealthData(View v) {
        Intent intent = new Intent(getActivity(), HealthInfoActivity.class);
        switch (v.getId()) {
            case R.id.mother_extra_data:
            case R.id.form_mother_temperature:
            case R.id.form_mother_weight:
                intent.putExtra(HealthInfoActivity.ROLE, R.id.rb_mother);
                break;
            case R.id.baby_extra_data:
            case R.id.form_baby_height:
            case R.id.form_baby_chest:
            case R.id.form_baby_head:
            case R.id.form_baby_weight:
                intent.putExtra(HealthInfoActivity.ROLE, R.id.rb_baby);
                break;
        }
        startActivity(intent);
    }

    @OnClick(R.id.ll_activity)
    private void startActivitys() {
        startActivity(new Intent(getActivity(), ActivitysActivity.class));
    }

    @OnClick(R.id.ll_food)
    private void startFood() {
        Intent intent=new Intent(getActivity(),ArticalDetailActivity.class);
        intent.putExtra("url","http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=org_month_meals&dd=XXXXXXXXX&bd=showdetail");
        startActivity(intent);
    }

    @OnClick(R.id.ll_knowledge)
    private void startKnowledge(){
        startActivity(new Intent(getActivity(), KnowledgeActivity.class));
    }
}
