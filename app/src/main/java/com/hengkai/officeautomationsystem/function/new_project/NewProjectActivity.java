package com.hengkai.officeautomationsystem.function.new_project;

import android.content.Intent;
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
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_person.SelectVisitPersonActivity;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_unit.SelectVisitUnitActivity;
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
    @BindView(R.id.tv_marketing_person)
    TextView tvMarketingPerson;
    @BindView(R.id.rl_marketing_person)
    RelativeLayout rlMarketingPerson;
    @BindView(R.id.tv_technology_person)
    TextView tvTechnologyPerson;
    @BindView(R.id.rl_technology_person)
    RelativeLayout rlTechnologyPerson;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.rl_unit)
    RelativeLayout rlUnit;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.rl_person)
    RelativeLayout rlPerson;
    @BindView(R.id.et_description)
    EditText etDescription;

    /**
     * 类型ID
     */
    private int typeID = -1;
    /**
     * 甲方负责人ID
     */
    private int personID = -1;
    /**
     * 所属单位ID
     */
    private int unitID = -1;
    /**
     * 营销负责人ID
     */
    private int marketingID = -1;
    /**
     * 技术负责人ID
     */
    private int technologyID = -1;

    /**
     * 用于区分选择的是营销还是技术负责人 1营销 2技术
     */
    private int personType = 0;

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

    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.rl_type, R.id.rl_marketing_person, R.id.rl_technology_person, R.id.rl_unit, R.id.rl_person})
    public void onViewClicked(View view) {
        Intent intent;
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
            case R.id.rl_marketing_person://选择营销负责人
                mPresenter.getPersonList();
                personType = 1;
                break;
            case R.id.rl_technology_person://选择技术负责人
                mPresenter.getPersonList();
                personType = 2;
                break;
            case R.id.rl_unit://选择单位
                intent = new Intent(this, SelectVisitUnitActivity.class);
                startActivityForResult(intent, CommonFinal.SELECT_VISIT_UNIT_REQUEST_CODE);
                break;
            case R.id.rl_person://选择甲方负责人
                if (unitID <= 0) {
                    ToastUtil.showToast("请先选择所属单位");
                    return;
                }
                intent = new Intent(this, SelectVisitPersonActivity.class);
                intent.putExtra("unitID", unitID);
                startActivityForResult(intent, CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE);
                break;
        }
    }

    private void commit() {
        String name = etName.getText().toString().trim();
        String type = tvType.getText().toString().trim();
        String marketingPerson = tvMarketingPerson.getText().toString().trim();
        String technologyPerson = tvTechnologyPerson.getText().toString().trim();
        String unit = tvUnit.getText().toString().trim();
        String person = tvPerson.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("请输入项目名称");
            return;
        } else if (TextUtils.isEmpty(type) || typeID == -1) {
            ToastUtil.showToast("请选择项目类型");
            return;
        } else if (TextUtils.isEmpty(unit) || unitID == -1) {
            ToastUtil.showToast("请选择所属单位");
            return;
        } else if (TextUtils.isEmpty(person) || personID == -1) {
            ToastUtil.showToast("请选择甲方负责人");
            return;
        } else if (TextUtils.isEmpty(marketingPerson) || marketingID == -1) {
            ToastUtil.showToast("请选择营销负责人");
            return;
        } else if (TextUtils.isEmpty(technologyPerson) || technologyID == -1) {
            ToastUtil.showToast("请选择技术负责人");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("PROJECTNAME", name);
        params.put("PROJECTTYPE", String.valueOf(typeID));
        params.put("PROJECTDWLX", String.valueOf(personID));
        params.put("PROMCOPALUSER", String.valueOf(marketingID));
        params.put("PROJECTJS", String.valueOf(technologyID));
        params.put("PROJECTDW", String.valueOf(unitID));
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
        PersonBottomDialogAdapter adapter;
        final BottomSheetDialog dialog;
        switch (personType) {
            case 1://营销
                adapter = new PersonBottomDialogAdapter(list, tvMarketingPerson.getText().toString().trim());
                dialog = getBottomSheetDialog(adapter);
                adapter.setOnItemClickListener(new PersonBottomDialogAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(NewProjectGetPersonEntity.DATEBean bean) {
                        tvMarketingPerson.setText(bean.userName);
                        marketingID = bean.userId;
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case 2://技术
                adapter = new PersonBottomDialogAdapter(list, tvTechnologyPerson.getText().toString().trim());
                dialog = getBottomSheetDialog(adapter);
                adapter.setOnItemClickListener(new PersonBottomDialogAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(NewProjectGetPersonEntity.DATEBean bean) {
                        tvTechnologyPerson.setText(bean.userName);
                        technologyID = bean.userId;
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonFinal.SELECT_VISIT_UNIT_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE) {
            String name = data.getStringExtra("name");
            unitID = data.getIntExtra("ID", 0);
            tvUnit.setText(name);

        } else if (requestCode == CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_PERSON_RESULT_CODE) {
            String name = data.getStringExtra("name");
            personID = Integer.valueOf(data.getStringExtra("ID"));
            tvPerson.setText(name);
        }
    }
}
