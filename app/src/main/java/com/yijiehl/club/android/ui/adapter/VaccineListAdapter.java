package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.Vaccine;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: VaccineListAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/31 <br/>
 *
 * @author 张志新 <br/>
 */
public class VaccineListAdapter extends BaseListViewAdapter<Vaccine>{

    public VaccineListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vaccine temp=mDatas.get(position);
        ViewHolder holder;
        if(convertView==null){
            convertView=View.inflate(mContext,R.layout.item_accine_list,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.mName.setText(temp.getDataName());
        holder.mTime.setText(temp.getDataContent());
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.tv_vaccine_time)
        TextView mTime;
        @ViewInject(R.id.tv_vaccine_name)
        TextView mName;
    }
}
