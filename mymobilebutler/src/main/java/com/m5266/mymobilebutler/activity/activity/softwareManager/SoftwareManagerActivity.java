package com.m5266.mymobilebutler.activity.activity.softwareManager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.adapter.SoftwareManagerAdapter;
import com.m5266.mymobilebutler.activity.bean.AppInfoBean;
import com.m5266.mymobilebutler.activity.utils.APPInfoProviderUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.m5266.mymobilebutler.activity.always.Constant.SYSTEM;
import static com.m5266.mymobilebutler.activity.always.Constant.USER;

public class SoftwareManagerActivity extends Activity {

    private Button software_manager_user_bt;
    private Button software_manager_memory_bt;

    private TextView software_manager_memory_tv;
    private TextView software_manager_SDK_tv;
    private ListView software_manager_lv;



    //private List<AppInfoBean> appinfos;

    private List<AppInfoBean> appinfos; //区分集合
    private List<AppInfoBean> userAppInfos;//用户应用程序数据集合
    private List<AppInfoBean> systemAppInfos;//系统应用集合
    private SoftwareManagerAdapter adapter;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(adapter==null) {
                ArrayList<AppInfoBean> lists = (ArrayList<AppInfoBean>) msg.obj;
                adapter = new SoftwareManagerAdapter(lists);
                software_manager_lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else {
                adapter.notifyDataSetChanged();
            }
        }
    };
    private View popuwind;
    private PopupWindow popu;
    private PackageManager pm;
    private AppInfoBean  appInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_manager);
        initView();
        initData();
        initAdapter(SYSTEM);
        initListener();
    }

    private void initListener() {
        software_manager_memory_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tongyi","系统程序");
                userAndSystem(SYSTEM);

            }
        });
        software_manager_user_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tongyi","应用程序");
                userAndSystem(USER);
            }
        });
        software_manager_lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismisspopu();
                return false;
            }
        });

        software_manager_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismisspopu();

                appInfoBean = adapter.getItem(position) ;

                popu = new PopupWindow(popuwind,-2,-2);
                popu.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                int[] location = new int[2];
                view.getLocationInWindow(location);
                popu.showAtLocation(parent, Gravity.LEFT+Gravity.TOP,60,location[1]);
                AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
                aa.setDuration(500);
                ScaleAnimation sa = new ScaleAnimation(0.2f,1.0f,0.2f,1.0f,
                                                Animation.RELATIVE_TO_SELF,0,
                                                Animation.RELATIVE_TO_SELF,0.5f);
                sa.setDuration(500);
                AnimationSet set = new AnimationSet(false);
                set.addAnimation(aa);
                set.addAnimation(sa);
                popuwind.setAnimation(set);
            }
        });
    }

    @Override
    protected void onDestroy() {
        dismisspopu();
        super.onDestroy();
    }

    private void initView() {
        software_manager_memory_tv = (TextView)findViewById(R.id.software_manager_memory_tv);
        software_manager_SDK_tv = (TextView)findViewById(R.id.software_manager_SDK_tv);
        software_manager_lv = (ListView)findViewById(R.id.software_manager_lv);
        software_manager_user_bt = (Button)findViewById(R.id.software_manager_user_bt);
        software_manager_memory_bt = (Button)findViewById(R.id.software_manager_memory_bt);

        pm = getPackageManager();

        popuwind = View.inflate(SoftwareManagerActivity.this, R.layout.popuwindow_item, null);
    }

    private void initData() {
        File file = Environment.getDataDirectory();
        long space = file.getFreeSpace();//内存卡是否可用

        File directory = Environment.getExternalStorageDirectory();
        long freeSpace = directory.getFreeSpace();//SD卡是否可用

        software_manager_memory_tv.setText("内存可用："+ Formatter.formatFileSize(this,space));
        software_manager_SDK_tv.setText("SD卡可用："+ Formatter.formatFileSize(this,freeSpace));



    }
    private void dismisspopu(){
        if(popu!=null && popu.isShowing()) {
            popu.dismiss();
            popu=null;
        }
    }
    private void initAdapter(final int type) {

        new Thread(){
            public void run(){
                appinfos = APPInfoProviderUtils.getAppInfos(SoftwareManagerActivity.this);
                userAppInfos = new ArrayList<AppInfoBean>();
                systemAppInfos = new ArrayList<AppInfoBean>();
                for (AppInfoBean appInfo : appinfos){
                    if(appInfo.isUserApp()) {
                        //用户程序
                        userAppInfos.add(appInfo);
                    }else{
                        //系统程序
                        systemAppInfos.add(appInfo);
                    }
                }

                userAndSystem(type);

            }
        }.start();
    }

    private void userAndSystem(int type){
        Message msg = new Message();
        switch (type){
            case USER:
                if(adapter==null) {
                    msg.obj = userAppInfos;
                }else{
                    adapter=null;
                    msg.obj = userAppInfos;
                }

                break;
            case SYSTEM:
                if(adapter==null) {
                    msg.obj = systemAppInfos;
                }else{
                    adapter=null;
                    msg.obj = systemAppInfos;
                }
                break;
        }

        handler.sendMessage(msg);
    }



    public void Start(View view) {
        //Intent intent = new Intent();
       // pm.queryIntentActivities(intent, flags);
    }

    public void message(View view) {
        Intent intent = new Intent();

        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:"+appInfoBean.getPackname()));
        startActivity(intent);
        dismisspopu();
    }

    public void share(View view) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"推荐你使用一款软件，软件的名称为：xxx");
        startActivity(intent);
        dismisspopu();
    }

    public void uninstall(View view) {
        Log.i("asdf",""+Uri.parse("packge:"+appInfoBean.getPackname()));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setAction("android.intent.action.DELETE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:"+appInfoBean.getPackname()));
        startActivity(intent);
        dismisspopu();
    }
}
























