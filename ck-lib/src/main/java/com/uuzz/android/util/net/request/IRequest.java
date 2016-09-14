/**
 * 项目名称：工具库 <br/>
 * 文件名称: IRequest.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/4.  <br/>
 */
package com.uuzz.android.util.net.request;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;

/**
 * 项目名称：工具库 <br/>
 * 类  名: IRequest <br/>
 * 类描述: 所有业务请求报文的基类<br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/4 <br/>
 * @author 谌珂 <br/>
 */
public interface IRequest {
    /**
     * 描 述：用于自检请求消息是否符合规范的方法，发送请求前检验数据的最后一道屏障<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     * @return 检测成功返回true，失败返回false
     */
    boolean selfCheck();

    /**
     * 描 述：获取接口最后的路径<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     */
    String getPath();

    /**
     * 描 述：获取需要启动的任务class<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     */
    Class<? extends AbstractTask> getTaskClass();

    /**
     * 描 述：获取需要生成结果对象的class<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/9/3 注释 <br/>
     */
    Class<? extends AbstractResponse> getResponseClass();

    /**
     * 描 述：描述是否是https链接<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/3 <br/>
     */
    boolean isHttps();

    /**
     * 描 述：描述是否是get<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/3 <br/>
     */
    boolean isGet();
}
