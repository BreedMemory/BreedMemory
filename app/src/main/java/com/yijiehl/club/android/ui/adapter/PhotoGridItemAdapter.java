package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;

import java.util.LinkedList;
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
public class PhotoGridItemAdapter extends BaseListViewAdapter {

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static List<String> mSelectedPhoto = new LinkedList<String>();
    /**
     * 所有图片
     */
    private List<String> mDatas;

    /**
     * 后面选择照片最大数量
     */
    private int maxCount = 9;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int VIEW_TYPE = 2;

    public PhotoGridItemAdapter(Context mContext, List<String> mDatas) {
        super(mContext);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size() + 1;
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
        ViewHolderTake viewHolderTake = null;
        ViewHolderPick viewHolderPick = null;
        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case TYPE_1:
                    convertView = View.inflate(mContext, R.layout.item_photo_grid_take, null);
                    viewHolderTake = new ViewHolderTake(convertView);
                    convertView.setTag(viewHolderTake);
                    break;
                case TYPE_2:
                    convertView = View.inflate(mContext, R.layout.item_photo_grid_pick, null);
                    viewHolderPick = new ViewHolderPick(convertView);
                    convertView.setTag(viewHolderPick);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_1:
                    viewHolderTake = (ViewHolderTake) convertView.getTag();
                    break;
                case TYPE_2:
                    viewHolderPick = (ViewHolderPick) convertView.getTag();
                    break;
            }
        }
        switch (type) {
            case TYPE_1:
                viewHolderTake.layoutTake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPhotoSelectedListener.takePhoto();
                    }
                });
                break;
            case TYPE_2:
                viewHolderPick.ibPhotoPick.setBackgroundResource(R.drawable.picture_unselected);
                viewHolderPick.ivPhoto.setBackgroundResource(R.mipmap.ic_launcher);

                ImageLoader.getInstance().displayImage("file:///" + mDatas.get(position - 1), viewHolderPick.ivPhoto);
                viewHolderPick.ivPhoto.setColorFilter(null);
                viewHolderPick.ivPhoto.setOnClickListener(new MyOnClickListener(viewHolderPick, position));

                /**已经选好的照片，显示出选择的效果*/
                if (mSelectedPhoto.contains(mDatas.get(position - 1))) {
                    viewHolderPick.ibPhotoPick.setBackgroundResource(R.drawable.pictures_selected);
                    viewHolderPick.ivPhoto.setColorFilter(R.color.colorPrimary);
                } else {
                    viewHolderPick.ibPhotoPick.setBackgroundResource(R.drawable.picture_unselected);
                    viewHolderPick.ivPhoto.setColorFilter(null);
                }
                break;
            default:
                break;
        }
        return convertView;
    }

    class ViewHolderTake {
        public ViewHolderTake(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.layout_grid_take)
        LinearLayout layoutTake;
    }

    class ViewHolderPick {
        public ViewHolderPick(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.iv_item_photo)
        ImageView ivPhoto;
        @ViewInject(R.id.iv_item_pick)
        ImageButton ibPhotoPick;
    }


    /**
     * 选择照片的单击事件
     */
    class MyOnClickListener implements View.OnClickListener {

        ViewHolderPick viewHolderPick;
        int position;

        public MyOnClickListener(ViewHolderPick viewHolderPick, int position) {
            this.viewHolderPick = viewHolderPick;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            //已经选择了该照片
            if (mSelectedPhoto.contains(mDatas.get(position - 1))) {
                mSelectedPhoto.remove(mDatas.get(position - 1));
                viewHolderPick.ibPhotoPick.setBackgroundResource(R.drawable.picture_unselected);
                viewHolderPick.ivPhoto.setColorFilter(null);
            } else {//没有选择该照片
                mSelectedPhoto.add(mDatas.get(position - 1));
                viewHolderPick.ibPhotoPick.setBackgroundResource(R.drawable.pictures_selected);
                viewHolderPick.ivPhoto.setColorFilter(R.color.colorAccent);
            }
            onPhotoSelectedListener.photoClick(mSelectedPhoto);
        }
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
