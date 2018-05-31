package com.example.avi.sdlproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class alarmrecv extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String Alarm=intent.getStringExtra("alarm");
        int ring=intent.getIntExtra("ring",0);
        switch (Alarm){
            case "Random Equation":
                Intent i= new Intent(context, randomeqs.class);
                i.putExtra("ring",ring);
                context.sendBroadcast(i, null);
                StaticWakeLock.lockOn(context);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                break;
            case "Tapping device 10 times" :
                Intent l= new Intent(context, tapsoonze.class);
                l.putExtra("ring",ring);
                context.sendBroadcast(l, null);
                StaticWakeLock.lockOn(context);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(l);
                break;
            case "Shaking device 10 times":
                Intent j= new Intent(context, shakecount.class);
                j.putExtra("ring",ring);
                context.sendBroadcast(j, null);
                StaticWakeLock.lockOn(context);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);
                break;
            case "Walking 20 steps":
                Intent k= new Intent(context,  stepcount.class);
                k.putExtra("ring",ring);
                context.sendBroadcast(k, null);
                StaticWakeLock.lockOn(context);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(k);
                break;
        }
    }
}
