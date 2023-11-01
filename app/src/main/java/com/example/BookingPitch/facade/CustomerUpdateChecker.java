package com.example.BookingPitch.facade;

import android.view.View;

import com.example.BookingPitch.MyApplication;

public class CustomerUpdateChecker {
    private String checkphone;
    private String checkname;
    private String checkaddress;

    public CustomerUpdateChecker(String checkphone, String checkname, String checkaddress) {
        this.checkphone = checkphone;
        this.checkname = checkname;
        this.checkaddress = checkaddress;
    }

    public boolean isValid(String phone, String name, String address) {
        this.checkphone = checkphone;
        this.checkname = checkname;
        this.checkaddress = checkaddress;

        if(!phone.matches(MyApplication.PHONE_REGEX))
        {
            return false;
        }
        else if(!name.matches(MyApplication.NAME_REGEX))
        {
            return false;
        }
        else return address.matches(MyApplication.ADDRESS_REGEX);
    }
}
