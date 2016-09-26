package com.yijiehl.club.android.entity;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import static android.provider.MediaStore.MediaColumns.MIME_TYPE;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PhotoDirectoryLoader <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/26 <br/>
 * @author 张志新 <br/>
 */
public class PhotoDirectoryLoader extends android.content.CursorLoader {

    final String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
    };

    public PhotoDirectoryLoader(Context context) {
        super(context);

        setProjection(IMAGE_PROJECTION);
        setUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        setSortOrder(MediaStore.Images.Media.DATE_ADDED + " DESC");

        setSelection(MIME_TYPE + "=? or " + MIME_TYPE + "=?");
        setSelectionArgs(new String[]{"image/jpeg", "image/png"});
    }

    private PhotoDirectoryLoader(Context context, Uri uri, String[] projection, String selection,
                                 String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }
}
