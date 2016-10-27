/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqSearchActivitys.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchActivitys;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSearchActivitys <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 * @author 谌珂 <br/>
 */
public class ReqSearchActivitys extends ReqBaseSearch {
    /**
     * 描 述：查询活动<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/14 <br/>
     * @param context 上下文
     * @param isLatest 如果为true只查询最新的一条活动
     */
    //Context context, String key, int start
    public ReqSearchActivitys(Context context, boolean isLatest, String key, int start) {
        super(context);
        this.dataClfy = isLatest?"latest":"";
        setKeyword(key);
        this.start = start;
    }

    /**
     * 描 述：根据id查询活动详情<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/14 <br/>
     * @param context 上下文
     * @param id 活动id
     */
    public ReqSearchActivitys(Context context, String id) {
        super(context);
        this.dataId = id;
    }

    @Override
    protected String getBizType() {
        return "crm_activity_main";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchActivitys.class;
    }

    @Override
    public boolean isGet() {
        return true;
    }
}
