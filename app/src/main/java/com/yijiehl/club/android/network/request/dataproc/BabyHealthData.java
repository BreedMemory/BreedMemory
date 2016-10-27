/**
 * 项目名称：手机在线 <br/>
 * 文件名称: BabyHealthData.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/27.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: BabyHealthData <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/27 <br/>
 * @author 谌珂 <br/>
 */
public abstract class BabyHealthData extends BaseDataEntity {

    /**
     * @param statTime     日期
     * @param statValue03 身高
     * @param statValue01 体重
     * @param statValue35 病名
     * @param statValue36 开始时间
     * @param statValue37 持续天数
     */
    public BabyHealthData(String statTime, String statValue03, String statValue01, String statValue35, String statValue36, String statValue37) {
        this.statTime = statTime;
        this.statValue03 = statValue03;
        this.statValue01 = statValue01;
        this.statValue35 = statValue35;
        this.statValue36 = statValue36;
        this.statValue37 = statValue37;
    }

    protected String statTime;
    protected String dataSummary;
    protected String dataInfo1;
    protected String dataInfo2;
    protected String statValue01;
    protected String statValue02;
    protected String statValue03;
    protected String statValue10;
    protected String statValue11;
    protected String statValue21;
    protected String statValue22;
    protected String statValue25;
    protected String statValue26;
    protected String statValue35;
    protected String statValue36;
    protected String statValue37;
    protected String fileDesc;
    protected String fileFlag;

    public String getStatTime() {
        return statTime;
    }

    public void setStatTime(String statTime) {
        this.statTime = statTime;
    }

    public String getDataSummary() {
        return dataSummary;
    }

    public void setDataSummary(String dataSummary) {
        this.dataSummary = dataSummary;
    }

    public String getDataInfo1() {
        return dataInfo1;
    }

    public void setDataInfo1(String dataInfo1) {
        this.dataInfo1 = dataInfo1;
    }

    public String getDataInfo2() {
        return dataInfo2;
    }

    public void setDataInfo2(String dataInfo2) {
        this.dataInfo2 = dataInfo2;
    }

    public String getStatValue01() {
        return statValue01;
    }

    public void setStatValue01(String statValue01) {
        this.statValue01 = statValue01;
    }

    public String getStatValue02() {
        return statValue02;
    }

    public void setStatValue02(String statValue02) {
        this.statValue02 = statValue02;
    }

    public String getStatValue03() {
        return statValue03;
    }

    public void setStatValue03(String statValue03) {
        this.statValue03 = statValue03;
    }

    public String getStatValue10() {
        return statValue10;
    }

    public void setStatValue10(String statValue10) {
        this.statValue10 = statValue10;
    }

    public String getStatValue11() {
        return statValue11;
    }

    public void setStatValue11(String statValue11) {
        this.statValue11 = statValue11;
    }

    public String getStatValue21() {
        return statValue21;
    }

    public void setStatValue21(String statValue21) {
        this.statValue21 = statValue21;
    }

    public String getStatValue22() {
        return statValue22;
    }

    public void setStatValue22(String statValue22) {
        this.statValue22 = statValue22;
    }

    public String getStatValue25() {
        return statValue25;
    }

    public void setStatValue25(String statValue25) {
        this.statValue25 = statValue25;
    }

    public String getStatValue26() {
        return statValue26;
    }

    public void setStatValue26(String statValue26) {
        this.statValue26 = statValue26;
    }

    public String getStatValue35() {
        return statValue35;
    }

    public void setStatValue35(String statValue35) {
        this.statValue35 = statValue35;
    }

    public String getStatValue36() {
        return statValue36;
    }

    public void setStatValue36(String statValue36) {
        this.statValue36 = statValue36;
    }

    public String getStatValue37() {
        return statValue37;
    }

    public void setStatValue37(String statValue37) {
        this.statValue37 = statValue37;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public String getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag;
    }

    @Override
    protected String getDataModle() {
        return "hldata_item_child";
    }
}
