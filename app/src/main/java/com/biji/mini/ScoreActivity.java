package com.biji.mini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.biji.mini.database.appDatabase;
import com.biji.mini.database.insertSHigh;

import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    private scoreListAdapter ScoreAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        RecyclerView recyclerView = findViewById(R.id.recycleview_score);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ScoreAdapter = new scoreListAdapter(this);
        recyclerView.setAdapter(ScoreAdapter);

        loadActivitiesList();

    }

    private void loadActivitiesList() {
            appDatabase db = appDatabase.getDbInstance(getApplicationContext());
            List<insertSHigh> list = db.insertSHighDao().getAll();
            ScoreAdapter.setScoreList(list);
    }
}