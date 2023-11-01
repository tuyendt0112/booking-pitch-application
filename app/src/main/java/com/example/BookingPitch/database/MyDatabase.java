package com.example.BookingPitch.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.BookingPitch.dao.CustomerDAO;
import com.example.BookingPitch.dao.HistoryBuyDAO;
import com.example.BookingPitch.dao.ManagerCategoryDAO;
import com.example.BookingPitch.dao.ManagerDAO;
import com.example.BookingPitch.dao.NotificationDAO;
import com.example.BookingPitch.dao.NotificationDetailsDAO;
import com.example.BookingPitch.dao.OrderDAO;
import com.example.BookingPitch.dao.OrderDetailsDAO;
import com.example.BookingPitch.dao.PitchCategoryDAO;
import com.example.BookingPitch.dao.PitchDAO;
import com.example.BookingPitch.dao.ServiceDAO;
import com.example.BookingPitch.dao.TimeDAO;
import com.example.BookingPitch.dao.TimeOrderDetailsDAO;
import com.example.BookingPitch.model.Customer;
import com.example.BookingPitch.model.HistoryBuy;
import com.example.BookingPitch.model.Manager;
import com.example.BookingPitch.model.ManagerCategory;
import com.example.BookingPitch.model.MyNotification;
import com.example.BookingPitch.model.MyTime;
import com.example.BookingPitch.model.NotificationDetails;
import com.example.BookingPitch.model.Order;
import com.example.BookingPitch.model.OrderDetails;
import com.example.BookingPitch.model.Pitch;
import com.example.BookingPitch.model.PithCategory;
import com.example.BookingPitch.model.ServiceBall;
import com.example.BookingPitch.model.TimeOrderDetails;

@Database(entities = {Customer.class,Manager.class, Order.class
        , OrderDetails.class, Pitch.class, PithCategory.class
        , ServiceBall.class, ManagerCategory.class, MyNotification.class
        , MyTime.class, TimeOrderDetails.class, HistoryBuy.class
        , NotificationDetails.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static String DB_NAME = "PITCH_MANAGER22";
    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CustomerDAO customerDAO();
    public abstract ManagerDAO managerDAO();
    public abstract OrderDAO orderDAO();
    public abstract OrderDetailsDAO orderDetailsDAO();
    public abstract PitchCategoryDAO pitchCategoryDAO();
    public abstract PitchDAO pitchDao();
    public abstract ServiceDAO serviceDAO();
    public abstract ManagerCategoryDAO managerCategoryDAO();
    public abstract TimeOrderDetailsDAO timeOrderDetailsDAO();
    public abstract TimeDAO timeDAO();
    public abstract HistoryBuyDAO historyBuyDAO();
    public abstract NotificationDetailsDAO notificationDetailsDAO();
    public abstract NotificationDAO notificationDAO();
}
