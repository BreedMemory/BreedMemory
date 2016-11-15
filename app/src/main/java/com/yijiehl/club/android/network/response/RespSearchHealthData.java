/**
 * 项目名称：手机在线 <br/>
 * 文件名称: RespSearchHealthData.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/24.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.HealthData;

import java.util.List;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: RespSearchHealthData <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/24 <br/>
 * @author 谌珂 <br/>
 */
public class RespSearchHealthData extends BaseResponse {
    private List<HealthData> resultList;

    public List<HealthData> getResultList() {
        return resultList;
    }

    public void setResultList(List<HealthData> resultList) {
        this.resultList = resultList;
    }

    /**
     * 描 述：把健康数据中的英文字符串转义为中文<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    public static String transformString(String value) {
        switch (value) {
            case "normal":
                return "正常";
            case "abnormal":
                return "不正常";
            case "higher":
                return "偏多";
            case "lower":
                return "偏少";
            case "nochk":
                return "未检测";
            case "good":
                return "良好";
            case "ordinary":
                return "一般";
            case "poorer":
                return "较差";
            case "breast_milk":
                return "母乳";
            case "powdered_milk":
                return "奶粉";
            case "mix_milk":
                return "混合";
            case "have":
                return "有";
            case "not":
                return "无";
            default:
                return value;
        }
    }

}
