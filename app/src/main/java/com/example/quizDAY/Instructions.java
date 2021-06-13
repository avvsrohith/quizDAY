package com.example.quizDAY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class Instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        Vibrator vibrator =(Vibrator)getSystemService(VIBRATOR_SERVICE);
        Button backbtn=findViewById(R.id.backbtnins);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(70);
                Main_menu();
            }
        });
    }

    public void Main_menu() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}