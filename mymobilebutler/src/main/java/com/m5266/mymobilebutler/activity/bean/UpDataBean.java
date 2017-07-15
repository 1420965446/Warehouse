package com.m5266.mymobilebutler.activity.bean;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class UpDataBean {

    public  String version;
    public  String downloadurl;
    public  String desc;

    @Override
    public String toString() {
        return "UpDataBean{" +
                "version='" + version + '\'' +
                ", downloadurl='" + downloadurl + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
