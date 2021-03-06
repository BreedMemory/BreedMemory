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
    public static final int MODE_NATIVE = 3;
    public static final int MODE_REMOTE = 4;

    private int mode = MODE_NATIVE;

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public UploadImageAdapter(Context mContext) {
        super(mContext);
    }
    public UploadImageAdapter(Context mContext, int mode) {
        super(mContext);
        this.mode = mode;
    }

    @Override
    public int getItemViewType(int position) {
        switch (mode) {
            case MODE_NATIVE:
                if(mDatas == null || (position == mDatas.size() && mDatas.size() < 9)) {
                    return TYPE_ADD;
                } else {
                    return TYPE_PHOTO;
                }
            default:
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
        switch (mode) {
            case MODE_NATIVE:
                if (mDatas != null && mDatas.size() >= 9) {
                    return 1;
                }
                return 2;
            default:
                return 1;
        }
    }

    @Override
    public int getCount() {
        switch (mode) {
            case MODE_NATIVE:
                return super.getCount() + getViewTypeCount() - 1;
            default:
                return super.getCount();
        }

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
            String uri;
            if (mode == MODE_REMOTE) {
                uri = ActivitySvc.createResourceUrl(mContext, mDatas.get(position));
            } else {
                uri = "file:///" + mDatas.get(position);
            }
            Glide.with(mContext).load(uri).into(holder.photo);
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
