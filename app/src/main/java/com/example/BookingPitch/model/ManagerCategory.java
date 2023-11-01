package com.example.BookingPitch.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MANAGER_CATEGORY")
public class ManagerCategory {


    private static ManagerCategory INSTANCE = null;

    public static synchronized ManagerCategory getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new ManagerCategory();
        }
        return(INSTANCE);
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

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
}
