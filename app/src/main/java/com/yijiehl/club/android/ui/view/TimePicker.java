/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: TimePicker.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/6.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.yijiehl.club.android.R;

import java.util.GregorianCalendar;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: TimePicker <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/6 <br/>
 * @author 谌珂 <br/>
 */
public class TimePicker extends FrameLayout {
    @ViewInject(R.id.npv_year)
    private NumberPickerView mYear;
    @ViewInject(R.id.npv_month)
    private NumberPickerView mMonth;
    @ViewInject(R.id.npv_day)
    private NumberPickerView mDay;

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    /**
     * 描 述：初始化内容视图<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     */
    private void initView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_time_picker, null);
        this.addView(v);
        InjectUtils.injectViews(v, this);
        mMonth.setListener(new NumberPickerView.OnValueChanged() {
            @Override
            public void onValueChanged(int position, int value) {
                int maxDay;
                switch (value) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        maxDay = 31;
                        break;
                    case 2:
                        if(mYear.getValue()%4 == 0 && mYear.getValue()%100 != 0) {
                            maxDay = 29;
                        } else {
                            maxDay = 28;
                        }
                        break;
                    default:
                        maxDay = 30;
                        break;
                }
                mDay.setMaxValue(maxDay);
            }
        });
    }

    /**
     * 描 述：获取选择日期的时间戳<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     * @return 时间戳
     */
    public long getDateTimeStamp() {
        GregorianCalendar lGregorianCalendar = new GregorianCalendar(mYear.getValue(), mMonth.getValue(), mDay.getValue());
        return lGregorianCalendar.getTimeInMillis();
    }

    /**
     * 描 述：获取选择日期的时间戳<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     * @return yyyy-MM-dd
     */
    public String getDate() {
        return TimeUtil.getTime(getDateTimeStamp());
    }
}
