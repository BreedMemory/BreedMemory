// Copyright (C) 2012-2014 UUZZ All rights reserved
package com.uuzz.android.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类 名: ScreenTools<br/>
 * 描 述: 屏幕相关工具类<br/>
 * 作 者: 杨禹恒<br/>
 * 创 建： 2014-12-3<br/>
 * 
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class ScreenTools {

    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
	
	
	/**
	 * Description:获取屏幕分辨率<br>
	 * Created by 李京蔚 on 2014年12月4日.<br>
	 * 
	 * @return int[0]=x,int[1]=y<br>
	 */
	public static int[] getScreenPixel(Context context) {
		int[] screenPixel = new int[2];
		int width = 0, height = 0;
		final DisplayMetrics metrics = new DisplayMetrics();
		Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Method mGetRawH, mGetRawW;

		try {
			// For JellyBean 4.2 (API 17) and onward
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
				display.getRealMetrics(metrics);

				width = metrics.widthPixels;
				height = metrics.heightPixels;
			} else {
				mGetRawH = Display.class.getMethod("getRawHeight");
				mGetRawW = Display.class.getMethod("getRawWidth");

				try {
					width = (Integer) mGetRawW.invoke(display);
					height = (Integer) mGetRawH.invoke(display);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NoSuchMethodException e3) {
			e3.printStackTrace();
		}

		screenPixel[0] = width;
		screenPixel[1] = height;
		return screenPixel;
	}

	/**
	 * 描 述：获取状态栏高度<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (1.7.3) 谌珂 2016/11/30 <br/>
	 */
	public static int getStatusHeight(Activity activity) {
		Rect rectangle = new Rect();
		Window window = activity.getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
		int statusBarHeight = rectangle.top;
		int contentViewTop =
				window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		return contentViewTop - statusBarHeight;
	}

}
