/**
 * 项目名称：手机大管家
 * 文件名称: LoginObservable.java
 * Created by 谌珂 on 2016/11/13.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.svc;

import java.util.Observable;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: LoginObservable<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class LoginObservable extends Observable {

    private static volatile LoginObservable instance;

    private LoginObservable() {
    }

    public static LoginObservable getInstance() {
        if(instance == null) {
            synchronized (LoginObservable.class) {
                if(instance == null) {
                    instance = new LoginObservable();
                }
            }
        }
        return instance;
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}
