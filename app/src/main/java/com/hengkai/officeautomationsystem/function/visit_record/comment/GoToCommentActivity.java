package com.hengkai.officeautomationsystem.function.visit_record.comment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/17.
 * 评论页面, 评论当前拜访跟进, 返回之前activity, 并更新数据
 */
public class GoToCommentActivity extends BaseActivity<GoToCommentPresenter> {

    @BindView(R.id.et_comment)
    EditText etComment;
    private int currentID;

    @Override
    protected int setupView() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        currentID = getIntent().getIntExtra("currentID", 0);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.GO_TO_COMMENT_ACTIVITY);
        return tags;
    }

    @Override
    protected GoToCommentPresenter bindPresenter() {
        return new GoToCommentPresenter();
    }

    @OnClick({R.id.iv_back, R.id.tv_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_comment:
                checkEditText();
                break;
        }
    }

    /**
     * 验证EditText
     */
    private void checkEditText() {
        String comment = etComment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            ToastUtil.showToast("请输入评论内容");
            return;
        }
        mPresenter.comment(currentID, comment);

    }
}
