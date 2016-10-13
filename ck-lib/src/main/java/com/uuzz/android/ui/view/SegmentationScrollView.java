/**
 * 项目名称：手机在线 <br/>
 * 文件名称: SegmentationScroll.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/11.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.uuzz.android.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: SegmentationScroll <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/11 <br/>
 * @author 谌珂 <br/>
 */
public class SegmentationScrollView extends ScrollView {
    public SegmentationScrollView(Context context) {
        super(context);
    }

    public SegmentationScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SegmentationScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SegmentationScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
