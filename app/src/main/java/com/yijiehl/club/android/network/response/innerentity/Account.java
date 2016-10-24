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
