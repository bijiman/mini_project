package com.biji.mini.database;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface insertSHighDao {
    @Insert
    void insert(insertSHigh score);

    @Query("SELECT * FROM insertSHigh")
    List<insertSHigh> getAll();
}
