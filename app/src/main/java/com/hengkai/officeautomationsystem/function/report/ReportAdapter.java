package com.hengkai.officeautomationsystem.function.report;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.ReportEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.PicassoCircleTransform;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/18.
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private List<ReportEntity.DATEBean> mList;
    /**
     * 0 - 日报
     * 1 - 周报
     */
    private int type;
    private Context context;
    private BottomSheetDialog dialog;

    public ReportAdapter(List<ReportEntity.DATEBean> list, int type) {
        mList = list;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        if (type == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_report_day, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_report_week, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ReportEntity.DATEBean bean = mList.get(position);
        holder.tvName.setText(bean.userName);
        String time = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, bean.createTime);
        holder.tvTime.setText(time);
        holder.tvToday.setText(bean.presentContent);
        holder.tvTomorrow.setText(bean.nextContent);
        holder.tvNeedHelp.setText(bean.demandContent);

        if (bean.commnet != null) {
            holder.tvCommentCount.setText("评论(" + bean.commnet.size() + ")");
        } else {
            holder.tvCommentCount.setText("评论(0)");
        }

        if (!TextUtils.isEmpty(bean.headPortrait)) {
            Picasso.with(context)
                    .load(bean.headPortrait)
                    .error(R.drawable.ic_user_blue)
                    .transform(new PicassoCircleTransform())
                    .resize(WindowUtil.dp2px(30, context), WindowUtil.dp2px(30, context))
                    .centerCrop()
                    .into(holder.ivHeader);
        } else {
            holder.ivHeader.setImageResource(R.drawable.ic_user_blue);
        }

        holder.tvAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCommentDialog(holder.getAdapterPosition());
            }
        });

        holder.llCommentContainer.removeAllViews();
        if (bean.commnet != null && bean.commnet.size() > 0) {
            for (int i = 0; i < bean.commnet.size(); i++) {
                View view = View.inflate(context, R.layout.item_comment_normal, null);
                TextView tvName = view.findViewById(R.id.tv_name);
                TextView tvContent = view.findViewById(R.id.tv_content);
                TextView tvTime = view.findViewById(R.id.tv_time);
                ImageView ivHeader = view.findViewById(R.id.iv_header);

                ReportEntity.DATEBean.CommnetBean commentBean = bean.commnet.get(i);
                tvName.setText(commentBean.createUserName);
                tvContent.setText(commentBean.commentContent);
                String commentTime = DateFormatUtils.getFormatedNewsTime(commentBean.commentTime);
                tvTime.setText(commentTime);
                if (!TextUtils.isEmpty(commentBean.userLink)) {
                    Picasso.with(context)
                            .load(commentBean.userLink)
                            .error(R.drawable.ic_user_blue)
                            .transform(new PicassoCircleTransform())
                            .resize(WindowUtil.dp2px(35, context), WindowUtil.dp2px(35, context))
                            .centerCrop()
                            .into(ivHeader);
                } else {
                    ivHeader.setImageResource(R.drawable.ic_user_blue);
                }
                holder.llCommentContainer.addView(view);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_today)
        TextView tvToday;
        @BindView(R.id.tv_tomorrow)
        TextView tvTomorrow;
        @BindView(R.id.tv_need_help)
        TextView tvNeedHelp;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.tv_add_comment)
        TextView tvAddComment;
        @BindView(R.id.iv_header)
        ImageView ivHeader;
        @BindView(R.id.ll_comment_container)
        LinearLayout llCommentContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void showAddCommentDialog(final int adapterPosition) {
        dialog = new BottomSheetDialog(context, R.style.BottomDialog);
        View view = View.inflate(context, R.layout.dialog_add_comment, null);
        final EditText etCommentContent = view.findViewById(R.id.et_comment_content);
        TextView tvCommit = view.findViewById(R.id.tv_commit);

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent = etCommentContent.getText().toString().trim();
                if (TextUtils.isEmpty(commentContent)) {
                    ToastUtil.showToast("您还没有评论");
                    return;
                }
                if (mListener != null) {
                    mListener.commit(commentContent, adapterPosition, System.currentTimeMillis());
                }
            }
        });

        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

    public interface OnCommitCommentListener {
        void commit(String commentContent, int adapterPosition, long currentTime);
    }

    private OnCommitCommentListener mListener;

    public void addOnCommitCommentListener(OnCommitCommentListener listener) {
        this.mListener = listener;
    }

}
