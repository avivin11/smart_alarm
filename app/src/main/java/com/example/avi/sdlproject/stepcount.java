package com.example.avi.sdlproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


public class stepcount extends AppCompatActivity implements SensorEventListener {
    private int count;
    private TextView stepscount;
    private SensorManager sm;
    private Sensor mysensor;
    private Vibrator V;
    private  boolean isrun;

    private final static String TAG = "StepDetector";
    private float mLimit = 10;
    private float mLastValues[] = new float[3 * 2];
    private float mScale[] = new float[2];
    private float mYOffset;

    private float mLastDirections[] = new float[3 * 2];
    private float mLastExtremes[][] = {new float[3 * 2], new float[3 * 2]};
    private float mLastDiff[] = new float[3 * 2];
    private int mLastMatch = -1;

    private MediaPlayer mp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(4718592);
        window.addFlags(2097280);
        setContentView(R.layout.activity_stepcount);
        isrun=true;
        int h = 480; // TODO: remove this constant
        mYOffset = h * 0.5f;
        mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
        count=0;
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mysensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(mysensor==null)
        {
            Toast.makeText(this,"Sensor required are not available",Toast.LENGTH_SHORT).show();
        }
        sm.registerListener(this,mysensor, SensorManager.SENSOR_DELAY_NORMAL);
        stepscount=(TextView) findViewById(R.id.Stepscount);
        setSensitivity((float)1.5);
        V = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setVolumeControlStream(AudioManager.STREAM_ALARM);
        int ring=getIntent().getIntExtra("ring",0);
        mp= MediaPlayer.create(this, ring);
        mp.start();

        mp.setLooping(true);
    }

    public void setSensitivity(float sensitivity) {
        mLimit = sensitivity; // 1.97  2.96  4.44  6.66  10.00  15.00  22.50  33.75  50.62
    }

    public void onSensorChanged(SensorEvent event) {
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        Sensor sensor = event.sensor;
        synchronized (this) {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float vSum = 0;
                for (int i = 0; i < 3; i++) {
                    final float v = mYOffset + event.values[i] * mScale[1];
                    vSum += v;
                }
                int k = 0;
                float v = vSum / 3;
                float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
                if (direction == -mLastDirections[k]) {
                    int extType = (direction > 0 ? 0 : 1);
                    mLastExtremes[extType][k] = mLastValues[k];
                    float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);
                    if (diff > mLimit && diff<8.88) {
                        boolean isAlmostAsLargeAsPrevious = diff > (2*mLastDiff[k] / 3);
                        boolean isPreviousLargeEnough = mLastDiff[k] > (2*diff / 3);
                        boolean isNotContra = (mLastMatch != 1 - extType);
                        if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                            if(count<20) {
                                count++;
                                Log.i(TAG, "step");
                                mLastMatch = extType;
                                stepscount.setText(String.valueOf(count));
                            }
                            else{
                                stepscount.setText("Thanks");
                                //V.vibrate(500);
                                isrun=false;
                                mp.stop();
                                finish();
                                System.exit(0);
                            }
                        } else {
                            mLastMatch = -1;
                        }
                    }
                    mLastDiff[k] = diff;
                }
                mLastDirections[k] = direction;
                mLastValues[k] = v;
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_UP)
            return true;
        else
            return false;
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
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