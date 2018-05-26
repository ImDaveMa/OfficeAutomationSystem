package com.hengkai.officeautomationsystem.function.visit_record.detail;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailGetVisitUnitEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.SPUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Harry on 2018/5/8.
 * 拜访跟进详情页面
 */
public class VisitRecordDetailActivity extends BaseActivity<VisitRecordDetailActivityPresenter> implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.tv_visit_type)
    TextView tvVisitType;
    @BindView(R.id.iv_visit_type)
    ImageView ivVisitType;
    @BindView(R.id.tv_visit_unit)
    TextView tvVisitUnit;
    @BindView(R.id.iv_visit_unit)
    ImageView ivVisitUnit;
    @BindView(R.id.tv_visit_customer)
    TextView tvVisitCustomer;
    @BindView(R.id.iv_visit_customer)
    ImageView ivVisitCustomer;
    @BindView(R.id.tv_visit_department)
    TextView tvVisitDepartment;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.iv_project)
    ImageView ivProject;
    @BindView(R.id.et_visit_summary)
    EditText etVisitSummary;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_end)
    Button btnEnd;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.ll_visit_type)
    LinearLayout llVisitType;
    @BindView(R.id.ll_visit_unit)
    LinearLayout llVisitUnit;
    @BindView(R.id.ll_visit_customer)
    LinearLayout llVisitCustomer;
    @BindView(R.id.ll_project)
    LinearLayout llProject;
    @BindView(R.id.ll_start_address)
    LinearLayout llStartAddress;
    @BindView(R.id.ll_end_address)
    LinearLayout llEndAddress;
    @BindView(R.id.tv_start_address)
    TextView tvStartAddress;
    @BindView(R.id.tv_end_address)
    TextView tvEndAddress;
    @BindView(R.id.tv_red_dot1)
    TextView tvRedDot1;
    @BindView(R.id.tv_red_dot2)
    TextView tvRedDot2;
    /**
     * 根据type的值判断提交类型(typeYM字段), 以区分是新增按钮进入还是列表item进入当前页面
     */
    private int type;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    /**
     * 所选单位的ID
     */
    private int unitID = -1;
    /**
     * 所选拜访人的ID
     */
    private String customerID = "-1";
    /**
     * 拜访类型的ID
     */
    private int visitTypeID = -1;
    /**
     * 所选项目的ID
     */
    private int projectID = -1;
    /**
     * 纬度
     */
    private double locationLatitude;
    /**
     * 经度
     */
    private double locationLongitude;
    /**
     * 详细地址
     */
    private String locationAddress;
    /**
     * 点击列表传入到当前页面的ID
     */
    private int currentID = -1;

    /**
     * 判断当前是开启状态还是结束状态
     */
    private boolean isStart;
    private PoiSearch poiSearch;

    @Override
    protected int setupView() {
        return R.layout.activity_visit_record_detail;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        getCurrentPhoneNumber();

        initButtonStatus();
        judgeType();
        initBaiDuLocation();

    }

    /**
     * @return 获取硬件上的电话号码
     */
    private String getCurrentPhoneNumber() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return tm.getLine1Number();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET_VISIT_UNIT_LIST);
        tags.add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET_VISIT_UNIT_CUSTOMER_LIST);
        tags.add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET_VISIT_UNIT_PROJECT_LIST);
        tags.add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_TO_SAVE_ON_NEW_ADD);
        tags.add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET);
        tags.add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_TO_END);
        return tags;
    }

    @Override
    protected VisitRecordDetailActivityPresenter bindPresenter() {
        return new VisitRecordDetailActivityPresenter();
    }

    /**
     * 判断提交的类型
     */
    private void judgeType() {
        String add = getIntent().getStringExtra("type");
        if (add.equals("add")) {
            type = 1001;    //点击新增按钮进入当前页面
        } else {
            type = 1002;    //点击列表item进入当前页面
            currentID = getIntent().getIntExtra("currentID", 0);
            //如果是从列表进入的话, 需要拿到ID请求网络 获取相关信息
            mPresenter.getVisitRecordDetail(currentID);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_save, R.id.ll_visit_type, R.id.ll_visit_unit, R.id.ll_visit_customer, R.id.ll_project, R.id.btn_start, R.id.btn_end, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:  //返回
                setResult(CommonFinal.VISIT_RECORD_RESULT_CODE);
                finish();
                break;
            case R.id.ll_visit_type:    //拜访类型
                visitType();
                break;
            case R.id.ll_visit_unit:    //拜访单位
                showDialog();
                mPresenter.getVisitUnitList();
                break;
            case R.id.ll_visit_customer:    //拜访人
                if (unitID == -1) {
                    ToastUtil.showToast("请先选择拜访单位");
                    return;
                }
                showDialog();
                mPresenter.getVisitCustomerList(unitID);
                break;
            case R.id.ll_project:   //跟进的项目
                if (customerID.equals("-1")) {
                    ToastUtil.showToast("请先选择拜访人");
                    return;
                }
                mPresenter.getVisitProjectList(customerID);
                break;
            case R.id.tv_save:  //保存
//                verificationTextView();
                if (type == 1001) {
                    //点击新增按钮进入当前页面
                    Map<String, String> params = new HashMap<>();
                    params.put("typeYM", "add");
                    params.put("companyId", String.valueOf(unitID));
                    params.put("type", String.valueOf(visitTypeID));
                    params.put("contactsId", customerID);
                    params.put("department", tvVisitDepartment.getText().toString().trim());
                    if (projectID != -1) {
                        params.put("projectId", String.valueOf(projectID));
                    }
                    params.put("summary", etVisitSummary.getText().toString().trim());
                    showDialog();
                    mPresenter.toSaveCurrentPageOnNewAdd(params);
                } else {
                    //点击列表item进入当前页面
                    Map<String, String> params = new HashMap<>();
                    params.put("ID", String.valueOf(currentID));
                    params.put("typeYM", "pro");
                    params.put("companyId", String.valueOf(unitID));
                    params.put("type", String.valueOf(visitTypeID));
                    if (projectID != -1) {
                        params.put("projectId", String.valueOf(projectID));
                    }
                    params.put("contactsId", customerID);
                    params.put("department", tvVisitDepartment.getText().toString().trim());
                    params.put("summary", etVisitSummary.getText().toString().trim());

                    showDialog();
                    mPresenter.toSaveOrCommit(false, params);
                }
                break;
            case R.id.btn_start:    //开始
                isStart = true;
                if (!verificationTextView()) {
                    return;
                }
                showDialog();
                easyPermission();
                break;
            case R.id.btn_end:      //结束
                isStart = false;
                if (!verificationTextView()) {
                    return;
                }
                showDialog();
                mLocationClient.start();
                break;
            case R.id.btn_commit:   //提交
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(currentID));
                params.put("typeYM", "approval");
                params.put("companyId", String.valueOf(unitID));
                params.put("type", String.valueOf(visitTypeID));
                if (projectID != -1) {
                    params.put("projectId", String.valueOf(projectID));
                }
                params.put("contactsId", customerID);
                params.put("department", tvVisitDepartment.getText().toString().trim());
                params.put("summary", etVisitSummary.getText().toString().trim());

                showDialog();
                mPresenter.toSaveOrCommit(true, params);
                break;
        }
    }

    /**
     * 拜访结束是点击 执行这个方法
     * @param addressName
     */
    private void toEndVisit(String addressName) {
        if (locationLatitude == 0 || locationLongitude == 0 || TextUtils.isEmpty(locationAddress)) {
            ToastUtil.showToast("获取地址失败, 请重新点击开始");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("ID", String.valueOf(currentID));
        params.put("typeYM", "end");
        params.put("companyId", String.valueOf(unitID));
        params.put("type", String.valueOf(visitTypeID));
        if (projectID != -1) {
            params.put("projectId", String.valueOf(projectID));
        }
        params.put("contactsId", customerID);
        params.put("department", tvVisitDepartment.getText().toString().trim());
        params.put("endLongitude", String.valueOf(locationLongitude));
        params.put("endLatitude", String.valueOf(locationLatitude));
        params.put("address", addressName);
//        params.put("phone", SPUtils.getString(UserInfo.PHONE.name(), ""));
        params.put("phone", getCurrentPhoneNumber());
        params.put("summary", etVisitSummary.getText().toString().trim());

        mPresenter.toEnd(params);
    }


    /**
     * 初始化button状态
     */
    private void initButtonStatus() {
        btnCommit.setEnabled(false);
        btnEnd.setEnabled(false);
    }

    /**
     * 当点击开始按钮后, 修改开始按钮的状态
     */
    public void setupStartButton() {
        String currentTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_5, System.currentTimeMillis());
        btnStart.setText(String.format("开始 %s", currentTime));
        btnStart.setEnabled(false);
        btnEnd.setEnabled(true);
    }

    /**
     * 当点击结束按钮后, 修改结束按钮的状态
     */
    public void setupEndButton() {
        String currentTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_5, System.currentTimeMillis());
        btnEnd.setText(String.format("结束 %s", currentTime));
        btnEnd.setEnabled(false);
        btnCommit.setEnabled(true);
    }

    /**
     * 开始拜访, 在确认了定位权限后调用
     * @param addressName
     */
    private void toStartVisit(String addressName) {
        if (locationLatitude == 0 || locationLongitude == 0 || TextUtils.isEmpty(locationAddress)) {
            ToastUtil.showToast("获取地址失败, 请重新点击");
            return;
        }

        if (type == 1001) {
            //点击新增按钮进入当前页面
            Map<String, String> params = new HashMap<>();
            params.put("typeYM", "addS");
            params.put("companyId", String.valueOf(unitID));
            params.put("type", String.valueOf(visitTypeID));
            params.put("contactsId", customerID);
            params.put("department", tvVisitDepartment.getText().toString().trim());
            if (projectID != -1) {
                params.put("projectId", String.valueOf(projectID));
            }
            params.put("startLongitude", String.valueOf(locationLongitude));
            params.put("startLatitude", String.valueOf(locationLatitude));
            params.put("address", addressName);
            params.put("phone", getCurrentPhoneNumber());
            params.put("summary", etVisitSummary.getText().toString().trim());

            showDialog();
            mPresenter.toStartCurrentPageOnNewAdd(params);
            //清空全局变量, 以便于结束按钮获取地址信息
            locationLongitude = 0;
            locationLatitude = 0;
            locationAddress = "";
        } else {
            //点击列表item进入当前页面
            Map<String, String> params = new HashMap<>();
            params.put("ID", String.valueOf(currentID));
            params.put("typeYM", "start");
            params.put("companyId", String.valueOf(unitID));
            params.put("type", String.valueOf(visitTypeID));
            if (projectID != -1) {
                params.put("projectId", String.valueOf(projectID));
            }
            params.put("contactsId", customerID);
            params.put("department", tvVisitDepartment.getText().toString().trim());
            params.put("startLongitude", String.valueOf(locationLongitude));
            params.put("startLatitude", String.valueOf(locationLatitude));
            params.put("address", addressName);
            params.put("phone", getCurrentPhoneNumber());
            params.put("summary", etVisitSummary.getText().toString().trim());

            showDialog();
            mPresenter.toStart(params);
        }
    }

    /**
     * 验证必填参数是否为空
     */
    private boolean verificationTextView() {
        String visitType = tvVisitType.getText().toString().trim();
        String visitUnit = tvVisitUnit.getText().toString().trim();
        String visitCustomer = tvVisitCustomer.getText().toString().trim();
        String visitProject = tvProject.getText().toString().trim();
        if (TextUtils.isEmpty(visitType) || visitType.equals("请选择")) {
            ToastUtil.showToast("请选择拜访类型");
            return false;
        }
        if (visitType.equals("拜访") || visitType.equals("跟进")){
            if (TextUtils.isEmpty(visitUnit) || visitUnit.equals("请选择")) {
                ToastUtil.showToast("请选择拜访单位");
                return false;
            } else if (TextUtils.isEmpty(visitCustomer) || visitCustomer.equals("请选择")) {
                ToastUtil.showToast("请选择拜访人");
                return false;
            }
        }
        if (visitType.equals("跟进")) {
            if (TextUtils.isEmpty(visitProject) || visitUnit.equals("请选择")) {
                ToastUtil.showToast("请选择跟进项目");
                return false;
            }
        }
        return true;
    }

    /**
     * 从服务器上通过拜访人的ID获取到项目列表
     */
    public void getVisitProjectList(List<VisitRecordDetailGetVisitUnitEntity.DATABean> list) {
        if (list.size() == 0) {
            ToastUtil.showToast("暂时没有跟进的项目");
            return;
        }
        BottomDialogAdapter adapter = new BottomDialogAdapter(list);
        final BottomSheetDialog dialog = getBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new BottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(VisitRecordDetailGetVisitUnitEntity.DATABean bean) {
                projectID = bean.id;
                tvProject.setText(bean.name);
                tvProject.setTextColor(getResources().getColor(R.color.black1));
                ivProject.setVisibility(View.GONE);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * 从服务器上通过拜访单位的ID获取到客户列表
     */
    public void getVisitCustomerList(List<VisitRecordDetailGetVisitUnitEntity.DATABean> list) {
        if (list.size() == 0) {
            ToastUtil.showToast("当前单位没有相关联系人");
            return;
        }
        BottomDialogAdapter adapter = new BottomDialogAdapter(list);
        final BottomSheetDialog dialog = getBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new BottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(VisitRecordDetailGetVisitUnitEntity.DATABean bean) {
                customerID = String.valueOf(bean.id);
                tvVisitCustomer.setText(bean.name);
                tvVisitCustomer.setTextColor(getResources().getColor(R.color.black1));
                ivVisitCustomer.setVisibility(View.GONE);
                tvVisitDepartment.setText(bean.department);
                tvVisitDepartment.setTextColor(getResources().getColor(R.color.black1));
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * 从服务器上获取拜访单位列表
     */
    public void getVisitUnitList(List<VisitRecordDetailGetVisitUnitEntity.DATABean> list) {
        BottomDialogAdapter adapter = new BottomDialogAdapter(list);
        final BottomSheetDialog dialog = getBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new BottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(VisitRecordDetailGetVisitUnitEntity.DATABean bean) {
                unitID = bean.id;
                tvVisitUnit.setText(bean.name);
                tvVisitUnit.setTextColor(getResources().getColor(R.color.black1));
                ivVisitUnit.setVisibility(View.GONE);
                dialog.cancel();
            }
        });

        dialog.show();
    }


    /**
     * 拜访类型
     */
    private void visitType() {
        View view = View.inflate(this, R.layout.dialog_visit_record_detail_visit_type, null);
        final TextView tvBF = view.findViewById(R.id.tv_bf);
        final TextView tvGJ = view.findViewById(R.id.tv_gj);
        final TextView tvZD = view.findViewById(R.id.tv_zd);
        final BottomSheetDialog dialog = getBottomSheetDialog(view);

        tvBF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitTypeID = 1;
                String bf = tvBF.getText().toString().trim();
                tvVisitType.setText(bf);
                tvVisitType.setTextColor(getResources().getColor(R.color.black1));
                ivVisitType.setVisibility(View.GONE);
                dialog.dismiss();
                tvRedDot1.setVisibility(View.VISIBLE);
                tvRedDot2.setVisibility(View.VISIBLE);
            }
        });

        tvGJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitTypeID = 0;
                tvVisitType.setText(tvGJ.getText().toString().trim());
                tvVisitType.setTextColor(getResources().getColor(R.color.black1));
                ivVisitType.setVisibility(View.GONE);
                dialog.dismiss();
                tvRedDot1.setVisibility(View.VISIBLE);
                tvRedDot2.setVisibility(View.VISIBLE);
            }
        });

        tvZD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitTypeID = 2;
                tvVisitType.setText(tvZD.getText().toString().trim());
                tvVisitType.setTextColor(getResources().getColor(R.color.black1));
                ivVisitType.setVisibility(View.GONE);
                dialog.dismiss();
                tvRedDot1.setVisibility(View.INVISIBLE);
                tvRedDot2.setVisibility(View.INVISIBLE);
            }
        });

        dialog.show();
    }

    /**
     * @return 获取底部弹窗
     */
    private BottomSheetDialog getBottomSheetDialog(View view) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        return dialog;
    }

    /**
     * 获取底部弹窗并且弹出
     */
    private BottomSheetDialog getBottomSheetDialog(BottomDialogAdapter adapter) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(this, R.layout.dialog_visit_record_detail, null);
        dialog.setContentView(view);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        return dialog;
    }

    /**
     * 初始化百度定位
     */
    private void initBaiDuLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());

        poiSearch = PoiSearch.newInstance();

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

        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);
//        option.setIsNeedLocationPoiList(true);
//        option.setIsNeedLocationDescribe(true);

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取纬度信息
            locationLatitude = location.getLatitude();
            //获取经度信息
            locationLongitude = location.getLongitude();
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            locationAddress = location.getAddrStr();//获取详细地址

            setupPoiSearch(location.getAddress().street, location.getLatitude(), location.getLongitude());

//            if (isStart) {
//                toStartVisit();
//            } else {
//                toEndVisit();
//            }

            mLocationClient.stop();//定位获得到结果后停止定位
        }
    }

    private void setupPoiSearch(String keyWord, double latitude, double longitude) {

        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                List<PoiInfo> allPoi = poiResult.getAllPoi();

                dismissDialog();

                if (poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    ToastUtil.showToast("无法获取周边位置信息, 请重新获取");
                    return;
                }
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    if (allPoi != null && allPoi.size() > 0) {
                        showBottomDialog(allPoi);
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

        PoiNearbySearchOption poiNearBySearchOption = new PoiNearbySearchOption();
        poiNearBySearchOption.keyword(keyWord);
        poiNearBySearchOption.location(new LatLng(latitude, longitude));
        poiNearBySearchOption.sortType(PoiSortType.distance_from_near_to_far);

        poiNearBySearchOption.pageCapacity(10);
//        poiNearBySearchOption.pageNum(1);
        poiNearBySearchOption.radius(100);

        poiSearch.searchNearby(poiNearBySearchOption);
    }

    /**
     * 底部弹窗, 显示定位结果集
     * @param allPoi 后边信息集合
     */
    private void showBottomDialog(List<PoiInfo> allPoi) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = View.inflate(this, R.layout.dialog_visit_record_detail, null);
        initDialogChildView(view, allPoi, dialog);
        dialog.setContentView(view);
        dialog.show();

    }

    /**
     * 初始化底部弹窗的子view
     * @param view
     * @param allPoi 后边信息集合
     * @param bottomDialog
     */
    private void initDialogChildView(View view, List<PoiInfo> allPoi, final Dialog bottomDialog) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        BottomPoiDialogAdapter adapter = new BottomPoiDialogAdapter(allPoi);
        recyclerView.setAdapter(adapter);

        adapter.setOnAdapterItemClickListener(new BottomPoiDialogAdapter.AdapterItemClickListener() {
            @Override
            public void getAddressName(String addressName) {
                if (isStart) {
                    tvStartAddress.setText(addressName);
                    llStartAddress.setVisibility(View.VISIBLE);
                    toStartVisit(addressName);
                } else {
                    tvEndAddress.setText(addressName);
                    llEndAddress.setVisibility(View.VISIBLE);
                    toEndVisit(addressName);
                }
                bottomDialog.dismiss();
            }
        });
    }

    /**
     * 通过列表进入当前页面, 并传值给各个控件
     */
    public void setupCurrentPage(VisitRecordDetailEntity.DATABean bean) {
        switch (bean.type) {
            case "0":
                tvVisitType.setText("跟进");
                visitTypeID = 0;
                break;
            case "1":
                tvVisitType.setText("拜访");
                visitTypeID = 1;
                break;
            default:
                tvVisitType.setText("招待");
                visitTypeID = 2;
                break;
        }
        ivVisitType.setVisibility(View.GONE);
        tvVisitUnit.setText(bean.companyName);
        ivVisitUnit.setVisibility(View.GONE);
        tvVisitCustomer.setText(bean.contactsName);
        ivVisitCustomer.setVisibility(View.GONE);
        tvVisitDepartment.setText(bean.department);
        tvProject.setText(bean.projectName);
        etVisitSummary.setText(bean.summary);
        unitID = bean.companyId;
        customerID = bean.contactsId;
        projectID = bean.projectId;
        tvVisitType.setTextColor(getResources().getColor(R.color.black1));
        tvVisitUnit.setTextColor(getResources().getColor(R.color.black1));
        tvVisitCustomer.setTextColor(getResources().getColor(R.color.black1));
        tvVisitDepartment.setTextColor(getResources().getColor(R.color.black1));
        tvProject.setTextColor(getResources().getColor(R.color.black1));

        if (bean.startTime != 0) {//加入判断状态, 如果有开始时间, 则说明之前已经开始了拜访
            String startTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_5, bean.startTime);
            btnStart.setText(String.format("开始 %s", startTime));
            btnStart.setEnabled(false);
            btnEnd.setEnabled(true);
        }
        if (bean.endTime != 0) {
            String endTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_5, bean.endTime);
            btnEnd.setText(String.format("结束 %s", endTime));
            btnEnd.setEnabled(false);
            btnCommit.setEnabled(true);
        }
        if (!bean.isSubmission) {//如果已提交, 则所有按钮都不能点
            btnCommit.setEnabled(false);
            btnStart.setEnabled(false);
            btnEnd.setEnabled(false);
            tvSave.setEnabled(false);
            tvSave.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(bean.startAddress)) {
            llStartAddress.setVisibility(View.VISIBLE);
            tvStartAddress.setText(bean.startAddress);

            if (!TextUtils.isEmpty(bean.endAddress)) {
                llEndAddress.setVisibility(View.VISIBLE);
                tvEndAddress.setText(bean.endAddress);
            }
        }
    }

    /**
     * 当点击开始按钮后, 禁止用户修改数据
     */
    public void setupLinearLayout() {
        llVisitType.setEnabled(false);
        llVisitUnit.setEnabled(false);
        llVisitCustomer.setEnabled(false);
        llProject.setEnabled(false);
    }

    /**
     * 运行时权限
     */
    public void easyPermission() {
        String[] permissionList = new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (Build.VERSION.SDK_INT >= 23) {
            if (EasyPermissions.hasPermissions(this, permissionList)) {
                mLocationClient.start();
            } else {
                EasyPermissions.requestPermissions(this, "需要手机定位相关权限", 1001, permissionList);
            }
        } else {
            mLocationClient.start();
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
        mLocationClient.start();
        showDialog();
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
            mLocationClient.start();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(CommonFinal.VISIT_RECORD_RESULT_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当页面结束的时候取消注册百度地图
        mLocationClient.unRegisterLocationListener(myListener);
        mLocationClient.stop();
        poiSearch.destroy();
    }
}
