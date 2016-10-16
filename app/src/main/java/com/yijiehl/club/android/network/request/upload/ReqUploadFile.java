/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqUploadFile.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/19.  <br/>
 */
package com.yijiehl.club.android.network.request.upload;

import android.content.Context;
import android.text.TextUtils;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.request.base.ReqBase;
import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.task.UploadTask;

import java.io.File;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqUploadFile <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/19 <br/>
 * @author 谌珂 <br/>
 */
public class ReqUploadFile extends ReqBase {
    public ReqUploadFile(Context context) {
        super(context);
    }

    public ReqUploadFile(Context context, UploadType bizType, File file, String relateCode) {
        super(context);
        this.bizType = bizType.getName();
        this.fileName = file.getName();
        this.fileName = file.getName();
        this.file = file;
        this.relateCode = relateCode;
    }

    /** 必填，业务类型
        msg_file 消息中的文件
        crm_photo_detail  单张相片上传
        user_portrait  用户图像/头像上传
        customer_portrait  客户图像/头像上传 */
    private String bizType;
    /** 关联编码 */
    private String relateCode;
    /** 文件类型 可为空，文件类型, 图像 语音 视频 声音 文件等，值一般是文件扩展名，如jpg png mp4 等 */
    private String fileType;
    /** 必填, 原始文件名称，考虑到有中文、空格等需要使用URLEncoder编码，某些类型的文件是不能上传的，比如 *.exe *.apk 等等 */
    private String fileName;
    /** 需要上传的文件 */
    private File file;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getRelateCode() {
        return relateCode;
    }

    public void setRelateCode(String relateCode) {
        this.relateCode = relateCode;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String getPath() {
        return "resfileup.htm";
    }

    @Override
    public Class<? extends AbstractTask> getTaskClass() {
        return UploadTask.class;
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return BaseResponse.class;
    }

    public enum UploadType {
        MSG_FILE(0, "msg_file"),
        CRM_PHOTO_DETAIL(1, "crm_photo_detail"),
        USER_PORTRAIT(2, "user_portrait"),
        CUSTOMER_PORTRAIT(3, "customer_portrait"),
        STAT_DATA(4, "stat_data");

        private int value;
        private String name;

        UploadType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static UploadType setValue(String name) {
            UploadType[] arr$ = values();
            for (UploadType c : arr$) {
                if (TextUtils.equals(c.getName(), name)) {
                    return c;
                }
            }
            return MSG_FILE;
        }
    }
}
