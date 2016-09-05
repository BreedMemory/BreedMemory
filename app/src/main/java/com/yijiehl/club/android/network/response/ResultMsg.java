/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ResultMsg.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/5.  <br/>
 */
package com.yijiehl.club.android.network.response;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ResultMsg <br/>
 * 类描述: 描述http请求结果状态<br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/5 <br/>
 * @author 谌珂 <br/>
 */
public class ResultMsg {
    /** 描述是否成功 */
    private boolean success;
    /** 失败的信息 */
    private String message;
    /** 失败的英文信息 */
    private String enMessage;
    /** 值为valid_failed表示服务端收到请求但验证失败，一般需要修改账号和密码 或者 重新登录(用于自动登录)，如果returnMsg.resultCode的值为空可能是参数不完整，连接不上等外部错误 */
    private String resultCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEnMessage() {
        return enMessage;
    }

    public void setEnMessage(String enMessage) {
        this.enMessage = enMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
