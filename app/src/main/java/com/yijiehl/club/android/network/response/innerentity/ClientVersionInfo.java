package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.BaseResponse;

import java.io.Serializable;

/**
 * @author LuoJ
 * @date 2014-11-24
 * @package com.xingcomm.android.videoconference.base.entity -- ClientVersionInfo.java
 * @Description 
 */
public class ClientVersionInfo extends BaseResponse implements Serializable{

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

	public String getClientNum() {
		return clientNum;
	}

	public void setClientNum(String clientNum) {
		this.clientNum = clientNum;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getDownCode() {
		return downCode;
	}

	public void setDownCode(String downCode) {
		this.downCode = downCode;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientAdapter() {
		return clientAdapter;
	}

	public void setClientAdapter(String clientAdapter) {
		this.clientAdapter = clientAdapter;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getIncFlag() {
		return incFlag;
	}

	public void setIncFlag(String incFlag) {
		this.incFlag = incFlag;
	}

	public String getIncName() {
		return incName;
	}

	public void setIncName(String incName) {
		this.incName = incName;
	}

	public String getIncSize() {
		return incSize;
	}

	public void setIncSize(String incSize) {
		this.incSize = incSize;
	}

	public String getIncMd5() {
		return incMd5;
	}

	public void setIncMd5(String incMd5) {
		this.incMd5 = incMd5;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReleaseInfo() {
		return releaseInfo;
	}

	public void setReleaseInfo(String releaseInfo) {
		this.releaseInfo = releaseInfo;
	}

	public String getReleaseDesc() {
		return releaseDesc;
	}

	public void setReleaseDesc(String releaseDesc) {
		this.releaseDesc = releaseDesc;
	}

	public int getForceFlag() {
		return forceFlag;
	}

	public void setForceFlag(int forceFlag) {
		this.forceFlag = forceFlag;
	}
}


