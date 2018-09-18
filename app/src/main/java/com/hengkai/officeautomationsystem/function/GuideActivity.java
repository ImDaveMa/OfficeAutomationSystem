package com.hengkai.officeautomationsystem.function;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.function.login.LoginActivity;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/7/4.
 * 引导页面
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.vp_splash)
    ViewPager vpSplash;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_jump)
    TextView tvJump;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;

    private int[] mImageViewIds = {R.drawable.ic_guide1, R.drawable.ic_guide2, R.drawable.ic_guide3};
    private List<ImageView> mImageViewList;
    private int mNum;

    @Override
    protected int setupView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        initData();

        vpSplash.setAdapter(new GuideAdapter());

        initListener();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void initData() {
        mImageViewList = new ArrayList<>();
        View view;
        for (int i = 0; i < mImageViewIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(mImageViewIds[i]);
            mImageViewList.add(imageView);

            //创建底部指示器(小圆点)
            view = new View(this);
            view.setBackgroundResource(R.drawable.selector_guide_activity_oval);
            view.setEnabled(false);

            //设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
            //设置间隔
            if (i != 0) {
                layoutParams.leftMargin = 10;
            }
            //添加到LinearLayout
            llContainer.addView(view, layoutParams);
        }
    }

    private void initListener() {

        llContainer.getChildAt(0).setEnabled(true);

        vpSplash.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == (mImageViewList.size() - 1)) {
                    tvStart.setVisibility(View.VISIBLE);
                    tvJump.setVisibility(View.GONE);
                } else {
                    tvStart.setVisibility(View.GONE);
                    tvJump.setVisibility(View.VISIBLE);
                }

                llContainer.getChildAt(mNum).setEnabled(false);
                llContainer.getChildAt(position).setEnabled(true);
                mNum = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterMainPage();
            }
        });

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterMainPage();
            }
        });

    }

    private void EnterMainPage() {
        SPUtils.putBoolean(UserInfo.IS_FIRST_ENTER.name(), false);
        Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = mImageViewList.get(position);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
