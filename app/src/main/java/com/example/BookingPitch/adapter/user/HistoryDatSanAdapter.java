package com.example.BookingPitch.adapter.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.activity.user.UserMainActivity;
import com.example.BookingPitch.database.MyDatabase;
import com.example.BookingPitch.model.Order;

import java.util.List;

public class HistoryDatSanAdapter extends RecyclerView.Adapter<HistoryDatSanAdapter.ViewHolder> {

    private Context context;
    private List<Order> list;
    private MyOnClick myOnClick;

    public HistoryDatSanAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryDatSanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_history_order_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryDatSanAdapter.ViewHolder holder, int position) {
        holder.tvDateCreate.setText(list.get(position).getDateCreate());
        holder.tvDatePlay.setText(list.get(position).getDatePlay());
        holder.tvId.setText("Note " + list.get(position).getId());
        holder.tvSoCa.setText(list.get(position).getSoCa() + "");
        holder.tvMoney.setText(MyApplication.convertMoneyToString(list.get(position).getTotal())+" VNĐ");

        if(list.get(position).getStatus() == MyApplication.CHUA_STATUS){
            holder.tvTrangThai.setText("Not play");
        }else if(list.get(position).getStatus() == MyApplication.DANG_STATUS){
            holder.tvTrangThai.setText("Are playing");
        }else if(list.get(position).getStatus() == MyApplication.DA_STATUS){
            holder.tvTrangThai.setText("Have played");
        }else if(list.get(position).getStatus() == MyApplication.NGHI_STATUS){
            holder.tvTrangThai.setText("Off");
        }else if(list.get(position).getStatus() == MyApplication.HUY_STATUS){
            holder.tvTrangThai.setText("Canceled");
        }

        if (list.get(position).getStatus() == MyApplication.CHUA_STATUS) {
            holder.btnHuy.setBackground(AppCompatResources.getDrawable(context, R.drawable.btn_background));
        } else {
            holder.btnHuy.setBackgroundColor(context.getResources().getColor(R.color.dark_gray));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDateCreate, tvId, tvDatePlay, tvSoCa,tvTrangThai,tvMoney;
        private Button btnHuy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDatePlay = itemView.findViewById(R.id.tv_date_play_item_history);
            tvTrangThai = itemView.findViewById(R.id.tv_trangthai_item_history);
            tvDateCreate = itemView.findViewById(R.id.tv_dateCreate_item_history);
            tvId = itemView.findViewById(R.id.tv_id_history_datsan);
            tvSoCa = itemView.findViewById(R.id.tv_soca_item_history_order);
            btnHuy = itemView.findViewById(R.id.btnhuy_item_history_order);
            tvMoney = itemView.findViewById(R.id.tv_money_item_history_oder);

            btnHuy.setOnClickListener(v->{
                Order order = list.get(getAdapterPosition());
                if(order.getStatus()==MyApplication.CHUA_STATUS){
                    UserMainActivity.customer.setCoin(UserMainActivity.customer.getCoin() + order.getTotal());
                    MyDatabase.getInstance(context).customerDAO().update(UserMainActivity.customer);

                    order.setStatus(MyApplication.HUY_STATUS);

                    MyDatabase.getInstance(context).orderDAO().update(order);
                    Toast.makeText(context, "Cancellation successfully", Toast.LENGTH_SHORT).show();
                    setData(MyDatabase.getInstance(context).orderDAO()
                            .getOrderWithCustomerId(UserMainActivity.customer.getId()));
                }
            });
            itemView.setOnClickListener(v->{
                myOnClick.myOnClick(list.get(getAdapterPosition()));
            });
        }
    }

    public void setMyOnClick(MyOnClick myOnClick) {
        this.myOnClick = myOnClick;
    }

    public void setData(List<Order> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public interface MyOnClick{
        void myOnClick(Order order);
    }
}
