package com.example.avi.sdlproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class tapsoonze extends AppCompatActivity {

    int count=10;
    TextView tt;
    private MediaPlayer mp;
    private  boolean isrun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(4718592);
        window.addFlags(2097280);
        setContentView(R.layout.activity_tapsnooze);
        tt=(TextView) findViewById(R.id.udpdatetoast);
        int ring=getIntent().getIntExtra("ring",0);
        mp= MediaPlayer.create(this, ring);
        mp.start();
        mp.setLooping(true);
        isrun=true;
    }
    public void incresecount(View view){

        String str;

        if (count>0){
            str="Tap "+(count)+" time more.";
            tt.setText(str);
            count--;
        }
        else{
            isrun=false;
            mp.stop();
            finish();
            System.exit(0);
        }
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
