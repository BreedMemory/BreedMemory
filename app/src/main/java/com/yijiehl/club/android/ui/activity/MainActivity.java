/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: MainActivity.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/9/5.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uuzz.android.ui.view.FootGroupBtn;
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
    /** 内容容器 */
    @ViewInject(R.id.vp_content_container)
    private ViewPager mViewPager;
    /** 底部tab容器 */
    @ViewInject(R.id.footer)
    private LinearLayout mFootContainer;
    /** 底部tab中间的圆形背景图 */
    @ViewInject(R.id.im_middle_background)
    private ImageView mFootMiddleBackground;
    /** 当前页面的索引 */
    @SaveInstance
    private int mCurrentPage;
    /** ViewPager缓存页面数目;当前页面的相邻N各页面都会被缓存 */
    private int cachePagers = 4;

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
        mViewPager.setOffscreenPageLimit(cachePagers);
    }

    @Override
    protected void configHeadLeftView() {
        super.configHeadLeftView();
        mLeftBtn.setText(R.string.icon_me);
        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MineActivity.class));
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
            if (i == index) {
                if (i == 2) {          //如果当前选中的是照片模块单独修改样式
                    mFootMiddleBackground.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_foot_btn_middle_pressed));
                } else {
                    ((FootGroupBtn) mFootContainer.getChildAt(i)).setFocus();
                }
            } else {
                if (i == 2) {
                    mFootMiddleBackground.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_foot_btn_middle));
                } else {
                    ((FootGroupBtn) mFootContainer.getChildAt(i)).setUnFocus();
                }
            }
        }
    }

    /**内部接口，具体执行跳转的方法封装这个接口中*/
    public interface FragmentToFragment {
        public void gotoFragment(ViewPager viewPager);
    }

    /**该接口类型的成员变量,并为其设置set方法*/
    private FragmentToFragment fragmentToFragment;

    public void setFragmentToFragment(FragmentToFragment fragmentToFragment) {
        this.fragmentToFragment = fragmentToFragment;
    }

    /**调用接口中gotoFragment方法的方法*/
    public void forSkip() {
        if (fragmentToFragment != null) {
            fragmentToFragment.gotoFragment(mViewPager);
        }
    }
}
