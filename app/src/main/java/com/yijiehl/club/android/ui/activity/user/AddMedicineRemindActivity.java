package com.yijiehl.club.android.ui.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchVaccine;
import com.yijiehl.club.android.network.response.RespSearchVaccine;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.view.TimePicker;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: AddMedicineRemindActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/31 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_add_medicine_remind)
public class AddMedicineRemindActivity extends BmActivity implements View.OnClickListener {

    /** 时间选择容器 */
    @ViewInject(R.id.ll_time_picker_container)
    private View mPickerContainer;
    /**时间显示*/
    @ViewInject(R.id.tv_remind_time)
    private TextView mTimeShow;
    /**疫苗名称*/
    @ViewInject(R.id.et_remind_name)
    private EditText mRemindName;

    /**疫苗名称取消按钮*/
    @ViewInject(R.id.iv_cancel)
    private ImageView mCancel;

    @ViewInject(R.id.tp_choose_date)
    private TimePicker mTimePicker;

    private String mTime;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.my_medicine);
    }

    @Override
    protected void configHeadRightView() {
        mRightBtn = new IconTextView(this);
        mHeadRightContainer.addView(mRightBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mRightBtn.setText(getString(R.string.save));

        mRightBtn.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTime = TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD);
        mTimeShow.setText(createTime(System.currentTimeMillis()));

        NetHelper.getDataFromNet(this, new ReqSearchVaccine(this), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchVaccine respSearchVaccine=(RespSearchVaccine)pResponse;

            }
        }, false);
        mRemindName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mCancel.setVisibility(View.VISIBLE);
                } else {
                    mCancel.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 描 述：构建时间<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/26 <br/>
     */
    private String createTime(long timestamp) {
        String text = mTime.replace("-", getString(R.string.year));
        text = text.replace("-", getString(R.string.month));
        text += "日 " + TimeUtil.getWeekStr(timestamp);
        return text;
    }

    @OnClick(R.id.layout_choose_remind_time)
    private void chooseTime(){
        mTimePicker.setDate(TimeUtil.getTime(System.currentTimeMillis(), TimeUtil.DEFAULT_FORMAT_YYYYMMDD));
        mPickerContainer.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_cancel)
    private void cancel(){
        if(!TextUtils.isEmpty(mRemindName.getText().toString())){
            mRemindName.getText().clear();
        }
    }

    /**保存疫苗提醒信息*/
    @Override
    public void onClick(View v) {
        // TODO: 2016/10/31 保存 疫苗信息
    }
}
