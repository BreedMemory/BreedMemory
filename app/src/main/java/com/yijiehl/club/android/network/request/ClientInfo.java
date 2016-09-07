/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: UserInfo.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.network.request;

import android.content.Context;

import com.uuzz.android.util.Common;
import com.uuzz.android.util.ScreenTools;
import com.uuzz.android.util.Utils;

import java.util.Calendar;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: UserInfo <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
public class ClientInfo {

    public ClientInfo(Context context) {
        this.deviceNum = Utils.getIMEI(context);
        this.width = String.valueOf(ScreenTools.getScreenPixel(context)[0]);
        this.height = String.valueOf(ScreenTools.getScreenPixel(context)[1]);
        this.langType = context.getResources().getConfiguration().locale.getLanguage();
        this.netInfo = String.valueOf(Utils.getNetworkType(context) == 1);
    }

    /** 客户端当前使用的版本号 */
    private String clientVersion = Common.APP_VERSION;
    /** 设备信息,小米4,ThinkPad T430等 */
    private String deviceInfo = android.os.Build.MODEL;
    /** 设备唯一编码，能取到则上传 */
    private String deviceNum;
    /** 操作系统, 如win7 */
    private String sysInfo = "android";
    /** 运行环境,如 net4.0, android4.3 */
    private String runEnv = android.os.Build.VERSION.RELEASE;
    /** 屏幕宽 */
    private String width;
    /** 屏幕高 */
    private String height;
    /** 语言,ch中文en英文等，用英文值 */
    private String langType;
    /** 客户端使用的时区, 值形如 GMT+8 */
    private String timeZone = Calendar.getInstance().getTimeZone().getDisplayName();
    /** 是否是使用wifi */
    private String netInfo;

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getSysInfo() {
        return sysInfo;
    }

    public void setSysInfo(String sysInfo) {
        this.sysInfo = sysInfo;
    }

    public String getRunEnv() {
        return runEnv;
    }

    public void setRunEnv(String runEnv) {
        this.runEnv = runEnv;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getNetInfo() {
        return netInfo;
    }

    public void setNetInfo(String netInfo) {
        this.netInfo = netInfo;
    }
}
