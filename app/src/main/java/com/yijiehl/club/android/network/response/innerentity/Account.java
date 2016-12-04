/**
 * 项目名称：手机在线 <br/>
 * 文件名称: Account.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/24.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.response.innerentity;

import android.content.Context;
import android.text.TextUtils;

import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: Account <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/24 <br/>
 * @author 谌珂 <br/>
 */
public class Account extends RespBaseSearchResult {

    /** 手机号码 */
    private String mobileNum;
    /** 关系编码 一期只设置简单的两类关系，夫妻couple 亲友kith */
    private String relationCode;
    /** 性别 */
    private String genderCode;
    /** 出生日期 可为空，形如 2008-08-08，对应的格式串为 yyyy-MM-dd */
    private String birthdate;
    /** 创建时间 */
    private String createTime;
    /** 描述信息 */
    private String dataDesc;
    /** 数据编码,该值可用于修改和查看详情 */
    private String dataCode;
    /*妈妈健康数据访问授权,1表示允许访问 0表示不允许访问*/
    private String accessAuth1;
    /*宝宝健康数据访问授权,1表示允许访问 0表示不允许访问*/
    private String accessAuth2;
    /*宝我的问答访问授权,1表示允许访问 0表示不允许访问*/
    private String accessAuth5;
    /*照片访问授权,1表示允许访问 0表示不允许访问*/
    private String accessAuth6;

    public String getAccessAuth1() {
        return accessAuth1;
    }

    public void setAccessAuth1(String accessAuth1) {
        this.accessAuth1 = accessAuth1;
    }

    public String getAccessAuth2() {
        return accessAuth2;
    }

    public void setAccessAuth2(String accessAuth2) {
        this.accessAuth2 = accessAuth2;
    }

    public String getAccessAuth5() {
        return accessAuth5;
    }

    public void setAccessAuth5(String accessAuth5) {
        this.accessAuth5 = accessAuth5;
    }

    public String getAccessAuth6() {
        return accessAuth6;
    }

    public void setAccessAuth6(String accessAuth6) {
        this.accessAuth6 = accessAuth6;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getRelationCode(Context context) {
        switch (RelateCode.setValue(relationCode)) {
            case COUPLE:
                return context.getString(R.string.couple);
            case KITH:
                return context.getString(R.string.kith);
        }
        return null;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public enum RelateCode {
        COUPLE(0, "couple"),
        KITH(1, "kith");

        private int value;
        private String name;

        RelateCode(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static RelateCode setValue(String name) {
            RelateCode[] arr$ = values();
            for (RelateCode c : arr$) {
                if (TextUtils.equals(c.getName(), name)) {
                    return c;
                }
            }
            return COUPLE;
        }
    }
}
