package com.hengkai.officeautomationsystem.function.go_out;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.go_out.list.GoOutListActivity;
import com.hengkai.officeautomationsystem.network.entity.GoOutEntity;
import com.hengkai.officeautomationsystem.utils.MaterialDateTimePickerUtils;
import com.hengkai.officeautomationsystem.utils.PicassoCircleTransform;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.jaeger.library.StatusBarUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
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
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Harry on 2018/5/3.
 * 外出页面
 */
public class GoOutActivity extends BaseActivity<GoOutPresenter> implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.iv_start_time)
    ImageView ivStartTime;
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.iv_end_time)
    ImageView ivEndTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.et_go_out_reason)
    EditText etGoOutReason;
    @BindView(R.id.rv_image_list)
    RecyclerView rvImageList;
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

    private int startYear, startMonth, startDay, startHour, startMinute;
    private int endYear, endMonth, endDay, endHour, endMinute;
    private String startTimeStr, endTimeStr;
    private GoOutActivityImageListAdapter adapter;
    /**
     * 加载图片的数量
     */
    private int imageCount;

    @Override
    protected int setupView() {
        return R.layout.activity_go_out;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);
        tvTitle.setText("外出");

        setupRecyclerView();

        mPresenter.getCopyPerson();
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvImageList.setLayoutManager(gridLayoutManager);
        adapter = new GoOutActivityImageListAdapter(this);
        rvImageList.setAdapter(adapter);
        adapter.setOnImageViewClickListener(new GoOutActivityImageListAdapter.OnImageViewClickListener() {
            @Override
            public void click() {
                imageCount = adapter.getItemCount();
                if (imageCount > 9) {
                    ToastUtil.showToast("最多可以选择9张图片");
                    return;
                }
                easyPermission();
//                addImage(10 - imgCount);
            }
        });
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.ASK_FOR_LEAVE_ACTIVITY_ADD);
        tags.add(NetworkTagFinal.ASK_FOR_LEAVE_ACTIVITY_DURATION);
        tags.add(NetworkTagFinal.ASK_FOR_LEAVE_ACTIVITY_GET_COPY_PERSON);
        return tags;
    }

    @Override
    protected GoOutPresenter bindPresenter() {
        return new GoOutPresenter();
    }

    @OnClick({R.id.iv_back, R.id.ll_start_time, R.id.ll_end_time, R.id.btn_commit, R.id.iv_start_time, R.id.iv_end_time, R.id.iv_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_start_time:
                MaterialDateTimePickerUtils.showDatePickerDialog(getFragmentManager(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        showStartTimePickerDialog(year, monthOfYear, dayOfMonth);
                    }
                });
                break;
            case R.id.iv_start_time:
                if (!tvStartTime.getText().toString().trim().equals("请选择")) {
                    setTextViewAttribute("请选择", R.drawable.ic_arrow_gray32, R.color.black3, tvStartTime, ivStartTime);
                    startTimeStr = "";
                }
                break;
            case R.id.ll_end_time:
                MaterialDateTimePickerUtils.showDatePickerDialog(getFragmentManager(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        showEndTimePickerDialog(year, monthOfYear, dayOfMonth);
                    }
                });
                break;
            case R.id.iv_end_time:
                if (!tvEndTime.getText().toString().trim().equals("请选择")) {
                    setTextViewAttribute("请选择", R.drawable.ic_arrow_gray32, R.color.black3, tvEndTime, ivEndTime);
                    endTimeStr = "";
                }
                break;
            case R.id.btn_commit:
                commit();
                break;
            case R.id.iv_list:
                startActivity(new Intent(this, GoOutListActivity.class));
                break;
        }
    }

    /**
     * 提交
     */
    private void commit() {
        String startTime = tvStartTime.getText().toString().trim();
        String endTime = tvEndTime.getText().toString().trim();
        String time = tvTime.getText().toString().trim();
        String leaveReason = etGoOutReason.getText().toString().trim();

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

        params.put("TIME", time);
        params.put("REASON", leaveReason);
        params.put("STARTTIME", startTime);
        params.put("ENDTIME", endTime);

        mPresenter.add(params);
    }

    /**
     * @param time 设置时长
     */
    public void setDuration(double time) {
        tvTime.setText(String.valueOf(time));
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
     * 添加图片
     */
    private void addImage(int count) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(count)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
//                .previewVideo(true)// 是否可预览视频 true or false
//                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
//                .isGif()// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
//                .previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
//                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {//修改头像 结果回调
            if (data == null) {
                return; //什么都不选择, 直接点击返回或者取消按钮的时候return掉
            }
//            showDialog();
            List<LocalMedia> medias = PictureSelector.obtainMultipleResult(data);
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//            String compressPath = medias.get(0).getCompressPath();
            if (medias.size() == 1) {
                adapter.addItem(medias.get(0));
            } else {
                adapter.addItems(medias);
            }
        } else if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //拒绝授权后，从系统设置了授权后，返回APP进行相应的操作
            addImage(10 - imageCount);
        }
    }

    public void easyPermission() {
        String[] permissionList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT >= 23) {
            if (EasyPermissions.hasPermissions(this, permissionList)) {
                addImage(10 - imageCount);
            } else {
                EasyPermissions.requestPermissions(this, "需要访问手机内存以及照相机相关权限", 1001, permissionList);
            }
        } else {
            addImage(10 - imageCount);
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
        addImage(10 - imageCount);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //拒绝了授权
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
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
