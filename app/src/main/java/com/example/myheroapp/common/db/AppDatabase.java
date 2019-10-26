package com.example.myheroapp.common.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myheroapp.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static String DB_NAME = "APP_DB";
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context){
        if(mInstance == null) {
            mInstance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
        }
        return mInstance;
    }

    public static AppDatabase getInstance(){
        return mInstance;
    }
}
