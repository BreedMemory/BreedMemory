/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: UploadPicture.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/19.  <br/>
 */
package com.yijiehl.club.android.network.request.dataproc;

import java.io.File;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: UploadPicture <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/19 <br/>
 * @author 谌珂 <br/>
 */
public class UploadPicture extends BaseDataEntity {
    public UploadPicture(File file) {
        this.fileName = file.getName();
        this.fileSize = String.valueOf(file.length()/8);
    }

    /** 相片名称 可为空 */
    private String dataName;
    /** 标签信息 可为空，多个标签以空格分开 */
    private String dataLabel;
    /** 相册ID 可为空，如果没有关联相册则该值为空，比如单独的个人相片上传 */
    private String albumId;
    /** 上传文件名称 必填, 原始文件名 */
    private String fileName;
    /** 上传文件大小 必填 */
    private String fileSize;
    /** 形如560*240  */
    private String imageSize;
    /** 是否缩略/压缩过  */
    private String compressFlag;
    /** 位置信息 可为空，如果能取到图片的位置信息则上传  */
    private String localInfo;
    /** 可为空，如能够取到则上传一个长整型的时间，java里面一般用 file.lastModified() 来获取(仅参考)  */
    private String fileTime;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getCompressFlag() {
        return compressFlag;
    }

    public void setCompressFlag(String compressFlag) {
        this.compressFlag = compressFlag;
    }

    public String getLocalInfo() {
        return localInfo;
    }

    public void setLocalInfo(String localInfo) {
        this.localInfo = localInfo;
    }

    public String getFileTime() {
        return fileTime;
    }

    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }

    @Override
    protected String getDataModle() {
        return "crm_photo_detail";
    }

    @Override
    protected OperateType getOperateType() {
        return OperateType.UPLOAD;
    }
}
