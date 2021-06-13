package com.example.quizDAY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Vibrator vibrator;
    public int HighScore,score;
    public TextView TV_HighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Button BTN_Instructions = findViewById(R.id.BTN_Instructions);
        BTN_Instructions.setOnClickListener(v -> Open_Instructions());
        Button BTN_Start = findViewById(R.id.BTN_Start);
        Show_HighScore();
        BTN_Start.setOnClickListener((v -> Open_Quiz()));
    }
    public void Open_Quiz() {
        vibrator.vibrate(70);
        Intent intent = new Intent(this, Quizactivity.class);
        startActivity(intent);
    }
    public void Open_Instructions() {
        vibrator.vibrate(70);
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
    }
    @SuppressLint("SetTextI18n")
    public void Show_HighScore(){
        SharedPreferences prefs = getSharedPreferences("Highscore",MODE_PRIVATE);
        HighScore= prefs.getInt("Highscore_key",-7);
        TV_HighScore = findViewById(R.id.TV_Highscore);
        TV_HighScore.setText("HIscore : "+HighScore);
    }


}

