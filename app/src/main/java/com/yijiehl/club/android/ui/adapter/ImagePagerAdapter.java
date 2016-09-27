package com.yijiehl.club.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.yijiehl.club.android.ui.fragment.ImageDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ImagePagerAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/27 <br/>
 *
 * @author 张志新 <br/>
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    public List<String> fileList;

    public ImagePagerAdapter(FragmentManager fm, List<String> fileList) {
        super(fm);
        this.fileList = fileList;
    }


    @Override
    public Fragment getItem(int position) {
        String url = fileList.get(position);
        return ImageDetailFragment.newInstance(url);
    }

    @Override
    public int getCount() {
        return fileList == null ? 0 : fileList.size();
    }
}
