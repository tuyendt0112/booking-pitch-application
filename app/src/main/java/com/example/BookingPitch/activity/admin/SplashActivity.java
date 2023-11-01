package com.example.BookingPitch.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.activity.user.UserMainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    TextView tv;

    String textAdmin = "Use our application will help you to manage your pitch easily.";
    String textAdmin2 = "Use our application will help you to manage your customer easily.";
    String textAdmin3 = "Use our application will help you to manage your time easily.";
    String textAdmin4 = "Use our application will help you to manage your services easily.";
    List<String> listStringAdmin = new ArrayList<>(Arrays.asList(textAdmin,textAdmin2,textAdmin3,textAdmin4));

    String textUser = "Use our application will help you to saving time booking pitch.";
    String textUser2 = "You can change your avatar in your account information";
    String textUser3 = "You con booking pitch fastest as you can when using our application";
    String textUser4 = "You can check again your booking";
    List<String> listStringUser = new ArrayList<>(Arrays.asList(textUser,textUser2,textUser3,textUser4));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String stk = getIntent().getStringExtra("account");

        lottieAnimationView = findViewById(R.id.lottie_splsah);
        int posRand = new Random().nextInt(3);
        tv = findViewById(R.id.tv_splash);
        if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_ADMIN){
            lottieAnimationView.setAnimation(R.raw.admin_anim);
            tv.setText(listStringAdmin.get(posRand));
        }else{
            lottieAnimationView.setAnimation(R.raw.user_anim);
            tv.setText(listStringUser.get(posRand));
        }


        new Handler().postDelayed(() -> {
            if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_ADMIN) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("account",stk);
                startActivity(intent);
            }else if(MyApplication.CURRENT_TYPE == MyApplication.TYPE_USER){
                Intent intent = new Intent(SplashActivity.this, UserMainActivity.class);
                intent.putExtra("account",stk);
                startActivity(intent);
            }
        },4000);
    }

    @Override
    public void onBackPressed() {
    }
}