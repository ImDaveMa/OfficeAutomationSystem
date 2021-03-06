package com.hengkai.officeautomationsystem.function.ask_for_leave;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.ask_for_leave.list.AskForLeaveListActivity;
import com.hengkai.officeautomationsystem.network.entity.GoOutEntity;
import com.hengkai.officeautomationsystem.utils.MaterialDateTimePickerUtils;
import com.hengkai.officeautomationsystem.utils.PicassoCircleTransform;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/4/26.
 * 请假页面
 */
public class AskForLeaveActivity extends BaseActivity<AskForLeavePresenter> implements RadioGroup.OnCheckedChangeListener {

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
    @BindView(R.id.ll_examine_and_approve)
    LinearLayout llExamineAndApprove;
    @BindView(R.id.ll_sender)
    LinearLayout llSender;
    @BindView(R.id.ll_sp)
    LinearLayout llSP;
    @BindView(R.id.ll_cs)
    LinearLayout llCS;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.iv_leave_type)
    ImageView ivLeaveType;
    @BindView(R.id.iv_start_time)
    ImageView ivStartTime;
    @BindView(R.id.iv_end_time)
    ImageView ivEndTime;

    private AlertDialog dialog;
    private int radioGroupId;
    private int checkedId;

    private boolean changeGroup = false;

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

        tvTitle.setText("请假");

        mPresenter.getCopyPerson();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.ASK_FOR_LEAVE_ACTIVITY_ADD);
        tags.add(NetworkTagFinal.ASK_FOR_LEAVE_ACTIVITY_DURATION);
        return tags;
    }

    @Override
    protected AskForLeavePresenter bindPresenter() {
        return new AskForLeavePresenter();
    }

    @OnClick({R.id.iv_back, R.id.btn_commit, R.id.ll_leave_type, R.id.iv_leave_type, R.id.ll_start_time,
            R.id.iv_start_time, R.id.ll_end_time, R.id.iv_end_time, R.id.iv_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.btn_commit://提交
                commit();
                break;
            case R.id.ll_leave_type://选择请假类型
                showSelectTypeDialog();
                break;
            case R.id.iv_leave_type://选择请假类型
                if (!tvLeaveType.getText().toString().trim().equals("请选择")) {
                    checkedId = 0;
                    setTextViewAttribute("请选择", R.drawable.ic_arrow_gray32, R.color.black3, tvLeaveType, ivLeaveType);
                }
                break;
            case R.id.ll_start_time://开始请假时间
//                if (tvLeaveType.getText().toString().trim().equals("请选择")) {
//                    ToastUtil.showToast("请先选择请假类型");
//                    return;
//                }
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
                    startTimeStr = "";
                    tvTime.setText("0.0");
                }
                break;
            case R.id.ll_end_time://结束请假时间
                if (tvStartTime.getText().toString().trim().equals("请选择") || TextUtils.isEmpty(tvStartTime.getText().toString().trim())) {
                    ToastUtil.showToast("请先选择开始时间");
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
                    endTimeStr = "";
                    tvTime.setText("0.0");
                }
                break;
            case R.id.iv_list:
                startActivity(new Intent(this, AskForLeaveListActivity.class));
                break;
        }
    }

    /**
     * 提交
     */
    private void commit() {
        String leaveType = tvLeaveType.getText().toString().trim();
        String startTime = tvStartTime.getText().toString().trim();
        String endTime = tvEndTime.getText().toString().trim();
        String time = tvTime.getText().toString().trim();
        String leaveReason = etLeaveReason.getText().toString().trim();

        if (TextUtils.isEmpty(leaveType) || leaveType.equals("请选择")) {
            ToastUtil.showToast("请选择请假类型");
            return;
        }
        if (TextUtils.isEmpty(startTime) || startTime.equals("请选择")) {
            ToastUtil.showToast("请选择开始时间");
            return;
        }
        if (TextUtils.isEmpty(endTime) || endTime.equals("请选择")) {
            ToastUtil.showToast("请选择结束时间");
            return;
        }
        if (TextUtils.isEmpty(leaveReason) || leaveReason.equals("请选择")) {
            ToastUtil.showToast("请填写请假事由");
            return;
        }

        Map<String, String> params = new HashMap<>();
        switch (leaveType) {
            case "零星假":
                params.put("LEAVETYPE", String.valueOf(1));
                break;
            case "事假":
                params.put("LEAVETYPE", String.valueOf(2));
                break;
            case "病假":
                params.put("LEAVETYPE", String.valueOf(3));
                break;
            case "婚假":
                params.put("LEAVETYPE", String.valueOf(4));
                break;
            case "产假":
                params.put("LEAVETYPE", String.valueOf(5));
                break;
            case "陪产假":
                params.put("LEAVETYPE", String.valueOf(6));
                break;
            case "丧假":
                params.put("LEAVETYPE", String.valueOf(7));
                break;
            default:
                break;
        }
        params.put("TIME", time);
        params.put("REASON", leaveReason);
        params.put("STARTTIME", startTime);
        params.put("ENDTIME", endTime);

        mPresenter.add(params);
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
                String newMinute;
                if (minute < 10) {
                    newMinute = "0" + minute;
                } else {
                    newMinute = minute + "";
                }
                String newHour;
                if (hourOfDay < 10) {
                    newHour = "0" + hourOfDay;
                } else {
                    newHour = "" + hourOfDay;
                }
                startTimeStr = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + newHour + ":" + newMinute;

                //判断结束时间是否存在
                if (!TextUtils.isEmpty(endTimeStr)) {
                    String formatType = "yyyy-MM-dd HH:mm";
                    SimpleDateFormat format = new SimpleDateFormat(formatType);
                    try {
                        long startTime = format.parse(startTimeStr).getTime();
                        long endTime = format.parse(endTimeStr).getTime();
                        if ((endTime - startTime) < 0) {
                            ToastUtil.showToast("开始时间不能大于结束时间");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

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
                String newMinute;
                if (minute < 10) {
                    newMinute = "0" + minute;
                } else {
                    newMinute = minute + "";
                }
                String newHour;
                if (hourOfDay < 10) {
                    newHour = "0" + hourOfDay;
                } else {
                    newHour = "" + hourOfDay;
                }
                endTimeStr = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + newHour + ":" + newMinute;

                //判断开始时间是否存在
                if (!TextUtils.isEmpty(startTimeStr)) {
                    String formatType = "yyyy-MM-dd HH:mm";
                    SimpleDateFormat format = new SimpleDateFormat(formatType);
                    try {
                        long startTime = format.parse(startTimeStr).getTime();
                        long endTime = format.parse(endTimeStr).getTime();
                        if ((endTime - startTime) < 0) {
                            ToastUtil.showToast("结束时间不能小于开始时间");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                setTextViewAttribute(endTimeStr, R.drawable.ic_cancel28, R.color.black, tvEndTime, ivEndTime);

                //计算时间先注释掉
                if (!TextUtils.isEmpty(startTimeStr) && !TextUtils.isEmpty(endTimeStr)) {
                    mPresenter.duration(startTimeStr, endTimeStr);
                }
            }
        });
    }

    /**
     * 选择请假类型的dialog
     */
    private void showSelectTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = View.inflate(this, R.layout.dialog_select_leave_type, null);
        RadioGroup rgGroup1 = dialogView.findViewById(R.id.rg_group1);
        RadioGroup rgGroup2 = dialogView.findViewById(R.id.rg_group2);

        if(checkedId > 0){
            RadioButton rBtn = dialogView.findViewById(checkedId);
            if(rBtn != null) {
                rBtn.setChecked(true);
            }
        }

        rgGroup1.setOnCheckedChangeListener(this);
        rgGroup2.setOnCheckedChangeListener(this);

        builder.setView(dialogView);
        dialog = builder.show();
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

    /**
     * @param time 设置时长
     */
    public void setDuration(double time) {
        tvTime.setText(String.valueOf(time));
    }

    /**
     * 切换 事假类型
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (!changeGroup) {
            changeGroup = true;
            this.radioGroupId = group.getId();
            this.checkedId = checkedId;

            RadioButton rbtn = group.findViewById(checkedId);
            setTextViewAttribute(rbtn.getText().toString().trim(), R.drawable.ic_cancel28, R.color.black1, tvLeaveType, ivLeaveType);

            if (group.getId() == R.id.rg_group1) {
                RadioGroup otherGroup = dialog.findViewById(R.id.rg_group2);
                otherGroup.clearCheck();
            } else if (group.getId() == R.id.rg_group2) {
                RadioGroup otherGroup = dialog.findViewById(R.id.rg_group1);
                otherGroup.clearCheck();
            }
            changeGroup = false;
        }

        dialog.dismiss();
    }

    /**
     * @param list 获取抄送人和审批人
     */
    public void getCopyPerson(List<GoOutEntity.DATABean> list) {
        for (GoOutEntity.DATABean bean : list) {
            View view = View.inflate(this, R.layout.item_person, null);
            ImageView ivHeader = view.findViewById(R.id.iv_header);
            TextView tvName = view.findViewById(R.id.tv_name);
            tvName.setText(bean.userName);
            if (!TextUtils.isEmpty(bean.iconLink)) {
                Picasso.with(this)
                        .load(bean.iconLink)
                        .error(R.drawable.ic_user_blue)
                        .transform(new PicassoCircleTransform())
                        .resize(WindowUtil.dp2px(40, this), WindowUtil.dp2px(40, this))
                        .centerCrop()
                        .into(ivHeader);
            } else {
                ivHeader.setImageResource(R.drawable.ic_user_blue);
            }
            if (bean.type == 1) { //审批
                llSP.addView(view);
            } else {  //抄送
                llCS.addView(view);
            }
        }
    }
}
