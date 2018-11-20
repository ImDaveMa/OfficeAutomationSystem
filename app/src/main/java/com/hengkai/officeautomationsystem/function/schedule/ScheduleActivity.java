package com.hengkai.officeautomationsystem.function.schedule;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.ScheduleEntity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/4/26.
 * Edit by Dave on 2018/11/19
 */
public class ScheduleActivity extends BaseActivity<SchedulePresenter> implements OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.calendarLayout)
    CalendarLayout calendarLayout;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public AlertDialog changeMonthDialog;
    private List<ScheduleEntity.ScheduleBean> mList = new ArrayList<>();
    private ScheduleListAdapter mAdapter;

    @Override
    protected int setupView() {
        return R.layout.activity_schedule;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initView() {
        ButterKnife.bind(this);

        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);

        tvTitle.setText(String.format("%d-%d", calendarView.getCurYear(), calendarView.getCurMonth()));
        tvOperation.setVisibility(View.VISIBLE);
        tvOperation.setText("新增");

        initCalendarView();

        // 初始化列表
        mAdapter = new ScheduleListAdapter(this, mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        changeMonthDialog = createDialog();
        mPresenter.getCalendarList(calendarView.getCurYear(), calendarView.getCurMonth());
    }

    @OnClick({R.id.iv_back,R.id.tv_operation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_operation:

                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> list = new ArrayList<>();
        list.add(NetworkTagFinal.SCHEDULE_ACTIVITY_GET_LIST);
        list.add(NetworkTagFinal.SCHEDULE_ACTIVITY_GET_LIST_WITH_DAY);
        return list;
    }

    @Override
    protected SchedulePresenter bindPresenter() {
        return new SchedulePresenter();
    }

    private void initCalendarView() {
        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onMonthChange(int year, int month) {
                tvTitle.setText(String.format("%d-%d", year, month));
                mPresenter.getCalendarList(year, month);
            }
        });
        calendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar calendar, boolean isClick) {
                mPresenter.getCalendarListWithDay(calendar.getYear(), calendar.getMonth(), calendar.getDay());
            }
        });
    }

    public void renderCalendar(List<String> data){
        List<Calendar> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String[] date = data.get(i).split("-");

            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            list.add(getSchemeCalendar(year, month, day, 0xFFdf1356, ""));
        }

        calendarView.setSchemeDate(list);
        //calendarView.update();
    }

    /**
     * 呈现选中的天
     * @param data
     */
    public void renderDayCalendars(List<ScheduleEntity.ScheduleBean> data){
        mList.clear();
        mList.addAll(data);
        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(View v, Object o, int position) {

    }
}
