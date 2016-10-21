/**
 * 项目名称：手机大管家
 * 文件名称: UploadImageAdapter.java
 * Created by 谌珂 on 2016/10/15.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.svc.ActivitySvc;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: UploadImageAdapter<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class UploadImageAdapter extends BaseListViewAdapter<String> {

    public static final int TYPE_ADD = 1;
    public static final int TYPE_PHOTO = 0;

    public UploadImageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if(mDatas == null || (position == mDatas.size() && mDatas.size() < 9)) {
            return TYPE_ADD;
        } else {
            return TYPE_PHOTO;
        }
    }

    public int getItemViewRes(int position) {
        if(getItemViewType(position) == TYPE_ADD) {
            return R.layout.item_add_photo_layout;
        } else {
            return R.layout.item_photo_layout;
        }
    }

    @Override
    public int getViewTypeCount() {
        if(mDatas != null && mDatas.size() >= 9){
            return 1;
        }
        return 2;
    }

    @Override
    public int getCount() {
        return super.getCount() + getViewTypeCount() - 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, getItemViewRes(position), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(getItemViewType(position) == TYPE_PHOTO) {
            Glide.with(mContext).load(ActivitySvc.createResourceUrl(mContext, mDatas.get(position))).into(holder.photo);
        }
        return convertView;
    }

    class ViewHolder {
        ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.im_photo)
        ImageView photo;
    }
}
