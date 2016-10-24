/**
 * 项目名称：手机在线 <br/>
 * 文件名称: RelateListAdapter.java <br/>
 * <p>
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
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.Account;
import com.yijiehl.club.android.ui.activity.user.AddRelativesAccount;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: RelateListAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/24 <br/>
 * @author 谌珂 <br/>
 */
public class RelateListAdapter extends BaseListViewAdapter<Account> implements AdapterView.OnItemClickListener {
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
        if(getCount() == position) {
            mContext.startActivity(new Intent(mContext, AddRelativesAccount.class));
        }
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
