package com.example.avi.sdlproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class Alarmset extends AppCompatActivity {

    static int pcount=0;
    SQLite dbalarm;
    TimePicker tp;
    AlarmManager am;
    Calendar ACalender;
    Context context;
    int hour;
    int min;
    private  boolean isrun;
    String soundtype="Ringtone";
    String ringtone1="miringtone";
    int resID= R.raw.miringtone;
    String alarmtype="Walking 20 steps";
    Button sound_type,ringtone,snooze_alarm_by;
    Button s1;
    ListView myListSound,myListRingtone,myListSnooze,myListAlarm;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmset);
        s1=(Button) findViewById(R.id.Addalarm);
        tp=(TimePicker) findViewById(R.id.timePicker1);
        sound_type=(Button) findViewById(R.id.soundtype);
        ringtone=(Button) findViewById(R.id.ringtone);
        //snooze_time=(Button) findViewById(R.id.snooze);
        isrun=true;
        snooze_alarm_by=(Button) findViewById(R.id.activity1);
        am= (AlarmManager) getSystemService(ALARM_SERVICE);
        ACalender= Calendar.getInstance();
        dbalarm=new SQLite(getApplicationContext());
        int alarmdelete=getIntent().getIntExtra("alarmdelete",0);

        if(alarmdelete==1) {
            int id=getIntent().getIntExtra("Id",0);
            deletealarm(id);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void deletealarm(int id){
        String id1=""+id;
        dbalarm.deleteData(id1);
        final Intent i=new Intent(this,alarmrecv.class);
        PendingIntent pi=PendingIntent.getBroadcast(this.getApplicationContext(),id,i,PendingIntent.FLAG_UPDATE_CURRENT);

        am.cancel(pi);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setalarm(View view){

        final Intent i=new Intent(this,alarmrecv.class);
        i.putExtra("alarm",alarmtype);
        i.putExtra("ring",resID);
        Intent goback=new Intent(this,MainActivity.class);
        goback.putExtra("button",322);
        PendingIntent pi;
        ACalender.set(Calendar.HOUR_OF_DAY,tp.getHour());
        ACalender.set(Calendar.MINUTE,tp.getMinute());
        hour=tp.getHour();
        min=tp.getMinute();
        dbalarm.insertData(pcount,Integer.valueOf(hour),Integer.valueOf(min),soundtype,ringtone1,alarmtype);
        pi=PendingIntent.getBroadcast(this.getApplicationContext(),pcount,i,PendingIntent.FLAG_UPDATE_CURRENT);

        pcount++;
        am.set(AlarmManager.RTC_WAKEUP,ACalender.getTimeInMillis(),pi);


        /*if (hour>12){
            hour=hour-12;
        }

        Toast.makeText(getApplicationContext(),"Alarm set to "+hour+":"+min,Toast.LENGTH_LONG).show();
        */
        startActivity(goback);

    }
    public void Sound(View view)
    {
        myListSound = new ListView(this);
        String[] levels = {"Ringtone" , "Vibrate" , "Ring and Vibrate" } ;

        ArrayAdapter myAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_single_choice,levels) ;
        myListSound.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myListSound.setAdapter(myAdapter);

        myListSound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView cv = (CheckedTextView) view ;
                cv.setChecked(true);
                soundtype= String.valueOf(adapterView.getItemAtPosition(i)) ;
                Toast.makeText(getApplicationContext() , soundtype , Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(myListSound);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void ringtone(View view)
    {
        myListRingtone = new ListView(this);
        String[] levels = {"Apple" , "Calm" , "Fun" , "Hilarious", "Boom" , "Electronic" ,"Jurassicpa", "Popular" ,"Nostalgia" , "Sun", "Wakeup" } ;

        ArrayAdapter myAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_single_choice,levels) ;
        myListRingtone.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myListRingtone.setAdapter(myAdapter);

        myListRingtone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView cv = (CheckedTextView) view ;
                cv.setChecked(true);
                ringtone1= String.valueOf(adapterView.getItemAtPosition(i)).toLowerCase() ;
                resID=getResources().getIdentifier(ringtone1, "raw", getPackageName());
                Toast.makeText(getApplicationContext() , ringtone1 , Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(myListRingtone);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
  /*  public void time(View view)
    {
        myListSnooze = new ListView(this);
        String[] levels = {"Never" , "1 time" , "2 times" , "3 times" , "4 times" , "5 times" ,"Unlimited"} ;

        ArrayAdapter myAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_single_choice,levels) ;
        myListSnooze.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myListSnooze.setAdapter(myAdapter);

        myListSnooze.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView cv = (CheckedTextView) view ;
                cv.setChecked(true);
                String level = String.valueOf(adapterView.getItemAtPosition(i)) ;
                Toast.makeText(getApplicationContext() , level , Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(myListSnooze);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }*/
    public void alarmtype(View view)
    {
        myListAlarm = new ListView(this);
        String[] levels = {"Random Equation" , "Tapping device 20 times" ,"Shaking device 10 times" , "Walking 20 steps"} ;

        ArrayAdapter myAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_single_choice,levels) ;
        myListAlarm.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myListAlarm.setAdapter(myAdapter);

        myListAlarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView cv = (CheckedTextView) view ;
                cv.setChecked(true);
                alarmtype = String.valueOf(adapterView.getItemAtPosition(i)) ;
                Toast.makeText(getApplicationContext() , alarmtype , Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(myListAlarm);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}