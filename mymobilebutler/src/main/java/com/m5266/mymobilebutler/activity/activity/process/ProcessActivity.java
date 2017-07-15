package com.m5266.mymobilebutler.activity.activity.process;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.adapter.processAdapter;
import com.m5266.mymobilebutler.activity.bean.AppInfoBean;
import com.m5266.mymobilebutler.activity.utils.SPUtils;
import com.m5266.mymobilebutler.activity.utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

import static com.m5266.mymobilebutler.activity.always.Constant.SYSTEM;
import static com.m5266.mymobilebutler.activity.always.Constant.USER;

public class ProcessActivity extends Activity {

    private ListView provess_user_lv;
    private TextView process_ll_tv_run;
    private TextView process_ll_size;
    private TextView process_quanxuan;
    private TextView process_bianji;
    private LinearLayout process_dibu;

    private ArrayList<AppInfoBean> systemBeen;
    private ArrayList<AppInfoBean> userBeen;
    private int count;
    private long availRam;
    private long totalRam;
    private processAdapter adapter;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(adapter==null) {
                ArrayList<AppInfoBean> lists = (ArrayList<AppInfoBean>) msg.obj;
                adapter = new processAdapter(lists);
                provess_user_lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else {
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        process_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isbianji = SPUtils.getBoolean(ProcessActivity.this, "isbianji");
                if(isbianji) {
                    process_bianji.setText("取消");
                    process_quanxuan.setVisibility(View.VISIBLE);
                    process_dibu.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }else{
                    process_bianji.setText("编辑");
                    process_quanxuan.setVisibility(View.GONE);
                    process_dibu.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
                SPUtils.setBoolean(ProcessActivity.this,"isbianji",!isbianji);

            }
        });
    }


    private void initView() {
        process_ll_tv_run = (TextView)findViewById(R.id.process_ll_tv_run);
        process_ll_size = (TextView)findViewById(R.id.process_ll_size);
        provess_user_lv = (ListView)findViewById(R.id.provess_user_lv);

        process_quanxuan = (TextView)findViewById(R.id.process_quanxuan);
        process_bianji = (TextView)findViewById(R.id.process_bianji);

        process_dibu = (LinearLayout)findViewById(R.id.process_dibu);
    }

    private void initData() {
        count = SystemInfoUtils.getRunProcessCount(this);
        availRam = SystemInfoUtils.getAVailRam(this);
        totalRam = SystemInfoUtils.getTotalRam(this);

        process_quanxuan.setVisibility(View.GONE);
        process_dibu.setVisibility(View.GONE);
        SPUtils.setBoolean(ProcessActivity.this,"isbianji",true);

        process_ll_tv_run.setText("运行中的进程："+count+"个");
        process_ll_size.setText("总内存/剩余："+Formatter.formatFileSize(this,totalRam)+"/"+ Formatter.formatFileSize(this,availRam));



        fillData(USER);
    }

    private void fillData(final int type) {
        new Thread(){
            public void run(){
                List<AppInfoBean> infos = SystemInfoUtils.getRunningProcessInfos(ProcessActivity.this);
                userBeen = new ArrayList<AppInfoBean>();
                systemBeen = new ArrayList<AppInfoBean>();
                for (AppInfoBean info : infos) {
                    if(info.isUserApp()) {
                        //用户进程
                        userBeen.add(info);
                    }else{
                        //系统进程
                        systemBeen.add(info);
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

                    msg.obj = userBeen;
                }else{
                    adapter=null;
                    msg.obj = userBeen;
                }

                break;
            case SYSTEM:
                if(adapter==null) {
                    msg.obj = systemBeen;
                }else{
                    adapter=null;
                    msg.obj = systemBeen;
                }
                break;
        }

        handler.sendMessage(msg);
    }
}



























