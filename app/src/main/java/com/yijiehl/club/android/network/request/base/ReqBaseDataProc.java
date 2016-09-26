/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqBaseDataProc.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/13.  <br/>
 */
package com.yijiehl.club.android.network.request.base;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.request.dataproc.BaseDataEntity;
import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.task.DefaultTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqBaseDataProc <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/13 <br/>
 * @author 谌珂 <br/>
 */
public class ReqBaseDataProc extends ReqBase {
    public ReqBaseDataProc(Context context, BaseDataEntity entity) {
        super(context);
        List<BaseDataEntity> list = new ArrayList<>();
        list.add(entity);
        this.dataList = JSON.toJSONString(list);
    }

    public ReqBaseDataProc(Context context, List<BaseDataEntity> list) {
        super(context);
        this.dataList = JSON.toJSONString(list);
    }

    private String dataList;

    public String getDataList() {
        return dataList;
    }

    public void setDataList(String dataList) {
        this.dataList = dataList;
    }

    @Override
    public String getPath() {
        return "crmdataproc.htm";
    }

    @Override
    public Class<? extends AbstractTask> getTaskClass() {
        return DefaultTask.class;
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return BaseResponse.class;
    }

    @Override
    public boolean isGet() {
        return true;
    }
}
