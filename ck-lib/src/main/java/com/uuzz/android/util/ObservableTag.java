/**
 * 项目名称：手机在线 <br/>
 * 文件名称: ObservableTag.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/20.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.uuzz.android.util;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: ObservableTag <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/20 <br/>
 * @author 谌珂 <br/>
 */
public class ObservableTag {
    /** 数据库改变的标记，用于传递给监听者 */
    public static final int DB_CHANGED = 0;
    /** 缓存数据的标记 */
    public static final int CACHE_DATA = 1;
    /** 上传图片成功的消息 */
    public static final int UPLOAD_SUCCESS = 2;
    /** 上传图片失败的消息 */
    public static final int UPLOAD_FAILED = 3;
    /** 上传图片完成的消息，可能既包含成功也包含失败，主要针对一组图片的上传 */
    public static final int UPLOAD_COMPLETE = 4;
}
