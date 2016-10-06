package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: KnowledgeListAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/6 <br/>
 *
 * @author 张志新 <br/>
 */
public class KnowledgeListAdapter extends BaseListViewAdapter{

    private List<String> data;

    public KnowledgeListAdapter(Context mContext, List<String> data) {
        super(mContext);
        this.data = data;
        mDatas = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=View.inflate(mContext,R.layout.item_knowledge_list,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.knowledgeTitle.setText(data.get(position));
        holder.knowledgeContent.setText("怀孕8周就意味着胎儿已经进入了全面发展的阶段。所以现在的胎儿正在火力全开的发育。虽然他还很小，但是发育的非常积极。");
        holder.knowledgeHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/10/6 此处事件需要完善
                Toaster.showShortToast(mContext,"您已收藏");
            }
        });
        holder.knowledgeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/10/6 此处事件需要完善
                Toaster.showShortToast(mContext,"您已转发");
            }
        });
        return convertView;
    }


    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }
        @ViewInject(R.id.tv_knowledge_list_title)
        TextView knowledgeTitle;
        @ViewInject(R.id.tv_knowledge_list_content)
        TextView knowledgeContent;
        @ViewInject(R.id.iv_knowledge_list_heart)
        ImageView knowledgeHeart;
        @ViewInject(R.id.iv_knowledge_list_share)
        ImageView knowledgeShare;
    }
}
