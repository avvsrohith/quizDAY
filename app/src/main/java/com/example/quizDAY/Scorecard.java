package com.example.quizDAY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Math.random;

public class Scorecard extends AppCompatActivity {
    public static final String HI_SCORE="hiScore";
    public Button restart,mainmenu;
    public TextView scoretv,hiscore_tv,TV_Message;
    public int score,Highscore;
    private Vibrator vibrator;
    public String[] Array_Message={"This is good, but there's always better","Don't settle for this","Try to beat the figure below","You couldn't beat this tiny Highscore?,Seriously?","Aim for the burger's cheese,You'll atleast bite till the mayo"};
    public Boolean HSmet,HScrossed;
    public static final String HighScore = "Highscore";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);
        restart=findViewById(R.id.BTN_Restart);
        mainmenu=findViewById(R.id.BTN_Mainmenu);
        scoretv=findViewById(R.id.TV_Score);
        TV_Message=findViewById(R.id.TV_Message);
        hiscore_tv=findViewById(R.id.TV_Highscore);
        vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        HSmet =false;
        HScrossed=false;
        Show_Score();
        Load_Highscore();
        Show_Highscore();
        Show_Message();
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                quiz();
            }
        });
        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                MainMenu();
            }
        });

    }
    public void Show_Score(){
        Intent intent = getIntent();
        score=intent.getIntExtra(Quizactivity.Score,0);
        scoretv.setText(String.valueOf(score));
    }
    public void quiz() {
        Intent intent = new Intent(this, Quizactivity.class);
        startActivity(intent);
    }
    public void MainMenu() {
        Intent result = new Intent(this,MainActivity.class);
        result.putExtra(HighScore,Highscore);
        startActivity(result);
    }
    public void Update_Highscore(){
            Highscore=score;
            SharedPreferences prefs=getSharedPreferences("Highscore",MODE_PRIVATE);
            SharedPreferences.Editor HSeditor = prefs.edit();
            HSeditor.putInt("Highscore_key", Highscore);
            HSeditor.apply();
        }

    @SuppressLint("SetTextI18n")
    public void Show_Highscore(){
        if(score==Highscore)
            HSmet =true;
        if(score>Highscore){
            HScrossed=true;
            Update_Highscore();}
        hiscore_tv.setText("HIscore : "+Highscore);

    }
    public void Load_Highscore(){
        SharedPreferences prefs = getSharedPreferences("Highscore",MODE_PRIVATE);
        Highscore= prefs.getInt("Highscore_key",-7);
    }
    public void Show_Message(){
        if(HSmet)
            TV_Message.setText("Impressive. On par with the best");
        else if(HScrossed)
            TV_Message.setText("You did it.Now Sky's your target");
        else{
            TV_Message.setText(Array_Message[(int)(random()*5)]);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Score", score);
    }
}