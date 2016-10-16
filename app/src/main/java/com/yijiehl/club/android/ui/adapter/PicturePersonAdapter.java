/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PicturePersonAdapter.java <br/>
 * Created by 张志新 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.PhotoInfo;
import com.yijiehl.club.android.ui.activity.photo.ImageViewerActivity;
import com.yijiehl.club.android.ui.activity.photo.UploadPhotoActivity;
import com.yijiehl.club.android.ui.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PicturePersonAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 *
 * @author 张志新 <br/>
 */
public class PicturePersonAdapter extends BaseListViewAdapter {

    private List<List<PhotoInfo>> data;//listview的数据源
    private List<PhotoInfo> dataGrid;//gridview 数据源

    public PicturePersonAdapter(Context mContext) {
        super(mContext);
    }

    public PicturePersonAdapter(Context mContext, List<List<PhotoInfo>> data) {
        super(mContext);
        this.data = data;
        mDatas = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_picture_person, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            // convertView = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        // TODO: 2016/9/16 需要给个人相册设置时间和低点
        holder.showTime.setText(data.get(position).get(0).getCreateDay());
        holder.showAddress.setText("北京");
        dataGrid = data.get(position);
        if (dataGrid != null && dataGrid.size() > 0) {
            holder.gridView.setAdapter(new ImageGridPersonAdapter(mContext, dataGrid));
        }
        /**
         * 图片列表点击事件
         * */
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, ImageViewerActivity.class);
                intent.putExtra(ImageViewerActivity.NATIVE, false);
                ArrayList<String> list = new ArrayList<>();
                // TODO: 2016/9/16 替换为真实数据
                list.add("http://pic17.nipic.com/20111119/7718434_152058893000_2.jpg");
                list.add("http://www.vnbaby.cn/uploadfile/2013/0121/20130121093919299.jpg");
                list.add("http://imgx.xiawu.com/xzimg/i4/i7/T1UW9VXi4sXXa4zfs__105950.jpg");
                intent.putStringArrayListExtra(UploadPhotoActivity.PATH, list);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.tv_show_time)
        TextView showTime;
        @ViewInject(R.id.iv_show_uploading)
        ImageView upLoading;
        @ViewInject(R.id.tv_show_address)
        TextView showAddress;
        @ViewInject(R.id.person_gridview)
        NoScrollGridView gridView;
    }
}
