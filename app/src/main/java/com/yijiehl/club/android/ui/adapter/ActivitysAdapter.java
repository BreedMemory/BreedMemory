/**
 * 项目名称：手机大管家
 * 文件名称: ActivitysAdapter.java
 * Created by 谌珂 on 2016/10/4.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;

import java.util.ArrayList;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: ActivitysAdapter<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class ActivitysAdapter extends BaseListViewAdapter<ActivitysAdapter.Entity> {
    public ActivitysAdapter(Context mContext) {
        super(mContext);
        mDatas = new ArrayList<>();
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
        mDatas.add(new Entity());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Entity temp = mDatas.get(position);
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_activitys, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(temp.getUrl(), holder.ivPic);
        holder.tvtitle.setText(temp.getTitle());
        holder.tvArea.setText(temp.getArea());
        holder.tvPlace.setText(temp.getPlace());
        holder.tvTime.setText(temp.getTime());
        return convertView;
    }

    public static class Entity {
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        String url;
        String time = "2016年9月9日 10:30 am";
        String title = "音乐聆听";
        String place = "艾玛家月子中心";
        String area = "北京市";
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.iv_item_pic)
        ImageView ivPic;
        @ViewInject(R.id.tv_item_title)
        TextView tvtitle;
        @ViewInject(R.id.tv_item_time_content)
        TextView tvTime;
        @ViewInject(R.id.tv_item_place_content)
        TextView tvPlace;
        @ViewInject(R.id.tv_item_area_content)
        TextView tvArea;
    }
}
