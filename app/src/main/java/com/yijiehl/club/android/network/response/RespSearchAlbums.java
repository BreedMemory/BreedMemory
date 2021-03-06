/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: RespSearchAlbums.java <br/>
 * Created by 张志新 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.AlbumInfo;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: RespSearchAlbums <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 * @author 张志新 <br/>
 */
public class RespSearchAlbums extends BaseResponse {
    private List<AlbumInfo> resultList;

    public List<AlbumInfo> getResultList() {
        return resultList;
    }

    public void setResultList(List<AlbumInfo> resultList) {
        this.resultList = resultList;
    }
}
