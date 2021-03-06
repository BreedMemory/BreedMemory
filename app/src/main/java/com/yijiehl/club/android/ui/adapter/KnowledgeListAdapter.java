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
import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.CollectArticle;
import com.yijiehl.club.android.network.response.innerentity.Article;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.ShareSvc;

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
public class KnowledgeListAdapter extends BaseListViewAdapter<Article> implements AdapterView.OnItemClickListener {


    public KnowledgeListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Article temp = mDatas.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_knowledge_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.knowledgeTitle.setText(temp.getDataName());
        holder.knowledgeContent.setText(temp.getDataSummary());
        if (temp.isCollected()) {
            holder.knowledgeHeart.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.knowledgeHeart.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.knowledgeHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DONE: 2016/10/6 此处事件需要完善
                    CollectArticle req = new CollectArticle(temp.getDataName(),
                            temp.getDataLable(),
                            temp.getImageInfo(),
                            temp.getDataShowUrl(),
                            temp.getDataSummary());
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
        holder.knowledgeShare.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        holder.knowledgeHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DONE: 2016/10/6 此处事件需要完善
                CollectArticle req = new CollectArticle(temp.getDataName(),
                        temp.getDataLable(),
                        temp.getImageInfo(),
                        temp.getDataShowUrl(),
                        temp.getDataSummary());
                NetHelper.getDataFromNet(mContext, new ReqBaseDataProc(mContext, req), new AbstractCallBack(mContext) {
                    @Override
                    public void onSuccess(AbstractResponse pResponse) {
                        refresh();
                        Toaster.showShortToast(mContext, mContext.getString(R.string.collect_success));
                    }
                });
            }
        });
        holder.knowledgeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DONE: 2016/10/6 此处事件需要完善
                ShareSvc.shareUrl((Activity) mContext,ActivitySvc.createWebUrl(temp.getDataShowUrl()),temp.getDataName(),temp.getDataSummary());
            }
        });
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!TextUtils.isEmpty(mDatas.get(position).getDataShowUrl())) {
            ActivitySvc.startArticle((Activity) mContext, true,
                    ActivitySvc.createWebUrl(mDatas.get(position).getDataShowUrl()),
                    mDatas.get(position).getDataName(),
                    mDatas.get(position).getDataLable(),
                    mDatas.get(position).getImageInfo(),
                    mDatas.get(position).getDataSummary());
        }
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
        IconTextView knowledgeHeart;
        @ViewInject(R.id.iv_knowledge_list_share)
        IconTextView knowledgeShare;
    }

    public void setCollected(String url) {
        for (Article answer : mDatas) {
            if (url.endsWith(answer.getDataShowUrl())) {
                answer.setCollected(true);
                notifyDataSetChanged();
                break;
            }
        }
    }
}
