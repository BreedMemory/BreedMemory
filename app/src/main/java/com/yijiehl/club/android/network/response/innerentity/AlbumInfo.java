/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: AlbumInfo.java <br/>
 * Created by 张志新 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.response.innerentity;/**
 * Created by asus on 2016/9/14.
 */

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: AlbumInfo <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 *
 * @author 张志新 <br/>
 */
public class AlbumInfo extends RespBaseSearchResult {

    /**
     * 数据标签
     */
    private String dataLabel;
    /**
     * 相片数量,相册里有多少张相片
     */
    private int dataNum;
    /**
     * 查看次数
     */
    private int viewNum;
    /**
     * 图片URL,可为空，可能挑取相册中某一张相片
     */
    private String imageInfo;
    /**
     * 缩略图URL,用于快捷显示
     */
    private String iconInfo1;
    /**
     * 创建时间,长整型时间
     */
    private long createTime;
    /**
     * 更新时间/修改时间
     */
    private long updateTime;
    /**
     * 数据描述,描述
     */
    private String dataDesc;

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public int getDataNum() {
        return dataNum;
    }

    public void setDataNum(int dataNum) {
        this.dataNum = dataNum;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getIconInfo1() {
        return iconInfo1;
    }

    public void setIconInfo1(String iconInfo1) {
        this.iconInfo1 = iconInfo1;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }
}
