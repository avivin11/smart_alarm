package com.example.avi.sdlproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Date d;
    ScrollView scrollV;
    LinearLayout li;
    SQLite dbalarm;



    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollV=(ScrollView) findViewById(R.id.scroll);
        d=new Date();
        tv=(TextView) findViewById(R.id.date1);
        li=(LinearLayout) findViewById(R.id.layout);
        dbalarm=new SQLite(this);
        showtimer();

        int val=getIntent().getIntExtra("button",0);
        if(val==322)
            showAlarm();
        else{
            showAlarm();
        }


    }
    public void showAlarm(){

        Cursor res=dbalarm.getALLData();


        while(res.moveToNext()) {
            Button btn = new Button(this);
            btn.setGravity(Gravity.CENTER);
            String am_pm="am";
            final int id=res.getInt(0);
            int hour=res.getInt(1);
            String min=""+res.getInt(2);
            if(hour>12){
                am_pm="pm";
                hour=hour-12;
            }
            if(res.getInt(2)<10){
                min="0"+res.getInt(2);
            }
            btn.setText(""+hour+":"+min+" "+am_pm);
            btn.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            btn.setBackgroundResource(R.color.colorPrimary);
            btn.setHeight(300);
            btn.setTextSize(30);
            btn.setTextColor(Color.rgb(255, 255, 255));


            li.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Alarmset.class);
                    startActivity(intent);
                }
            });
            btn.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View view) {

                    Intent i= new Intent(MainActivity.this, Alarmset.class);
                    i.putExtra("alarmdelete",1);
                    i.putExtra("Id",id);
                    Toast.makeText(getApplicationContext(),"ALarm deleted",Toast.LENGTH_LONG).show();
                    startActivity(i);
                    return true;
                }
            });
       }
    }
    public void stopwatch(View view){
        Intent intent=new Intent(this,Stopwatch.class);
        startActivity(intent);
    }
    public void  nextpage(View view) {
        Intent intent=new Intent(this,Alarmset.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showtimer(){
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        String thisDate = currentDate.format(d);
        tv.setText(thisDate);
    }



    }

