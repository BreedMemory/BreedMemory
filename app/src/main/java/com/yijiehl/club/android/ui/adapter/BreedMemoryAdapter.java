/**
 * 项目名称：手机大管家
 * 文件名称: ImageViewerAdapter.java
 * Created by 谌珂 on 2016/10/16.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.HealthData;

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
public class BreedMemoryAdapter extends PagerAdapter {

    public BreedMemoryAdapter(Context context) {
        this.mContext = context;
    }
    private Context mContext;
    /** view集合 */
    private LinkedList<View> views = new LinkedList<>();
    /** 数据集合 */
    private List<HealthData> datas;

    public List<HealthData> getDatas() {
        return datas;
    }

    public void setDatas(List<HealthData> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return 366;
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
        View view = null;
        ViewHolder holder;
        HealthData tempData = null;
        if(datas != null && datas.size() > position) {
            tempData = datas.get(position);
        }
        for (View lView : views) {
            if(lView.getParent() == null) {
                view = lView;
                break;
            }
        }
        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_breed_memory_layout, null);
            views.add(view);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        container.addView(view);

        if(tempData != null) {
            holder.mContent.setText(tempData.getImageDesc());
            holder.mDate.setText(tempData.getDataSummary());
            if(TextUtils.isEmpty(tempData.getImageInfo())) {
                holder.mPic.setVisibility(View.GONE);
            } else {
                Glide.with(mContext).load(tempData.getImageInfo()).dontAnimate().into(holder.mPic);
                holder.mPic.setVisibility(View.VISIBLE);
            }
        }

        int week = TimeUtil.getWeek(System.currentTimeMillis()) + 7*52 - position - 1;
        int resBg, resCenterBg;
        switch (week % 7) {
            case 0:
                resBg = R.drawable.bg_03;
                resCenterBg = R.drawable.bg_breed_memory_3;
                break;
            case 1:
                resBg = R.drawable.bg_06;
                resCenterBg = R.drawable.bg_breed_memory_6;
                break;
            case 2:
                resBg = R.drawable.bg_02;
                resCenterBg = R.drawable.bg_breed_memory_2;
                break;
            case 3:
                resBg = R.drawable.bg_04;
                resCenterBg = R.drawable.bg_breed_memory_4;
                break;
            case 4:
                resBg = R.drawable.bg_01;
                resCenterBg = R.drawable.bg_breed_memory_1;
                break;
            case 5:
                resBg = R.drawable.bg_05;
                resCenterBg = R.drawable.bg_breed_memory_5;
                break;
            case 6:
                resBg = R.drawable.bg_07;
                resCenterBg = R.drawable.bg_breed_memory_7;
                break;
            default:
                resBg = R.drawable.bg_01;
                resCenterBg = R.drawable.bg_breed_memory_1;
                break;
        }
        holder.mBackground.setBackgroundResource(resBg);
        holder.mCenterBackground.setBackgroundResource(resCenterBg);


        return view;
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.tv_baby_date)
        TextView mDate;
        @ViewInject(R.id.tv_content)
        TextView mContent;
        @ViewInject(R.id.iv_pic)
        ImageView mPic;
        @ViewInject(R.id.rl_background)
        View mBackground;
        @ViewInject(R.id.rl_container_bg)
        View mCenterBackground;
    }
}
