package com.hengkai.officeautomationsystem.function.ask_for_leave;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.utils.MaterialDateTimePickerUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/4/26.
 * 请假页面
 */
public class AskForLeaveActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_leave_count)
    TextView tvLeaveCount;
    @BindView(R.id.tv_leave_type)
    TextView tvLeaveType;
    @BindView(R.id.ll_leave_type)
    LinearLayout llLeaveType;
    @BindView(R.id.tv_holiday1)
    TextView tvHoliday1;
    @BindView(R.id.tv_holiday2)
    TextView tvHoliday2;
    @BindView(R.id.tv_my_holiday)
    TextView tvMyHoliday;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.et_leave_reason)
    EditText etLeaveReason;
    @BindView(R.id.ll_leave_reason)
    LinearLayout llLeaveReason;
    @BindView(R.id.tv_examine_and_approve1)
    TextView tvExamineAndApprove1;
    @BindView(R.id.tv_examine_and_approve2)
    TextView tvExamineAndApprove2;
    @BindView(R.id.ll_examine_and_approve)
    LinearLayout llExamineAndApprove;
    @BindView(R.id.tv_sender1)
    TextView tvSender1;
    @BindView(R.id.tv_sender2)
    TextView tvSender2;
    @BindView(R.id.ll_sender)
    LinearLayout llSender;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.iv_leave_type)
    ImageView ivLeaveType;
    @BindView(R.id.iv_start_time)
    ImageView ivStartTime;
    @BindView(R.id.iv_end_time)
    ImageView ivEndTime;

    private int startYear, startMonth, startDay, startHour, startMinute;
    private int endYear, endMonth, endDay, endHour, endMinute;
    private String startTimeStr, endTimeStr;

    @Override
    protected int setupView() {
        return R.layout.activity_ask_for_leave;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.iv_back, R.id.btn_commit, R.id.ll_leave_type, R.id.iv_leave_type, R.id.ll_start_time,
            R.id.iv_start_time, R.id.ll_end_time, R.id.iv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.btn_commit://提交

                break;
            case R.id.ll_leave_type://选择请假类型
                showSelectTypeDialog();
                break;
            case R.id.iv_leave_type://选择请假类型
                if (!tvLeaveType.getText().toString().trim().equals("请选择")) {
                    setTextViewAttribute("请选择", R.drawable.ic_arrow_gray32, R.color.black3, tvLeaveType, ivLeaveType);
                }
                break;
            case R.id.ll_start_time://开始请假时间
                if (tvLeaveType.getText().toString().trim().equals("请选择")) {
                    ToastUtil.showToast("请先选择请假类型");
                    return;
                }
                MaterialDateTimePickerUtils.showDatePickerDialog(getFragmentManager(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        showStartTimePickerDialog(year, monthOfYear, dayOfMonth);
                    }
                });
                break;
            case R.id.iv_start_time://开始请假时间
                if (!tvStartTime.getText().toString().trim().equals("请选择")) {
                    setTextViewAttribute("请选择", R.drawable.ic_arrow_gray32, R.color.black3, tvStartTime, ivStartTime);
                }
                break;
            case R.id.ll_end_time://结束请假时间
                if (tvLeaveType.getText().toString().trim().equals("请选择")) {
                    ToastUtil.showToast("请先选择请假类型");
                    return;
                }
                MaterialDateTimePickerUtils.showDatePickerDialog(getFragmentManager(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        showEndTimePickerDialog(year, monthOfYear, dayOfMonth);
                    }
                });
                break;
            case R.id.iv_end_time://结束请假时间
                if (!tvEndTime.getText().toString().trim().equals("请选择")) {
                    setTextViewAttribute("请选择", R.drawable.ic_arrow_gray32, R.color.black3, tvEndTime, ivEndTime);
                }
                break;
        }
    }

    /**
     * 显示出开始时间选择器
     */
    private void showStartTimePickerDialog(final int year, final int monthOfYear, final int dayOfMonth) {
        MaterialDateTimePickerUtils.showTimePickerDialog(getFragmentManager(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                startYear = year;
                startMonth = monthOfYear;
                startDay = dayOfMonth;
                startHour = hourOfDay;
                startMinute = minute;
                startTimeStr = year + "-" + monthOfYear + "-" + dayOfMonth + " " + hourOfDay + ":" + minute;

                setTextViewAttribute(startTimeStr, R.drawable.ic_cancel28, R.color.black, tvStartTime, ivStartTime);
            }
        });
    }

    /**
     * 显示出结束时间选择器
     */
    private void showEndTimePickerDialog(final int year, final int monthOfYear, final int dayOfMonth) {
        MaterialDateTimePickerUtils.showTimePickerDialog(getFragmentManager(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                endYear = year;
                endMonth = monthOfYear;
                endDay = dayOfMonth;
                endHour = hourOfDay;
                endMinute = minute;
                endTimeStr = year + "-" + monthOfYear + "-" + dayOfMonth + " " + hourOfDay + ":" + minute;

                if (!TextUtils.isEmpty(startTimeStr) && !TextUtils.isEmpty(endTimeStr)) {
                    calculateLeaveTime();
                }

                setTextViewAttribute(endTimeStr, R.drawable.ic_cancel28, R.color.black, tvEndTime, ivEndTime);
            }
        });
    }

    /**
     * 计算请假时间
     */
    private void calculateLeaveTime() {
        // TODO: 2018/4/25 这里的算法很复杂, 需要预设很多参数(每周哪天休息, 节假日, 午休多久等) , 所以暂时先这样写 后期是改成手动输入还是接口获取再说
        String formatType = "yyyy-MM-dd HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        try {
            long startTime = format.parse(startTimeStr).getTime();
            long endTime = format.parse(endTimeStr).getTime();
            float difference = (float)(endTime - startTime) / 1000 / 60 / 60;

            if (endYear == startYear && endMonth == startMonth && endDay == startDay) {

            }

            if (difference <= 0) {
                ToastUtil.showToast("结束时间不能小于开始时间");
                return;
            }
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            tvTime.setText(df.format(difference));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择请假类型的dialog
     */
    private void showSelectTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = View.inflate(this, R.layout.dialog_select_leave_type, null);
        final TextView text1 = dialogView.findViewById(R.id.tv_text1);
        final TextView text2 = dialogView.findViewById(R.id.tv_text2);
        final TextView text3 = dialogView.findViewById(R.id.tv_text3);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.show();
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setTextViewAttribute(text1.getText().toString().trim(), R.drawable.ic_cancel28, R.color.black, tvLeaveType, ivLeaveType);
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setTextViewAttribute(text2.getText().toString().trim(), R.drawable.ic_cancel28, R.color.black, tvLeaveType, ivLeaveType);
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setTextViewAttribute(text3.getText().toString().trim(), R.drawable.ic_cancel28, R.color.black, tvLeaveType, ivLeaveType);
            }
        });
    }

    /**
     * 设置请选择文本框的相关属性
     *
     * @param text       文本框的文本
     * @param resourceId 文本框的DrawableEnd的资源ID
     * @param color      颜色ID
     * @param textView   要设置的TextView
     * @param imageView  要设置的ImageView
     */
    private void setTextViewAttribute(String text, int resourceId, int color, TextView textView, ImageView imageView) {
        textView.setText(text);
        imageView.setImageResource(resourceId);
        textView.setTextColor(getResources().getColor(color));
    }

}
