package com.biji.mini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton snakeBtn = findViewById(R.id.snakeBtn);
        snakeBtn.setOnClickListener(view -> startActivity(new Intent(this, snakeActivity.class)));

        MaterialButton scoreBtn = findViewById(R.id.scoreBtn);
        scoreBtn.setOnClickListener(view -> startActivity(new Intent(this, ScoreActivity.class)));
    }
}