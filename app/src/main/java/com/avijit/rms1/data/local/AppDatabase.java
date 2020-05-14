package com.avijit.rms1.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.avijit.rms1.data.local.entities.*;
import com.avijit.rms1.data.local.daos.*;

@Database(entities = {User.class, Division.class, District.class, Area.class},version = 3 ,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "app_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context,AppDatabase.class,DB_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    public abstract UserDao userDao();
    public abstract DivisionDao divisionDao();
    public abstract DistrictDao districtDao();
    public abstract AreaDao areaDao();
}
