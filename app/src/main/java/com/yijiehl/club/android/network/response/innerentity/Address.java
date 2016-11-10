/**
 * 项目名称：手机在线 <br/>
 * 文件名称: Address.java <br/>
 * <p>
 * Created by 谌珂 on 2016/11/9.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: Address <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/11/9 <br/>
 * @author 谌珂 <br/>
 */
public class Address extends RespBaseSearchResult {

    private String areaInfo;

    public String getAreaInfo() {
        return areaInfo;
    }

    public void setAreaInfo(String areaInfo) {
        this.areaInfo = areaInfo;
    }
}
