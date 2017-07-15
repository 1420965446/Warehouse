package com.m5266.mymobilebutler.activity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m5266.mymobilebutler.activity.always.Constant;

/**
 * Created by root on 2017/7/7.
 */

public class BlackOpenHelper extends SQLiteOpenHelper {


    public BlackOpenHelper(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_EDIT);
    }

    /**
     * mode 拦截模式 0 电话拦截 1 短信拦截 2 全部拦截
     * 初始化数据库的表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ Constant.TABLE_NAME+"( "+Constant._ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+Constant.COLUM_PHONE+" VARCHAR(20), "+Constant.COLUM_MODE+" VARCHAR(2)) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
