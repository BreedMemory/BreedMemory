/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: HostViewPagerAdapter.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/5.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.fragment.BaseHostFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: HostViewPagerAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/5 <br/>
 * @author 谌珂 <br/>
 */
public class HostViewPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    public HostViewPagerAdapter(FragmentManager fm, MainActivity activity) {
        super(fm);
        mActivity = activity;
    }

    private MainActivity mActivity;
    /** Fragment队列 */
    private List<BaseHostFragment> fragments = new ArrayList<>();
    {
        // TODO: 谌珂 2016/9/5 添加Fragment
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        fragments.get(position).onShow();
        mActivity.setFootFocus(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
