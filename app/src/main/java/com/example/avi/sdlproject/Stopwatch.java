package com.example.avi.sdlproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Stopwatch extends AppCompatActivity {

    private int seconds;
    private int timer2;
    private  boolean isrun;
    public MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(4718592);
        window.addFlags(2097280);
        setContentView(R.layout.activity_stopwatch);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("seconds",seconds);
        outState.putBoolean("isrun",isrun);

    }


    public void gettime(View view){
        EditText timer1=(EditText) findViewById(R.id.timer);
        if(timer1.getText()==null || timer1.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Enter the time first",Toast.LENGTH_SHORT).show();
            return;
        }

        timer2=Integer.parseInt(timer1.getText().toString());

        isrun=true;
        runtimer();

    }

    public void playstp(View view){
        isrun=true;

    }
    public void pausestp(View view){

        isrun=false;
    }
    public void ret(View view){
        isrun=false;
        seconds=0;

    }

    public void runtimer(){
        seconds=timer2*60;

        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView txt=(TextView) findViewById(R.id.showtimer);
                int hrs=seconds/3600;
                int min=(seconds%3600)/60;
                int secs=seconds%60;

                String str=String.format("%d:%02d:%02d",hrs,min,secs);
                txt.setText(str);
                if(isrun){

                    seconds--;

                    if (seconds==0)
                    {
                        isrun=false;
                        Context cont = null;
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.boom);
                        mp.start();
                        return;
                    }
                }
                else{
                    if (seconds==0) {
                        return;
                    }
                }

                handler.postDelayed(this,1000);

            }
        });
    }

}
