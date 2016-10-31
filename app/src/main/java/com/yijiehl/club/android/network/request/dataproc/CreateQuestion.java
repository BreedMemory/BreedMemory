package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: CreateQuestion <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/21 <br/>
 *
 * @author 张志新 <br/>
 */
public class CreateQuestion extends BaseDataEntity {

    public CreateQuestion(String dataContent, boolean hasFile) {
        this.dataContent = dataContent;
        this.fileFlag = hasFile ? 1 : 0;
    }

    /**
     * 是否附带有文件,1表示有 0或空表示没有
     */
    private int fileFlag;
    /**
     * 是否附带有文件,1表示有 0或空表示没有
     */
    private String dataContent;

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public int getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(int fileFlag) {
        this.fileFlag = fileFlag;
    }

    @Override
    protected String getDataModel() {
        return "kb_question_main";
    }

    @Override
    protected OperateType getOperateType() {
        return OperateType.CREATE;
    }
}
