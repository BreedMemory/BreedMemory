/**
 * 项目名称：工具库 <br/>
 * 文件名称: CacheDataEntity.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/2/17.  <br/>
 */
package com.uuzz.android.util.database.entity;

import com.uuzz.android.util.database.annotation.TableProperty;

/**
 * 项目名称：工具库 <br/>
 * 类  名: CacheDataEntity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/2/17 <br/>
 * @author 谌珂 <br/>
 */
public class CacheDataEntity {
    public CacheDataEntity() {
    }

    public CacheDataEntity(String mName, String mData, String mUserId) {
        this.mName = mName;
        this.mData = mData;
        this.mUserId = mUserId;
    }

    /** 请求特征码 */
    @TableProperty
    private String mName;
    /** 缓存返回数据 */
    @TableProperty
    private String mData;
    /** 用户id */
    @TableProperty
    private String mUserId;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }
}
