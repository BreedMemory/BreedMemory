package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.DeleteCollect;
import com.yijiehl.club.android.network.request.dataproc.DeletePicture;
import com.yijiehl.club.android.network.response.innerentity.Collection;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.user.MyCellectActivity;
import com.yijiehl.club.android.ui.dialog.BaseDialog;
import com.yijiehl.club.android.ui.dialog.MessageDialog;

import java.util.ArrayList;

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
public class CollectionAdapter extends BaseListViewAdapter<Collection> implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public CollectionAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Collection temp = mDatas.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_collection, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (temp.getDataType().indexOf(MyCellectActivity.QUESTION) != -1) {
            holder.mTitle.setText("问题");
        } else if (temp.getDataType().indexOf(MyCellectActivity.PHOTO) != -1) {
            holder.mTitle.setText("照片");
        } else if (temp.getDataType().indexOf(MyCellectActivity.ARTICLE) != -1) {
            holder.mTitle.setText("文章");
        }
        holder.mContent.setText(temp.getDataInfo());
        if (TextUtils.isEmpty(temp.getImageInfo())) {
            Glide.with(mContext).load(R.drawable.bg_collect_default).into(holder.mIamge);
        } else {
            Glide.with(mContext).load(ActivitySvc.createResourceUrl(mContext, temp.getImageInfo())).into(holder.mIamge);
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!TextUtils.isEmpty(mDatas.get(position).getDataShowUrl())) {
            ActivitySvc.startArticle(mContext, true,
                    mDatas.get(position).getDataShowUrl(),
                    mDatas.get(position).getDataName(),
                    mDatas.get(position).getDataLable(),
                    mDatas.get(position).getImageInfo(),
                    mDatas.get(position).getDataInfo());
        } else {
            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> codes = new ArrayList<>();
            list.add(mDatas.get(position).getImageInfo());
            codes.add(mDatas.get(position).getDataCode());
            ActivitySvc.startImageViewer(mContext, list, codes, null, false, 0,true);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        MessageDialog dialog = MessageDialog.getInstance(mContext);
        dialog.setMessage(R.string.do_you_delete_me);
        dialog.showDoubleBtnDialog(new BaseDialog.OnBtnsClickListener() {
            @Override
            public void onLeftClickListener(View v, BaseDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onRightClickListener(View v, final BaseDialog dialog) {
                NetHelper.getDataFromNet(mContext, new ReqBaseDataProc(mContext, new DeleteCollect(mDatas.get(position).getDataCode())), new AbstractCallBack(mContext) {
                    @Override
                    public void onSuccess(AbstractResponse pResponse) {
                        mDatas.remove(position);
                        refresh();
                        dialog.dismiss();
                        Toaster.showShortToast(mContext, mContext.getString(R.string.delete_success));
                    }
                });
            }
        });
        return true;
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
