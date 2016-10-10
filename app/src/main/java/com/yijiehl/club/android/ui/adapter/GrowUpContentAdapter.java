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
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;

import java.util.ArrayList;
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
    private List<String> urlData = new ArrayList<>();

    public GrowUpContentAdapter(Context mContext) {
        super(mContext);
    }

    public GrowUpContentAdapter(Context mContext, List<String> data) {
        super(mContext);
        this.data = data;
        mDatas=data;
        urlData.add("http://imgx.xiawu.com/xzimg/i4/i7/T1UW9VXi4sXXa4zfs__105950.jpg");
        urlData.add("http://i2.s2.dpfile.com/pc/9f9d763bdeea2976024a3b0abced9788(700x700)/thumb.jpg");
        urlData.add("http://www.vnbaby.cn/uploadfile/2013/0121/20130121093919299.jpg");
        urlData.add("http://pn.680.com/news/2012-05/2012051910421844_.jpg");
        urlData.add("http://imgx.xiawu.com/xzimg/i4/i7/T1UW9VXi4sXXa4zfs__105950.jpg");
        urlData.add("http://i2.s2.dpfile.com/pc/9f9d763bdeea2976024a3b0abced9788(700x700)/thumb.jpg");
        urlData.add("http://www.vnbaby.cn/uploadfile/2013/0121/20130121093919299.jpg");
        urlData.add("http://pn.680.com/news/2012-05/2012051910421844_.jpg");
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
        holder.tvcontext.setText("小孩子，需要选择适合的保险产品。然后，众多的保险产品当中，关注.....");
        holder.ivshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/10/2
                Toaster.showShortToast(mContext,"您已收藏");
            }
        });
        holder.ivheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/10/2
                Toaster.showShortToast(mContext,"您已分享");
            }
        });

        ImageLoader.getInstance().displayImage(urlData.get(position), holder.ivPic, new ImageLoadingListener() {
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
