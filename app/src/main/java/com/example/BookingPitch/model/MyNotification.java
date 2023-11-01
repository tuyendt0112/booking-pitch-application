package com.example.BookingPitch.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MY_NOTIFICATION")
public class MyNotification {

    private static MyNotification INSTANCE = null;

    public static synchronized MyNotification getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new MyNotification();
        }
        return(INSTANCE);
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;
    private String date;
    private int status;

    public MyNotification(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
