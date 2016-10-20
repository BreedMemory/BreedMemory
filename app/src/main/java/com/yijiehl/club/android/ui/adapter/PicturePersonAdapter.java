/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PicturePersonAdapter.java <br/>
 * Created by 张志新 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.PhotoInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.fragment.PictureFragment;
import com.yijiehl.club.android.ui.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.LinkedList;
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
public class PicturePersonAdapter extends BaseListViewAdapter<List<PhotoInfo>> {

    public PicturePersonAdapter(PictureFragment mFragment) {
        super(mFragment.getActivity());
        this.mFragment = mFragment;
    }

    private PictureFragment mFragment;

    private StartImageViewer mStartImageViewer = new StartImageViewer();

    /**
     * 描 述：伪泛型引起的重载不能编译！大坑！<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/19 <br/>
     */
    @Override
    @Deprecated
    public void setDatas(List<List<PhotoInfo>> mDatas) {
        super.setDatas(mDatas);
    }
    /**
     * 描 述：伪泛型引起的重载不能编译！大坑！<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/19 <br/>
     */
    @Override
    @Deprecated
    public void addDatas(List<List<PhotoInfo>> datas) {
        super.addDatas(datas);
    }

    /**
     * 描 述：设置数据源<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/19 <br/>
     * @param photos 新未分组数据
     */
    public void setData(List<PhotoInfo> photos) {
        if(photos == null || photos.size() == 0) {
            return;
        }
        mDatas = new ArrayList<>();
        mDatas.addAll(arrangePhoto(photos));
        refresh();
    }

    public int getAllCount() {
        int count = 0;
        if (mDatas == null) {
            return  0;
        }
        for (List<PhotoInfo> list : mDatas) {
            count += list.size();
        }
        return count;
    }

    /**
     * 描 述：对相片进行分组添加<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/19 <br/>
     * @param photos 新未分组数据
     */
    public void addData(List<PhotoInfo> photos) {
        if(photos == null || photos.size() == 0) {
            return;
        }
        LinkedList<List<PhotoInfo>> temp = arrangePhoto(photos);
        if (getCount() == 0) {
            mDatas = temp;
            return;
        }
        //如果原始数据的最后一组跟新数据的第一组时间一样则合并
        if(mDatas.get(mDatas.size()-1).get(0).getCreateDay() == temp.getFirst().get(0).getCreateDay()) {
            mDatas.get(mDatas.size()-1).addAll(temp.removeFirst());
        }
        mDatas.addAll(temp);
        refresh();
    }

    /**
     * 描 述：对相片进行分组<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/19 <br/>
     * @param photos 未分组的数据
     * @return 分组后的数据
     */
    private LinkedList<List<PhotoInfo>> arrangePhoto(List<PhotoInfo> photos) {
        LinkedList<List<PhotoInfo>> result = new LinkedList<>();
        for (PhotoInfo photoInfo : photos) {
            //如果组为空或者最后一组的照片日期跟当前照片日期不一样则创建一个新组
            if(result.size() == 0 || result.getLast().get(0).getCreateDay() != photoInfo.getCreateDay()) {
                result.add(new ArrayList<PhotoInfo>());
            }
            result.get(result.size()-1).add(photoInfo);
        }
        return result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_picture_person, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            // convertView = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        // TODO: 2016/9/16 需要给个人相册设置时间和低点

        holder.showTime.setText(TimeUtil.getTime(mDatas.get(position).get(0).getCreateTime(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD));
//        holder.showAddress.setText("北京");
        List<PhotoInfo> dataGrid = mDatas.get(position);
        if (dataGrid != null && dataGrid.size() > 0) {
            holder.gridView.setAdapter(new ImageGridPersonAdapter(mContext, dataGrid));
        }
        holder.gridView.setTag(R.id.picture_position, position);
        /**
         * 图片列表点击事件
         * */
        holder.gridView.setOnItemClickListener(mStartImageViewer);
        return convertView;
    }

    private class StartImageViewer implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if(position == parent.getAdapter().getCount()-1) {
                mFragment.upload();
            } else {
                int groupPosition = (int) parent.getTag(R.id.picture_position);
                ArrayList<String> list = new ArrayList<>();
                list.add(mDatas.get(groupPosition).get(position).getImageInfo());
                ActivitySvc.startImageViewer(mContext, list, false);
            }
        }
    }

    private class ViewHolder {
        ViewHolder(View v) {
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
