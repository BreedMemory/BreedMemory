/**
 * 项目名称：手机大管家
 * 文件名称: MyMessageAdapter.java
 * Created by 谌珂 on 2016/10/31.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.MyMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: MyMessageAdapter<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class MyMessageAdapter extends BaseListViewAdapter<MyMessage> {
    public MyMessageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_message_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.title.setText(mDatas.get(position).getDataName());
//        holder.content.setText(mDatas.get(position).getDataContent());
        holder.time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(mDatas.get(position).getNoticeTime())));
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.tv_title)
        TextView title;
        @ViewInject(R.id.tv_content)
        TextView content;
        @ViewInject(R.id.tv_time)
        TextView time;
    }
}
