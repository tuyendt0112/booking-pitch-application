package com.example.BookingPitch.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Order.class,parentColumns = "id",childColumns = "orderId",onDelete = CASCADE),
                        @ForeignKey(entity = MyTime.class,parentColumns = "id",childColumns = "timeId",onDelete = CASCADE)})
public class TimeOrderDetails {

    private static TimeOrderDetails INSTANCE = null;

    public static synchronized TimeOrderDetails getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new TimeOrderDetails();
        }
        return(INSTANCE);
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int orderId;
    private int timeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }
}
