/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PhotoInfo.java <br/>
 * Created by 张志新 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.response.innerentity;/**
 * Created by asus on 2016/9/14.
 */

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
public class PhotoInfo {

    /**
     * 数据标签
     */
    private String dataLabel;
    /**
     * 所在相册ID
     */
    private String albumId;
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
    private String createTime;

    public String getCreateDay() {
        return createDay;
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay;
    }

    /**
     * 创建对应的日期值(天),形如 20160916、20160917 等，可用于按日期分类
     */
    private String createDay;
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

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
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

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
