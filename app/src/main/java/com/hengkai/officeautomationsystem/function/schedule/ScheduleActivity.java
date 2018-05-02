package com.hengkai.officeautomationsystem.function.schedule;

import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harry on 2018/4/26.
 */
public class ScheduleActivity extends BaseActivity {

    @Override
    protected int setupView() {
        return R.layout.activity_schedule;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        initCalendarView();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void initCalendarView() {
        CalendarLayout calendarLayout = findViewById(R.id.calendarLayout);
        CalendarView calendarView = findViewById(R.id.calendarView);

        List<Calendar> list = new ArrayList<>();
        int year = calendarView.getCurYear();
        int month = calendarView.getCurMonth();

        list.add(getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        list.add(getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        list.add(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
        list.add(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
        list.add(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
        list.add(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
        list.add(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
        list.add(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));

        calendarView.setSchemeDate(list);

        TextView tv_current_time = findViewById(R.id.tv_current_time);
        String currentTime = calendarView.getCurYear() + "-" + calendarView.getCurMonth() + "-" + calendarView.getCurDay();
        tv_current_time.setText(currentTime);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }
}
