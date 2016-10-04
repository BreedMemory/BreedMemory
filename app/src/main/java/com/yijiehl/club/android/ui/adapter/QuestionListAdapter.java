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
 * 类  名: QuestionListAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/4 <br/>
 *
 * @author 张志新 <br/>
 */
public class QuestionListAdapter extends BaseListViewAdapter {

    private List<String> data;

    public QuestionListAdapter(Context mContext, List<String> data) {
        super(mContext);
        this.data = data;
        mDatas = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=View.inflate(mContext,R.layout.item_question_list,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.questionTitle.setText(data.get(position));
        holder.questionContent.setText(data.get(position));
        holder.questionTime.setText("2016-10-04");
        holder.questionAnswer.setText("已回复");
        holder.questionHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/10/4 此处事件需要完善
                Toaster.showShortToast(mContext,"您已收藏");
            }
        });
        holder.questionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/10/4 此处事件需要完善
                Toaster.showShortToast(mContext,"您已转发");
            }
        });
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }
        @ViewInject(R.id.tv_question_list_title)
        TextView questionTitle;
        @ViewInject(R.id.tv_question_list_content)
        TextView questionContent;
        @ViewInject(R.id.tv_question_list_time)
        TextView questionTime;
        @ViewInject(R.id.tv_question_list_answer)
        TextView questionAnswer;
        @ViewInject(R.id.iv_question_list_heart)
        ImageView questionHeart;
        @ViewInject(R.id.iv_question_list_share)
        ImageView questionShare;
    }
}
