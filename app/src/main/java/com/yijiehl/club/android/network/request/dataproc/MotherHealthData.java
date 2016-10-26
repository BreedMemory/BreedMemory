/**
 * 项目名称：手机大管家
 * 文件名称: MotherHealthData.java
 * Created by 谌珂 on 2016/10/26.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: MotherHealthData<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public abstract class MotherHealthData extends BaseDataEntity {

    public MotherHealthData(String statTime, String statValue01, String statValue10, String statValue11, String statValue12) {
        this.statValue01 = statValue01;
        this.statValue10 = statValue10;
        this.statValue11 = statValue11;
        this.statValue12 = statValue12;
        this.statTime = statTime;
    }

    protected String statTime;
    protected String dataSummary;
    protected String dataInfo1;
    protected String dataInfo2;
    protected String statValue01;
    protected String statValue02;
    protected String statValue05;
    protected String statValue06;
    protected String statValue07;
    protected String statValue10;
    protected String statValue11;
    protected String statValue12;
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

    public String getStatValue05() {
        return statValue05;
    }

    public void setStatValue05(String statValue05) {
        this.statValue05 = statValue05;
    }

    public String getStatValue06() {
        return statValue06;
    }

    public void setStatValue06(String statValue06) {
        this.statValue06 = statValue06;
    }

    public String getStatValue07() {
        return statValue07;
    }

    public void setStatValue07(String statValue07) {
        this.statValue07 = statValue07;
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

    public String getStatValue12() {
        return statValue12;
    }

    public void setStatValue12(String statValue12) {
        this.statValue12 = statValue12;
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
        return "crm_hldata_item_my";
    }
}
