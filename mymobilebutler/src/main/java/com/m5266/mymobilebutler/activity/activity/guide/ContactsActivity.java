package com.m5266.mymobilebutler.activity.activity.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.m5266.mymobilebutler.activity.always.Constant;
import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.adapter.ContactAdapter;
import com.m5266.mymobilebutler.activity.bean.ContactBean;
import com.m5266.mymobilebutler.activity.utils.ContactUtils;

import java.util.ArrayList;
import java.util.List;



public class ContactsActivity extends Activity {
    private ListView contact_lv;
    private ContactAdapter adapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            ArrayList<ContactBean> list= (ArrayList<ContactBean>) msg.obj;
            adapter = new ContactAdapter(list);
            contact_lv.setAdapter(adapter);
            Log.i("newlog","list="+"setAda2");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        contact_lv = (ListView) findViewById(R.id.contact_lv);
    }

    private void initData() {
        new Thread(){
            public void run(){
                List<ContactBean> list = ContactUtils.getAllContact(ContactsActivity.this);
                ContactUtils.getAllContact(ContactsActivity.this);
                Message msg = Message.obtain();
                msg.obj = list;
                handler.sendMessage(msg);

            }
        }.start();
    }

    private void initListener() {

        contact_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = adapter.getItem(position).phone;
                Intent intent=new Intent();
                intent.putExtra(Constant.PHONE,phone);
                setResult(0,intent);
                finish();

            }
        });
    }
}
