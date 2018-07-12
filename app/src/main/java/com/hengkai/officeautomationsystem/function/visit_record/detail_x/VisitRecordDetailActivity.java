package com.hengkai.officeautomationsystem.function.visit_record.detail_x;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
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
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
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
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_person.SelectVisitPersonActivity;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_project.SelectVisitProjectActivity;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_unit.SelectVisitUnitActivity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Harry on 2018/6/15.
 * 拜访跟进详情页面
 */
public class VisitRecordDetailActivity extends BaseActivity<VisitRecordDetailPresenter> implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_visit_type)
    TextView tvVisitType;
    @BindView(R.id.iv_visit_type)
    ImageView ivVisitType;
    @BindView(R.id.tv_red_dot1)
    TextView tvRedDot1;
    @BindView(R.id.tv_visit_unit)
    TextView tvVisitUnit;
    @BindView(R.id.iv_visit_unit)
    ImageView ivVisitUnit;
    @BindView(R.id.tv_red_dot2)
    TextView tvRedDot2;
    @BindView(R.id.tv_visit_customer)
    TextView tvVisitCustomer;
    @BindView(R.id.iv_visit_customer)
    ImageView ivVisitCustomer;
    @BindView(R.id.tv_visit_department)
    TextView tvVisitDepartment;
    @BindView(R.id.tv_red_dot3)
    TextView tvRedDot3;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.iv_project)
    ImageView ivProject;
    @BindView(R.id.tv_start_address)
    TextView tvStartAddress;
    @BindView(R.id.ll_start_address)
    LinearLayout llStartAddress;
    @BindView(R.id.tv_end_address)
    TextView tvEndAddress;
    @BindView(R.id.ll_end_address)
    LinearLayout llEndAddress;
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
    /**
     * 当前拜访跟进的ID
     */
    private int currentVisitRecordID = 0;
    /**
     * 当前拜访单位的ID
     */
    private int currentUnitID = 0;
    /**
     * 当前拜访客户的ID
     */
    private String currentCustomerID = "";

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private PoiSearch poiSearch;
    /**
     * 判断当前状态是否为已经开始拜访的
     */
    private boolean isStart;
    private String locationStartAddress, locationEndAddress;
    private double locationLatitude;
    private double locationLongitude;
    /**
     * 拜访类型
     */
    private int visitTypeID = -1;
    /**
     * 所选项目的ID
     */
    private int projectID = 0;

    @Override
    protected int setupView() {
        return R.layout.activity_visit_record_detail;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        //初始化提交和结束按钮的状态
        btnCommit.setEnabled(false);
        btnEnd.setEnabled(false);

        judgeType();
        initButtonStatus();
        initBaiDuLocation();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET);
        tags.add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY);
        return tags;
    }

    @Override
    protected VisitRecordDetailPresenter bindPresenter() {
        return new VisitRecordDetailPresenter();
    }

    @OnClick({R.id.iv_back, R.id.tv_save, R.id.ll_visit_type, R.id.ll_visit_unit, R.id.ll_visit_customer, R.id.ll_project, R.id.btn_start, R.id.btn_end, R.id.btn_commit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(CommonFinal.VISIT_RECORD_RESULT_CODE);
                finish();
                break;
            case R.id.tv_save:  //保存
                showDialog();
                mPresenter.submissionData("visit_save", String.valueOf(currentVisitRecordID),
                        String.valueOf(currentUnitID), String.valueOf(visitTypeID), String.valueOf(projectID),
                        String.valueOf(currentCustomerID), tvVisitDepartment.getText().toString().trim(),
                        etVisitSummary.getText().toString().trim(), null, null, null,
                        null, null, null, null, null);
                break;
            case R.id.ll_visit_type://选择拜访类型
                selectVisitType();
                break;
            case R.id.ll_visit_unit://选择拜访单位
                if (visitTypeID == -1) {
                    ToastUtil.showToast("请先选择拜访类型");
                    return;
                }
                intent = new Intent(this, SelectVisitUnitActivity.class);
                startActivityForResult(intent, CommonFinal.SELECT_VISIT_UNIT_REQUEST_CODE);
                break;
            case R.id.ll_visit_customer://选择拜访人
                if (currentUnitID <= 0) {
                    ToastUtil.showToast("请先选择拜访单位");
                    return;
                }
                intent = new Intent(this, SelectVisitPersonActivity.class);
                intent.putExtra("unitID", currentUnitID);
                startActivityForResult(intent, CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE);
                break;
            case R.id.ll_project://选择跟进项目
                if (TextUtils.isEmpty(currentCustomerID)) {
                    ToastUtil.showToast("请先选择拜访人");
                    return;
                }
                intent = new Intent(this, SelectVisitProjectActivity.class);
                intent.putExtra("currentCustomerID", currentCustomerID);
                startActivityForResult(intent, CommonFinal.SELECT_VISIT_PROJECT_REQUEST_CODE);
                break;
            case R.id.btn_start://开始
                if (!verificationTextView()) {
                    return;
                }
                isStart = true;
                showDialog();
                easyPermission();
                break;
            case R.id.btn_end://结束
                if (!verificationTextView()) {
                    return;
                }
                isStart = false;
                showDialog();
                mLocationClient.start();
                break;
            case R.id.btn_commit://提交
                String summary = etVisitSummary.getText().toString().trim();
                if (TextUtils.isEmpty(summary)) {
                    ToastUtil.showToast("请填写总结");
                    return;
                }
                showDialog();
                if (currentCustomerID.equals("null")) {
                    currentCustomerID = "";
                }
                mPresenter.submissionData("visit_submission", String.valueOf(currentVisitRecordID),
                        String.valueOf(currentUnitID), String.valueOf(visitTypeID), String.valueOf(projectID),
                        currentCustomerID, tvVisitDepartment.getText().toString().trim(),
                        etVisitSummary.getText().toString().trim(), null, null,
                        null, null, null, null, null, null);
                break;
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    /**
     * @param currentVisitRecordID 当前拜访的ID, 通过保存, 开始, 结束后获得
     */
    public void callbackSubmissionData(int currentVisitRecordID) {
        this.currentVisitRecordID = currentVisitRecordID;

    }

    /**
     * 选择拜访类型
     */
    private void selectVisitType() {
        View view = View.inflate(this, R.layout.dialog_visit_record_detail_visit_type, null);
        final TextView tvBF = view.findViewById(R.id.tv_bf);
        final TextView tvGJ = view.findViewById(R.id.tv_gj);
        final TextView tvZD = view.findViewById(R.id.tv_zd);
        final TextView tvHT = view.findViewById(R.id.tv_ht);
        final TextView tvBS = view.findViewById(R.id.tv_bs);
        final TextView tvFA = view.findViewById(R.id.tv_fa);
        final BottomSheetDialog dialog = getBottomSheetDialog(view);

        tvBF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitTypeID = 1;
                tvVisitType.setText(tvBF.getText().toString().trim());
                ivVisitType.setVisibility(View.GONE);
                //判断当前页面是否是从列表进入, 如果当前状态是开始的状态, 则添加小红点判断
                if (currentVisitRecordID != 0 && !btnStart.isEnabled() && btnEnd.isEnabled()) {
                    tvRedDot1.setVisibility(View.VISIBLE);
                    tvRedDot2.setVisibility(View.VISIBLE);
                    tvRedDot3.setVisibility(View.INVISIBLE);
                } else {
                    tvRedDot1.setVisibility(View.INVISIBLE);
                    tvRedDot2.setVisibility(View.INVISIBLE);
                    tvRedDot3.setVisibility(View.INVISIBLE);
                }
                dialog.dismiss();
            }
        });

        tvGJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitTypeID = 0;
                tvVisitType.setText(tvGJ.getText().toString().trim());
                ivVisitType.setVisibility(View.GONE);
                tvRedDot1.setVisibility(View.VISIBLE);
                tvRedDot2.setVisibility(View.VISIBLE);
                tvRedDot3.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        tvZD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitTypeID = 2;
                tvVisitType.setText(tvZD.getText().toString().trim());
                ivVisitType.setVisibility(View.GONE);
                tvRedDot1.setVisibility(View.INVISIBLE);
                tvRedDot2.setVisibility(View.INVISIBLE);
                tvRedDot3.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });

        tvFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitTypeID = 3;
                tvVisitType.setText(tvFA.getText().toString().trim());
                ivVisitType.setVisibility(View.GONE);
                tvRedDot1.setVisibility(View.VISIBLE);
                tvRedDot2.setVisibility(View.VISIBLE);
                tvRedDot3.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        tvHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitTypeID = 4;
                tvVisitType.setText(tvHT.getText().toString().trim());
                ivVisitType.setVisibility(View.GONE);
                tvRedDot1.setVisibility(View.VISIBLE);
                tvRedDot2.setVisibility(View.VISIBLE);
                tvRedDot3.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        tvBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitTypeID = 5;
                tvVisitType.setText(tvBS.getText().toString().trim());
                ivVisitType.setVisibility(View.GONE);
                tvRedDot1.setVisibility(View.VISIBLE);
                tvRedDot2.setVisibility(View.VISIBLE);
                tvRedDot3.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 获取到底部弹窗的实例
     */
    private BottomSheetDialog getBottomSheetDialog(View view) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        return dialog;
    }

    /**
     * 判断提交的类型
     */
    private void judgeType() {
        String add = getIntent().getStringExtra("type");
        if (add.equals("add")) {//点击新增按钮进入当前页面

            // 从联系人库 - 联系人详情 - 进入到当前页面
            int companyId = getIntent().getIntExtra("companyId", 0);
            String companyName = getIntent().getStringExtra("companyName");
            int userId = getIntent().getIntExtra("userId", 0);
            String userName = getIntent().getStringExtra("userName");
            String department = getIntent().getStringExtra("department");
            if (companyId > 0) {
                tvVisitUnit.setText(companyName);
                ivVisitUnit.setVisibility(View.GONE);
                tvVisitCustomer.setText(userName);
                ivVisitCustomer.setVisibility(View.GONE);
                tvVisitDepartment.setText(department);
                currentUnitID = companyId;
                currentCustomerID = String.valueOf(userId);
            }
        } else {//点击列表item进入当前页面
            currentVisitRecordID = getIntent().getIntExtra("currentID", 0);
            //如果是从列表进入的话, 需要拿到ID请求网络 获取相关信息
            mPresenter.getVisitRecordDetail(currentVisitRecordID);
        }
    }

    /**
     * @return 获取硬件上的电话号码
     */
    private String getCurrentPhoneNumber() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return tm.getLine1Number() == null ? "" : tm.getLine1Number();
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
            // float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            // String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            if (errorCode == 505) {
                ToastUtil.showToast("百度定位KEY不存在或者非法，请按照说明文档重新申请KEY");
                dismissDialog(); // 防止无法关闭弹窗，导致假死
                mLocationClient.stop();//定位获得到结果后停止定位
                return;
            }

            // String locationAddress = location.getAddrStr();//获取详细地址

            setupPoiSearch(location.getAddress().street, location.getLatitude(), location.getLongitude());

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
     *
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
     *
     * @param view
     * @param allPoi       后边信息集合
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
                    locationStartAddress = addressName;
                    toStartVisit(addressName);
                } else {
                    locationEndAddress = addressName;
                    toEndVisit(addressName);
                }
                bottomDialog.dismiss();
            }
        });
    }

    private void toStartVisit(String addressName) {
        if (locationLatitude == 0 || locationLongitude == 0) {
            ToastUtil.showToast("获取地址失败, 请重新点击");
            return;
        }
        if (visitTypeID == -1) {
            ToastUtil.showToast("请选择拜访类型");
            return;
        } /*else if (visitTypeID == 1) {//拜访
            if (currentUnitID == 0 || TextUtils.isEmpty(currentCustomerID)) {
                ToastUtil.showToast("填写的信息不完整, 请按星标填写");
                return;
            }
        }*/ else if (visitTypeID == 0) {//跟进
            if (currentUnitID <= 0 || TextUtils.isEmpty(currentCustomerID) || projectID <= 0) {
                ToastUtil.showToast("填写的信息不完整, 请按星标填写");
                return;
            }
        } else if (visitTypeID == 2) {//招待
            //可以都不用填写
        }
        showDialog();
        mPresenter.submissionData("visit_start", String.valueOf(currentVisitRecordID),
                String.valueOf(currentUnitID), String.valueOf(visitTypeID), String.valueOf(projectID),
                String.valueOf(currentCustomerID), tvVisitDepartment.getText().toString().trim(),
                etVisitSummary.getText().toString().trim(), String.valueOf(locationLongitude),
                String.valueOf(locationLatitude), getCurrentPhoneNumber(), addressName,
                null, null, null, null);

        //清空全局变量, 以便于结束按钮获取地址信息
        locationLongitude = 0;
        locationLatitude = 0;
    }

    private void toEndVisit(String addressName) {
        if (locationLatitude == 0 || locationLongitude == 0) {
            ToastUtil.showToast("获取地址失败, 请重新点击");
            return;
        }
        if (visitTypeID == 1) {//拜访
            if (currentUnitID == 0 || TextUtils.isEmpty(currentCustomerID)) {
                ToastUtil.showToast("填写的信息不完整, 请按星标填写");
                return;
            }
        }
        showDialog();
        mPresenter.submissionData("visit_end", String.valueOf(currentVisitRecordID),
                String.valueOf(currentUnitID), String.valueOf(visitTypeID), String.valueOf(projectID),
                String.valueOf(currentCustomerID), tvVisitDepartment.getText().toString().trim(),
                etVisitSummary.getText().toString().trim(), null, null, null, null,
                String.valueOf(locationLongitude), String.valueOf(locationLatitude),
                getCurrentPhoneNumber(), addressName);
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
            case "2":
                tvVisitType.setText("招待");
                visitTypeID = 2;
                break;
            case "3":   //方案
                tvVisitType.setText("方案");
                visitTypeID = 3;
                break;
            case "4":   //合同
                tvVisitType.setText("合同");
                visitTypeID = 4;
                break;
            case "5":   //标书
                tvVisitType.setText("标书");
                visitTypeID = 5;
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
        currentUnitID = bean.companyId;
        currentCustomerID = bean.contactsId;
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
            setupLinearLayout();
        }
        if (!bean.isSubmission) {//如果已提交, 则所有按钮都不能点
            btnCommit.setEnabled(false);
            btnStart.setEnabled(false);
            btnEnd.setEnabled(false);
            tvSave.setEnabled(false);
            tvSave.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(bean.startAddress)) {    //为开始地址栏和结束地址栏赋值
            llStartAddress.setVisibility(View.VISIBLE);
            tvStartAddress.setText(bean.startAddress);

            if (!TextUtils.isEmpty(bean.endAddress)) {
                llEndAddress.setVisibility(View.VISIBLE);
                tvEndAddress.setText(bean.endAddress);
            }
        }
    }

    /**
     * 点击开始或者结束按钮成功后 在页面里显示当前地址信息
     */
    public void setAddressState() {
        if (isStart) {
            tvStartAddress.setText(locationStartAddress);
            llStartAddress.setVisibility(View.VISIBLE);
        } else {
            tvEndAddress.setText(locationEndAddress);
            llEndAddress.setVisibility(View.VISIBLE);
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
//        if (tvVisitType.getText().toString().trim().equals("拜访")) {
//            llVisitUnit.setEnabled(true);
//            llVisitCustomer.setEnabled(true);
//            llProject.setEnabled(true);
//        } else {
//            llVisitUnit.setEnabled(false);
//            llVisitCustomer.setEnabled(false);
//            llProject.setEnabled(false);
//        }
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

        //点击开始成功后, 显示拜访单位和拜访人为必填项, 以便于在结束的时候做判断
        tvRedDot1.setVisibility(View.VISIBLE);
        tvRedDot2.setVisibility(View.VISIBLE);
        tvRedDot3.setVisibility(View.INVISIBLE);
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
        if (visitType.equals("拜访") && isStart) {
            if (TextUtils.isEmpty(visitUnit) || visitUnit.equals("请选择")) {
                ToastUtil.showToast("请选择拜访单位");
                return false;
            } else if (TextUtils.isEmpty(visitCustomer) || visitCustomer.equals("请选择")) {
                ToastUtil.showToast("请选择拜访人");
                return false;
            }
        }
        if (visitType.equals("跟进")) {
            if (TextUtils.isEmpty(visitUnit) || visitUnit.equals("请选择")) {
                ToastUtil.showToast("请选择拜访单位");
                return false;
            } else if (TextUtils.isEmpty(visitCustomer) || visitCustomer.equals("请选择")) {
                ToastUtil.showToast("请选择拜访人");
                return false;
            }else if (TextUtils.isEmpty(visitProject) || visitUnit.equals("请选择")) {
                ToastUtil.showToast("请选择跟进项目");
                return false;
            }
        }
        if (visitType.equals("方案") || visitType.equals("合同") || visitType.equals("标书")) {
            if (TextUtils.isEmpty(visitUnit) || visitUnit.equals("请选择")) {
                ToastUtil.showToast("请选择拜访单位");
                return false;
            } else if (TextUtils.isEmpty(visitCustomer) || visitCustomer.equals("请选择")) {
                ToastUtil.showToast("请选择拜访人");
                return false;
            } else if (TextUtils.isEmpty(visitProject) || visitProject.equals("请选择")) {
                ToastUtil.showToast("请选择跟进项目");
                return false;
            }
        }
        return true;
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
        } else if (requestCode == CommonFinal.SELECT_VISIT_UNIT_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE) {
            String name = data.getStringExtra("name");
            currentUnitID = data.getIntExtra("ID", 0);
            tvVisitUnit.setText(name);
            ivVisitUnit.setVisibility(View.GONE);

            tvVisitCustomer.setText("请选择");
            ivVisitCustomer.setVisibility(View.VISIBLE);
            tvVisitDepartment.setText("");
            tvProject.setText("请选择");
            ivProject.setVisibility(View.VISIBLE);
        } else if (requestCode == CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_PERSON_RESULT_CODE) {
            String name = data.getStringExtra("name");
            String department = data.getStringExtra("department");
            currentCustomerID = data.getStringExtra("ID");
            tvVisitCustomer.setText(name);
            ivVisitCustomer.setVisibility(View.GONE);
            tvVisitDepartment.setText(department);

            tvProject.setText("请选择");
            ivProject.setVisibility(View.VISIBLE);
        } else if (requestCode == CommonFinal.SELECT_VISIT_PROJECT_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_PROJECT_RESULT_CODE) {
            String name = data.getStringExtra("name");
            projectID = data.getIntExtra("ID", 0);

            tvProject.setText(name);
            ivProject.setVisibility(View.GONE);
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
