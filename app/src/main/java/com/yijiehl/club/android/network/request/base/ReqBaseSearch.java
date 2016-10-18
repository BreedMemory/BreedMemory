/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqBaseSearch.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/12.  <br/>
 */
package com.yijiehl.club.android.network.request.base;

import android.content.Context;

import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.task.DefaultTask;

import java.util.Calendar;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqBaseSearch <br/>
 * 类描述: 搜索请求接口的基类<br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/12 <br/>
 * @author 谌珂 <br/>
 */
public abstract class ReqBaseSearch extends ReqBase {

    /** 业务类型 */
    protected String bizType = getBizType();
    /** 搜索的关键词,可为空, 为空就搜索全部数据,该数据需要用URLEncoder处理一下 */
    protected String keyword;
    /** 数据分类，可为空，查询条件的分类编码和结果中的分类可以不一样 */
    protected String dataClfy;
    /** 数据对象标识，可选，比如当搜索某个群组的成员时需要传入群组ID，搜索单个群组的成员时传入群组ID */
    protected String dataId;
    /** 扩展参数，可选，不一定有，无参数时值为 [] ，数据如果不为空需要用URLEncoder处理一下 */
    protected String dataParams = "[]";
    /** 客户端时区 */
    protected String timeZone = Calendar.getInstance().getTimeZone().getDisplayName();
    /** 开始时间,可选，如果有格式形如 2014-09-08 16:45:36 */
    protected String startTime;
    /** 可选，如果有格式形如 2014-09-08 16:45:36 */
    protected String endTime;
    /** 可选，用于分页查询数据，默认为0，表示数据从最前面开始传送，需要与下面的limit配合使用，比如limit值为25，那么查询第二页的数据则值为 start=25，第三页的值为start=50, 每四页的值为start=75等 */
    protected int start;
    /** 可选，用于分页查询数据，一页大小，为空或小于等于0表示查全部或第一页，当不设置时系统默认每次最多传送50条数据。 */
    protected int limit = 10;

    public ReqBaseSearch(Context context) {
        super(context);
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDataClfy() {
        return dataClfy;
    }

    public void setDataClfy(String dataClfy) {
        this.dataClfy = dataClfy;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataParams() {
        return dataParams;
    }

    public void setDataParams(String dataParams) {
        this.dataParams = dataParams;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 描 述：获取搜索业务类型<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/12 <br/>
     */
    protected abstract String getBizType();

    @Override
    public String getPath() {
        return "crmsearch.htm";
    }

    @Override
    public Class<? extends AbstractTask> getTaskClass() {
        return DefaultTask.class;
    }

    @Override
    public boolean isGet() {
        return false;
    }
}
