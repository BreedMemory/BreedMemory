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
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.PhotoInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.fragment.PictureFragment;

import java.util.ArrayList;
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

    private boolean isSelect;

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    private ArrayList<String> mSelectedPhoto = new ArrayList<>();

    public ArrayList<String> getmSelectedPhoto() {
        return mSelectedPhoto;
    }

    public void setmSelectedPhoto(ArrayList<String> mSelectedPhoto) {
        this.mSelectedPhoto = mSelectedPhoto;
    }

    private MyClickListener mListener = new MyClickListener();
    private PictureFragment.DeleteListPhoto deleteListPhoto;

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    public ImageGridPersonAdapter(Context mContext, List<PhotoInfo> data) {
        super(mContext);
        mDatas = data;
    }

    public ImageGridPersonAdapter(Context mContext, List<PhotoInfo> data, boolean isSelect) {
        super(mContext);
        mDatas = data;
        this.isSelect = isSelect;
    }


    public ImageGridPersonAdapter(Context mContext, List<PhotoInfo> data, boolean isSelect,PictureFragment.DeleteListPhoto listener) {
        super(mContext);
        mDatas = data;
        this.isSelect = isSelect;
        this.deleteListPhoto = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_picture_grid_person, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == getCount() - 1) {
            holder.add.setVisibility(View.VISIBLE);
            holder.ivContent.setVisibility(View.GONE);
        } else {
            holder.add.setVisibility(View.GONE);
            holder.ivContent.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(ActivitySvc.createResourceUrl(mContext, mDatas.get(position).getIconInfo1())).placeholder(R.drawable.bg_loading).into(holder.ivContent);
        }
        if (isSelect) {
            // TODO: 2016/11/27  d显示蒙版
            holder.ivChoose.setVisibility(View.VISIBLE);


        } else {
            // TODO: 2016/11/27 隐藏蒙版
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
        /**已经选好的照片，显示出选择的效果*//*
        if (mSelectedPhoto.contains(mDatas.get(position))) {
            Glide.with(mContext).load(R.drawable.picture_selected).dontAnimate().into(holder.ivChoose);
            holder.ivContent.setColorFilter(R.color.colorPrimary);
        } else {
            Glide.with(mContext).load(R.drawable.picture_unselected).dontAnimate().into(holder.ivChoose);
            holder.ivContent.setColorFilter(null);
        }*/
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.id.pick_picture_tag);
            if (mSelectedPhoto.contains(mDatas.get(position))) {
                mSelectedPhoto.remove(mDatas.get(position));
            } else {
                //mSelectedPhoto.add(mDatas.get(position));
            }
        }
    }

    private class ViewHolder {
        ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.iv_content)
        ImageView ivContent;
        @ViewInject(R.id.icv_add)
        IconTextView add;
        @ViewInject(R.id.iv_choose)
        ImageView ivChoose;
    }
}
