package com.hengkai.officeautomationsystem.function.management_of_goods;

import android.app.Activity;
import android.content.Intent;
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
public class GoodsInActivity extends BaseActivity<GoodsInPresenter> {

    /**
     * 选择物品回调请求码
     */
    private final int REQUEST_CODE_SELECT_GOODS = 10230;
    private GoodsParamsEntity mGoodsParamsEntity;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.ll_goods_list)
    LinearLayout llGoodsList;
    @BindView(R.id.tv_goods_add_item)
    TextView tvGoodsAddItem;
    @BindView(R.id.et_goods_summary)
    EditText etGoodsSummary;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_approval_container)
    LinearLayout llApprovalContainer;
    @BindView(R.id.ll_copy_container)
    LinearLayout llCopyContainer;
    @BindView(R.id.lr_goods_selector)
    RelativeLayout lrGoodsSelector;

    @Override
    protected int setupView() {
        return R.layout.activity_goods_in;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        tvTitle.setText("入库申请");

        // 请求页面参数
        mPresenter.getParams();
    }

    @Override
    protected GoodsInPresenter bindPresenter() {
        return new GoodsInPresenter();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.USE_GOODS_ACTIVITY_SAVE_GOODS);
        return tags;
    }

    @OnClick({R.id.iv_back, R.id.tv_goods_add_item, R.id.tv_goods_delete, R.id.lr_goods_selector, R.id.tv_submit})
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
                Intent intent = new Intent(GoodsInActivity.this, SelectGoodsActivity.class);
                intent.putExtra(SelectGoodsActivity.KEY_POSITION, 0);
                startActivityForResult(intent, REQUEST_CODE_SELECT_GOODS);
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

            // 累加总价
            countPrice += inputNum * cost;
        }

        EditText etSummary = findViewById(R.id.et_goods_summary);
        String reason = etSummary.getText().toString();
        if(TextUtils.isEmpty(reason)){
            ToastUtil.showToast("请输填写入库说明");
            etSummary.requestFocus();
            return;
        }

        // 输入的列表参数
        String outGoods = getGoodsItemsJSON();
        mPresenter.saveGoodsIn(countPrice, reason, outGoods);
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
                    // TODO: 2018/5/12 没有添加头像信息

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
                Intent intent = new Intent(GoodsInActivity.this, SelectGoodsActivity.class);
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
                    tvGoodsName.setText(name);
                    // 名称Tag 保存ID
                    tvGoodsName.setTag(id);
                    EditText etNum = parentView.findViewById(R.id.et_goods_num);
                    // 数量Tag 保存单价
                    etNum.setTag(price);
                    etNum.requestFocus();
                    // 删除Tag 保存库存
                    TextView tvDelete = parentView.findViewById(R.id.tv_goods_delete);
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
