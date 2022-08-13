package com.biji.mini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.biji.mini.database.appDatabase;
import com.biji.mini.database.insertSHigh;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public class snakeActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private final List<SnakePoints> snakePointsList = new ArrayList<>();

    private SurfaceView surfaceView;
    private TextView scoreTV;
    private String movingPosition = "bottom";
    private SurfaceHolder surfaceHolder;
    public int score = 0;
    private static final int pointSize = 30;
    private static final int defaultTailPoints = 3;
    private static final int snakeColor = Color.YELLOW;
    private static final int snakeMovingSpeed = 850;
    private int positionX, positionY;
    private Timer timer;
    private Canvas canvas = null;
    private Paint pointColor = null;

    private appDatabase db;

    public Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        surfaceView = findViewById(R.id.surfaceView);
        scoreTV = findViewById(R.id.scoreTV);

        final AppCompatImageButton topBtn = findViewById(R.id.topBtn);
        final AppCompatImageButton bottomBtn = findViewById(R.id.bottomBtn);
        final AppCompatImageButton leftBtn = findViewById(R.id.leftBtn);
        final AppCompatImageButton rightBtn = findViewById(R.id.rightBtn);

        surfaceView.getHolder().addCallback(this);

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movingPosition.equals("bottom"))
                {
                    movingPosition = "top";
                }
            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movingPosition.equals("top"))
                {
                    movingPosition = "bottom";
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movingPosition.equals("right"))
                {
                    movingPosition = "left";
                }

            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movingPosition.equals("left"))
                {
                    movingPosition = "right";
                }
            }
        });

        MaterialButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        MaterialButton scoreBtn = findViewById(R.id.scoreBtn1);
        scoreBtn.setOnClickListener(view -> {
            startActivity(new Intent(this,ScoreActivity.class));
        });

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

        init();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
    private void init()
    {

        snakePointsList.clear();

        scoreTV.setText("0");

        score = 0;

        movingPosition = "right";

        int startPositionX = (pointSize) * defaultTailPoints;
        for (int i = 0; i < defaultTailPoints; i++)
        {
            SnakePoints snakePoints = new SnakePoints(startPositionX, pointSize);
            snakePointsList.add(snakePoints);

            startPositionX = startPositionX - (pointSize);
        }
        addPoint();

        moveSnake();

    }

    private void addPoint()
    {
        int surfaceWidth = surfaceView.getWidth() - (pointSize);
        int surfaceHeight = surfaceView.getHeight() - (pointSize);

        int randomXPosition = new Random().nextInt(surfaceWidth / pointSize);
        int randomYposition = new Random().nextInt(surfaceHeight / pointSize);
        Log.d("randomXPosition", String.valueOf(randomXPosition));
        Log.d("randomYposition", String.valueOf(randomYposition));
        if ((randomXPosition % 2) != 0)
        {
            randomXPosition = randomXPosition + 1;
        }

        if ((randomYposition % 2) != 0)
        {
            randomYposition = randomYposition + 1;
        }


        positionX = (pointSize * randomXPosition) + pointSize;
        positionY = (pointSize * randomYposition) + pointSize;
    }


    private void moveSnake()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                int headPositionX = snakePointsList.get(0).getPositionX();
                int headPositionY = snakePointsList.get(0).getPositionY();

                if(headPositionX == positionX && positionY == headPositionY)
                {
                    growSnake();
                    addPoint();
                }
                switch (movingPosition)
                {
                    case "right" :
                        snakePointsList.get(0).setPositionX(headPositionX + (pointSize));
                        snakePointsList.get(0).setPositionY(headPositionY);
                        break;

                    case "left" :
                        snakePointsList.get(0).setPositionX(headPositionX - (pointSize));
                        snakePointsList.get(0).setPositionY(headPositionY);
                        break;

                    case "top" :
                        snakePointsList.get(0).setPositionX(headPositionX);
                        snakePointsList.get(0).setPositionY(headPositionY - (pointSize));
                        break;

                    case "bottom" :
                        snakePointsList.get(0).setPositionX(headPositionX);
                        snakePointsList.get(0).setPositionY(headPositionY + (pointSize));
                        break;

                }
                if(checkGameOver(headPositionX, headPositionY))
                {
                    timer.purge();
                    timer.cancel();

                    db = Room.databaseBuilder(getApplicationContext(),
                            appDatabase.class, "mini")
                            .allowMainThreadQueries()
                            .build();
                    AlertDialog.Builder builder = new AlertDialog.Builder(snakeActivity.this);
                    builder.setMessage("Your Score = "+score);

                    builder.setTitle("Game Over");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Start Again?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            init();
                            insertData(new insertSHigh(score));
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            builder.show();
                        }


                    });
                }
                else {
                    canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.YELLOW, PorterDuff.Mode.CLEAR);
                    canvas.drawCircle(snakePointsList.get(0).getPositionX(), snakePointsList.get(0).getPositionY(), pointSize, createPointColor());
                    canvas.drawCircle(positionX, positionY, pointSize, createPointColor());

                    for (int i = 1; i < snakePointsList.size(); i++)
                    {

                        int getTempPositionX = snakePointsList.get(i).getPositionX();
                        int getTempPositionY = snakePointsList.get(i).getPositionY();

                        snakePointsList.get(i).setPositionX(headPositionX);
                        snakePointsList.get(i).setPositionY(headPositionY);
                        canvas.drawCircle(snakePointsList.get(i).getPositionX(), snakePointsList.get(i).getPositionY(), pointSize, createPointColor());

                        headPositionX = getTempPositionX;
                        headPositionY = getTempPositionY;


                    }

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
        }, 1000- snakeMovingSpeed, 1000- snakeMovingSpeed);
    }

    private void growSnake()
    {
        SnakePoints snakePoints = new SnakePoints(0,0);
        snakePointsList.add(snakePoints);

        score++;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTV.setText(String.valueOf(score));
            }
        });
    }

    public boolean checkGameOver(int headPositionX, int headPositionY)
    {
        boolean gameOver = false;

        if(snakePointsList.get(0).getPositionX() < 0 ||
                snakePointsList.get(0).getPositionY() < 0 ||
                snakePointsList.get(0).getPositionX() >= surfaceView.getWidth() ||
                snakePointsList.get(0).getPositionY() >= surfaceView.getHeight())
        {
            gameOver = true;
        }
        else
        {
            for(int i = 1; i < snakePointsList.size(); i++)
            {
                if (headPositionX == snakePointsList.get(i).getPositionX() &&
                        headPositionY == snakePointsList.get(i).getPositionY())
                {
                    gameOver = true;
                    break;
                }
            }
        }

        return gameOver;
    }

    private Paint createPointColor()
    {
        if (pointColor == null)
        {
            pointColor = new Paint();
            pointColor.setColor(snakeColor);
            pointColor.setStyle(Paint.Style.FILL);
            pointColor.setAntiAlias(true);
        }
        return pointColor;
    }
    private void insertData(final insertSHigh score)
    {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.insertSHighDao().insert(score);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(snakeActivity.this, "Score Inserted", Toast.LENGTH_SHORT).show();
                    }
                });
                }

        });

    }
}