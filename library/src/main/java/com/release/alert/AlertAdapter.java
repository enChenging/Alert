package com.release.alert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author Mr.release
 * @create 2019-12-30
 * @Describe
 */
public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {

    private List<ItemBean> mAlertViewItems;
    private Context mContext;
    private AlertAdapter.OnItemClickListener mOnItemClickListener;

    public AlertAdapter(Context context, List<ItemBean> alertViewItems) {
        this.mContext = context;
        this.mAlertViewItems = alertViewItems;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(AlertAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_alert_view_bottom, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
        ItemBean bean = mAlertViewItems.get(position);
        if (mAlertViewItems.size() == 1){
            holder.tv_text.setBackground(ContextCompat.getDrawable(mContext,R.drawable.alert_bottom_single2_selector));
        }else {
            if (position == 0){
                holder.tv_text.setBackground(ContextCompat.getDrawable(mContext,R.drawable.alert_bottom_top2_selector));
            }else if (position == mAlertViewItems.size()-1){
                holder.tv_text.setBackground(ContextCompat.getDrawable(mContext,R.drawable.alert_bottom_bottom2_selector));
            }else {
                holder.tv_text.setBackground(ContextCompat.getDrawable(mContext,R.drawable.alert_bottom_middle2_selector));
            }
        }
        holder.tv_text.setText(bean.name);
    }

    @Override
    public int getItemCount() {
        return mAlertViewItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_text;

        ViewHolder(View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_text);
        }
    }
}
