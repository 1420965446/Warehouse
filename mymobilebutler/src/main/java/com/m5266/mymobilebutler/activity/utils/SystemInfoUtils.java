package com.m5266.mymobilebutler.activity.utils;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.m5266.mymobilebutler.activity.bean.AppInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2017/7/11.
 */

public class SystemInfoUtils {

    private static List<ActivityManager.RunningAppProcessInfo> infos;

    public static int getRunProcessCount(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        infos = am.getRunningAppProcesses();
        return infos.size();
    }

    public static long getAVailRam(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        return outInfo.availMem;
    }

    public static long getTotalRam(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        return outInfo.totalMem;
    }

    public static List<AppInfoBean> getRunningProcessInfos(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();
        List<AppInfoBean> appInfoBeen = new ArrayList<AppInfoBean>();
        List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            String proceName = info.processName;
            long memsize = am.getProcessMemoryInfo(new int[]{info.pid})[0].getTotalPrivateDirty() * 1024;
            AppInfoBean infoBean = new AppInfoBean();
            try {
                PackageInfo packageInfo = pm.getPackageInfo(proceName, 0);
                String appname = packageInfo.applicationInfo.loadLabel(pm).toString();
                Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    //系统进程
                    infoBean.setUserApp(false);
                } else {
                    //用户进程
                    infoBean.setUserApp(true);
                }
                infoBean.setAppname(appname);
                infoBean.setIcon(icon);
                infoBean.setApksize(memsize);


            } catch (Exception e) {
                e.printStackTrace();
            }
            appInfoBeen.add(infoBean);

        }

        return appInfoBeen;
    }
}
