package com.yijiehl.club.android.entity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: MediaStoreHelper <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/26 <br/>
 * @author 张志新 <br/>
 */
public class MediaStoreHelper {

    public final static int INDEX_ALL_PHOTOS = 0;

    public static void getPhotoDirs(Activity activity, PhotosResultCallback resultCallback) {
//        activity.getSupportLoaderManager()
//                .initLoader(0, null, new PhotoDirLoaderCallbacks(activity, resultCallback));
        activity.getLoaderManager().initLoader(0, null, new PhotoDirLoaderCallbacks(activity, resultCallback));
    }

    static class PhotoDirLoaderCallbacks implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

        Context context;
        PhotosResultCallback resultCallback;

        public PhotoDirLoaderCallbacks(Context context, PhotosResultCallback resultCallback) {
            this.context = context;
            this.resultCallback = resultCallback;
        }

        @Override
        public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new PhotoDirectoryLoader(context);
        }

        @Override
        public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
            if (data == null) return;
            List<PhotoDirectory> directories = new ArrayList<>();
            PhotoDirectory photoDirectoryAll = new PhotoDirectory();
            // photoDirectoryAll.setName(context.getString(R.string.all_image));
            //photoDirectoryAll.setId("ALL");

            while (data.moveToNext()) {

                int imageId = data.getInt(data.getColumnIndexOrThrow(_ID));
                String bucketId = data.getString(data.getColumnIndexOrThrow(BUCKET_ID));
                String name = data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME));
                String path = data.getString(data.getColumnIndexOrThrow(DATA));

                PhotoDirectory photoDirectory = new PhotoDirectory();
                photoDirectory.setId(bucketId);
                photoDirectory.setName(name);

                if (!directories.contains(photoDirectory)) {
                    photoDirectory.setCoverPath(path);
                    photoDirectory.addPhoto(imageId, path);
                    photoDirectory.setDateAdded(data.getLong(data.getColumnIndexOrThrow(DATE_ADDED)));
                    directories.add(photoDirectory);
                } else {
                    directories.get(directories.indexOf(photoDirectory)).addPhoto(imageId, path);
                }

                photoDirectoryAll.addPhoto(imageId, path);
            }
            if (photoDirectoryAll.getPhotoPaths().size() > 0) {
                photoDirectoryAll.setCoverPath(photoDirectoryAll.getPhotoPaths().get(0));
            }
            directories.add(INDEX_ALL_PHOTOS, photoDirectoryAll);
            if (resultCallback != null) {
                resultCallback.onResultCallback(directories);
            }
        }

        @Override
        public void onLoaderReset(android.content.Loader<Cursor> loader) {

        }
    }

    public interface PhotosResultCallback {
        void onResultCallback(List<PhotoDirectory> directories);
    }

}
