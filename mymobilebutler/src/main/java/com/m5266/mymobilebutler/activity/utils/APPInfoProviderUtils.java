package com.m5266.mymobilebutler.activity.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.m5266.mymobilebutler.activity.bean.AppInfoBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取手机中所有的应用程序信息 /data/app/xxx/system/app/xxx
 */
public class APPInfoProviderUtils {

    private static AppInfoBean bean;

    public static List<AppInfoBean> getAppInfos (Context context){
        //获取安装包信息
        PackageManager pm = context.getPackageManager();//获取所有安装在设备上的信息
        List<PackageInfo> packageInfos =pm.getInstalledPackages(0);//参数如果是可选的，一般填0
        List<AppInfoBean> lists = new ArrayList<AppInfoBean>();
        for (PackageInfo packageInfo : packageInfos) {
            String packageName = packageInfo.packageName;
            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
            String path = packageInfo.applicationInfo.sourceDir;

            File file = new File(path);
            long size = file.length();
            bean = new AppInfoBean();

            bean.setAppname(appName);
            bean.setIcon(icon);
            bean.setPackname(packageName);
            bean.setApksize(size);

            int flags = packageInfo.applicationInfo.flags;
            //与系统程序作比较，如果是0，表明是应用程序，1则是系统程序
            if((flags & ApplicationInfo.FLAG_SYSTEM)==0) {
                //用户程序
                bean.setUserApp(true);
            }else{
                //系统程序
                bean.setUserApp(false);
            }
            if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE)==0) {
                //手机系统内部
                bean.setInRom(true);
            }else{
                //SD卡
                bean.setInRom(false);
            }
            lists.add(bean);
        }



        return lists;
    }
}
