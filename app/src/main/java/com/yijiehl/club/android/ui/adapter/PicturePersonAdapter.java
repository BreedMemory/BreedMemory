/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PicturePersonAdapter.java <br/>
 * Created by 张志新 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
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
import com.yijiehl.club.android.ui.view.NoScrollGridView;

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
        holder.showTime.setText("");
        holder.showAddress.setText("北京");
        dataGrid = data.get(position);
        if (dataGrid != null && dataGrid.size() > 0) {
            holder.gridView.setAdapter(new ImageGridPersonAdapter(mContext, dataGrid));
        }
        /**
         * 图片列表点击事件
         * */
        // TODO: 2016/9/16 图片点击进行放大查看
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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