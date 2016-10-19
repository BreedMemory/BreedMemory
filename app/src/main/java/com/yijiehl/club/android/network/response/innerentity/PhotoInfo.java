/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PhotoInfo.java <br/>
 * Created by 张志新 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.response.innerentity;/**
 * Created by asus on 2016/9/14.
 */

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PhotoInfo <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 *
 * @author 张志新 <br/>
 */
public class PhotoInfo extends RespBaseSearchResult {

    /**
     * 数据标签
     */
    private String dataLabel;
    /**
     * 数据编码
     */
    private String dataCode;
    /**
     * 相片数量,相册里有多少张相片
     */
    private int dataNum;
    /**
     * 所在相册ID
     */
    private long albumId;
    /**
     * 所在相册名称
     */
    private String albumName;
    /**
     * 查看次数
     */
    private String viewNum;
    /**
     * 图片URL,相片URL
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
     * 创建对应的日期值(天) 形如 20160916、20160917 等，可用于按日期分类
     */
    private int createDay;
    /**
     * 更新时间/修改时间 可为空，可用于判断该相片是否修改过
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

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public int getDataNum() {
        return dataNum;
    }

    public void setDataNum(int dataNum) {
        this.dataNum = dataNum;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
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

    public int getCreateDay() {
        return createDay;
    }

    public void setCreateDay(int createDay) {
        this.createDay = createDay;
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
