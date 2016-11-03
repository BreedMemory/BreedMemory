package com.yijiehl.club.android.ui.controlversion;

import android.view.View;

/**
 * @author LuoJ
 * @date 2014-11-20
 * @package xingcomm.android.library.dialog -- IBasePopDialog.java
 * @Description 
 */
public interface IBasePopDialog {
	
	int setContentLayout();
	
	void initView(View rootView);
	
	void showAtScreenCenter();
	
	/**
	 * 显示对话窗，并且开始倒计时，倒计时完毕后，对话框关闭
	 * @param countDown
	 * @param callback
	 */
	void showAtScreenCenterWithTimer(int countDown, CountDownTimer.CountDownCallback callback);
	
	void dismiss();
	
}
