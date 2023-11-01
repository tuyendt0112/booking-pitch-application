package com.example.BookingPitch.adapter.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.model.HistoryBuy;

import java.util.List;

public class HistoryBuyAdapter extends RecyclerView.Adapter<HistoryBuyAdapter.ViewHolder> {
    private Context context;
    private List<HistoryBuy> list;

    public HistoryBuyAdapter(Context context, List<HistoryBuy> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryBuyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_buy,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryBuyAdapter.ViewHolder holder, int position) {
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvMoney.setText("-"+MyApplication.convertMoneyToString(list.get(position).getMoney())+" VNĐ");
        if(list.get(position).getStatus() == MyApplication.NAPTIEN_THANHCONG){
            holder.tvStatus.setText("Success");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        }else{
            holder.tvStatus.setText("Waiting for confirmation");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate,tvMoney,tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date_item_history_buy);
            tvMoney = itemView.findViewById(R.id.tv_money_item_history_buy);
            tvStatus = itemView.findViewById(R.id.tv_status_item_history_buy);
        }
    }
}