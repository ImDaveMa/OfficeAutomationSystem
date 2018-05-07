package com.hengkai.officeautomationsystem.function.visit_record;

import android.Manifest;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Harry on 2018/5/7.
 * 拜访跟进记录页面
 */
public class VisitRecordActivity extends BaseActivity<VisitRecordActivityPresenter> implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private boolean isLoadMore = false;
    private VisitRecordActivityAdapter adapter;
    private List<VisitRecordEntity.DATABean> mList;

    @Override
    protected int setupView() {
        return R.layout.activity_visit_record;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        initBaiDuLocation();

        mList = new ArrayList<>();

        setupRecyclerView();

    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.VISIT_RECORD_ACTIVITY_GET_VISIT_RECORD_LIST);
        return tags;
    }

    @Override
    protected VisitRecordActivityPresenter bindPresenter() {
        return new VisitRecordActivityPresenter();
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VisitRecordActivityAdapter(mList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
//                pageNum = 1;
                mPresenter.getVisitRecordList();
                isLoadMore = false;
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                pageNum++;
                mPresenter.getVisitRecordList();
                isLoadMore = true;
            }
        });
    }

    /**
     * 停止下拉刷新和上拉刷新
     */
    public void stopRefreshing() {
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setRefreshing(false);
    }

    public void getVisitRecordList(List<VisitRecordEntity.DATABean> list) {
        if (!isLoadMore) {
            mList.clear();
        }
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_back, R.id.iv_add, R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: //返回
                finish();
                break;
            case R.id.iv_add:   //新增拜访

                break;
            case R.id.iv_more:   //更多
                setupPopWindow();
                break;
        }
    }

    /**
     * 配置popupWindow相关
     */
    private void setupPopWindow() {
        View popView = View.inflate(this, R.layout.view_visit_record_pop_window, null);
        PopupWindow popupWindow = new PopupWindow(popView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        initPopupWindowListener(popView, popupWindow);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(8);//设置Z轴的高度
        }
        //设置动画
        popupWindow.setAnimationStyle(R.style.popupWindowAnimation);
        //设置背景颜色
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //设置可以获取焦点
        popupWindow.setFocusable(true);
        //设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(true);
        //更新popupwindow的状态
        popupWindow.update();
        //获取状态栏以及标题栏的高度
        TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        float actionBarHeight = actionbarSizeTypedArray.getDimension(0, 0);//actionbar高度
        int statusBarHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));

        popupWindow.showAtLocation(swipeTarget, Gravity.TOP | Gravity.END, 0, (int) actionBarHeight + statusBarHeight);
    }

    /**
     * @param view        初始化PopupWindow内部控件的监听事件
     * @param popupWindow
     */
    private void initPopupWindowListener(View view, final PopupWindow popupWindow) {
        final TextView tv_pop_one_day = view.findViewById(R.id.tv_pop_one_day);
        final TextView tv_pop_seven_day = view.findViewById(R.id.tv_pop_seven_day);
        final TextView tv_pop_Fifteen_day = view.findViewById(R.id.tv_pop_Fifteen_day);

        tv_pop_one_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tv_pop_one_day.getText().toString().trim();
                ToastUtil.showToast(str);
                popupWindow.dismiss();
            }
        });

        tv_pop_seven_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tv_pop_seven_day.getText().toString().trim();
                ToastUtil.showToast(str);
                popupWindow.dismiss();
            }
        });

        tv_pop_Fifteen_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tv_pop_Fifteen_day.getText().toString().trim();
                ToastUtil.showToast(str);
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 初始化百度定位
     */
    private void initBaiDuLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();

        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000);

        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);

        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);

        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);

        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            mLocationClient.stop();//定位获得到结果后停止定位
        }
    }

    public void easyPermission() {
        String[] permissionList = new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (Build.VERSION.SDK_INT >= 23) {
            if (EasyPermissions.hasPermissions(this, permissionList)) {

            } else {
                EasyPermissions.requestPermissions(this, "需要手机定位相关权限", 1001, permissionList);
            }
        } else {

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

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //拒绝了授权
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
