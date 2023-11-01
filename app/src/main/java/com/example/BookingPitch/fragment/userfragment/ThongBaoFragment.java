package com.example.BookingPitch.fragment.userfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.activity.user.UserMainActivity;
import com.example.BookingPitch.adapter.user.NotificationAdapterUser;
import com.example.BookingPitch.database.MyDatabase;
import com.example.BookingPitch.model.MyNotification;

import java.util.List;


public class ThongBaoFragment extends Fragment {

    List<MyNotification> notifications;
    RecyclerView recyclerView;
    NotificationAdapterUser adapterUser;
    TextView tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifications = MyDatabase.getInstance(getContext()).notificationDAO().getNotifiWithCusId(UserMainActivity.customer.getId(), MyApplication.HOATDONG_STATUS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_bao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_notifi_user);
        tv = view.findViewById(R.id.tv_count_notification_user);
        tv.setText(notifications.size()+"");

        adapterUser = new NotificationAdapterUser(getContext(),notifications);
        recyclerView.setAdapter(adapterUser);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }
}