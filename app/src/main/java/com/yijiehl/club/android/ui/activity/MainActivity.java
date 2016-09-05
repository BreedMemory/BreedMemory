/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: MainActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/5.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.uuzz.android.ui.view.FootGroupBtn;
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.adapter.HostViewPagerAdapter;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: MainActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/5 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BmActivity {
    /** 标题栏左侧按钮 */
    private IconTextView mLeftBtn;
    /** 标题栏右侧按钮 */
    private IconTextView mRightBtn;
    /** 内容容器 */
    @ViewInject(R.id.vp_content_container)
    private ViewPager mViewPager;
    /** 底部tab容器 */
    @ViewInject(R.id.footer)
    private LinearLayout mFootContainer;
    /** 当前页面的索引 */
    @SaveInstance
    private int mCurrentPage;

    public IconTextView getmLeftBtn() {
        return mLeftBtn;
    }

    public IconTextView getmRightBtn() {
        return mRightBtn;
    }

    @Override
    protected String getHeadTitle() {
        return getString(R.string.host_page);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager.setCurrentItem(mCurrentPage);
        setFootFocus(mCurrentPage);
        //创建适配器
        HostViewPagerAdapter mAdapter = new HostViewPagerAdapter(getFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mAdapter);
    }

    @Override
    protected void configHeadLeftView() {
        mHeadLeftContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));
        mLeftBtn = new IconTextView(this);
        mHeadLeftContainer.addView(mLeftBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLeftBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        // TODO: 谌珂 2016/9/5 替换图标
        mLeftBtn.setText(getString(R.string.icon_return));

        mHeadLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 谌珂 2016/9/5 跳转到个人账户
            }
        });
    }

    @Override
    protected void configHeadRightView() {
        mHeadRightContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));
        mRightBtn = new IconTextView(this);
        mHeadRightContainer.addView(mRightBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        // TODO: 谌珂 2016/9/5 替换图标
        mRightBtn.setText(getString(R.string.icon_return));

        mHeadRightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 谌珂 2016/9/5 跳转到个人账户
            }
        });
    }

    /**
     * 描 述：点击底部tab按钮后切换ViewPager的内容，并记录页面索引<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     */
    @OnClick({R.id.fgb_grow_up, R.id.fgb_health, R.id.fgb_host, R.id.fgb_photo, R.id.fgb_question})
    private void switchContent(View v) {
        mCurrentPage = mFootContainer.indexOfChild(v);
        mViewPager.setCurrentItem(mCurrentPage);
    }

    /**
     * 描 述：根据索引给底部tab设置样式<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     * @param index 被选中页面的索引
     */
    public void setFootFocus(int index) {
        for (int i = 0; i < mFootContainer.getChildCount(); i++) {
            if(i == index) {
                ((FootGroupBtn) mFootContainer.getChildAt(index)).setFocus();
            } else {
                ((FootGroupBtn) mFootContainer.getChildAt(index)).setUnFocus();
            }
        }
    }
}
