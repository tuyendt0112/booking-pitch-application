package com.example.BookingPitch.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.activity.SelectTypeActivity;
import com.example.BookingPitch.database.MyDatabase;
import com.example.BookingPitch.fragment.adminfragment.DatSanFragment;
import com.example.BookingPitch.fragment.adminfragment.KhachHangFragment;
import com.example.BookingPitch.fragment.adminfragment.ManHinhCaNhanFragment;
import com.example.BookingPitch.fragment.adminfragment.NhanVienFragment;
import com.example.BookingPitch.fragment.adminfragment.RequestBuyFragment;
import com.example.BookingPitch.fragment.adminfragment.SanBongFragment;
import com.example.BookingPitch.fragment.adminfragment.ThongBaoFragmentAdmin;
import com.example.BookingPitch.fragment.adminfragment.TimeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    public static String ACCOUNT = "";
    public static int ID_MAX_ORDER = 0;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    public static int CURRENT_FRAGMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ID_MAX_ORDER = MyDatabase.getInstance(this).orderDAO().getIdMax();

        ACCOUNT = getIntent().getStringExtra("account");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);

        toolbar.setNavigationOnClickListener(v->{
            drawerLayout.openDrawer(GravityCompat.START);
        });

        if(!ACCOUNT.equals("Admin")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.item_nhanvien).setVisible(false);
        }

        navigationView.setNavigationItemSelectedListener(item -> {

            Menu menu = navigationView.getMenu();
            //Booking Pitches
            if(item.getItemId() == R.id.item_datsan && CURRENT_FRAGMENT!=0){
                menu.findItem(item.getItemId()).setChecked(true);
                CURRENT_FRAGMENT = 0;
                replaceFragment(new DatSanFragment());
                getSupportActionBar().setTitle("Booking Pitches");
            }
            //Pitches Management
            else if(item.getItemId() == R.id.item_sanbong && CURRENT_FRAGMENT!=1){
                menu.findItem(item.getItemId()).setChecked(true);
                CURRENT_FRAGMENT = 1;
                replaceFragment(new SanBongFragment());
                getSupportActionBar().setTitle("Pitches management");
            }
            //Schedule Management
            else if(item.getItemId() == R.id.item_giothidau && CURRENT_FRAGMENT!=2){
                menu.findItem(item.getItemId()).setChecked(true);
                CURRENT_FRAGMENT = 2;
                replaceFragment(new TimeFragment());
                getSupportActionBar().setTitle("Schedule management");
            }
            //Customer Management
            else if(item.getItemId() == R.id.item_khachhang && CURRENT_FRAGMENT!=3){
                menu.findItem(item.getItemId()).setChecked(true);
                CURRENT_FRAGMENT = 3;
                replaceFragment(new KhachHangFragment());
                getSupportActionBar().setTitle("Customer management");
            }
            //Announcement
            else if(item.getItemId() == R.id.item_nhanvien && CURRENT_FRAGMENT!=4) {
                if (ACCOUNT.equals(MyApplication.ADMIN_CATEGORY)) {
                    menu.findItem(item.getItemId()).setChecked(true);
                    CURRENT_FRAGMENT = 4;
                    replaceFragment(new NhanVienFragment());
                    getSupportActionBar().setTitle("Staff management");
                } else {
                    Toast.makeText(MainActivity.this, "You cannot access", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            //Money Request
            else if(item.getItemId() == R.id.item_man_hinh_ca_nhan && CURRENT_FRAGMENT!=5)  {
                menu.findItem(item.getItemId()).setChecked(true);
                CURRENT_FRAGMENT = 5;
                replaceFragment(new ManHinhCaNhanFragment());
                getSupportActionBar().setTitle("Personality");
            }
            //Personality
            else if(item.getItemId() == R.id.item_man_hinh_thong_bao && CURRENT_FRAGMENT!=6)  {
                menu.findItem(item.getItemId()).setChecked(true);
                CURRENT_FRAGMENT = 6;
                replaceFragment(new ThongBaoFragmentAdmin());
                getSupportActionBar().setTitle("Announcement");
            }
            //Staff Management
            else if(item.getItemId() == R.id.item_accept_naptien && CURRENT_FRAGMENT!=7)  {
                menu.findItem(item.getItemId()).setChecked(true);
                CURRENT_FRAGMENT = 7;
                replaceFragment(new RequestBuyFragment());
                getSupportActionBar().setTitle("Money request");
            }
            //Log Out
            else if(item.getItemId() == R.id.item_dangxuat){
                finishAffinity();
                CURRENT_FRAGMENT = 0;
                MyApplication.CURRENT_TYPE = -1;
                Intent intent = new Intent(MainActivity.this, SelectTypeActivity.class);
                startActivity(intent);
            }
            drawerLayout.close();
            return true;
        });
        // First Page Showing
        replaceFragment(new DatSanFragment());
        getSupportActionBar().setTitle("Booking Pitches");
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_content,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isOpen())drawerLayout.close();
        else{
            finishAffinity();
            System.exit(0);
        }
    }
}