package com.yijiehl.club.android.network.request;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.request.base.ReqBase;
import com.yijiehl.club.android.network.request.base.ReqBaseLogin;
import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.task.DefaultTask;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqQuiz <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/21 <br/>
 *
 * @author 张志新 <br/>
 */
public class ReqQuiz extends ReqBase {

    public ReqQuiz(Context context,String dataContent, boolean isCreate, boolean hasFile) {
        super(context);
        this.dataContent = dataContent;
        this.opType = isCreate ? "create" : "update";
        this.fileFlag = hasFile ? 1 : 0;
    }

    /**
     * 数据模型,必填
     */
    private String dataModel = "kb_question_main";
    /**
     * 操作类型,必填,create创建或者update修改
     */
    private String opType;
    /**
     * 内容
     */
    private String dataContent;
    /**
     * 描述、补充说明
     */
    private String dataDesc;
    /**
     * 是否附带有文件,1表示有 0或空表示没有
     */
    private int fileFlag;

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
}
