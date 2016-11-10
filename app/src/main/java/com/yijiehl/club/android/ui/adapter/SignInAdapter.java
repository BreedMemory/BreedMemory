package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.SignIn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: SignInAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/19 <br/>
 *
 * @author 张志新 <br/>
 */
public class SignInAdapter extends BaseListViewAdapter<SignIn> {

    public SignInAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SignIn signIn = mDatas.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sign_in, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvWeek.setText(new SimpleDateFormat("EEEE").format(new Date(signIn.getCreateTime())));
        holder.tvDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(signIn.getCreateTime())));
        holder.tvTime.setText(new SimpleDateFormat("HH:mm").format(new Date(signIn.getCreateTime())));
        holder.tvSign.setText("签到成功");
        return convertView;
    }


    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.tv_week)
        TextView tvWeek;
        @ViewInject(R.id.tv_date)
        TextView tvDate;
        @ViewInject(R.id.tv_time)
        TextView tvTime;
        @ViewInject(R.id.tv_sign_success)
        TextView tvSign;


    }
}
