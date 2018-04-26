package com.hengkai.officeautomationsystem.utils;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by Harry on 2018/4/24.
 * 时间选择器的工具包
 */
public class MaterialDateTimePickerUtils {

    private static final Calendar CALENDAR = Calendar.getInstance();

    private static DatePickerDialog getDatePickerDialog(DatePickerDialog.OnDateSetListener onDateSetListener) {
        return DatePickerDialog.newInstance(onDateSetListener,
                CALENDAR.get(Calendar.YEAR),
                CALENDAR.get(Calendar.MONTH),
                CALENDAR.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 显示日期选择器的Dialog
     * @param manager android.app.FragmentManager包下的FragmentManager
     * @param onDateSetListener 监听
     */
    public static void showDatePickerDialog(android.app.FragmentManager manager, DatePickerDialog.OnDateSetListener onDateSetListener) {
        getDatePickerDialog(onDateSetListener).show(manager, "Datepickerdialog");
    }

    private static TimePickerDialog getTimePickerDialog(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        return TimePickerDialog.newInstance(onTimeSetListener,
                CALENDAR.get(Calendar.HOUR_OF_DAY),
                CALENDAR.get(Calendar.MINUTE),
                true);
    }

    /**
     * 显示时间选择器的Dialog
     * @param manager android.app.FragmentManager包下的FragmentManager
     * @param onTimeSetListener 监听
     */
    public static void showTimePickerDialog(android.app.FragmentManager manager, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        getTimePickerDialog(onTimeSetListener).show(manager, "Timepickerdialog");
    }
}
