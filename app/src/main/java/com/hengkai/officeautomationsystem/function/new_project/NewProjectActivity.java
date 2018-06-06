package com.hengkai.officeautomationsystem.function.new_project;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.NewProjectGetPersonEntity;
import com.hengkai.officeautomationsystem.network.entity.NewProjectGetTypeEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/6/5.
 * 新增项目的界面
 */
public class NewProjectActivity extends BaseActivity<NewProjectPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.rl_type)
    RelativeLayout rlType;
    @BindView(R.id.tv_person_in_charge)
    TextView tvPersonInCharge;
    @BindView(R.id.rl_person_in_charge)
    RelativeLayout rlPersonInCharge;
    @BindView(R.id.et_description)
    EditText etDescription;

    private int typeID = -1;
    private int personID = -1;

    @Override
    protected int setupView() {
        return R.layout.activity_new_project;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("新增项目");

    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.NEW_PROJECT_ACTIVITY_ADD_PROJECT);
        tags.add(NetworkTagFinal.NEW_PROJECT_ACTIVITY_GET_PERSON_LIST);
        tags.add(NetworkTagFinal.NEW_PROJECT_ACTIVITY_GET_TYPE_LIST);
        return tags;
    }

    @Override
    protected NewProjectPresenter bindPresenter() {
        return new NewProjectPresenter();
    }

    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.rl_type, R.id.rl_person_in_charge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                commit();
                break;
            case R.id.rl_type:
                showDialog();
                mPresenter.getTypeList();
                break;
            case R.id.rl_person_in_charge:
                mPresenter.getPersonList();
                break;
        }
    }

    private void commit() {
        String name = etName.getText().toString().trim();
        String type = tvType.getText().toString().trim();
        String person = tvPersonInCharge.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("请输入项目名称");
            return;
        } else if (TextUtils.isEmpty(type) && typeID != -1) {
            ToastUtil.showToast("请选择项目类型");
            return;
        } else if (TextUtils.isEmpty(person) && personID != -1) {
            ToastUtil.showToast("请选择项目负责人");
            return;
        } else if (TextUtils.isEmpty(description)) {
            ToastUtil.showToast("请输入项目简述");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("PROJECTNAME", name);
        params.put("PROJECTTYPE", String.valueOf(typeID));
        params.put("PROMCOPALUSER", String.valueOf(personID));
        params.put("PROJECTSUMMARIZE", description);

        mPresenter.commit(params);
    }

    public void getTypeList(List<NewProjectGetTypeEntity.DATEBean> list) {
        String type = tvType.getText().toString().trim();
        TypeBottomDialogAdapter adapter = new TypeBottomDialogAdapter(list, type);
        final BottomSheetDialog dialog = getBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new TypeBottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewProjectGetTypeEntity.DATEBean bean) {
                tvType.setText(bean.value);
                typeID = bean.id;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getPersonList(List<NewProjectGetPersonEntity.DATEBean> list) {
        String person = tvPersonInCharge.getText().toString().trim();
        PersonBottomDialogAdapter adapter = new PersonBottomDialogAdapter(list, person);
        final BottomSheetDialog dialog = getBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new PersonBottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewProjectGetPersonEntity.DATEBean bean) {
                tvPersonInCharge.setText(bean.userName);
                personID = bean.userId;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 获取底部弹窗并且弹出
     */
    private BottomSheetDialog getBottomSheetDialog(RecyclerView.Adapter adapter) {
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
}
