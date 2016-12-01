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
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.photo.AlbumPhotoActivity;

import java.util.ArrayList;

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
public class ImageGridAlbumAdapter extends BaseListViewAdapter<PhotoInfo> {


    private boolean isSelect;
    private AlbumPhotoActivity.DeleteListPhoto deleteListPhoto;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        refresh();
    }

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    private ArrayList<String> mSelectedPhoto = new ArrayList<>();

    public ImageGridAlbumAdapter(Context mContext) {
        super(mContext);
    }

    public ImageGridAlbumAdapter(Context mContext, AlbumPhotoActivity.DeleteListPhoto deleteListPhoto) {
        super(mContext);
        this.deleteListPhoto = deleteListPhoto;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_picture_grid_album, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(ActivitySvc.createResourceUrl(mContext, mDatas.get(position).getIconInfo1())).placeholder(R.drawable.bg_loading).into(holder.ivContent);

        if (isSelect) {
            // DONE: 2016/11/27  显示蒙版
            holder.ivChoose.setVisibility(View.VISIBLE);

        } else {
            // DONE: 2016/11/27 隐藏蒙版
            holder.ivChoose.setVisibility(View.GONE);
        }

        holder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSelectedPhoto.contains(mDatas.get(position).getDataCode())) {
                    mSelectedPhoto.add(mDatas.get(position).getDataCode());
                    Glide.with(mContext).load(R.drawable.picture_selected).dontAnimate().into(holder.ivChoose);
                    holder.ivContent.setColorFilter(R.color.colorPrimary);
                    deleteListPhoto.addDataCode(mDatas.get(position).getDataCode());
                } else {
                    mSelectedPhoto.remove(mDatas.get(position).getDataCode());
                    Glide.with(mContext).load(R.drawable.picture_unselected).dontAnimate().into(holder.ivChoose);
                    holder.ivContent.setColorFilter(null);
                    deleteListPhoto.deleteDataCode(mDatas.get(position).getDataCode());
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.iv_content)
        ImageView ivContent;
        @ViewInject(R.id.iv_choose)
        ImageView ivChoose;
    }
}
