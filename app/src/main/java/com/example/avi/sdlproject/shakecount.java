package com.example.avi.sdlproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


public class shakecount extends AppCompatActivity implements SensorEventListener {
    int count;
    private SensorManager sm;
    private Sensor mysensor;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private TextView textview;
    private Vibrator v;
    private MediaPlayer mp;
    private  boolean isrun;

    public void onSensorChanged(SensorEvent se) {
        sm.registerListener(this, mysensor, SensorManager.SENSOR_DELAY_NORMAL);
        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter
        if (mAccel > 7.5 &&mAccel<10) {
            count--;
            if(count==1)
                textview.setText("Shake phone "+String.valueOf(count)+" time");
            else if(count>1)
                textview.setText("Shake phone "+String.valueOf(count)+" times");
            else
            {

                isrun=false;
                textview.setText("Thanks");
                mp.stop();
                finish();
                System.exit(0);
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(4718592);
        window.addFlags(2097280);
        setContentView(R.layout.activity_shakecount);
        isrun=true;
        count=10;
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mysensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(mysensor==null)
        {
            Toast.makeText(this,"Sensor required are not available",Toast.LENGTH_SHORT).show();
        }
        sm.registerListener(this,mysensor, SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        textview=(TextView) findViewById(R.id.textView);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        int ring=getIntent().getIntExtra("ring",0);
        mp= MediaPlayer.create(this, ring);
        mp.start();
        mp.setLooping(true);
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
