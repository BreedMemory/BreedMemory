/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: QuestionFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: QuestionFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 * @author 谌珂 <br/>
 */
public class QuestionFragment extends BaseHostFragment {
    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return null;
    }

    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        return false;
    }

    @Override
    protected boolean isRightBtnVisible() {
        return false;
    }
}
