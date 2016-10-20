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
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.CollectArticle;
import com.yijiehl.club.android.network.response.innerentity.Article;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: GrowUpContentAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 张志新 <br/>
 */
public class GrowUpContentAdapter extends BaseListViewAdapter<Article> implements AdapterView.OnItemClickListener {
    public static final int ALL_DATA = 0;
    public static final int HEALTH_DATA = 1;
    public static final int EDUCATION_DATA = 2;

    /** 显示模式 {@link #ALL_DATA}, {@link #HEALTH_DATA}, {@link #EDUCATION_DATA} */
    private int mode;
    private List<Article> allData = new ArrayList<>();
    private List<Article> healthData;
    private List<Article> educationData;

    private MyClickListener listener = new MyClickListener();

    public GrowUpContentAdapter(Context mContext, int mode) {
        super(mContext);
        this.mode = mode;
        mDatas = allData;
    }

    public void setMode(int mode) {
        this.mode = mode;
        switch (mode) {
            case ALL_DATA:
                mDatas = allData;
                break;
            case HEALTH_DATA:
                mDatas = healthData;
                break;
            case EDUCATION_DATA:
                mDatas = educationData;
                break;
            default:
                break;
        }
        refresh();
    }

    /**
     * 描 述：设置数据源<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
     * @param mode 数据源类型 {@link #ALL_DATA}, {@link #HEALTH_DATA}, {@link #EDUCATION_DATA}
     * @param datas 数据源
     */
    public void setDatas(int mode, List<Article> datas) {
        if(datas == null || datas.size() < 1) {
            return;
        }
        switch (mode) {
            case ALL_DATA:
                throw new IllegalArgumentException("Can not set all data!");
            case HEALTH_DATA:
                healthData = datas;
                break;
            case EDUCATION_DATA:
                educationData = datas;
                break;
            default:
                break;
        }
        allData.clear();
        allData.addAll(datas);
        refresh();
    }

    public List<Article> getDatas(int mode) {
        switch (mode) {
            case HEALTH_DATA:
                return healthData;
            case EDUCATION_DATA:
                return educationData;
            default:
                return allData;
        }
    }

    /**
     * 描 述：添加数据源<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
     * @param mode 数据源类型 {@link #ALL_DATA}, {@link #HEALTH_DATA}, {@link #EDUCATION_DATA}
     * @param datas 数据源
     */
    public void addDatas(int mode, List<Article> datas) {
        if(datas == null || datas.size() < 1) {
            return;
        }
        switch (mode) {
            case ALL_DATA:
                throw new IllegalArgumentException("Can not set all data!");
            case HEALTH_DATA:
                if(healthData == null) {
                    healthData = datas;
                } else {
                    healthData.addAll(datas);
                }
                break;
            case EDUCATION_DATA:
                if(educationData == null) {
                    educationData = datas;
                } else {
                    educationData.addAll(datas);
                }
                break;
            default:
                break;
        }
        allData.addAll(datas);
        refresh();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_growup, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            // convertView = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        if(mDatas.get(position).isCollected()) {
            holder.ivheart.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.ivheart.setTextColor(mContext.getResources().getColor(R.color.textColorHint));
        }
        holder.tvtitle.setText(mDatas.get(position).getDataName());
        holder.tvcontext.setText(mDatas.get(position).getDataSummary());
        holder.ivshare.setTag(R.id.share, position);
        holder.ivheart.setTag(R.id.collect, position);
        holder.ivshare.setOnClickListener(listener);
        holder.ivheart.setOnClickListener(listener);

        Glide.with(mContext).load(ActivitySvc.createResourceUrl(mContext, mDatas.get(position).getImageInfo())).into(holder.ivPic);
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final int position;
            switch (v.getId()) {
                case R.id.iv_item_heart:
                    position = (int) v.getTag(R.id.collect);
                    Article artivle = mDatas.get(position);
                    if(artivle.isCollected()) {
                        return;
                    }
                    CollectArticle req = new CollectArticle(artivle.getDataName(),
                            artivle.getDataLable(),
                            artivle.getImageInfo(),
                            artivle.getDataShowUrl(),
                            artivle.getDataSummary());
                    NetHelper.getDataFromNet(mContext, new ReqBaseDataProc(mContext, req), new AbstractCallBack(mContext) {
                        @Override
                        public void onSuccess(AbstractResponse pResponse) {
                            mDatas.get(position).setCollected(true);
                            refresh();
                            Toaster.showShortToast(mContext, mContext.getString(R.string.collect_success));
                        }
                    });
                    break;
                case R.id.iv_item_share:
                    // TODO: 2016/10/2
                    Toaster.showShortToast(mContext,"您已分享");
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(TextUtils.isEmpty(mDatas.get(position).getDataShowUrl())) {
            return;
        }
        Intent intent=new Intent(mContext, ArticalDetailActivity.class);
        intent.putExtra(ArticalDetailActivity.URL, ActivitySvc.createWebUrl(mDatas.get(position).getDataShowUrl()));
        mContext.startActivity(intent);
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }
        @ViewInject(R.id.iv_item_pic)
        ImageView ivPic;
        @ViewInject(R.id.iv_item_heart)
        IconTextView ivheart;
        @ViewInject(R.id.iv_item_share)
        IconTextView ivshare;
        @ViewInject(R.id.tv_item_title)
        TextView tvtitle;
        @ViewInject(R.id.tv_item_content)
        TextView tvcontext;
    }
}
