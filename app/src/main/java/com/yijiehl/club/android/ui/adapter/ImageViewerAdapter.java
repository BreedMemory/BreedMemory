/**
 * 项目名称：手机大管家
 * 文件名称: ImageViewerAdapter.java
 * Created by 谌珂 on 2016/10/16.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.uuzz.android.ui.view.ImageViewer;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.svc.ActivitySvc;

import java.util.LinkedList;
import java.util.List;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: ImageViewerAdapter<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class ImageViewerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    public ImageViewerAdapter(Context context, List<String> paths, boolean isNative, PageSelectedListener lPageSelectedListener) {
        this.mContext = context;
        this.paths = paths;
        this.isNative = isNative;
        this.mPageSelectedListener = lPageSelectedListener;
    }
    private View.OnClickListener mListener;
    private PageSelectedListener mPageSelectedListener;

    public void setListener(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    private Context mContext;
    /** view集合 */
    private LinkedList<ImageViewer> views = new LinkedList<>();
    /** 图片路径集合 */
    private List<String> paths;
    /** 是否加载的本地图片 */
    private boolean isNative;

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public int getCount() {
        if(paths != null) {
            return paths.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(object == null) {
            return;
        }
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageViewer view = null;
        for (ImageViewer lImageViewer : views) {
            if(lImageViewer.getParent() == null) {
                view = lImageViewer;
                break;
            }
        }
        if(view == null) {
            view = (ImageViewer) LayoutInflater.from(mContext).inflate(R.layout.item_image_viewer_layout, null);
            views.add(view);
        }
        view.setOnClickListener(mListener);
        if(isNative) {
            ImageLoader.getInstance().displayImage("file:///" + paths.get(position), view);
        } else {
            if(paths.get(position).startsWith("http")) {
                ImageLoader.getInstance().displayImage(paths.get(position), view);
            } else {
                ImageLoader.getInstance().displayImage(ActivitySvc.createResourceUrl(mContext, paths.get(position)), view);
            }
        }
        view.reset();
        container.addView(view);

        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (ImageViewer lImageViewer : views) {
            lImageViewer.reset();
        }
        if(mPageSelectedListener == null) {
            return;
        }
        mPageSelectedListener.onPageSelector(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface PageSelectedListener {
        void onPageSelector(int position);
    }
}
