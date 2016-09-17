/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PictureClubAdapter.java <br/>
 * Created by 张志新 on 2016/9/13.  <br/>
 */
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
import com.yijiehl.club.android.network.response.innerentity.AlbumInfo;
import com.yijiehl.club.android.network.response.innerentity.ClubInfo;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PictureClubAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/13 <br/>
 * @author 张志新 <br/>
 */
public class PictureClubAdapter extends BaseListViewAdapter {

    private List<AlbumInfo> data;//相册数据源

    public PictureClubAdapter(Context mContext) {
        super(mContext);
    }

    public PictureClubAdapter(Context mContext, List<AlbumInfo> data) {
        super(mContext);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_picture_club, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            // convertView = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvClubTitle.setText(data.get(position).getDataNum());
        holder.ivClubPicNum.setText(data.get(position).getDataNum());
        holder.tvClubPivTime.setText(data.get(position).getCreateTime());

        // TODO: 2016/9/13 给相册下载缩略图
        ImageLoader.getInstance().displayImage(data.get(position).getIconInfo1(), holder.ivClubPic, new ImageLoadingListener() {
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

    class ViewHolder{
        public ViewHolder(View v){
            InjectUtils.injectViews(v,this);
        }
        @ViewInject(R.id.iv_club_pic)
        ImageView ivClubPic;
        @ViewInject(R.id.tv_club_title)
        TextView tvClubTitle;
        @ViewInject(R.id.tv_clubpic_num)
        TextView ivClubPicNum;
        @ViewInject(R.id.tv_clubpic_time)
        TextView tvClubPivTime;
    }
}
