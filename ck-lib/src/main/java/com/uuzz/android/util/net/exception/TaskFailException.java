/**
 * 项目名称：工具库 <br/>
 * 文件名称: TaskFailException.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/4.  <br/>
 */
package com.uuzz.android.util.net.exception;

/**
 * 项目名称：工具库 <br/>
 * 类  名: TaskFailException <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/4 <br/>
 * @author 谌珂 <br/>
 */
public class TaskFailException extends Exception {

    public TaskFailException() {
    }

    public TaskFailException(String detailMessage) {
        super(detailMessage);
    }
}
