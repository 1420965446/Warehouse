package com.m5266.mymobilebutler.activity.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m5266.mymobilebutler.activity.always.Constant;
import com.m5266.mymobilebutler.activity.bean.BlacklistBean;
import com.m5266.mymobilebutler.activity.db.BlackOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by root on 2017/7/7.
 */

public class BlacklitsOpenHelperDao {

    private final BlackOpenHelper mHelper;

    public BlacklitsOpenHelperDao(Context context){
        mHelper = new BlackOpenHelper(context);
    }

    /**
     * 数据库的添加方法
     * @param phone 电话号码
     * @param mode 拦截模式
     * @return ture表示“添加成功”  flase 表示“添加失败”
     */
    public boolean add (String phone,String mode){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.COLUM_PHONE,phone);
        values.put(Constant.COLUM_MODE,mode);
        long id = db.insert(Constant.TABLE_NAME, null, values);
        db.close();
        if(id != -1) {
            return true;
        }
        return false;
    }

    /**
     * 数据库中删除的方法
     * @param phone 电话号码
     * @return ture表示“删除成功”  flase 表示“删除失败”
     */
    public boolean delete (String phone){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int delete = db.delete(Constant.TABLE_NAME, Constant.COLUM_PHONE + "=?", new String[]{phone});
        db.close();
        if(id != 0) {
            return true;
        }
        return false;
    }

    /**
     * 数据库中通过电话查询其拦截模式
     * @param phone 电话号码
     * @return 拦截模式
     */
    public String select(String phone){
        String mode = "";
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(Constant.TABLE_NAME, new String[]{Constant.COLUM_MODE},
                Constant.COLUM_PHONE + "=?",
                new String[]{phone}, null, null, null);
        while (cursor.moveToNext()){
             mode = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return mode;
    }

    /**
     * 数据库中查找全部数据的方法
     * @return
     */
    public List<BlacklistBean> getAllBalckPhone(){
        List<BlacklistBean> lists=new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(Constant.TABLE_NAME, new String[]{Constant.COLUM_PHONE,
                Constant.COLUM_MODE}, null, null, null, null, "_id desc");
        while (cursor.moveToNext()){
            BlacklistBean blackNumberBean=new BlacklistBean();
            blackNumberBean.phone=cursor.getString(0);
            blackNumberBean.mode=cursor.getString(1);
            lists.add(blackNumberBean);
        }
        cursor.close();
        db.close();
        return lists;
    }

    /**
     * 数据库中修改数据的方法
     * @param phone 电话号码
     * @param mode 拦截模式
     * @return ture表示“修改成功”  flase 表示“修改失败”
     */
    public boolean updata (String phone,String mode){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constant.COLUM_MODE,mode);
        int update = db.update(Constant.TABLE_NAME, values, Constant.COLUM_PHONE + "=?", new String[]{phone});
        db.close();
        if(update!=0) {
            return  true;
        }else {
            return false;
        }
    }

    public List<BlacklistBean> getPartBalckPhone(int  maxCount,int startIndex){
        List<BlacklistBean> lists=new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor=   db.rawQuery("select  phone,mode from blacknumberinfo  limit ? offset ? ",
                new String[]{String.valueOf(maxCount),String.valueOf(startIndex)});
        while (cursor.moveToNext()){
            BlacklistBean blackNumberBean=new BlacklistBean();
            blackNumberBean.phone=cursor.getString(0);
            blackNumberBean.mode=cursor.getString(1);
            lists.add(blackNumberBean);
        }
        cursor.close();
        db.close();
        return lists;
    }

}
