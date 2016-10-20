/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PictureClubAdapter.java <br/>
 * Created by 张志新 on 2016/9/13.  <br/>
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.AlbumInfo;
import com.yijiehl.club.android.ui.activity.photo.AlbumPhotoActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PictureClubAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/13 <br/>
 * @author 张志新 <br/>
 */
public class PictureClubAdapter extends BaseListViewAdapter<AlbumInfo> implements AdapterView.OnItemClickListener {

    public PictureClubAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_picture_club, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvClubTitle.setText(mDatas.get(position).getDataName());
        holder.ivClubPicNum.setText(mDatas.get(position).getDataDesc());
        holder.tvClubPivTime.setText(TimeUtil.getTime(mDatas.get(position).getCreateTime(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD));

        Glide.with(mContext).load(mDatas.get(position).getIconInfo1()).dontAnimate().placeholder(R.drawable.bg_loading).into(holder.ivClubPic);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, AlbumPhotoActivity.class);
        intent.putExtra(AlbumPhotoActivity.DATA_ID, mDatas.get(position).getDataId());
        intent.putExtra(AlbumPhotoActivity.TIME, TimeUtil.getTime(mDatas.get(position).getCreateTime(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD));
        mContext.startActivity(intent);
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
