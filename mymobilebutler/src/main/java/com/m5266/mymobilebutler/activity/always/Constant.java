package com.m5266.mymobilebutler.activity.always;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class Constant implements BaseColumns {
    public static final String SP_NAME = "config";
    public static final String SP_UOTO_UPDATA = "update";
    public static final String SAFEPASSWORD = "safepassword";
    public static final String FINISHSETUP = "finishSetup";
    public static final String SIM = "sim";
    public static final String PHONE = "phone";
    public static final String SAFEPHONE = "safephone";
    public static final String PROTECT = "protect";
    public static final String  X = "x";
    public static final String Y = "y";

    public static final int USER = 0;
    public static final int SYSTEM = 1;

    /**
     * 数据库相关
     */
    public static final String DB_NAME = "blcknumber.db";//数据库的名称
    public static final int DB_EDIT = 1;//数据库的版本
    public static  final String TABLE_NAME="blacknumberinfo";//数据库的表名
    public  static  final String COLUM_PHONE="phone";//数据库的某个表中的列
    public  static  final String COLUM_MODE="mode";


    public static class URL {
        public static final String BASE_URL = "http://192.168.14.130:8080/";
        public static final String UPDATE = BASE_URL + "info.json";
    }
}
