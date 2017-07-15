package com.m5266.mymobilebutler.activity.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by root on 2017/7/11.
 */

public class AppInfoBean {

    private Drawable icon;
    private String appname;
    private String packname;
    private boolean inRom;//系统APP
    private long apksize;
    private  boolean userApp;//用户APP

    public AppInfoBean() {

    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public boolean isInRom() {
        return inRom;
    }

    public void setInRom(boolean inRom) {
        this.inRom = inRom;
    }

    public long getApksize() {
        return apksize;
    }

    public void setApksize(long apksize) {
        this.apksize = apksize;
    }

    public boolean isUserApp() {
        return userApp;
    }

    public void setUserApp(boolean userApp) {
        this.userApp = userApp;
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "icon=" + icon +
                ", appname='" + appname + '\'' +
                ", packname='" + packname + '\'' +
                ", inRom=" + inRom +
                ", apksize=" + apksize +
                ", userApp=" + userApp +
                '}';
    }

}
