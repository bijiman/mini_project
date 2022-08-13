package com.biji.mini.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class insertSHigh {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name ="score")
    public int score;
    public String scorestring = String.valueOf(score);
    public insertSHigh(int score){
        this.score = score;
    }

    public static insertSHigh[] isiScore() {
        return new insertSHigh[]{
                new insertSHigh(0),
        };
    }
}
