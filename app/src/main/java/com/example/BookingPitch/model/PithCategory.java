package com.example.BookingPitch.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PITCH_CATEGORY")
public class PithCategory {

    private static PithCategory INSTANCE = null;

    public static synchronized PithCategory getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new PithCategory();
        }
        return(INSTANCE);
    }

    @PrimaryKey
    private int id;
    private String name;
    private int money;

    public PithCategory() {
    }

    public PithCategory(int id, String name, int money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
