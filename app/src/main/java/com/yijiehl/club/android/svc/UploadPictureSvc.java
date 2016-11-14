/**
 * 项目名称：手机大管家
 * 文件名称: UploadPictureSvc.java
 * Created by 谌珂 on 2016/10/16.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.svc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.uuzz.android.util.FileUtil;
import com.uuzz.android.util.ObservableTag;
import com.uuzz.android.util.ScreenTools;
import com.uuzz.android.util.log.Logger;
import com.uuzz.android.util.net.NetHelper;
import com.yijiehl.club.android.entity.UploadPictureMessage;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.UploadPicture;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;
import com.yijiehl.club.android.network.response.base.BaseResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: UploadPictureSvc<br/>
 * 类描述: 上传图片任务类，可设置监听者，收到的监听者消息都在子线程中<br/>
 *
 * @author 谌珂 <br/>
 *         实现的主要功能<br/>
 *         版    本：1.0.0<br/>
 */
public class UploadPictureSvc extends Observable implements Observer {

    private Logger logger = new Logger(UploadPictureSvc.class);

    /**
     * 用于记录分组上传的组标记和上传个数
     */
    private HashMap<Long, Integer> groupCountPair = new HashMap<>();


    /**
     * 单例
     */
    private static UploadPictureSvc instance;

    private UploadPictureSvc() {
        addObserver(this);
    }

    public static UploadPictureSvc getInstance() {
        if (instance == null) {
            synchronized (UploadPictureSvc.class) {
                if (instance == null) {
                    instance = new UploadPictureSvc();
                }
            }
        }
        return instance;
    }

    /**
     * 描 述：上传单张图片，成功后发送{@link com.uuzz.android.util.ObservableTag#UPLOAD_SUCCESS}，上传失败发送{@link com.uuzz.android.util.ObservableTag#UPLOAD_FAILED}，附带{@link UploadPictureMessage}<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     *
     * @param context    上下文
     * @param uploadType 上传类型
     * @param tabs       上传标签，多个标签用逗号隔开
     * @param path       本地图片路径
     * @param timestamp  主要用于组上传中标记是哪个组的任务
     */
    public void uploadSinglePicture(final @NonNull Context context, final @NonNull ReqUploadFile.UploadType uploadType, final @Nullable String tabs, final @NonNull String path, final long timestamp, final @Nullable String relateCode) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(path);
                    if (!file.exists()) {
                        return;
                    }
                    int degree = readPictureDegree(path);
                    Bitmap bm = getImage(ScreenTools.getScreenPixel(context)[0]/2, ScreenTools.getScreenPixel(context)[1]/2, path);
                    file.delete();
                    FileUtil.saveBitmap(path, bm);
                    file = new File(path);

                    if(degree != 0) {
                        bm = rotateBitmap(bm, degree);
                        file.delete();
                        FileUtil.saveBitmap(path, bm);
                        file = new File(path);
                    }
                    UploadPicture upload = new UploadPicture(new File(path), tabs);
                    ReqBaseDataProc proc = new ReqBaseDataProc(context, upload);
                    BaseResponse baseResponse;
                    String code;
                    if(TextUtils.isEmpty(relateCode)) {
                        baseResponse = (BaseResponse) NetHelper.getDataFromNet(context, proc, false);
                        if(baseResponse.isNeedLogin()) {
                            ActivitySvc.startLoginActivity(context);
                            return;
                        }
                        if (!baseResponse.getReturnMsg().isSuccess()) {
                            throw new Exception("Get relative code failed! " + baseResponse.getReturnMsg().getMessage());
                        }
                        code = baseResponse.getReturnMsg().getResultCode();
                    } else {
                        code = relateCode;
                    }
                    ReqUploadFile uploadFile = new ReqUploadFile(context, uploadType, file, code);
                    baseResponse = (BaseResponse) NetHelper.getDataFromNet(context, uploadFile, false);
                    if(baseResponse.isNeedLogin()) {
                        ActivitySvc.startLoginActivity(context);
                    }
                    if (!baseResponse.getReturnMsg().isSuccess()) {
                        throw new Exception("Upload picture failed! " + baseResponse.getReturnMsg().getMessage());
                    }
                    Message msg = Message.obtain();
                    msg.what = ObservableTag.UPLOAD_SUCCESS;
                    msg.obj = new UploadPictureMessage(path, baseResponse.getReturnMsg().getResultCode(), timestamp);
                    setChanged();
                    notifyObservers(msg);
                } catch (Exception e) {
                    // DONE: 谌珂 2016/10/16 发送上传失败消息
                    Message msg = Message.obtain();
                    msg.what = ObservableTag.UPLOAD_FAILED;
                    msg.obj = new UploadPictureMessage(path, null, timestamp);
                    setChanged();
                    notifyObservers(msg);
                    logger.e("upload picture failed, " + msg.obj.toString()+ " " + e);
                }
            }
        });
    }

    /**
     * 描 述：上传单张图片，成功后发送{@link com.uuzz.android.util.ObservableTag##UPLOAD_SUCCESS}，上传失败发送{@link com.uuzz.android.util.ObservableTag##UPLOAD_FAILED}，附带{@link UploadPictureMessage}</><br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     *
     * @param context    上下文
     * @param uploadType 上传类型
     * @param tabs       上传标签，多个标签用逗号隔开
     * @param path       本地图片路径
     * @param relateCode 关联id
     */
    public void uploadSinglePicture(@NonNull Context context, @NonNull ReqUploadFile.UploadType uploadType, @Nullable String tabs, @NonNull String path, @Nullable String relateCode) {
        uploadSinglePicture(context, uploadType, tabs, path, 0, relateCode);
    }

    /**
     * 描 述：上传单张图片，成功后发送{@link com.uuzz.android.util.ObservableTag##UPLOAD_SUCCESS}，上传失败发送{@link com.uuzz.android.util.ObservableTag##UPLOAD_FAILED}，附带{@link UploadPictureMessage}</><br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     *
     * @param context    上下文
     * @param uploadType 上传类型
     * @param tabs       上传标签，多个标签用逗号隔开
     * @param path       本地图片路径
     */
    public void uploadSinglePicture(@NonNull Context context, @NonNull ReqUploadFile.UploadType uploadType, @Nullable String tabs, @NonNull String path) {
        uploadSinglePicture(context, uploadType, tabs, path, 0, null);
    }

    /**
     * 描 述：上传多张图片，所有图片上传任务完成后发送{@link com.uuzz.android.util.ObservableTag##UPLOAD_COMPLETE}，附带任务时间戳,其中可能包含失败，也可能包含成功<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     *
     * @param context    上下文
     * @param uploadType 上传类型
     * @param tabs       上传标签，多个标签用逗号隔开
     * @param paths      本地图片路径
     * @param timestamp  用于组上传中标记是哪个组的任务
     * @param relateCode 关联id
     */
    public void uploadMultiplePicture(@NonNull Context context, @NonNull ReqUploadFile.UploadType uploadType, @Nullable String tabs, @NonNull List<String> paths, long timestamp, @Nullable String relateCode) {
        if (paths == null || paths.size() == 0 || paths.isEmpty()) {
            return;
        }
        groupCountPair.put(timestamp, paths.size());
        for (String path : paths) {
            uploadSinglePicture(context, uploadType, tabs, path, timestamp, relateCode);
        }
    }

    /**
     * 描 述：上传多张图片，所有图片上传任务完成后发送{@link com.uuzz.android.util.ObservableTag##UPLOAD_COMPLETE}，附带任务时间戳,其中可能包含失败，也可能包含成功<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     *
     * @param context    上下文
     * @param uploadType 上传类型
     * @param tabs       上传标签，多个标签用逗号隔开
     * @param paths      本地图片路径
     * @param timestamp  用于组上传中标记是哪个组的任务
     */
    public void uploadMultiplePicture(@NonNull Context context, @NonNull ReqUploadFile.UploadType uploadType, @Nullable String tabs, @NonNull List<String> paths, long timestamp) {
        uploadMultiplePicture(context, uploadType, tabs, paths, timestamp, null);
    }

    /**
     * 描 述：用于判断一个上传组是否已经完成<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     */
    @Override
    public void update(Observable observable, Object data) {
        Message msg = (Message) data;
        if(observable != instance){
            return;
        }
        if (msg.what == ObservableTag.UPLOAD_COMPLETE) {          //不接受上传完成消息
            return;
        }
        UploadPictureMessage result = (UploadPictureMessage) msg.obj;
        if (isComplete(result.getTimestamp())) {
            msg = Message.obtain();
            msg.what = ObservableTag.UPLOAD_COMPLETE;
            msg.obj = result;
            setChanged();
            notifyObservers(msg);
        }
    }

    /**
     * 描 述：判断一组任务是否完全完成<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/16 <br/>
     *
     * @param timestamp 组任务时间戳
     * @return 上传完成返回true，未完成或不是组上传任务返回false
     */
    private synchronized boolean isComplete(long timestamp) {
        if (groupCountPair.get(timestamp) == null) {
            return false;
        }
        int count = groupCountPair.get(timestamp);
        if (count > 1) {                    //剩余个数大于1说明任务还未完成，把剩余个数-1
            groupCountPair.put(timestamp, --count);
            return false;
        }
        groupCountPair.remove(timestamp);  //上传完成后从groupCountPair中移除此时间戳
        return true;
    }

    private int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            logger.e("read picture degree error", e);
        }
        return degree;
    }

    /**
     * 旋转图片
     * @param bitmap 旋转前的图片
     * @param rotate 旋转角度
     * @return 旋转后的图片
     */
    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null) {
            return null;
        }
        if(rotate == 0) {
            return bitmap;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
        bitmap.recycle();
        return bm;
    }

    private Bitmap getImage(float width, float height, String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > width) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / width);
        } else if (w < h && h > height) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / height);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private Bitmap compressImage(Bitmap image) {
        if(image == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024>800) {  //循环判断如果压缩后图片是否大于800kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        FileUtil.closeInputStream(isBm);
        image.recycle();
        return bitmap;
    }
}
