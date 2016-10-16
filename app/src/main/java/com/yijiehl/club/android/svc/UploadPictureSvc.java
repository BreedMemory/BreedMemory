/**
 * 项目名称：手机大管家
 * 文件名称: UploadPictureSvc.java
 * Created by 谌珂 on 2016/10/16.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.svc;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.uuzz.android.util.net.NetHelper;
import com.yijiehl.club.android.entity.UploadPictureMessage;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.UploadPicture;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;
import com.yijiehl.club.android.network.response.base.BaseResponse;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: UploadPictureSvc<br/>
 * 类描述: 上传图片任务类，可设置监听者，收到的监听者消息都在子线程中<br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class UploadPictureSvc extends Observable implements Observer {
    /** 上传图片成功的消息 */
    public static final int UPLOAD_SUCCESS = 0;
    /** 上传图片失败的消息 */
    public static final int UPLOAD_FAILED = 1;
    /** 上传图片完成的消息，可能既包含成功也包含失败，主要针对一组图片的上传 */
    public static final int UPLOAD_COMPLETE = 2;

    /** 用于记录分组上传的组标记和上传个数 */
    private HashMap<Long, Integer> groupCountPair = new HashMap<>();


    /** 单例 */
    private static UploadPictureSvc instance;

    private UploadPictureSvc() {
        addObserver(this);
    }

    public static UploadPictureSvc getInstance() {
        if(instance == null) {
            synchronized (UploadPictureSvc.class) {
                if(instance == null) {
                    instance = new UploadPictureSvc();
                }
            }
        }
        return instance;
    }

    /**
     * 描 述：上传单张图片，成功后发送{@link #UPLOAD_SUCCESS}，上传失败发送{@link #UPLOAD_FAILED}，附带{@link UploadPictureMessage}<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param context 上下文
     * @param uploadType 上传类型
     * @param tabs 上传标签，多个标签用逗号隔开
     * @param path 本地图片路径
     * @param timestamp 主要用于组上传中标记是哪个组的任务
     */
    public void uploadSinglePicture(final @NonNull Context context, final @NonNull ReqUploadFile.UploadType uploadType, final @Nullable String tabs, final @NonNull String path, final long timestamp) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(path);
                    UploadPicture upload = new UploadPicture(new File(path), tabs);
                    ReqBaseDataProc proc = new ReqBaseDataProc(context, upload);
                    BaseResponse baseResponse = (BaseResponse) NetHelper.getDataFromNet(context, proc, false);
                    if(!baseResponse.getReturnMsg().isSuccess()) {
                        throw new Exception("Get relative code failed!");
                    }
                    ReqUploadFile uploadFile = new ReqUploadFile(context, uploadType, file, baseResponse.getReturnMsg().getResultCode());
                    baseResponse = (BaseResponse) NetHelper.getDataFromNet(context, uploadFile, false);
                    if(!baseResponse.getReturnMsg().isSuccess()) {
                        throw new Exception("Get relative code failed!");
                    }
                    Message msg = Message.obtain();
                    msg.what = UPLOAD_SUCCESS;
                    msg.obj = new UploadPictureMessage(path, baseResponse.getReturnMsg().getResultCode(), timestamp);
                    setChanged();
                    notifyObservers(msg);
                } catch (Exception e) {
                    // DONE: 谌珂 2016/10/16 发送上传失败消息
                    Message msg = Message.obtain();
                    msg.what = UPLOAD_FAILED;
                    msg.obj = new UploadPictureMessage(path, null, timestamp);;
                    setChanged();
                    notifyObservers(msg);
                }
            }
        });
    }

    /**
     * 描 述：上传单张图片，成功后发送{@link #UPLOAD_SUCCESS}，上传失败发送{@link #UPLOAD_FAILED}，附带{@link UploadPictureMessage}</><br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param context 上下文
     * @param uploadType 上传类型
     * @param tabs 上传标签，多个标签用逗号隔开
     * @param path 本地图片路径
     */
    public void uploadSinglePicture(@NonNull Context context, @NonNull ReqUploadFile.UploadType uploadType, @Nullable String tabs, @NonNull String path) {
        uploadSinglePicture(context, uploadType, tabs, path, 0);
    }

    /**
     * 描 述：上传多张图片，所有图片上传任务完成后发送{@link #UPLOAD_COMPLETE}，附带任务时间戳,其中可能包含失败，也可能包含成功<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param context 上下文
     * @param uploadType 上传类型
     * @param tabs 上传标签，多个标签用逗号隔开
     * @param paths 本地图片路径
     * @param timestamp 用于组上传中标记是哪个组的任务
     */
    public void uploadMultiplePicture(@NonNull Context context, @NonNull ReqUploadFile.UploadType uploadType, @Nullable String tabs, @NonNull List<String> paths, long timestamp) {
        groupCountPair.put(timestamp, paths.size());
        for (String path : paths) {
            uploadSinglePicture(context, uploadType, tabs, path, timestamp);
        }
    }

    /**
     * 描 述：用于判断一个上传组是否已经完成<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     */
    @Override
    public void update(Observable observable, Object data) {
        Message msg = (Message) data;
        if(msg.what == UPLOAD_COMPLETE) {          //不接受上传完成消息
            return;
        }
        UploadPictureMessage result = (UploadPictureMessage) msg.obj;
        if(isComplete(result.getTimestamp())) {
            msg = Message.obtain();
            msg.what = UPLOAD_COMPLETE;
            msg.obj = result.getTimestamp();
            setChanged();
            notifyObservers(msg);
        }
    }

    /**
     * 描 述：判断一组任务是否完全完成<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     * @param timestamp 组任务时间戳
     * @return 上传完成返回true，未完成或不是组上传任务返回false
     */
    private synchronized boolean isComplete(long timestamp) {
        if(groupCountPair.get(timestamp) == null) {
            return false;
        }
        int count = groupCountPair.get(timestamp);
        if(count > 1) {                    //剩余个数大于1说明任务还未完成，把剩余个数-1
            groupCountPair.put(timestamp, --count);
            return false;
        }
        groupCountPair.remove(timestamp);  //上传完成后从groupCountPair中移除此时间戳
        return true;
    }
}
