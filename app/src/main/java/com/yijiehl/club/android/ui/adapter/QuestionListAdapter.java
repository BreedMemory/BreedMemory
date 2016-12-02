package com.yijiehl.club.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.CollectQuestion;
import com.yijiehl.club.android.network.response.innerentity.Answer;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.ShareSvc;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class QuestionListAdapter extends BaseListViewAdapter<Answer> implements AdapterView.OnItemClickListener {

    public QuestionListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_question_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.questionTitle.setText(mDatas.get(position).getDataContent());
        holder.questionContent.setText(mDatas.get(position).getReplyInfo());
        //holder.questionTime.setText(DateUtils.formatDateTime(mContext,data.get(position).getCreateTime(),0));
        holder.questionTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(mDatas.get(position).getCreateTime())));
        //holder.questionAnswer.setText((data.get(position).getEplyFlag()==0?"未回复":"回复"));
        if (mDatas.get(position).isCollected()) {
            holder.questionHeart.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.questionHeart.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.questionHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DONE: 2016/10/4 此处事件需要完善
                    CollectQuestion req = new CollectQuestion(mDatas.get(position).getDataContent(), null,
                            mDatas.get(position).getImageInfo(), mDatas.get(position).getDataShowUrl(), mDatas.get(position).getReplyInfo());
                    NetHelper.getDataFromNet(mContext, new ReqBaseDataProc(mContext, req), new AbstractCallBack(mContext) {
                        @Override
                        public void onSuccess(AbstractResponse pResponse) {
                            refresh();
                            Toaster.showShortToast(mContext, mContext.getString(R.string.collect_success));
                        }
                    });
                }
            });
        }
        holder.questionShare.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        holder.questionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DONE: 2016/10/4 此处事件需要完善
                ShareSvc.shareUrl((Activity) mContext, ActivitySvc.createWebUrl(mDatas.get(position).getDataShowUrl()), mDatas.get(position).getDataContent(), mDatas.get(position).getReplyInfo());
            }
        });
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (TextUtils.isEmpty(mDatas.get(position).getDataShowUrl())) {
            return;
        }
        ActivitySvc.startArticle((Activity) mContext, true,
                ActivitySvc.createWebUrl(mDatas.get(position).getDataShowUrl()),
                mDatas.get(position).getDataContent(),
                mDatas.get(position).getDataDesc(),
                mDatas.get(position).getImageInfo(),
                mDatas.get(position).getReplyInfo(),
                mContext.getResources().getString(R.string.question));
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
        IconTextView questionHeart;
        @ViewInject(R.id.iv_question_list_share)
        IconTextView questionShare;
    }

    public void setCollected(String url) {
        for (Answer answer : mDatas) {
            if (url.endsWith(answer.getDataShowUrl())) {
                answer.setCollected(true);
                notifyDataSetChanged();
                break;
            }
        }
    }
}
