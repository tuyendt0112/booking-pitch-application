package com.example.BookingPitch.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.adapter.admin.AdapterForm;
import com.example.BookingPitch.database.MyDatabase;
import com.example.BookingPitch.fragment.adminfragment.LoginFragment;
import com.example.BookingPitch.model.Manager;
import com.example.BookingPitch.model.ManagerCategory;
import com.example.BookingPitch.model.MyTime;
import com.example.BookingPitch.model.PithCategory;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class
FormActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    public LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //MyDatabase.getInstance(this).orderDAO().deleteAll();
        createData();

        loginFragment = new LoginFragment();

        AdapterForm adapter = new AdapterForm(this);
        viewPager2 = findViewById(R.id.viewpager_form);
        viewPager2.setAdapter(adapter);
        tabLayout = findViewById(R.id.tablayout_form);

        if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_USER) {
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
                if (position == 0) tab.setText("Log In");
                else if (position == 1) tab.setText("Sign Up");
            }).attach();
        }else{
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
                if (position == 0) tab.setText("Log In");
            }).attach();
        }
    }

    public void createData(){
        createAdminDataIfNotExists();
        createPitchCategoryDataIfNotExists();
        addTimeDataIfNotExit();
    }

    public void createAdminDataIfNotExists(){
        if(MyDatabase.getInstance(this).managerCategoryDAO().getAll().size()==0){
            ManagerCategory category = ManagerCategory.getInstance();
            category.setName(MyApplication.ADMIN_CATEGORY);
            MyDatabase.getInstance(this).managerCategoryDAO().insert(category);
            Manager manager1 = Manager.getInstance();
            manager1.setPhone("Admin");
            manager1.setName("ADMIN");
            manager1.setPosition(MyDatabase.getInstance(this).managerCategoryDAO().getIdAdmin(MyApplication.ADMIN_CATEGORY).get(0).getId());
            manager1.setPassword("123456");
            MyDatabase.getInstance(this).managerDAO().insert(manager1);
        }
    }

    public void createPitchCategoryDataIfNotExists(){
        if(MyDatabase.getInstance(this).pitchCategoryDAO().getAll().size()==0){
            PithCategory category = new PithCategory(MyApplication.ID_CATEGORY_PITCH_5,"Pitch (5 players)",300000);
            MyDatabase.getInstance(this).pitchCategoryDAO().insert(category);
            category = new PithCategory(MyApplication.ID_CATEGORY_PITCH_7,"Pitch (7 players)",600000);
            MyDatabase.getInstance(this).pitchCategoryDAO().insert(category);
            category = new PithCategory(MyApplication.ID_CATEGORY_PITCH_11,"Pitch (11 players)",1200000);
            MyDatabase.getInstance(this).pitchCategoryDAO().insert(category);
        }
    }

    public void registerSuccess(String s1, String s2) {
        viewPager2.setCurrentItem(0);
        loginFragment.edtStk.setText(s1);
        loginFragment.edtPassword.setText(s2);
    }

    public void addTimeDataIfNotExit(){
        if(MyDatabase.getInstance(this).timeDAO().getAll().size() == 0){
            for (int i = 1;i<=12;i++){
                MyTime myTime = MyTime.getInstance();
                myTime.setName("Shift "+i);
                myTime.setId(i);
                myTime.setStartTime((i-1)*2);
                myTime.setEndTime(i*2);
                MyDatabase.getInstance(this).timeDAO().insert(myTime);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}