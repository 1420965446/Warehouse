package com.m5266.mymobilebutler.activity.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by root on 2017/7/10.
 */

public class LocationDao {

    public static String getLocation (Context context,String phone){
        String location = "找不到该号码归属地！";              //context.getFilesDir().getAbsolutePath() + "/address.db"
        if(phone.matches("^1[35678]\\d{9}")) {//context.getFilesDir().getAbsolutePath()+"/address.db"
                                              //"/data/data/com.m5266.mymobilebutler/activity/files/address.db"
            SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getFilesDir().getAbsolutePath()+"/address.db", null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery("select location from data2 where id=(select outkey from data1 where  id=?)", new String[]{phone.substring(0, 7)});
            while (cursor.moveToNext()){
                location=cursor.getString(0);
            }
        }else switch (phone.length()) {
            case 3:
                if ("110".equals(phone)) {
                    location = "匪警";
                } else if ("120".equals(phone)) {
                    location = "急救";
                } else if ("119".equals(phone)) {
                    location = "火警";
                }
                break;

            case 4:
                location = "模拟器";
                break;
            case 5:
                location = "客服";
                break;
            case 7:
                //本地座机
                if (!phone.startsWith("0")) {
                    location = "本地号码";

                }
                break;
            case 8:
                //本地座机
                if (!phone.startsWith("0")) {
                    location = "本地号码";

                }

                break;
            default:
                if (phone.length() >= 10 && phone.startsWith("0")) {

                    SQLiteDatabase db = SQLiteDatabase.openDatabase(context.getFilesDir().getAbsolutePath()+"/address.db", null, SQLiteDatabase.OPEN_READONLY);
                    Cursor cursor = db.rawQuery("select location from data2 where area=?", new String[]{phone.substring(1, 3)});
                    if(cursor.moveToNext()) {
                        //如果能够进来,说明查询到了位置
                        location=cursor.getString(0);
                    }else{
                        cursor=db.rawQuery("select location from data2 where area=?", new String[]{phone.substring(1, 4)});
                        cursor.moveToNext();
                        location=cursor.getString(0);

                    }

                    location=location.substring(0,location.length()-2);

                }

                break;

        }

        return location;

    }

}
