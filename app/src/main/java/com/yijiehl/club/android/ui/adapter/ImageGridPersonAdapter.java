/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ImageGridPersonAdapter.java <br/>
 * Created by 张志新 on 2016/9/16.  <br/>
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
import com.yijiehl.club.android.network.response.innerentity.PhotoInfo;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ImageGridPersonAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/16 <br/>
 *
 * @author 张志新 <br/>
 */
public class ImageGridPersonAdapter extends BaseListViewAdapter<PhotoInfo> {

    public ImageGridPersonAdapter(Context mContext, List<PhotoInfo> data) {
        super(mContext);
        mDatas= data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_picture_grid_person, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mDatas.get(position).getIconInfo1()).placeholder(R.drawable.bg_loading).into(holder.ivContent);
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.iv_content)
        ImageView ivContent;
    }
}
