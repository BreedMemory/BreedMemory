package com.uuzz.android.util;

import android.app.Application;
import android.util.Log;

import com.uuzz.android.util.log.FileLog;

public class UEHandler implements Thread.UncaughtExceptionHandler {

    public UEHandler(Application app) {
		
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
        FileLog.getInstance().saveLog("crash", "Crash", "Crash", arg1);
        Log.e("UEHandler", "Lottery is crash!", arg1);
        android.os.Process.killProcess(android.os.Process.myPid());
	}
}
