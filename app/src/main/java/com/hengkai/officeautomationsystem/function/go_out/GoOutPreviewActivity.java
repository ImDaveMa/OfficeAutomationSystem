package com.hengkai.officeautomationsystem.function.go_out;

import android.graphics.BitmapFactory;

import com.github.chrisbanes.photoview.PhotoView;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;

import java.util.ArrayList;

/**
 * Created by Harry on 2018/5/4.
 */
public class GoOutPreviewActivity extends BaseActivity {
    @Override
    protected int setupView() {
        return R.layout.activity_go_out_preview;
    }

    @Override
    protected void initView() {
        String path = getIntent().getStringExtra("previewImage");
        PhotoView photoView = findViewById(R.id.photo_view);
        photoView.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
}
