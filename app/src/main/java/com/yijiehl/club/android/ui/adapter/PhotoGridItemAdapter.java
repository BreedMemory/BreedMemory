package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.svc.ActivitySvc;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PhotoGridItemAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/23 <br/>
 *
 * @author 张志新 <br/>
 */
public class PhotoGridItemAdapter extends BaseListViewAdapter<String> {

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    private ArrayList<String> mSelectedPhoto = new ArrayList<>();

    /**
     * 后面选择照片最大数量
     */
    public static final int MAX_COUNT = 9;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int VIEW_TYPE = 2;

    private MyClickListener mListener = new MyClickListener();


    public ArrayList<String> getmSelectedPhoto() {
        return mSelectedPhoto;
    }

    public void setmSelectedPhoto(ArrayList<String> mSelectedPhoto) {
        this.mSelectedPhoto = mSelectedPhoto;
    }

    public PhotoGridItemAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case TYPE_1:
                    convertView = View.inflate(mContext, R.layout.item_photo_grid_take, null);
                    break;
                case TYPE_2:
                    convertView = View.inflate(mContext, R.layout.item_photo_grid_pick, null);
                    break;
            }
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (type) {
            case TYPE_1:
                holder.layoutTake.setOnClickListener(mListener);
                break;
            case TYPE_2:
                position--;
                Glide.with(mContext).load("file:///" + mDatas.get(position)).dontAnimate().placeholder(R.drawable.bg_loading).into(holder.ivPhoto);
                holder.ivPhoto.setTag(R.id.pick_picture_content, position);
                holder.ivPhotoPick.setTag(R.id.pick_picture_tag, position);
                holder.ivPhoto.setOnClickListener(mListener);
                holder.ivPhotoPick.setOnClickListener(mListener);

                /**已经选好的照片，显示出选择的效果*/
                if (mSelectedPhoto.contains(mDatas.get(position))) {
                    Glide.with(mContext).load(R.drawable.picture_selected).dontAnimate().into(holder.ivPhotoPick);
                    holder.ivPhoto.setColorFilter(R.color.colorPrimary);
                } else {
                    Glide.with(mContext).load(R.drawable.picture_unselected).dontAnimate().into(holder.ivPhotoPick);
                    holder.ivPhoto.setColorFilter(null);
                }
                break;
            default:
                break;
        }
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int position;
            switch (v.getId()) {
                case R.id.iv_item_photo:
                    position = (int) v.getTag(R.id.pick_picture_content);
                    ArrayList<String> list = new ArrayList<>();
                    list.add(mDatas.get(position));
                    ActivitySvc.startImageViewer(mContext, list, true, 0);
                    break;
                case R.id.iv_item_pick:
                    position = (int) v.getTag(R.id.pick_picture_tag);
                    //已经选择了该照片
                    if (mSelectedPhoto.contains(mDatas.get(position))) {
                        mSelectedPhoto.remove(mDatas.get(position));
                    } else if(mSelectedPhoto.size() >= MAX_COUNT) {//没有选择该照片
                        Toaster.showShortToast(mContext, R.string.upload_count_must_less_than_9);
                    } else {
                        mSelectedPhoto.add(mDatas.get(position));
                    }
                    notifyDataSetChanged();
                    if(onPhotoSelectedListener != null) {
                        onPhotoSelectedListener.photoClick(mSelectedPhoto);
                    }
                    break;
                default:
                    if (onPhotoSelectedListener != null) {
                        onPhotoSelectedListener.takePhoto();
                    }
                    break;
            }
        }
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.layout_grid_take)
        LinearLayout layoutTake;
        @ViewInject(R.id.iv_item_photo)
        ImageView ivPhoto;
        @ViewInject(R.id.iv_item_pick)
        ImageView ivPhotoPick;
    }

    public OnPhotoSelectedListener onPhotoSelectedListener;

    public void setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener) {
        this.onPhotoSelectedListener = onPhotoSelectedListener;
    }

    public interface OnPhotoSelectedListener {
        public void photoClick(List<String> number);

        public void takePhoto();
    }
}
