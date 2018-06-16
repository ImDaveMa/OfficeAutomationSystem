package com.hengkai.officeautomationsystem.function.visit_record.detail_x;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.hengkai.officeautomationsystem.R;

import java.util.List;

/**
 * Created by Harry on 2018/2/27.
 * 获取底部弹窗的dialog(周边poi的适配器)
 */

public class BottomPoiDialogAdapter extends RecyclerView.Adapter<BottomPoiDialogAdapter.ViewHolder> {

    private List<PoiInfo> allPoi;

    public BottomPoiDialogAdapter(List<PoiInfo> allPoi) {
        this.allPoi = allPoi;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_get_poi_bottom_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PoiInfo info = allPoi.get(position);
        holder.tv_address.setText(info.address);
        holder.tv_name.setText(info.name);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.getAddressName(info.address + " " + info.name);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allPoi.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_address;
        TextView tv_name;
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_name = itemView.findViewById(R.id.tv_name);
            container = (LinearLayout) itemView;
        }
    }

    public interface AdapterItemClickListener {
        void getAddressName(String addressName);
    }

    private AdapterItemClickListener listener;

    /**
     * 适配器回传地址信息给主页面控件
     * @param listener
     */
    public void setOnAdapterItemClickListener(AdapterItemClickListener listener) {
        this.listener = listener;
    }

}
