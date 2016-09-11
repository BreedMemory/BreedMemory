package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: GrowUpContentAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 张志新 <br/>
 */
public class GrowUpContentAdapter extends BaseListViewAdapter {

    private List<String> data;

    public GrowUpContentAdapter(Context mContext) {
        super(mContext);
    }

    public GrowUpContentAdapter(Context mContext, List<String> data) {
        super(mContext);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_growup, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            // convertView = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvtitle.setText(data.get(position));
        holder.tvcontext.setText(data.get(position));

        ImageLoader.getInstance().displayImage(data.get(position), holder.ivPic, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.iv_item_pic)
        ImageView ivPic;
        @ViewInject(R.id.iv_item_heart)
        ImageView ivheart;
        @ViewInject(R.id.iv_item_share)
        ImageView ivshare;
        @ViewInject(R.id.tv_item_title)
        TextView tvtitle;
        @ViewInject(R.id.tv_item_content)
        TextView tvcontext;
    }
}
