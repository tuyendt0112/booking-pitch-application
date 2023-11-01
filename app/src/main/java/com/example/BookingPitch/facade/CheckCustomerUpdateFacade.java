package com.example.BookingPitch.facade;

public class CheckCustomerUpdateFacade {
    private String phone;
    private String name;
    private String address;

    CustomerUpdateChecker employeeUpdateChecker;

    public CheckCustomerUpdateFacade(String phone, String name, String address) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        employeeUpdateChecker = new CustomerUpdateChecker(phone,name,address);
    }

    public boolean UpdateCustomerFacade()
    {
        return employeeUpdateChecker.isValid(phone,name,address);
    }
}
