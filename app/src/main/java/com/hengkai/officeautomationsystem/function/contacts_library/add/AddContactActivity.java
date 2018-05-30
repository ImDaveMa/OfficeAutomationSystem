package com.hengkai.officeautomationsystem.function.contacts_library.add;

import android.content.Intent;
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
import com.hengkai.officeautomationsystem.function.goods_supplier.AddGoodsSupplierPresenter;
import com.hengkai.officeautomationsystem.function.goods_supplier.AddGoodsSupplierTypeBottomDialogAdapter;
import com.hengkai.officeautomationsystem.function.goods_supplier.EditGoodsSupplierTypeBottomDialogAdapter;
import com.hengkai.officeautomationsystem.network.entity.AddGoodsSupplierEntity;
import com.hengkai.officeautomationsystem.network.entity.EditGoodsSupplierEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物品领用Activity
 */
public class AddContactActivity extends BaseActivity<AddContactPresenter> {

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
    @BindView(R.id.lr_goods_supplier_state_selector)
    RelativeLayout lrGoodsSupplierStateSelector;
    @BindView(R.id.tv_goods_supplier_state)
    TextView tvGoodsSupplierState;
    @BindView(R.id.et_goods_supplier_description)
    EditText etGoodsSupplierDescription;
    @BindView(R.id.et_goods_supplier_remark)
    EditText etGoodsSupplierRemark;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int setupView() {
        return R.layout.activity_add_contact;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);

        etGoodsSupplierName.requestFocus();
    }

    @Override
    protected AddContactPresenter bindPresenter() {
        return new AddContactPresenter();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.ADD_CONTACT_ACTIVITY_ADD_CONTACT);
        return tags;
    }

    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.lr_goods_supplier_state_selector})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.lr_goods_supplier_state_selector:
                final BottomSheetDialog dialog = new BottomSheetDialog(this);
                View contentView = getLayoutInflater().inflate(R.layout.layout_bottom_yes_or_no,null);
                TextView tvYes = contentView.findViewById(R.id.tv_yes);
                tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvGoodsSupplierState.setText("是");
                        tvGoodsSupplierState.setTag(1);
                        dialog.dismiss();
                    }
                });
                TextView tvNo = contentView.findViewById(R.id.tv_no);
                tvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvGoodsSupplierState.setText("否");
                        tvGoodsSupplierState.setTag(0);
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(contentView);
                dialog.show();
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

        int state = Integer.parseInt(tvGoodsSupplierState.getTag() + "");

//        mPresenter.saveContact(name, type, etGoodsSupplierCity.getText().toString(), etGoodsSupplierAddress.getText().toString(),
//                etGoodsSupplierPostalCode.getText().toString(), etGoodsSupplierContacts.getText().toString(), etGoodsSupplierPhone.getText().toString(),
//                etGoodsSupplierFax.getText().toString(), etGoodsSupplierMailbox.getText().toString(), etGoodsSupplierQq.getText().toString(),
//                etGoodsSupplierWechat.getText().toString(), etGoodsSupplierSource.getText().toString(), state,
//                etGoodsSupplierDescription.getText().toString(), etGoodsSupplierRemark.getText().toString());

    }

    /**
     * 添加成功
     */
    protected void addSuccess() {
        ToastUtil.showToast("物品供应商添加成功");
        setResult(RESULT_OK);
        finish();
    }

}
