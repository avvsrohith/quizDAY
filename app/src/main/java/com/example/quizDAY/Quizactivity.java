
        package com.example.quizDAY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.random;

import java.lang.*;
import java.util.ArrayList;
import java.util.Locale;

public class Quizactivity extends AppCompatActivity {
    public   TextView questiontv,scoretv,timertv;
    public RadioButton opt1,opt2,opt3,opt4,crctbtn,selected;
    public RadioGroup radiogroup;
    public Button button_confirm,button_back;
    public Boolean answered,Correct,Confirmed;
    public ConstraintLayout QuizBG;
    private Vibrator vibrator;
    public static int date,year,month,A,correct,wrongans=0,correctans=0;
    public String[] dayslist ={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    public int score;
    public int crctid;
    public String Question;
    public int selectedid;
    public CountDownTimer timer;
    public long timeleft =60000;
    public static final String Score = "Score";
    public Handler handler;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizactivity);
        questiontv=findViewById(R.id.TV_Question);
        scoretv=findViewById(R.id.TV_Score);
        timertv=findViewById(R.id.TV_Timer);
        button_confirm=findViewById(R.id.BTN_Confirm);
        button_back=findViewById(R.id.BTN_Back);
        opt1=findViewById(R.id.RB_opt1);
        opt2=findViewById(R.id.RB_opt2);
        opt3=findViewById(R.id.RB_opt3);
        opt4=findViewById(R.id.RB_opt4);
        vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        radiogroup=findViewById(R.id.radioGroup);
        QuizBG=findViewById(R.id.QuizBG);
        score=0;
        correctans=0;
        wrongans=0;
        scoretv.setText("Score:"+ score);
        if(savedInstanceState==null)
        New_Question();
        else {
            score = savedInstanceState.getInt("Score");
            timeleft = savedInstanceState.getLong("Timeleft");
            answered = savedInstanceState.getBoolean("Answered");
            correctans = savedInstanceState.getInt("Correctans");
            wrongans = savedInstanceState.getInt("Wrongans");
            selectedid = savedInstanceState.getInt("SelectedID");
            correct = savedInstanceState.getInt("Correct");
            Correct = savedInstanceState.getBoolean("correctboolean");
            Confirmed = savedInstanceState.getBoolean("Confirmed");
            Question = savedInstanceState.getString("Question");
            questiontv.setText(Question);
            RadioButton[] opt = {opt1, opt2, opt3, opt4};
            crctbtn = opt[correct];
            scoretv.setText("Score : " + score);

            if (answered)
                Highlight_Button();
            if(Confirmed){
                if(timer!=null)
                timer.cancel();
                if(Correct)
                    If_Correct();
                else
                    If_Wrong();
                CHeck_for_click();
            }
            else{
            Start_Timer();
            CHeck_for_click();}
        }

        button_back.setOnClickListener(v -> Mainmenu());
    }

    public void New_Question(){
        Start_Timer();
        timertv.setTextColor(Color.WHITE);
        Reset_button_backgrounds();
        QuizBG.setBackgroundResource(R.drawable.bg_blueblack);
        questiontv.setText(Random_Date());
        Generate_Options();
        answered=false;
        Confirmed=false;
        button_confirm.setText("Confirm");
        CHeck_for_click();
        }

    public void CHeck_for_click(){
            button_confirm.setOnClickListener(v -> {
                vibrator.vibrate(70);
                if(Confirmed)
                {   if(timer!=null)
                    timer.cancel();
                    if(Correct)
                        New_Question();
                    else{
                        if(timeleft>0)
                            New_Question();
                        else
                            Score_Card();

                    }


                }
                else {
                    Confirmed = true;
                    if (answered) {
                        button_confirm.setText("Next");
                        Check_Answer();
                    } else
                        Toast.makeText(Quizactivity.this, "Select an answer", Toast.LENGTH_LONG).show();
                }});
        }
    public void clicked(View view) {
        if (answered) {

            Reset_button_backgrounds();
        }
        else{

            answered = true;
        }
        vibrator.vibrate(70);
        selectedid = radiogroup.getCheckedRadioButtonId();
        Highlight_Button();
    }
    public void Highlight_Button(){
        selected = findViewById(selectedid);
        selected.setTextColor(Color.parseColor("#00162B"));
        selected.setBackgroundResource(R.drawable.whitecurved);
    }
    public void Reset_button_backgrounds(){
            RadioButton[] opt={opt1,opt2,opt3,opt4};
        for(int i=0;i<4;i++) {
            opt[i].setTextColor(Color.parseColor("#FFFFFF"));
            opt[i].setBackgroundResource(R.drawable.negativebg);
        }
}
    @SuppressLint("SetTextI18n")
    public void Start_Timer(){
        timer = new CountDownTimer(timeleft,1000) {
            @Override
            public void onTick(long l) {
                timeleft=l;
                updatetimertv();
            }
            @Override
            public void onFinish() {
                questiontv.setTextColor(Color.RED);
                questiontv.setText("TIME UP !");
                button_confirm.setText("View Score");
                button_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Score_Card();
                    }
                });

            }
        }.start();
    }
    public void updatetimertv(){
        int sec = (int) timeleft/1000;
        String Timer_text=String.format(Locale.getDefault(),"00:%02d",sec);
        timertv.setText(Timer_text);
        if(timeleft<10000)
            timertv.setTextColor(Color.RED);

    }
    @SuppressLint("SetTextI18n")
    public void Check_Answer(){
        answered=true;
        timer.cancel();


        if(selectedid==crctid)
        {
            Correct=true;
            long[] pattern={0,180,100,180};
            vibrator.vibrate(pattern,-1);
            correctans++;
            score+=2;
            scoretv.setText("Score:"+ score);
            If_Correct();
        }
        else {
            Correct=false;
            vibrator.vibrate(300);
            score--;
            scoretv.setText("Score:" + score);
            wrongans++;
            timeleft = 0;
            updatetimertv();
            If_Wrong();

        }
    }
    public void If_Correct(){
        answered=true;
        crctbtn.setBackgroundResource(R.drawable.btn_greencurved);
        crctbtn.setTextColor(Color.WHITE);
        QuizBG.setBackgroundResource(R.drawable.greenbg);
        if((timeleft=60000+(correctans*5000)-wrongans*10000)>60000){
            timeleft=60000;
            correctans--;
        }
    }
    public void If_Wrong(){
        answered=true;
        QuizBG.setBackgroundResource(R.drawable.redbg);
        selected.setBackgroundResource(R.drawable.btn_redcurved);
        selected.setTextColor(Color.WHITE);
        crctbtn.setBackgroundResource(R.drawable.btn_greencurved);
        crctbtn.setTextColor(Color.WHITE);
        crctbtn.setTextColor(Color.parseColor("#FFFFFF"));
        timeleft=60000+(correctans*5000)-(wrongans*10000);
        if(timeleft<=0) {
            questiontv.setText("INSUFFICIENT TIME");
            questiontv.setTextColor(Color.RED);
            button_confirm.setText("View Score");
        }


    }
    public void Score_Card(){
        QuizBG.setBackgroundResource(R.drawable.redbg);
            Intent intent=new Intent(Quizactivity.this,Scorecard.class);
            intent.putExtra("Score",score);
            startActivity(intent);

    }
    public void Mainmenu(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    public static int randbw(int start,int end){
        return start+(int)(random()*(end-start));
    }
    public String Random_Date(){
        year = randbw(1900,2100);
        month=randbw(1,12);
        if (month % 2 == 0 && month!=2)
            date = randbw(1, 30);
        else
            date = randbw(1, 31);
        if((month==2)){
            if((year%4==0 || year%400==0)&&(year%100!=0))
            date=randbw(1,29);
            else
            date=randbw(1,28);
        }
        Question=date+" - "+month+" - "+year;
        return Question;
    }
    public String Answer(int d, int m, int y){
        int k,l;

        if (m>2) {
            k = m-2;
        } else {
            k = m+10;
            y--;
        }

        int c=(int)Math.floor((double)y/100);
        int D=y%100;
        l=d+D-(2*c)+(int)Math.floor((double)D/4)+(int)Math.floor((double)c/4)+(int)Math.floor((2.6*k)-0.2);
        if (l >= 0) {
            A = l % 7;
        }
        else {
            A = 7-((-l)%7);
        }
        return dayslist[A];

    }
    @SuppressLint("SetTextI18n")
    public void Generate_Options(){
        int i=0;

        RadioButton[] opt={opt1,opt2,opt3,opt4};
        correct=(int)(4*random());
        opt[correct].setText((Answer(date,month,year)));
        crctbtn = opt[correct];
        crctid =crctbtn.getId();
        ArrayList<Integer> numbers = new ArrayList<>();
        while (numbers.size() < 3) {
            int r1 = randbw(0,6);
            if ((!numbers.contains(r1)) && (r1!=A)) {
                if(i==correct){
                    i++;
                    continue;
                }
                numbers.add(r1);
                opt[i++].setText(dayslist[r1]);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            timer.cancel();}

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Score",score);
        outState.putLong("Timeleft",timeleft);
        outState.putBoolean("Answered",answered);
        outState.putInt("Correctans",correctans);
        outState.putInt("Wrongans",wrongans);
        outState.putInt("Correct",correct);
        outState.putBoolean("Confirmed",Confirmed);
        outState.putString("Question",Question);
        if(answered)
        outState.putInt("SelectedID",selectedid);
        if(Confirmed)
            outState.putBoolean("correctboolean",Correct);

    }
}