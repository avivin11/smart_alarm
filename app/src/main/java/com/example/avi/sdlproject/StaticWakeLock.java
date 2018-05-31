package com.example.avi.sdlproject;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class StaticWakeLock {
    private static WakeLock wl = null;

    public StaticWakeLock() {
    }

    public static void lockOn(Context context) {
        PowerManager pm = (PowerManager)context.getSystemService("power");
        if(wl == null) {
            wl = pm.newWakeLock(268435482, "MATH_ALARM");
        }

        wl.acquire();
    }

    public static void lockOff(Context context) {
        try {
            if(wl != null) {
                wl.release();
            }
        } catch (Exception var2) {
            ;
        }

    }
}
