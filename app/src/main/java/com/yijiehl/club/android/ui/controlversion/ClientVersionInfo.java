package com.yijiehl.club.android.ui.controlversion;

import java.io.Serializable;

/**
 * @author LuoJ
 * @date 2014-11-24
 * @package com.xingcomm.android.videoconference.base.entity -- ClientVersionInfo.java
 * @Description 
 */
public class ClientVersionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String clientNum;//新客户端编号
	public String versionId;//待更新版本号
	public String versionName;//待更新版本名称
	public String downCode;//下载更新包的编码
	public String versionType;//版本类型 可为空, alpha、beta、gamma、release等
	public String clientType;//适用的客户端类型 可用于客户端判断是否与当前环境相符，值为windows, android, macos, iphone, ipad等
	public String clientAdapter;//客户端运行环境适配
	public String fileName;//
	public String fileSize;//全量文件大小，一般是多少M
	public String fileMd5;//用于文件完整性校验
	public String incFlag;//1表示可以增量更新，可下载增量包0表示该版本需要全量更新
	public String incName;//
	public String incSize;//增量文件大小，一般是多少M
	public String incMd5;//用于增量文件完整性校验
	public String releaseTime;//发布时间
	public String releaseInfo;//发布信息
	public String releaseDesc;//发布描述
	public int forceFlag;//是否强制更新

}


