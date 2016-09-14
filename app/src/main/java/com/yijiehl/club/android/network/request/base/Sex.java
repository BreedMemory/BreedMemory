/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: Sex.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.request.base;

import android.text.TextUtils;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: Sex <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 * @author 谌珂 <br/>
 */
public enum Sex {
    MALE(0, "m"),
    FEMALE(1, "f"),
    UNKONW(2, "u");

    private int value;
    private String name;

    Sex(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static Sex setValue(String name) {
        Sex[] arr$ = values();
        for (Sex c : arr$) {
            if (TextUtils.equals(c.getName(), name)) {
                return c;
            }
        }
        return UNKONW;
    }
}
