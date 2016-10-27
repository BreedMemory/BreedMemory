package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.Article;
import com.yijiehl.club.android.network.response.innerentity.Collection;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: CollectionAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/24 <br/>
 *
 * @author 张志新 <br/>
 */
public class CollectionAdapter extends BaseListViewAdapter<Collection> implements AdapterView.OnItemClickListener{

    public CollectionAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Collection temp=mDatas.get(position);
        ViewHolder holder=null;
        if(convertView==null){
            convertView=View.inflate(mContext,R.layout.item_collection,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.mTitle.setText(temp.getDataType());
        holder.mContent.setText(temp.getDataInfo());
        if(TextUtils.isEmpty(temp.getImageInfo())){
            Glide.with(mContext).load(R.drawable.bg_collect_default).into(holder.mIamge);
        }else{
            Glide.with(mContext).load(ActivitySvc.createResourceUrl(mContext, temp.getImageInfo())).into(holder.mIamge);
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(!TextUtils.isEmpty(mDatas.get(position).getDataShowUrl())){
            Intent intent=new Intent(mContext,ArticalDetailActivity.class);
            intent.putExtra(ArticalDetailActivity.URL, ActivitySvc.createWebUrl(mDatas.get(position).getDataShowUrl()));
            mContext.startActivity(intent);
        }
    }


    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.tv_collect_title)
        TextView mTitle;
        @ViewInject(R.id.tv_collect_content)
        TextView mContent;
        @ViewInject(R.id.iv_collect_image)
        ImageView mIamge;
    }
}
