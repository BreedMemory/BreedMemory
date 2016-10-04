/**
 * 项目名称：手机大管家
 * 文件名称: FormAdapter.java
 * Created by 谌珂 on 2016/10/3.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.yijiehl.club.android.ui.fragment.BabyFormFragment;
import com.yijiehl.club.android.ui.fragment.MotherFormFragment;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: FormAdapter<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class FormAdapter extends FragmentPagerAdapter {
    public FormAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MotherFormFragment();
            case 1:
                return new BabyFormFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
