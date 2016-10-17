package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;

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
public class PhotoGridItemAdapter extends BaseListViewAdapter<String> implements AdapterView.OnItemClickListener {

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
                break;
            case TYPE_2:
                if(!TextUtils.equals(mDatas.get(position - 1), (CharSequence) holder.ivPhoto.getTag(R.id.pick_picture_content))){
                    ImageLoader.getInstance().displayImage("file:///" + mDatas.get(position - 1), holder.ivPhoto);
                    holder.ivPhoto.setTag(R.id.pick_picture_content, mDatas.get(position - 1));
                }

                /**已经选好的照片，显示出选择的效果*/
                if (mSelectedPhoto.contains(mDatas.get(position - 1))) {
                    holder.ivPhotoPick.setBackgroundResource(R.drawable.picture_selected);
                    holder.ivPhoto.setColorFilter(R.color.colorPrimary);
                } else {
                    holder.ivPhotoPick.setBackgroundResource(R.drawable.picture_unselected);
                    holder.ivPhoto.setColorFilter(null);
                }
                break;
            default:
                break;
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 && onPhotoSelectedListener != null) {
            onPhotoSelectedListener.takePhoto();
        } else {
            position--;
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
