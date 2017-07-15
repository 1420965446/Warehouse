package com.m5266.mymobilebutler;

import android.test.AndroidTestCase;

import com.m5266.mymobilebutler.activity.db.dao.BlacklitsOpenHelperDao;

import java.util.Random;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class TestBlackNumberDao extends AndroidTestCase {

    public void testAdd(){
        BlacklitsOpenHelperDao dao=new BlacklitsOpenHelperDao(getContext());
        Random random=new Random();
        for(int i = 0; i < 200; i++) {
            dao.add("1862775"+i,String.valueOf(random.nextInt(3)));
        }
    }

    public  void testDelete(){
        BlacklitsOpenHelperDao dao=new BlacklitsOpenHelperDao(getContext());
        boolean delete = dao.delete("1862775");
        assertEquals(true,delete);
    }

    public  void testUpdate(){
        BlacklitsOpenHelperDao dao=new BlacklitsOpenHelperDao(getContext());
        boolean update = dao.updata("18627752172", "1");
        assertEquals(true,update);
    }

    public  void testFind(){
        BlacklitsOpenHelperDao dao=new BlacklitsOpenHelperDao(getContext());
        String mode = dao.select("18627758");
        assertEquals("1",mode);
    }
}
