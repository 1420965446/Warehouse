package com.m5266.mymobilebutler.activity.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by root on 2017/7/8.
 */

public class ServiceStateUtils {

    public static boolean getServiceState(Activity activity, String ServiceName) {
        boolean flag = false;
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo service : services) {
            String className = service.service.getClassName();
            if (ServiceName.equals(className)) {
                return true;
            }
        }
        return false;
    }

}
