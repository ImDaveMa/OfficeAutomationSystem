package com.hengkai.officeautomationsystem.function.unit_library.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.hengkai.officeautomationsystem.utils.PinYinUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2017/10/17.
 * 联系人列表的适配器
 */

public class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.ViewHolder> {

    private List<NewUnitKeywordEntity.DATABean> mList;

    public KeywordAdapter(List<NewUnitKeywordEntity.DATABean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keyword, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        NewUnitKeywordEntity.DATABean bean = mList.get(position);
        holder.tvName.setText(bean.name);
        String currentFirstWord = PinYinUtil.getPinYin(bean.name).charAt(0) + "";
        if (position > 0) {
            //获取上一个item的首字母
            String lastFirstWord = mList.get(position - 1).pinyin.charAt(0) + "";
            //那当前的首字母和上一个item的首字母做比较, 如果相同则隐藏首字母布局
            if (lastFirstWord.equals(currentFirstWord)) {
                holder.tvFirstWord.setVisibility(View.GONE);
            } else {
                holder.tvFirstWord.setVisibility(View.VISIBLE);
                holder.tvFirstWord.setText(currentFirstWord);
            }
        } else {
            holder.tvFirstWord.setText(currentFirstWord);
            holder.tvFirstWord.setVisibility(View.VISIBLE);
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_first_word)
        TextView tvFirstWord;
        @BindView(R.id.tv_name)
        TextView tvName;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            item = (LinearLayout) itemView;
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
