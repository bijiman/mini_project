package com.biji.mini.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {insertSHigh.class}, version = 1)
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
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(()-> getDbInstance(context).insertSHighDao().inserta(insertSHigh.isiScore()));
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }
}

