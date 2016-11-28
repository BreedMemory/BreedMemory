/**
 * 项目名称：手机在线 <br/>
 * 文件名称: RelateListAdapter.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/10/24.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

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
import com.yijiehl.club.android.network.request.dataproc.DeleteRelation;
import com.yijiehl.club.android.network.response.innerentity.Account;
import com.yijiehl.club.android.ui.activity.user.AddRelativesAccountActivity;
import com.yijiehl.club.android.ui.dialog.BaseDialog;
import com.yijiehl.club.android.ui.dialog.MessageDialog;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: RelateListAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/24 <br/>
 * @author 谌珂 <br/>
 */
public class RelateListAdapter extends BaseListViewAdapter<Account> implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public RelateListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Account temp = mDatas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_relate_account_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.relate.setText(temp.getRelationCode(mContext));
        holder.account.setText(temp.getMobileNum());
        holder.name.setText(temp.getDataName());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // DONE: 谌珂 2016/10/24 点击到底部按钮进入添加
        if (getCount() == position) {
            Intent intent = new Intent(mContext, AddRelativesAccountActivity.class);
            intent.putExtra(AddRelativesAccountActivity.MODE, false);
            intent.putExtra(AddRelativesAccountActivity.DATACODE, "");
            mContext.startActivity(intent);
        }else{
            Intent intent = new Intent(mContext, AddRelativesAccountActivity.class);
            intent.putExtra(AddRelativesAccountActivity.RELATIONCODE, mDatas.get(position).getRelationCode(mContext));
            intent.putExtra(AddRelativesAccountActivity.DATANAME, mDatas.get(position).getDataName());
            intent.putExtra(AddRelativesAccountActivity.MOBILENUM, mDatas.get(position).getMobileNum());
            intent.putExtra(AddRelativesAccountActivity.AUTH1, mDatas.get(position).getAccessAuth1());
            intent.putExtra(AddRelativesAccountActivity.AUTH2, mDatas.get(position).getAccessAuth2());
            intent.putExtra(AddRelativesAccountActivity.AUTH5, mDatas.get(position).getAccessAuth5());
            intent.putExtra(AddRelativesAccountActivity.AUTH6, mDatas.get(position).getAccessAuth6());
            intent.putExtra(AddRelativesAccountActivity.MODE, true);
            intent.putExtra(AddRelativesAccountActivity.DATACODE, mDatas.get(position).getDataCode());
            mContext.startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        if (getCount() != position) {

            MessageDialog dialog = MessageDialog.getInstance(mContext);
            dialog.setMessage(R.string.do_you_delete_me);
            dialog.showDoubleBtnDialog(new BaseDialog.OnBtnsClickListener() {
                @Override
                public void onLeftClickListener(View v, BaseDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onRightClickListener(View v, final BaseDialog dialog) {
                    NetHelper.getDataFromNet(mContext, new ReqBaseDataProc(mContext, new DeleteRelation(mDatas.get(position).getDataName(), mDatas.get(position).getMobileNum(), mDatas.get(position).getRelationCode(mContext),mDatas.get(position).getDataCode())), new AbstractCallBack(mContext) {
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
        }
        return true;
    }

    class ViewHolder {
        public ViewHolder(View v) {
            InjectUtils.injectViews(v, this);
        }

        @ViewInject(R.id.tv_relate)
        TextView relate;
        @ViewInject(R.id.tv_account)
        TextView account;
        @ViewInject(R.id.tv_name)
        TextView name;
    }
}
