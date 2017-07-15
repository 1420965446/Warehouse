package com.m5266.mymobilebutler.activity.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.m5266.mymobilebutler.activity.custom.ClockView;
import com.m5266.mymobilebutler.activity.utils.CustomToast;

public class ClockService extends Service {

    private ClockView clockView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private long mLastTime = System.currentTimeMillis();
    private long mCurTime = System.currentTimeMillis();
    @Override
    public void onCreate() {
        clockView = new ClockView(this);
        //CustomToast customToast = new CustomToast();
        CustomToast.showMyToast(this,null, clockView);
    }

    @Override
    public void onDestroy() {
        if(clockView!=null) {
            CustomToast.removeMyToast(clockView);
        }

    }
}
