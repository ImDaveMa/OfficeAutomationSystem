package com.hengkai.officeautomationsystem.function.goods_supplier;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.goods_unit.GoodsUnitActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.EditGoodsTypeBottomDialogAdapter;
import com.hengkai.officeautomationsystem.network.entity.AddGoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.AddGoodsSupplierEntity;
import com.hengkai.officeautomationsystem.network.entity.EditGoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.EditGoodsSupplierEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物品领用Activity
 */
public class AddGoodsSupplierActivity extends BaseActivity<AddGoodsSupplierPresenter> {
    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";

    private static final int REQUEST_CODE_UNIT = 1001;
    private static final int REQUEST_CODE_SUPPLIER = 1002;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_goods_supplier_name)
    EditText etGoodsSupplierName;
    @BindView(R.id.tv_goods_supplier_type)
    TextView tvGoodsSupplierType;
    @BindView(R.id.lr_goods_supplier_type_selector)
    RelativeLayout lrGoodsSupplierTypeSelector;
    @BindView(R.id.et_goods_supplier_city)
    EditText etGoodsSupplierCity;
    @BindView(R.id.et_goods_supplier_address)
    EditText etGoodsSupplierAddress;
    @BindView(R.id.et_goods_supplier_postal_code)
    EditText etGoodsSupplierPostalCode;
    @BindView(R.id.et_goods_supplier_contacts)
    EditText etGoodsSupplierContacts;
    @BindView(R.id.et_goods_supplier_phone)
    EditText etGoodsSupplierPhone;
    @BindView(R.id.et_goods_supplier_fax)
    EditText etGoodsSupplierFax;
    @BindView(R.id.et_goods_supplier_mailbox)
    EditText etGoodsSupplierMailbox;
    @BindView(R.id.et_goods_supplier_qq)
    EditText etGoodsSupplierQq;
    @BindView(R.id.et_goods_supplier_wechat)
    EditText etGoodsSupplierWechat;
    @BindView(R.id.et_goods_supplier_source)
    EditText etGoodsSupplierSource;
    @BindView(R.id.et_goods_supplier_description)
    EditText etGoodsSupplierDescription;
    @BindView(R.id.et_goods_supplier_remark)
    EditText etGoodsSupplierRemark;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private List<AddGoodsSupplierEntity.ParamBean> addParams;
    private List<EditGoodsSupplierEntity.SupplierBean.ParamlistBean> editParams;

    @Override
    protected int setupView() {
        return R.layout.activity_add_goods_supplier;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);

        // 获取页面传参
        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_KEY_ID, 0);

        if (id <= 0) {
            tvTitle.setText("新增供应商");
            // 请求页面参数
            mPresenter.getAddSupplierParams();
        } else {
            tvTitle.setText("修改供应商");
            mPresenter.getUpdateSupplierParams(id);
        }

        etGoodsSupplierName.requestFocus();
    }

    @Override
    protected AddGoodsSupplierPresenter bindPresenter() {
        return new AddGoodsSupplierPresenter();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.ADD_GOODS_SUPPLIER_ACTIVITY_GET_PARAMS);
        tags.add(NetworkTagFinal.ADD_GOODS_SUPPLIER_ACTIVITY_DO_ADD);
        tags.add(NetworkTagFinal.UPDATE_GOODS_SUPPLIER_ACTIVITY_GET_PARAMS);
        tags.add(NetworkTagFinal.UPDATE_GOODS_SUPPLIER_ACTIVITY_DO_UPDATE);
        return tags;
    }

    @OnClick({R.id.iv_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                saveGoodsSupplier();
                break;
        }
    }

    /**
     * 保存方法
     */
    private void saveGoodsSupplier() {
        // 获取页面传参
        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_KEY_ID, 0);

        String name = etGoodsSupplierName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("请输入供应商名称");
            etGoodsSupplierName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tvGoodsSupplierType.getText())) {
            ToastUtil.showToast("请选择供应商类型");
            return;
        }
        int type = Integer.parseInt(tvGoodsSupplierType.getTag() + "");

        if (TextUtils.isEmpty(etGoodsSupplierCity.getText())) {
            ToastUtil.showToast("请输入供应商所在城市");
            etGoodsSupplierCity.requestFocus();
            return;
        }

        if (id <= 0) {
            mPresenter.addGoodsSupplier(name, type, etGoodsSupplierCity.getText().toString(), etGoodsSupplierAddress.getText().toString(),
                    etGoodsSupplierPostalCode.getText().toString(), etGoodsSupplierContacts.getText().toString(), etGoodsSupplierPhone.getText().toString(),
                    etGoodsSupplierFax.getText().toString(), etGoodsSupplierMailbox.getText().toString(), etGoodsSupplierQq.getText().toString(),
                    etGoodsSupplierWechat.getText().toString(), etGoodsSupplierSource.getText().toString(),etGoodsSupplierDescription.getText().toString(),
                    etGoodsSupplierRemark.getText().toString());
        } else {
            mPresenter.updateGoodsSupplier(id, name, type, etGoodsSupplierCity.getText().toString(), etGoodsSupplierAddress.getText().toString(),
                    etGoodsSupplierPostalCode.getText().toString(), etGoodsSupplierContacts.getText().toString(), etGoodsSupplierPhone.getText().toString(),
                    etGoodsSupplierFax.getText().toString(), etGoodsSupplierMailbox.getText().toString(), etGoodsSupplierQq.getText().toString(),
                    etGoodsSupplierWechat.getText().toString(), etGoodsSupplierSource.getText().toString(),etGoodsSupplierDescription.getText().toString(),
                    etGoodsSupplierRemark.getText().toString());
        }
    }

    /**
     * 请求添加参数成功回调方法
     * 给页面赋值
     *
     * @param bean
     */
    protected void getAddParamsSuccess(AddGoodsSupplierEntity bean) {
        if (bean != null) {
            if (bean.param != null) {
                addParams = bean.param;

                lrGoodsSupplierTypeSelector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAddGoodsSupplierType();
                    }
                });
            }
        } else {
            ToastUtil.showToast("参数加载失败");
            finish();
        }
    }

    /**
     * 请求修改参数成功回调方法
     * 给页面赋值
     *
     * @param bean
     */
    protected void getEditParamsSuccess(EditGoodsSupplierEntity bean) {
        if (bean != null) {
            editParams = bean.supplier.paramlist;

            EditGoodsSupplierEntity.SupplierBean supplierBean = bean.supplier;
            if (supplierBean != null) {
                etGoodsSupplierName.setText(supplierBean.name);
                for (int i = 0; i < editParams.size(); i++){
                    if(supplierBean.supperType == editParams.get(i).id){
                        tvGoodsSupplierType.setText(supplierBean.name);
                    }
                }
                tvGoodsSupplierType.setTag(supplierBean.supperType);
                etGoodsSupplierCity.setText(supplierBean.city);
                etGoodsSupplierAddress.setText(supplierBean.address);
                etGoodsSupplierPostalCode.setText(supplierBean.postalCode);
                etGoodsSupplierContacts.setText(supplierBean.contacts);
                etGoodsSupplierPhone.setText(supplierBean.phone);
                etGoodsSupplierFax.setText(supplierBean.fax);
                etGoodsSupplierMailbox.setText(supplierBean.mailbox);
                etGoodsSupplierQq.setText(supplierBean.qq);
                etGoodsSupplierWechat.setText(supplierBean.wechat);
                etGoodsSupplierSource.setText(supplierBean.source);
                etGoodsSupplierDescription.setText(supplierBean.description);
                etGoodsSupplierRemark.setText(supplierBean.remark);
            }
            if (supplierBean.paramlist != null) {
                lrGoodsSupplierTypeSelector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEditGoodsSupplierType();
                    }
                });
            }
        } else {
            ToastUtil.showToast("物品供应商不存在或者已经被删除");
            finish();
        }
    }

    /**
     * 显示添加时的物品供应商类别列表
     */
    private void showAddGoodsSupplierType() {
        //呈现选择Project
        AddGoodsSupplierTypeBottomDialogAdapter adapter = new AddGoodsSupplierTypeBottomDialogAdapter(addParams);
        final BottomSheetDialog dialog = getAddBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new AddGoodsSupplierTypeBottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(AddGoodsSupplierEntity.ParamBean bean) {
                tvGoodsSupplierType.setText(bean.name);
                tvGoodsSupplierType.setTag(bean.id);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * 显示修改时的物品供应商类别列表
     */
    private void showEditGoodsSupplierType() {
        //呈现选择Project
        EditGoodsSupplierTypeBottomDialogAdapter adapter = new EditGoodsSupplierTypeBottomDialogAdapter(editParams);
        final BottomSheetDialog dialog = getEditBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new EditGoodsSupplierTypeBottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(EditGoodsSupplierEntity.SupplierBean.ParamlistBean bean) {
                tvGoodsSupplierType.setText(bean.name);
                tvGoodsSupplierType.setTag(bean.id);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * 获取底部物品供应商类型弹窗并且弹出
     */
    private BottomSheetDialog getAddBottomSheetDialog(AddGoodsSupplierTypeBottomDialogAdapter adapter) {
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

    /**
     * 获取底部物品供应商类型弹窗并且弹出
     */
    private BottomSheetDialog getEditBottomSheetDialog(EditGoodsSupplierTypeBottomDialogAdapter adapter) {
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

    /**
     * 添加成功
     */
    protected void addSuccess() {
        ToastUtil.showToast("物品供应商添加成功");
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 编辑成功
     */
    protected void editSuccess() {
        ToastUtil.showToast("物品供应商编辑成功");
        setResult(RESULT_OK);
        finish();
    }

}
