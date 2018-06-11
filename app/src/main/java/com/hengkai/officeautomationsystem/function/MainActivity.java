package com.hengkai.officeautomationsystem.function;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.function.home.HomeFragment;
import com.hengkai.officeautomationsystem.function.mine.MineFragment;
import com.hengkai.officeautomationsystem.function.work_platform.WorkPlatformFragment;
import com.hengkai.officeautomationsystem.jpush.JPushUtil;
import com.hengkai.officeautomationsystem.utils.OpenActivityUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private HomeFragment homeFragment;
    private WorkPlatformFragment workPlatformFragment;
    private MineFragment mineFragment;

    public static boolean isForeground = false;

    @Override
    protected int setupView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        setupBottomNavigationBar();

        bottomNavigationBar.selectTab(0);

        registerMessageReceiver();  // used for receive msg
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    /**
     * 初始化底部导航栏
     */
    private void setupBottomNavigationBar() {

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        //角标的数字
//        BadgeItem numberBadgeItem = new BadgeItem()
//                .setBorderWidth(0)
//                .setBackgroundColorResource(R.color.colorAccent)
//                .setText("5")
//                .setHideOnSelect(false);

//        numberBadgeItem.toggle(false); //开启或者关闭角标数字提示, 不传参数也可以

//        ShapeBadgeItem shapeBadgeItem = new ShapeBadgeItem()

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_bottom_navigation_bar_home, "首页").setInActiveColor(R.color.black7).setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_bottom_navigation_bar_work_platform, "工作").setInActiveColor(R.color.black7).setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_bottom_navigation_bar_mine, "我的").setInActiveColor(R.color.black7).setActiveColorResource(R.color.blue))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                if (homeFragment != null) ft.hide(homeFragment);
                if (workPlatformFragment != null) ft.hide(workPlatformFragment);
                if (mineFragment != null) ft.hide(mineFragment);

                switch (position) {
                    case 0:
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            ft.add(R.id.fl_container, homeFragment);
                        } else {
                            // 调用刷新
                            homeFragment.updateAll();
                            ft.show(homeFragment);
                        }
                        break;
                    case 1:
                        if (workPlatformFragment == null) {
                            workPlatformFragment = new WorkPlatformFragment();
                            ft.add(R.id.fl_container, workPlatformFragment);
                        } else {
                            ft.show(workPlatformFragment);
                        }
                        break;
                    case 2:
                        if (mineFragment == null) {
                            mineFragment = new MineFragment();
                            ft.add(R.id.fl_container, mineFragment);
                        } else {
                            ft.show(mineFragment);
                        }
                        break;
                    default:
                        break;
                }
                ft.commit();
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                homeFragment.updateAll();
            }
        });
    }

    /**
     * 页面回调结果事件
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OpenActivityUtils.REQUEST_CODE){
            if(homeFragment!=null) {
                homeFragment.updateAll();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {     //判断当前是否点击的是back键
            exitSystemByDoubleClick();
        }
        return false;
    }

    /**
     * 是否双击退出应用程序
     */
    private boolean isExit = false;

    /**
     * 双击退出应用程序
     */
    private void exitSystemByDoubleClick() {
        Timer timer;
        if (!isExit) {
            isExit = true;
            ToastUtil.showToast(MainActivity.this, "再按一次退出程序");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; //取消退出
                }
            }, 2000);       // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    // 处理自定义消息
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.hengkai.officeautomationsystem.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    setCostomMsg(messge);
                }
            } catch (Exception e){
            }
        }
    }

    private void setCostomMsg(String msg){
        ToastUtil.showToast(msg);
    }

}
