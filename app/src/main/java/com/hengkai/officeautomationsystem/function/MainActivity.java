package com.hengkai.officeautomationsystem.function;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.function.home.HomeFragment;
import com.hengkai.officeautomationsystem.function.mine.MineFragment;
import com.hengkai.officeautomationsystem.function.work_platform.WorkPlatformFragment;
import com.hengkai.officeautomationsystem.network.entity.CheckVersionEntity;
import com.hengkai.officeautomationsystem.network.service.CheckVersionService;
import com.hengkai.officeautomationsystem.utils.OpenActivityUtils;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.VersionUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.vondear.rxtool.RxAppTool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

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

        // 收到通知公告广播注册
        registerReceiver();

        checkVersionNumber();
    }

    /**
     * 检测版本号
     */
    private void checkVersionNumber() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        CheckVersionService service = retrofit.create(CheckVersionService.class);
        Map<String, String> params = new HashMap<>();
        service.checkVersion(URLFinal.CHECK_VERSION, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CheckVersionEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CheckVersionEntity checkVersionEntity) {
                        if (checkVersionEntity.CODE == 1) {
                            if (!checkVersionEntity.versionNumber.equals(VersionUtils.getVerName(MainActivity.this))) {
                                easyPermission();
                            }
                        } else if (checkVersionEntity.CODE == 0) {
                            showLoginDialog(MainActivity.this);
                        } else {
                            ToastUtil.showToast(checkVersionEntity.MES);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("请求版本号失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 下载新版本的apk文件
     */
    private void goToDownLoad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("版本升级").setMessage("当前有新版本, 是否升级");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                download();
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }

    private void download() {
        //初始化下载
        FileDownloader.setup(this);

        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/恒凯OA/downloadApk";
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        } else {
            File file1 = new File(path + "/hk_OA.apk");
            if (file1.exists()) {
                file1.delete();
            }
        }
        FileDownloader.getImpl().create(URLFinal.DOWNLOAD_NEW_VERSION)
                .setPath(path + "/hk_OA.apk")
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //等待，已经进入下载队列
                        Toast.makeText(MainActivity.this, "正在下载中, 请稍后", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        // Log.i("666", "progress: " + soFarBytes / totalBytes * 100);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        File file = new File(task.getPath());
                        RxAppTool.installApp(MainActivity.this, file);//安装apk
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNotificationReceiver);
        super.onDestroy();
    }


    private NotificationReceiver mNotificationReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.hengkai.officeautomationsystem.MESSAGE_RECEIVED_ACTION";
    public static final String NOTIFICATION_RECEIVED_ACTION = "com.hengkai.officeautomationsystem.NOTIFICATION_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerReceiver() {
        // 处理自定义消息
        mNotificationReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mNotificationReceiver, filter);

        // 接收到新的通知的广播
        mNotificationReceiver = new NotificationReceiver();
        filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(NOTIFICATION_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mNotificationReceiver, filter);
    }

    public class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    setCostomMsg(messge);
                } else if (NOTIFICATION_RECEIVED_ACTION.equals(intent.getAction())) {
                    reloadData();
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        ToastUtil.showToast(msg);
    }

    public void easyPermission() {
        String[] permissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            if (EasyPermissions.hasPermissions(this, permissionList)) {
                goToDownLoad();
            } else {
                EasyPermissions.requestPermissions(this, "需要读取手机内存的权限, 如果拒绝可能无法正常更新App", 1001, permissionList);
            }
        } else {
            goToDownLoad();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //同意了授权
        goToDownLoad();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //拒绝了授权
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //拒绝授权后，从系统设置了授权后，返回APP进行相应的操作
            goToDownLoad();
        }
        if (requestCode == OpenActivityUtils.REQUEST_CODE) {
            if (homeFragment != null) {
                homeFragment.updateAll();
            }
        }
    }
}
