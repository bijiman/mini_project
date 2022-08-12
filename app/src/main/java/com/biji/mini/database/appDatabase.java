package com.biji.mini.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executors;

@Database(entities = {insertSHigh.class}, version = 2)
public abstract class appDatabase extends RoomDatabase {
    public abstract insertSHighDao insertSHighDao();

    private static appDatabase INSTANCE;

    public static appDatabase getDbInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    appDatabase.class,
                    "mini")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}

