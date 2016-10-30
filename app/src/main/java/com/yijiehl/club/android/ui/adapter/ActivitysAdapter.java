/**
 * 项目名称：手机大管家
 * 文件名称: ActivitysAdapter.java
 * Created by 谌珂 on 2016/10/4.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.ActivityInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ArticleDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: ActivitysAdapter<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class ActivitysAdapter extends BaseListViewAdapter<ActivityInfo> implements AdapterView.OnItemClickListener{

    public ActivitysAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActivityInfo temp = mDatas.get(position);
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_activitys, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(ActivitySvc.createResourceUrl(mContext, temp.getImageInfo())).into(holder.ivPic);
        holder.tvtitle.setText(temp.getDataName());
        holder.tvContent.setText(temp.getDataSummary());
        holder.tvPlace.setText(temp.getAddrInfo());
        holder.tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(temp.getStartTime())));
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(!TextUtils.isEmpty(mDatas.get(position).getDataShowUrl())){
            Intent intent=new Intent(mContext,ArticleDetailActivity.class);
            intent.putExtra(ArticleDetailActivity.URL, ActivitySvc.createWebUrl(mDatas.get(position).getDataShowUrl()));
            mContext.startActivity(intent);
        }
    }


    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.iv_item_pic)
        ImageView ivPic;
        @ViewInject(R.id.tv_item_title)
        TextView tvtitle;
        @ViewInject(R.id.tv_item_content)
        TextView tvContent;
        @ViewInject(R.id.tv_item_place)
        TextView tvPlace;
        @ViewInject(R.id.tv_item_time)
        TextView tvTime;
    }
}
