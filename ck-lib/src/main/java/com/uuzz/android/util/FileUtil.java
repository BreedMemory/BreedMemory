package com.uuzz.android.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.uuzz.android.util.log.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class FileUtil {

	/**
	 * 描 述：生成权限数组<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/7/13 注释 <br/>
	 */
	public static String[] createPermissions() {
		String[] permissions;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
		} else {
			permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
		}
		return permissions;
	}

	/**
	 * 描 述：判断是否存在sd卡<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2015/12/29 注释 <br/>
	 */
	private static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		return TextUtils.equals(status, Environment.MEDIA_MOUNTED);
	}

	/**
	 * 描 述：获取手机存储的文件路径<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2015/12/22 注释 <br/>
	 */
	public static String getRootFilePath() {
		String path;
		if (hasSDCard()) {
			path =  Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/breedmemory/";
		} else {
			path =  Environment.getDataDirectory().getAbsolutePath() + "/data/" + Common.PACKAGE_NAME + "/";
		}
		initFilePath(null, path);
		return path;
	}

//	public static String generName(String name) {
//		return String.valueOf(name.hashCode());
//	}

//	/**
//	 *
//	 * @param url
//	 * @return file path
//	 */
//	public static String getImageFromCache(String url) {
//		String fileName = generName(url);
//		String filePath = PATH_IMAGECACHE + File.separator + fileName;
//		if (isFileExists(filePath) && !isFileEmpty(filePath)) {
//			GlobalLog.d(TAG, "get file from local path ; filePath : "+filePath);
//			return filePath;
//		} else {
//			if (DownLoadUtil.downFile(url, PATH_IMAGECACHE + File.separator,
//					fileName)) {
//				GlobalLog.d(TAG, "download success : " + url + PATH_IMAGECACHE
//						+ File.separator + " : "+fileName);
//				return filePath;
//			} else {
//				return null;
//			}
//		}
//	}

	/**
	 * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
	 *
	 * @param path 图片保存的路径（包括文件名）
	 * @param bitmap 图片对象
	 * @throws IOException
	 */
	 public static String saveBitmap(String path, String fileName, Bitmap bitmap) throws
			 IOException {
		 if(bitmap == null){
		 	throw new IOException();
		 }
		 initFilePath(null, path);
		 File file = new File(path+fileName);
		 file.createNewFile();
		 FileOutputStream fos = new FileOutputStream(file);
		 bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
		 fos.flush();
		 fos.close();
		 return file.toURI().toString();
	 }

	/**
	 * 判断文件是否存在
	 * @param filePath 文件路径
	 * @return 是否存在
	 */
	public static boolean isFileExists(String filePath) {
		return new File(filePath).exists();
	}

	/**
	 * 获取文件的大小
	 * @param filePath 文件路径
	 * @return 文件大小
	 */
	public static long getFileSize(String filePath) {
		return new File(filePath).length();
	}
	
	/**
	 * 判断文件是否为空
	 * @param filePath 文件路径
	 * @return 是否为空
	 */
	public static boolean isFileEmpty(String filePath) {
		return getFileSize(filePath) <= 0;
	}

	/**
	 * 描 述：初始化文件路径<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2015/12/31 注释 <br/>
	 */
	public static boolean initFilePath(Logger logger, String path) {
		boolean b = false;
		try {
			File file = new File(path);
			if (!file.exists()) {
				b = file.mkdirs();
				if(logger == null) {
					return b;
				}
				logger.i("create path :  " + path + " : "
						+ b);
			}
		} catch (Exception e) {
			if(logger == null) {
				return b;
			}
			logger.i("create path :  " + path + " failed!", e);
		}
		return b;
	}

//	/**
//	 * 描 述：初始化文件路径<br/>
//	 * 作者：谌珂<br/>
//	 * 历 史: (版本) 谌珂 2015/12/31 注释 <br/>
//	 */
//	public static void initFilePath(String path) {
//		File file = new File(path);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//	}

	/**
	 * 描 述：清空文件夹<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/2/22 注释 <br/>
	 * @param path 文件夹路径
	 */
	public static void clearFloder(String path) {
		File file = new File(path);
		if(!file.isDirectory()) {
			return;
		}
		file.delete();
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}

	/**
	 * 描 述：关闭字节流<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (1.0.0) 谌珂 2016/8/18 <br/>
	 */
	public static void closeInputStream(InputStream ips) {
		try {
			ips.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 描 述：关闭读入流<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (1.0.0) 谌珂 2016/8/18 <br/>
	 */
	public static void closeReader(Reader reader) {
		try {
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 描 述：根据图片路径解析出图片名<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
	 * @param path 图片路径
	 * @return 图片名
	 */
	public static String formatImageName(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1, path.length() - 1);
	}

	/**
	 * 描 述：插入图片到系统相册<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
	 * @param context 上下文
	 * @param path 图片路径
     */
	public static void insertImageIntoGallery(Context context, String path) {
//		try {
			MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
//			MediaStore.Images.Media.insertImage(context.getContentResolver(),
//					path, String.valueOf(formatImageName(path)),
//					"孕育迹忆");
//		} catch (FileNotFoundException e) {
//			Log.d("FileUtils", "Insert image into system gallery error.", e);
//		}
	}

	/**
	 * 描 述：通知系统相册扫描新图片<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (1.7.3) 谌珂 2016/10/18 <br/>
	 */
	public static void notificationScanFile(Context context, String path) {
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
	}
}
