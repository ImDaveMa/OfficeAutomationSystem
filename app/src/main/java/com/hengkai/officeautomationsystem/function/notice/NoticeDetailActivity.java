package com.hengkai.officeautomationsystem.function.notice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommentVisitEntity;
import com.hengkai.officeautomationsystem.network.entity.NoticeDetailEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.PicassoCircleTransform;
import com.hengkai.officeautomationsystem.utils.SPUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeDetailActivity extends BaseActivity<NoticeDetailPresenter> {
    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sv_main)
    ScrollView svMain;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_notice_title)
    TextView tvNoticeTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_read_count)
    TextView tvReadCount;
    @BindView(R.id.tv_unread_count)
    TextView tvUnreadCount;
    @BindView(R.id.rv_read_list)
    RecyclerView rvReadList;
    @BindView(R.id.rv_unread_list)
    RecyclerView rvUnreadList;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.ll_comment_list)
    LinearLayout llCommentList;
    @BindView(R.id.tv_no_comment)
    TextView tvNoComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.tv_save)
    TextView tvSave;

    private int id = 0;

    @Override
    protected int setupView() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("通知公告详情");

        // 获取ID
        id = getIntent().getIntExtra(EXTRA_KEY_ID, 0);
        mPresenter.getNoticeDetail(id);
        mPresenter.getNoticeComments(id);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.NOTICE_DETAIL_ACTIVITY_GET_NOTICE_LIST);
        tags.add(NetworkTagFinal.NOTICE_DETAIL_ACTIVITY_GET_COMMENT_LIST);
        tags.add(NetworkTagFinal.NOTICE_DETAIL_ACTIVITY_COMMENT_LIST);
        return null;
    }

    @Override
    protected NoticeDetailPresenter bindPresenter() {
        return new NoticeDetailPresenter();
    }

    @OnClick({R.id.iv_back,R.id.tv_unread_count, R.id.tv_read_count, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_read_count:
                tvUnreadCount.setTextColor(getResources().getColor(R.color.app_theme_color));
                tvReadCount.setTextColor(getResources().getColor(R.color.black));

                rvReadList.setVisibility(View.VISIBLE);
                rvUnreadList.setVisibility(View.GONE);
                break;
            case R.id.tv_unread_count:
                tvUnreadCount.setTextColor(getResources().getColor(R.color.black));
                tvReadCount.setTextColor(getResources().getColor(R.color.app_theme_color));

                rvReadList.setVisibility(View.GONE);
                rvUnreadList.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_save:
                commentNotice();
                break;
        }
    }

    /**
     * 保存回复
     */
    private void commentNotice(){
        if(TextUtils.isEmpty(etComment.getText())){
            etComment.requestFocus();
            return;
        }
        mPresenter.comment(id, etComment.getText().toString().trim());
    }

    /**
     * 获取详情数据
     */
    protected void prepareData(NoticeDetailEntity.DATEBean bean) {
        if (bean == null) {
            ToastUtil.showToast("没有获取到内容");
            finish();
            return;
        }
        // 初始化通知公告
        if (bean.notice != null) {
            if(!TextUtils.isEmpty(bean.notice.createUserAvatar)) {
                Picasso.with(this).load(bean.notice.createUserAvatar).error(R.drawable.ic_user_blue)
                        .transform(new PicassoCircleTransform())
                        .resize(WindowUtil.dp2px(50, this), WindowUtil.dp2px(50, this))
                        .centerCrop().into(ivHeader);
            }
            tvName.setText(bean.notice.createUserName);
            tvTime.setText(DateFormatUtils.getFormatedNewsTime(bean.notice.createTime));
            tvNoticeTitle.setText(bean.notice.noticeTitle);
            if(!TextUtils.isEmpty(bean.notice.noticeValue)) {
                tvContent.setText(Html.fromHtml(bean.notice.noticeValue));
            }
        }
        //初始化已读未读
        if (bean.alrList != null) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 5) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            tvReadCount.setText(String.format("已读 %d", bean.alrList.size()));
            rvReadList.setLayoutManager(layoutManager);
            rvReadList.setAdapter(new ReadUserItemAdapter(bean.alrList));
        }
        if (bean.notList != null) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 5) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            tvUnreadCount.setText(String.format("未读 %d", bean.notList.size()));
            rvUnreadList.setLayoutManager(layoutManager);
            rvUnreadList.setAdapter(new UnreadUserItemAdapter(bean.notList));
        }
    }

    /**
     * 获取评论列表结果
     */
    protected void prepareCommentList(List<CommentVisitEntity.DATEBean> beans) {
        if (beans == null || beans.size() <= 0) {
            tvNoComment.setVisibility(View.VISIBLE);
            tvCommentCount.setText("评论");
        } else {
            tvNoComment.setVisibility(View.GONE);
            tvCommentCount.setText(String.format("评论 %d", beans.size()));

            for (CommentVisitEntity.DATEBean bean : beans) {
                // 处理列表数据
                View view = LayoutInflater.from(this).inflate(R.layout.item_comment_visit, null);
                // TODO: 2018/5/31 没有头像
                ImageView ivHeader = view.findViewById(R.id.iv_header);
//                Picasso.with(this).load(bean.).error(R.drawable.ic_user64)
//                        .transform(new PicassoCircleTransform())
//                        .resize(WindowUtil.dp2px(50, this), WindowUtil.dp2px(50, this))
//                        .centerCrop().into(ivHeader);

                TextView tvName = view.findViewById(R.id.tv_name);
                tvName.setText(bean.createUserName);
                TextView tvContent = view.findViewById(R.id.tv_content);
                tvContent.setText(bean.comment_content);
                TextView tvTime = view.findViewById(R.id.tv_time);
                tvTime.setText(DateFormatUtils.getFormatedNewsTime(bean.comment_time));

                llCommentList.addView(view);
            }
        }
    }

    /**
     * 添加回复成功回调
     */
    protected void addCommentSuccess(){
        // 如果没有回复内容，则隐藏，并且给回复数置为1
        if(tvNoComment.isShown()){
            tvNoComment.setVisibility(View.GONE);
            tvCommentCount.setText("评论 1");
        }


        // 添加一项
        View view = LayoutInflater.from(this).inflate(R.layout.item_comment_visit, null);
        // TODO: 2018/5/31 没有头像
        ImageView ivHeader = view.findViewById(R.id.iv_header);
        String headerImage = SPUtils.getString(UserInfo.ICON_LINK.name(),"");
        Picasso.with(this).load(headerImage).error(R.drawable.ic_user_blue)
            .transform(new PicassoCircleTransform())
            .resize(WindowUtil.dp2px(50, this), WindowUtil.dp2px(50, this))
            .centerCrop().into(ivHeader);

        TextView tvName = view.findViewById(R.id.tv_name);
        tvName.setText(SPUtils.getString(UserInfo.REAL_NAME.name(),""));
        TextView tvContent = view.findViewById(R.id.tv_content);
        tvContent.setText(etComment.getText().toString().trim());
        TextView tvTime = view.findViewById(R.id.tv_time);
        tvTime.setText(DateFormatUtils.getFormatedNewsTime(new Date().getTime()));

        llCommentList.addView(view);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                svMain.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        // 输入框置为空
        etComment.setText("");
    }
}
