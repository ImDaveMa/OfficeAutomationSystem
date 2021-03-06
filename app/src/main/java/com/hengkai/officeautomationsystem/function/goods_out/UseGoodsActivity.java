package com.hengkai.officeautomationsystem.function.goods_out;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.management_of_goods.SelectGoodsActivity;
import com.hengkai.officeautomationsystem.network.entity.GoodsParamsEntity;
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
public class UseGoodsActivity extends BaseActivity<UseGoodsPresenter> {

    /**
     * 选择物品回调请求码
     */
    private final int REQUEST_CODE_SELECT_GOODS = 10230;
    private GoodsParamsEntity mGoodsParamsEntity;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_goods_use_for)
    EditText etGoodsUseFor;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.ll_goods_list)
    LinearLayout llGoodsList;
    @BindView(R.id.tv_goods_add_item)
    TextView tvGoodsAddItem;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_approval_container)
    LinearLayout llApprovalContainer;
    @BindView(R.id.ll_copy_container)
    LinearLayout llCopyContainer;
    @BindView(R.id.lr_project_selector)
    RelativeLayout lrProjectSelector;
    @BindView(R.id.lr_goods_selector)
    RelativeLayout lrGoodsSelector;

    @Override
    protected int setupView() {
        return R.layout.activity_use_goods;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        tvTitle.setText("领用申请");

        // 请求页面参数
        mPresenter.getParams();
    }

    @Override
    protected UseGoodsPresenter bindPresenter() {
        return new UseGoodsPresenter();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.USE_GOODS_ACTIVITY_SAVE_GOODS);
        tags.add(NetworkTagFinal.USE_GOODS_ACTIVITY_GET_GOODS_PARAMS);
        return tags;
    }

    @OnClick({R.id.iv_back, R.id.tv_goods_add_item, R.id.tv_goods_delete, R.id.lr_goods_selector, R.id.lr_project_selector, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_goods_add_item: // 添加项
                if (llGoodsList.getChildCount() >= 10) {
                    ToastUtil.showToast("最多选择10种物品");
                    return;
                }
                createGoodsItem();
                break;
            case R.id.tv_goods_delete: // 删除第一项
                View child = llGoodsList.getChildAt(0);
                llGoodsList.removeView(child);
                setGoodsIndex();
                break;
            case R.id.lr_goods_selector: // 第一项选择物品
                Intent intent = new Intent(UseGoodsActivity.this, SelectGoodsActivity.class);
                intent.putExtra(SelectGoodsActivity.KEY_POSITION, 0);
                startActivityForResult(intent, REQUEST_CODE_SELECT_GOODS);
                break;
            case R.id.lr_project_selector: // 选择项目参数
                showProjectList();
                break;
            case R.id.tv_submit:
                submitForm();
                break;
        }
    }

    /**
     * 验证并提交表单
     */
    private void submitForm() {

        String goodsUseFor = etGoodsUseFor.getText().toString();
        //验证
        if (TextUtils.isEmpty(goodsUseFor)) {
            ToastUtil.showToast("请填写物品用途");
            etGoodsUseFor.requestFocus();
            return;
        }

        // 总价
        double countPrice = 0;
        // 验证列表
        for (int i = 0; i < llGoodsList.getChildCount(); i++) {
            View parentView = llGoodsList.getChildAt(i);

            // 验证物品ID
            TextView tvName = parentView.findViewById(R.id.tv_goods_name);
            if (tvName.getTag() == null || TextUtils.isEmpty(tvName.getTag().toString()) || Integer.parseInt(tvName.getTag().toString()) <= 0) {
                ToastUtil.showToast(String.format("请选择明细%d的物品", i + 1));
                return;
            }
            // 验证数量
            EditText etNum = parentView.findViewById(R.id.et_goods_num);
            if (TextUtils.isEmpty(etNum.getText())) {
                ToastUtil.showToast(String.format("请输入明细%d的数量", i + 1));
                return;
            }
            // 获取单价
            double cost = etNum.getTag() == null ? 0 : Double.parseDouble(etNum.getTag().toString());
            int inputNum = Integer.parseInt(etNum.getText().toString());
            // 验证数量是否超出
            TextView tvDelete = parentView.findViewById(R.id.tv_goods_delete);
            int num = tvDelete.getTag() == null ? 0 : Integer.parseInt(tvDelete.getTag().toString());
            if (inputNum > num) {
                String unit = tvName.getTag(R.id.tv_goods_name) + "";
                ToastUtil.showToast(String.format("物品%s库存不足，剩余%d%s", tvName.getText(), num, unit));
                etNum.requestFocus();
                return;
            }
            // 累加总价
            countPrice += inputNum * cost;
        }

        // 获取项目ID
        int projectId = tvProject.getTag() == null ? 0 : Integer.parseInt(tvProject.getTag().toString());

        // 输入的列表参数
        String outGoods = getGoodsItemsJSON();
        mPresenter.saveGoods(countPrice, goodsUseFor, outGoods, projectId);
    }

    /**
     * 提交成功
     */
    protected void submitSuccess() {
        ToastUtil.showToast("申请成功");
        setResult(Activity.RESULT_OK);
        finish();
    }

    /**
     * 获取页面参数成功
     */
    protected void getParamsSuccess(GoodsParamsEntity goodsParamsEntity) {
        mGoodsParamsEntity = goodsParamsEntity == null ? new GoodsParamsEntity() : goodsParamsEntity;

        // 添加界面上的内容
        List<GoodsParamsEntity.ApsBean> beans = mGoodsParamsEntity.getAps();
        if (beans != null && beans.size() > 0) {
            for (int i = 0; i < beans.size(); i++) {
                GoodsParamsEntity.ApsBean bean = beans.get(i);
                if(bean != null) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_person, null);
                    ImageView ivHeader = view.findViewById(R.id.iv_header);
                    // TODO: 2018/5/10 没有添加头像信息

                    TextView tvName = view.findViewById(R.id.tv_name);
                    tvName.setText(bean.getUserName());
                    // 审批人
                    if (bean.getType() == 1) {
                        llApprovalContainer.addView(view);
                    } else if (bean.getType() == 2) { // 抄送人
                        llCopyContainer.addView(view);
                    }
                }
            }
        }
    }

    /**
     * 显示项目列表
     */
    private void showProjectList() {
        //呈现选择Project
        GoodsBottomDialogAdapter adapter = new GoodsBottomDialogAdapter(mGoodsParamsEntity.getProjectlist());
        final BottomSheetDialog dialog = getBottomSheetDialog(adapter);
        adapter.setOnItemClickListener(new GoodsBottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(GoodsParamsEntity.ProjectBean bean) {
                tvProject.setText(bean.getName());
                tvProject.setTag(bean.getId());
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * 获取底部弹窗并且弹出
     */
    private BottomSheetDialog getBottomSheetDialog(GoodsBottomDialogAdapter adapter) {
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
     * 添加物品项实现
     */
    private void createGoodsItem() {
        final View view = LayoutInflater.from(this).inflate(R.layout.item_add_goods_detail, null);
        view.setTag(llGoodsList.getChildCount());
        TextView delete = view.findViewById(R.id.tv_goods_delete);
        //绑定删除事件
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llGoodsList.removeView(view);
                setGoodsIndex();
            }
        });
        RelativeLayout lrGSelector = view.findViewById(R.id.lr_goods_selector);
        lrGSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UseGoodsActivity.this, SelectGoodsActivity.class);
                int id = view.getTag() == null ? 0 : Integer.parseInt(view.getTag().toString());
                intent.putExtra(SelectGoodsActivity.KEY_POSITION, id);
                startActivityForResult(intent, REQUEST_CODE_SELECT_GOODS);
            }
        });
        llGoodsList.addView(view);
        setGoodsIndex();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SELECT_GOODS:
                if (resultCode == Activity.RESULT_OK) {
                    // 获取参数
                    int position = data.getExtras().getInt(SelectGoodsActivity.KEY_POSITION);
                    String name = data.getExtras().getString(SelectGoodsActivity.KEY_NAME);
                    int id = data.getExtras().getInt(SelectGoodsActivity.KEY_ID);
                    double price = data.getExtras().getDouble(SelectGoodsActivity.KEY_PRICE);
                    int num = data.getExtras().getInt(SelectGoodsActivity.KEY_NUM);
                    String unit = data.getExtras().getString(SelectGoodsActivity.KEY_UNIT);

                    // 判断选择并且合并
                    for (int i = 0; i < llGoodsList.getChildCount(); i++) {
                        View parentView = llGoodsList.getChildAt(i);
                        TextView tvName = parentView.findViewById(R.id.tv_goods_name);
                        String oldId = tvName.getTag() == null ? "" : tvName.getTag().toString();
                        if (oldId.equals(id + "")) {
                            ToastUtil.showToast("不能重复选择物品");
                            return;
                        }
                    }

                    // 获取对象并保存参数
                    View parentView = llGoodsList.getChildAt(position);
                    TextView tvGoodsName = parentView.findViewById(R.id.tv_goods_name);
                    EditText etNum = parentView.findViewById(R.id.et_goods_num);
                    TextView tvDelete = parentView.findViewById(R.id.tv_goods_delete);

                    // 如果当前项原来是有值的，那么清空原来的数量
                    if(!TextUtils.isEmpty(tvGoodsName.getText())){
                        etNum.setText("");
                    }

                    // 页面赋值
                    tvGoodsName.setText(name);
                    // 名称Tag 保存ID
                    tvGoodsName.setTag(id);
                    // 名称Tag Key：R.id.tv_goods_name 保存单位
                    tvGoodsName.setTag(R.id.tv_goods_name, unit);
                    // 数量Tag 保存单价
                    etNum.setTag(price);
                    // 显示库存
                    etNum.setHint(String.format("库存数：%d%s", num, unit));
                    etNum.requestFocus();
                    // 删除Tag 保存库存
                    tvDelete.setTag(num);
                }
                break;
        }
    }

    /**
     * 排序物品
     */
    private void setGoodsIndex() {
        for (int i = 0; i < llGoodsList.getChildCount(); i++) {
            View parentView = llGoodsList.getChildAt(i);
            parentView.setTag(i);
            TextView tvIndex = parentView.findViewById(R.id.tv_goods_index);
            tvIndex.setText(String.format("物品明细(%d)", i + 1));

            TextView delete = parentView.findViewById(R.id.tv_goods_delete);
            if (llGoodsList.getChildCount() > 1) {
                delete.setVisibility(View.VISIBLE);
            } else {
                delete.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 生成所有物品项数据JSON参数
     */
    private String getGoodsItemsJSON() {
        ArrayList<InsertGoodsBean> beans = new ArrayList<>();

        for (int i = 0; i < llGoodsList.getChildCount(); i++) {
            View parentView = llGoodsList.getChildAt(i);
            InsertGoodsBean bean = new InsertGoodsBean();
            // 数量 存了单价在里面
            EditText etNum = parentView.findViewById(R.id.et_goods_num);
            bean.setNUM(Integer.parseInt(etNum.getText().toString()));
            // 物品ID
            TextView tvName = parentView.findViewById(R.id.tv_goods_name);
            bean.setGOODSID(tvName.getTag() == null ? 0 : Integer.parseInt(tvName.getTag().toString()));
            // 获取单价
            double price = etNum.getTag() == null ? 0 : Double.parseDouble(etNum.getTag().toString());
            // 获取数量
            int num = etNum.getTag() == null ? 0 : Integer.parseInt(etNum.getText().toString());
            // 算总价
            bean.setTOTAL(num * price);

            beans.add(bean);
        }
        return new Gson().toJson(beans);
    }

    /**
     * 网络请求输入对象
     */
    class InsertGoodsBean {

        /**
         * GOODSID : 1
         * NUM : 5
         * TOTAL : 60
         */

        private int GOODSID;
        private int NUM;
        private double TOTAL;

        public int getGOODSID() {
            return GOODSID;
        }

        public void setGOODSID(int GOODSID) {
            this.GOODSID = GOODSID;
        }

        public int getNUM() {
            return NUM;
        }

        public void setNUM(int NUM) {
            this.NUM = NUM;
        }

        public double getTOTAL() {
            return TOTAL;
        }

        public void setTOTAL(double TOTAL) {
            this.TOTAL = TOTAL;
        }
    }
}
