/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ImageGridPersonAdapter.java <br/>
 * Created by 张志新 on 2016/9/16.  <br/>
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
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
public class ImageGridPersonAdapter extends BaseListViewAdapter {

    private List<PhotoInfo> data;

    public ImageGridPersonAdapter(Context mContext) {
        super(mContext);
    }

    public ImageGridPersonAdapter(Context mContext, List<PhotoInfo> data) {
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
        // TODO: 2016/9/16 给个人相册缩略图
        ImageLoader.getInstance().displayImage(data.get(position).getIconInfo1(), holder.ivContent, new ImageLoadingListener() {
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

        @ViewInject(R.id.iv_content)
        ImageView ivContent;
    }
}
