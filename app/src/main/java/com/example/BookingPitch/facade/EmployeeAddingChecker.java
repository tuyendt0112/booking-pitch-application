package com.example.BookingPitch.facade;

import com.example.BookingPitch.MyApplication;

public class EmployeeAddingChecker {
    private String checkphone;
    private String checkname;
    private String checkpassword;
    private String checkpassword2;

    public EmployeeAddingChecker(String checkphone, String checkname, String checkpassword, String checkpassword2) {
        this.checkphone = checkphone;
        this.checkname = checkname;
        this.checkpassword = checkpassword;
        this.checkpassword2 = checkpassword2;
    }

    public boolean isValid(String phone, String name, String password, String password2) {
        this.checkphone = phone;
        this.checkname = name;
        this.checkpassword = password;
        this.checkpassword2 = password2;

        if(!phone.matches(MyApplication.PHONE_REGEX))
        {
            return false;
        }
        else if(!name.matches(MyApplication.NAME_REGEX))
        {
            return false;
        }
        else if(!password.matches(MyApplication.PASS_REGEX))
        {
            return false;
        }
        else return password.equals(password2);
    }
}
