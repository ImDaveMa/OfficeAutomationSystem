package com.hengkai.officeautomationsystem.function.approve;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApproveListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tab_approve)
    TabLayout tabApprove;
    @BindView(R.id.vp_approve)
    ViewPager vpApprove;

    private List<Fragment> fragmentList;

    @Override
    protected int setupView() {
        return R.layout.activity_approve;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("审批列表");

        initFragment();
        initConfig();
    }

    /**
     * 关闭页面时调用，用来取消指定的网络请求
     *
     * @return
     */
    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected ApproveListPresenter bindPresenter() {
        return new ApproveListPresenter();
    }

    /**
     * @param list
     */
    public void prepareData(List<MessageEntity.MsgBean> list) {
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();

        //待审核状态
        ApproveFragment approveFragment = new ApproveFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ApproveFragment.BUNDLE_KEY_STATE, 0);
        approveFragment.setArguments(bundle);
        fragmentList.add(approveFragment);

        //全部
        fragmentList.add(new ApproveFragment());
    }

    /**
     * 配置列表相关
     */
    private void initConfig() {
        tabApprove.setTabMode(TabLayout.MODE_FIXED);
        tabApprove.setSelectedTabIndicatorHeight(5);    // 下方滚动的下划线高度
        tabApprove.setBackgroundResource(R.color.tab_bg);
        tabApprove.setTabTextColors(getResources().getColorStateList(R.color.selector_tab_text_color));
        tabApprove.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_text_color_selected));  // 下方滚动的下划线颜色
        tabApprove.setupWithViewPager(vpApprove);

        TrainingRecordPagerAdapter adapter = new TrainingRecordPagerAdapter(getSupportFragmentManager());
        vpApprove.setAdapter(adapter);
    }

    private class TrainingRecordPagerAdapter extends FragmentPagerAdapter {

        private String[] tabNames;

        public TrainingRecordPagerAdapter(FragmentManager fm) {
            super(fm);
            tabNames = getResources().getStringArray(R.array.approve_state);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }
    }
}
