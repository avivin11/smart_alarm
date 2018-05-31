package com.example.avi.sdlproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class randomeqs extends AppCompatActivity {

    static int ans,n[]=new int[3];

    MediaPlayer mp;
    int count=0;
    private  boolean isrun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(4718592);
        window.addFlags(2097280);
        setContentView(R.layout.activity_randomeqs);
        isrun=true;
        setequation();
        int ring=getIntent().getIntExtra("ring",0);
        mp= MediaPlayer.create(this, ring);
        mp.start();
    }
    public void setequation(){
        if (count<3){
            TextView showtxt=(TextView) findViewById(R.id.Randomcalshow);
            Random ran=new Random();
            n[0]=ran.nextInt(15)+5;
            ans=n[0];
            n[1]=ran.nextInt(15)+5;
            n[2]=ran.nextInt(15)+5;
            char sign1=sign(ran.nextInt(3),1);
            char sign2=sign(ran.nextInt(2),2);
            String str= new String(""+n[0]+sign1+n[1]+sign2+n[2]);
            showtxt.setText(str);
            count++;
        }

        else{
            isrun=false;
            mp.stop();
            finish();
            System.exit(0);
        }

    }
    public static char sign(int a,int b)
    {
        switch(a)
        {
            case 0:
                ans+=n[b];
                return '+';
            case 1:
                ans-=n[b];
                return '-';
            default:
                ans*=n[b];
                return '*';
        }
    }
    public void submit_ans(View view) {
        EditText get_ans = (EditText) findViewById(R.id.randomeqans);
        int res = Integer.parseInt(get_ans.getText().toString());
        check(res);
    }
    public  void check(int res){

        EditText get_ans = (EditText) findViewById(R.id.randomeqans);
        if (res == ans) {
            Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
            setequation();
        }
        else {
            Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
        }
        get_ans.setText("");
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_UP)
            return true;
        else
            return false;
    }
    protected void onResume() {
        super.onResume();
        isrun= true;
    }
    public void onBackPressed() {
        if(!isrun) {
            super.onBackPressed();
        }
    }
    protected void onPause() {
        super.onPause();
        StaticWakeLock.lockOff(this);
    }
    protected void onDestroy() {
        if(!isrun) {
            super.onDestroy();
        }
    }
}
