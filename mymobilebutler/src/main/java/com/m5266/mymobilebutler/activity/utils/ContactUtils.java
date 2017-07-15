package com.m5266.mymobilebutler.activity.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.m5266.mymobilebutler.activity.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2017/7/4.
 */

public class ContactUtils {

    public static List<ContactBean> getAllContact(Context context) {
        List<ContactBean> list = new ArrayList<ContactBean>();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri=Uri.parse("content://com.android.contacts/data");
        Cursor cursor = cr.query(uri, new String[]{"_id"}, null, null, null);
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            ContactBean contactBean=null;
            if(!TextUtils.isEmpty(id)) {
                Log.i("newlog","Id="+id);

                contactBean=new ContactBean();
                Cursor dataCursor = cr.query(dataUri, new String[]{"mimetype", "data1"}, "raw_contact_id=?", new String[]{id}, null);
                while (dataCursor.moveToNext()){

                    Log.i("newlog","DataCursor="+dataCursor);

                    String mimetype = dataCursor.getString(0);
                    String data1 = dataCursor.getString(1);
                    Log.i("newlog","mimetype="+dataCursor.getString(0));
                    Log.i("newlog","data1="+dataCursor.getString(1));
                    if(mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        contactBean.phone=data1;
                    }else  if(mimetype.equals("vnd.android.cursor.item/name")) {
                        contactBean.name=data1;
                    }
                }
            }
            list.add(contactBean);
        }
        Log.i("newlog","list="+list.toString());
        return list;
    }

}
