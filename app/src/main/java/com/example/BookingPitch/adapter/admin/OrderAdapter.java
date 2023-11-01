package com.example.BookingPitch.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.database.MyDatabase;
import com.example.BookingPitch.model.Customer;
import com.example.BookingPitch.model.Order;
import com.example.BookingPitch.model.Pitch;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private List<Order> list;
    private Context context;
    private OrderAdapter.MyOnClick onClick;

    public OrderAdapter(List<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        Customer customer = MyDatabase.getInstance(context).customerDAO().getCustomerWithID(list.get(position).getCustomerId()).get(0);
        Pitch pitch = MyDatabase.getInstance(context).pitchDao().getPitchId(list.get(position).getPitchId()).get(0);

        holder.tvTenKhachHang.setText(customer.getName());
        holder.tvTenSanBong.setText(pitch.getName());
        holder.tvTienSan.setText(MyApplication.convertMoneyToString(list.get(position).getTotalPitchMoney())+" VNĐ");
        holder.tvTienDichVu.setText(MyApplication.convertMoneyToString(list.get(position).getTotalServiceMoney())+" VNĐ");
        holder.tvMaPhieuThongTin.setText("Note "+ list.get(position).getId());
        holder.tvDate.setText(list.get(position).getDateCreate());
        holder.tvTotal.setText(MyApplication.convertMoneyToString(list.get(position).getTotal()) +"VNĐ");
        holder.tvDatePlay.setText("Day "+list.get(position).getDatePlay());
        holder.tvSoCa.setText(list.get(position).getSoCa()+"");
        holder.tvChiPhi.setText(MyApplication.convertMoneyToString(list.get(position).getChiPhiKhac()) + " VNĐ");

        if(list.get(position).getStatus() == MyApplication.CHUA_STATUS){
            holder.tvStatus.setText("Not play");
        }else if(list.get(position).getStatus() == MyApplication.DANG_STATUS){
            holder.tvStatus.setText("Are playing");
        }else if(list.get(position).getStatus() == MyApplication.DA_STATUS){
            holder.tvStatus.setText("Have played");
        }else if(list.get(position).getStatus() == MyApplication.NGHI_STATUS){
            holder.tvStatus.setText("Off");
        }else if(list.get(position).getStatus() == MyApplication.HUY_STATUS){
            holder.tvStatus.setText("Canceled");
        }

        if(list.get(position).getStatus() == MyApplication.CHUA_STATUS){
            holder.btnHuy.setBackground(AppCompatResources.getDrawable(context,R.drawable.btn_background));
        }else{
            holder.btnHuy.setBackgroundColor(context.getResources().getColor(R.color.dark_gray));
        }
    }

    public int[] getArrayDate(String date){
        String[] str = date.split("-");
        int arr[] = new int[str.length];
        try{
            for(int i = 0;i<str.length;i++){
                arr[i] = Integer.parseInt(str[i]);
            }
        }catch (NumberFormatException e){
            return null;
        }
        return arr;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMaPhieuThongTin;
        private TextView tvTenKhachHang;
        private TextView tvTenSanBong;
        private TextView tvDate;
        private TextView tvSoCa;
        private TextView tvTotal,tvTienSan,tvTienDichVu,tvStatus,tvChiPhi;
        private Button btnHuy;
        private TextView tvDatePlay;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieuThongTin =itemView.findViewById(R.id.tv_id_item_order);
            tvTenKhachHang =  itemView.findViewById(R.id.tv_ten_khach_hang_item_order);
            tvTenSanBong = itemView.findViewById(R.id.tv_ten_san_bong_item_order);
            tvDate = itemView.findViewById(R.id.tv_date_item_order);
            tvTotal = itemView.findViewById(R.id.tv_total_item_order);
            tvTienSan = itemView.findViewById(R.id.tv_tiensan_item_order);
            btnHuy = itemView.findViewById(R.id.btnhuy_item_order);
            tvStatus = itemView.findViewById(R.id.tv_status_item_order);
            tvDatePlay = itemView.findViewById(R.id.tv_date_play_itemorder);
            tvSoCa = itemView.findViewById(R.id.tv_soca_itemorder);
            tvChiPhi = itemView.findViewById(R.id.tv_chiphi_khac_item_order);
            cardView = itemView.findViewById(R.id.cardView_phieuThongTin);

            btnHuy.setOnClickListener(v->{
                Order order = list.get(getAdapterPosition());
                if(order.getStatus()==MyApplication.CHUA_STATUS){
                    Customer customer = MyDatabase.getInstance(context).customerDAO().getCustomerWithID(order.getCustomerId()).get(0);
                    customer.setCoin(customer.getCoin() + order.getTotal());
                    MyDatabase.getInstance(context).customerDAO().update(customer);

                    list.get(getAdapterPosition()).setStatus(MyApplication.HUY_STATUS);

                    MyDatabase.getInstance(context).orderDAO().update(list.get(getAdapterPosition()));
                    Toast.makeText(context, "Cancellation successfully", Toast.LENGTH_SHORT).show();
                    setData(MyDatabase.getInstance(context).orderDAO().getAll());
                }
            });

            itemView.setOnClickListener( v -> {
                onClick.myOnClick(list.get(getAdapterPosition()));
            });
        }
    }

    public void setOnClick(OrderAdapter.MyOnClick onClick) {
        this.onClick = onClick;
    }

    public interface MyOnClick{
        void myOnClick(Order order);
    }

    public void setData(List<Order> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
