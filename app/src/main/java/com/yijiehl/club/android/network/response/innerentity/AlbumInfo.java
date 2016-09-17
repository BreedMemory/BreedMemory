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
    private String dataNum;
    /**
     * 查看次数
     */
    private String viewNum;
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
    private String createTime;
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

    public String getDataNum() {
        return dataNum;
    }

    public void setDataNum(String dataNum) {
        this.dataNum = dataNum;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

}
