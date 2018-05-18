package com.hengkai.officeautomationsystem.function.management_of_goods;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.goods_supplier.GoodsSupplierActivity;
import com.hengkai.officeautomationsystem.function.goods_unit.GoodsUnitActivity;
import com.hengkai.officeautomationsystem.network.entity.AddGoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.EditGoodsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物品领用Activity
 */
public class AddGoodsActivity extends BaseActivity<AddGoodsPresenter> {
    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";

    private static final int REQUEST_CODE_UNIT = 1001;
    private static final int REQUEST_CODE_SUPPLIER = 1002;

    private AddGoodsEntity addGoodsEntity;
    private EditGoodsEntity editGoodsEntity;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_goods_name)
    EditText etGoodsName;
    @BindView(R.id.tv_goods_type)
    TextView tvGoodsType;
    @BindView(R.id.lr_goods_type_selector)
    RelativeLayout lrGoodsTypeSelector;
    @BindView(R.id.tv_goods_unit_name)
    TextView tvGoodsUnitName;
    @BindView(R.id.lr_goods_unit_selector)
    RelativeLayout lrGoodsUnitSelector;
    @BindView(R.id.et_goods_cost)
    EditText etGoodsCost;
    @BindView(R.id.tv_goods_supplier_name)
    TextView tvGoodsSupplierName;
    @BindView(R.id.lr_goods_supplier_selector)
    RelativeLayout lrGoodsSupplierSelector;
    @BindView(R.id.et_goods_band)
    EditText etGoodsBand;
    @BindView(R.id.et_goods_spec)
    EditText etGoodsSpec;
    @BindView(R.id.et_goods_remark)
    EditText etGoodsRemark;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_goods_num)
    EditText etGoodsNum;
    @BindView(R.id.tv_goods_total)
    TextView tvGoodsTotal;
    @BindView(R.id.rl_edit_container)
    LinearLayout rlEditContainer;

    @Override
    protected int setupView() {
        return R.layout.activity_add_goods;
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
            tvTitle.setText("新增物品");
            // 请求页面参数
            mPresenter.getAddParams();
        } else {
            tvTitle.setText("修改物品");
            rlEditContainer.setVisibility(View.VISIBLE);
            mPresenter.getEditParams(id);
        }

        etGoodsName.requestFocus();
    }

    @Override
    protected AddGoodsPresenter bindPresenter() {
        return new AddGoodsPresenter();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.GOODS_DETAIL_ACTIVITY_GET_DETAIL);
        return tags;
    }

    @OnClick({R.id.iv_back,R.id.lr_goods_unit_selector,R.id.lr_goods_supplier_selector,R.id.tv_submit})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.lr_goods_unit_selector:
                intent = new Intent(this, GoodsUnitActivity.class);
                intent.putExtra(GoodsUnitActivity.REQUEST_EXTRA_KEY_TITLE, "选择物品单位");
                startActivityForResult(intent,REQUEST_CODE_UNIT);
                break;
            case R.id.lr_goods_supplier_selector:
                intent = new Intent(this, GoodsSupplierActivity.class);
                intent.putExtra(GoodsUnitActivity.REQUEST_EXTRA_KEY_TITLE, "选择供货商");
                startActivityForResult(intent,REQUEST_CODE_SUPPLIER);
                break;
            case R.id.tv_submit:
                saveGoods();
                break;
        }
    }

    /**
     * 保存方法
     */
    private void saveGoods(){
        // 获取页面传参
        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_KEY_ID, 0);

        if (id <= 0) {
//            mPresenter.addGoods();
        } else {
//            mPresenter.updateGoods();
        }
    }

    /**
     * 请求添加参数成功回调方法
     * 给页面赋值
     *
     * @param bean
     */
    protected void getAddParamsSuccess(AddGoodsEntity bean) {
        if (bean != null) {
            addGoodsEntity = bean;
            DecimalFormat df = new DecimalFormat("#.00");

            if(bean.param != null){
                lrGoodsTypeSelector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAddGoodsType();
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
    protected void getEditParamsSuccess(EditGoodsEntity bean) {
        if (bean != null) {
            editGoodsEntity = bean;
            DecimalFormat df = new DecimalFormat("#.00");

            EditGoodsEntity.GoodsBean egBean = bean.goods;
            if(egBean != null) {
                etGoodsName.setText(egBean.name);
                tvGoodsType.setText(egBean.goodsTypeName);
                tvGoodsType.setTag(egBean.goodsType);
                tvGoodsUnitName.setText(egBean.unitName);
                tvGoodsUnitName.setTag(egBean.unitId);
                etGoodsCost.setText(df.format(egBean.cost));
                tvGoodsSupplierName.setText(egBean.supplierName);
                tvGoodsSupplierName.setTag(egBean.supplierId);
                etGoodsBand.setText(egBean.brand);
                etGoodsSpec.setText(egBean.spec);
                etGoodsRemark.setText(egBean.remark);
                etGoodsNum.setText(egBean.num + "");
                tvGoodsTotal.setText(egBean.total);
            }
            if(egBean.paramlist != null){
                lrGoodsTypeSelector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEditGoodsType();
                    }
                });
            }
        } else {
            ToastUtil.showToast("物品不存在或者已经被删除");
            finish();
        }
    }

    /**
     * 显示添加时的物品类别列表
     */
    private void showAddGoodsType() {
        //呈现选择Project
        AddGoodsTypeBottomDialogAdapter adapter = new AddGoodsTypeBottomDialogAdapter(addGoodsEntity.param);
        final BottomSheetDialog dialog = getAddBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new AddGoodsTypeBottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(AddGoodsEntity.ParamBean bean) {
                tvGoodsType.setText(bean.name);
                tvGoodsType.setTag(bean.id);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * 显示修改时的物品类别列表
     */
    private void showEditGoodsType() {
        //呈现选择Project
        EditGoodsTypeBottomDialogAdapter adapter = new EditGoodsTypeBottomDialogAdapter(editGoodsEntity.goods.paramlist);
        final BottomSheetDialog dialog = getEditBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new EditGoodsTypeBottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(EditGoodsEntity.GoodsBean.ParamlistBean bean) {
                tvGoodsType.setText(bean.name);
                tvGoodsType.setTag(bean.id);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * 获取底部物品类型弹窗并且弹出
     */
    private BottomSheetDialog getAddBottomSheetDialog(AddGoodsTypeBottomDialogAdapter adapter) {
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
     * 获取底部物品类型弹窗并且弹出
     */
    private BottomSheetDialog getEditBottomSheetDialog(EditGoodsTypeBottomDialogAdapter adapter) {
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
        if(resultCode == RESULT_OK) {
            // 获取选择的单位
            if (requestCode == REQUEST_CODE_UNIT) {
                String name = data.getStringExtra(GoodsUnitActivity.EXTRA_KEY_NAME);
                int id = data.getIntExtra(GoodsUnitActivity.EXTRA_KEY_ID,0);
                tvGoodsUnitName.setText(name);
                tvGoodsUnitName.setTag(id);
            } else if (requestCode == REQUEST_CODE_SUPPLIER) {// 获取选择的供货商
                String name = data.getStringExtra(GoodsSupplierActivity.EXTRA_KEY_NAME);
                int id = data.getIntExtra(GoodsSupplierActivity.EXTRA_KEY_ID,0);
                tvGoodsSupplierName.setText(name);
                tvGoodsSupplierName.setTag(id);
            }
        }
    }
}
