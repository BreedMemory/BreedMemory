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

import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.MineActivity;
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
     * 搜索框
     */
    @ViewInject(R.id.layout_search)
    private RelativeLayout mSearch;
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
    private LinearLayout zeroMonth;
    /**
     * 3个月-1岁
     */
    @ViewInject(R.id.layout_three_months)
    private LinearLayout threeMonth;
    /**
     * 1岁-1岁半
     */
    @ViewInject(R.id.layout_one_year)
    private LinearLayout oneYear;
    /**
     * 1岁半-3岁
     */
    @ViewInject(R.id.layout_one_half_years)
    private LinearLayout oneHalfYears;

    private boolean isBabyShow;


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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: 2016/10/4 此页面的所有的方法都需要完善单击事件；暂时跳转预览页面。。。

    @OnClick(R.id.layout_search)
    private void search(){
        Toaster.showShortToast(getActivity(),"此搜索功能暂未实现");
    }

    @OnClick(R.id.btn_my_question)
    private void myQuestion() {
        startActivity(new Intent(getActivity(), QuestionListActivity.class));
    }

    @OnClick(R.id.layout_mother_zone)
    private void motherZone() {

    }

    @OnClick(R.id.layout_baby_zone)
    private void babyZone() {
        if (!isBabyShow) {
            babyContextList.setVisibility(View.GONE);
            isBabyShow = true;
        } else {
            babyContextList.setVisibility(View.VISIBLE);
            isBabyShow = false;
        }
    }

    @OnClick(R.id.layout_zero_month)
    private void zeroMonth() {
        Intent intent = new Intent(getActivity(), ArticalDetailActivity.class);
        intent.putExtra("url", "http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=kb_article_main&dd=XXXXXXXXX&bd=showdetail");
        startActivity(intent);
    }

    @OnClick(R.id.layout_three_months)
    private void threeMonth() {
        Intent intent = new Intent(getActivity(), ArticalDetailActivity.class);
        intent.putExtra("url", "http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=kb_article_main&dd=XXXXXXXXX&bd=showdetail");
        startActivity(intent);
    }

    @OnClick(R.id.layout_one_year)
    private void oneYear() {
        Intent intent = new Intent(getActivity(), ArticalDetailActivity.class);
        intent.putExtra("url", "http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=kb_article_main&dd=XXXXXXXXX&bd=showdetail");
        startActivity(intent);
    }

    @OnClick(R.id.layout_one_half_years)
    private void oneHalfYear() {
        Intent intent = new Intent(getActivity(), ArticalDetailActivity.class);
        intent.putExtra("url", "http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=kb_article_main&dd=XXXXXXXXX&bd=showdetail");
        startActivity(intent);
    }
}
