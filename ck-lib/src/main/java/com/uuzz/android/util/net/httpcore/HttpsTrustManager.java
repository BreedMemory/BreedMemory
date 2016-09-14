/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: HttpsTrustManager.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/12.  <br/>
 */
package com.uuzz.android.util.net.httpcore;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: HttpsTrustManager <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/12 <br/>
 * @author 谌珂 <br/>
 */
public class HttpsTrustManager implements X509TrustManager {
    // 该方法检查客户端的证书，若不信任该证书则抛出异常。由于我们不需要对客户端进行认证，因此我们只需要执行默认的信任管理器的这个方法。JSSE中 ，
    // 默认的信任管理器类为TrustManager
    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

    }

    // 该方法检查服务器的证书，若不信任该证书同样抛出异常。通过自己实现该方法，可以使之信任我们指定的任何证书。在实现该方法时，
    // 也可以简单的不做任何处理， 即一个空的函数体，由于不会抛出异常，它就会信任任何证书。
    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

    }

    //返回受信任的X509证书数组
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[]{};
    }

}
