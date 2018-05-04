package com.hengkai.officeautomationsystem.function.go_out;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
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
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.utils.ImageUtil;
import com.hengkai.officeautomationsystem.utils.MaterialDateTimePickerUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.unistrong.yang.zb_permission.ZbPermission;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/3.
 * 外出页面
 */
public class GoOutActivity extends BaseActivity {

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
    @BindView(R.id.tv_examine_and_approve1)
    TextView tvExamineAndApprove1;
    @BindView(R.id.tv_examine_and_approve2)
    TextView tvExamineAndApprove2;
    @BindView(R.id.ll_examine_and_approve)
    LinearLayout llExamineAndApprove;
    @BindView(R.id.tv_sender1)
    TextView tvSender1;
    @BindView(R.id.tv_sender2)
    TextView tvSender2;
    @BindView(R.id.ll_sender)
    LinearLayout llSender;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    private int startYear, startMonth, startDay, startHour, startMinute;
    private int endYear, endMonth, endDay, endHour, endMinute;
    private String startTimeStr, endTimeStr;
    private GoOutActivityImageListAdapter adapter;

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
                addImage();
            }
        });
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.iv_back, R.id.ll_start_time, R.id.ll_end_time, R.id.btn_commit, R.id.iv_start_time, R.id.iv_end_time})
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
                }
                break;
            case R.id.btn_commit:
                break;
        }
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
                startTimeStr = year + "-" + monthOfYear + "-" + dayOfMonth + " " + hourOfDay + ":" + minute;

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
                endTimeStr = year + "-" + monthOfYear + "-" + dayOfMonth + " " + hourOfDay + ":" + minute;

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

                setTextViewAttribute(endTimeStr, R.drawable.ic_cancel28, R.color.black, tvEndTime, ivEndTime);
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
    private void addImage() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量 int
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
                adapter.addItem(medias.get(0), 0);
            } else {
                adapter.addItems(medias);
            }
        }
    }

    private void requestPermission() {
        ZbPermission.with(this)
                .addRequestCode(1001)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .request(new ZbPermission.ZbPermissionCallback() {
                    @Override
                    public void permissionSuccess(int requestCode) {
                        ToastUtil.showToast("权限申请成功");
                    }

                    @Override
                    public void permissionFail(int requestCode) {
                        ToastUtil.showToast("权限申请失败, 有可能造成拍照或者读取图片失败");
                    }
                });
    }
}
