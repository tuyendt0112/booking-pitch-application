package com.example.BookingPitch.facade;

public class CheckCustomerAddingFacade {
    private String phone;
    private String name;
    private String address;
    private String password;
    private String password2;

    CustomerAddingChecker customerAddingChecker;
    public CheckCustomerAddingFacade(String phone, String name, String address, String password, String password2) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.password = password;
        this.password2 = password2;

        customerAddingChecker = new CustomerAddingChecker(phone,name,address,password,password2);
    }

    public boolean AddingCustomerFacade(){
        return customerAddingChecker.isValid(phone, name, address, password, password2);
    }
}