package com.example.BookingPitch.facade;

public class CheckEmployeeAddingFacade {
    private String phone;
    private String name;
    private String password;
    private String password2;

    EmployeeAddingChecker employeeAddingChecker;

    public CheckEmployeeAddingFacade(String phone, String name, String password, String password2) {
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.password2 = password2;
        employeeAddingChecker = new EmployeeAddingChecker(phone,name,password,password2);

    }

    public boolean AddingEmployeeFacade()
    {
        return employeeAddingChecker.isValid(phone,name,password,password2);
    }
}
