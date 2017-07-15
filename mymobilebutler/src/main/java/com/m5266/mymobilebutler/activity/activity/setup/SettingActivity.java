package com.m5266.mymobilebutler.activity.activity.setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.always.Constant;
import com.m5266.mymobilebutler.activity.custom.itemRelativeLayout;
import com.m5266.mymobilebutler.activity.service.CallSmsService;
import com.m5266.mymobilebutler.activity.service.ClockService;
import com.m5266.mymobilebutler.activity.service.LocationShowService;
import com.m5266.mymobilebutler.activity.utils.SPUtils;
import com.m5266.mymobilebutler.activity.utils.ServiceStateUtils;

public class SettingActivity extends Activity {

    private itemRelativeLayout set_rl;//自动更新
    private itemRelativeLayout set_item_rl_black;//黑名单拦截
    private itemRelativeLayout set_item_rl_locationshow;//号码归属地显示
    private itemRelativeLayout set_item_rl_rocket;//卫士小火箭显示
    private itemRelativeLayout set_item_rl_clock;//时钟小助手显示
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
        initListener();

    }

    /**
     * 该方法用于初始化控件
     */
    private void initView() {
        set_rl = (itemRelativeLayout)findViewById(R.id.set_item_rl);
        set_item_rl_black = (itemRelativeLayout)findViewById(R.id.set_item_rl_black);
        set_item_rl_locationshow = (itemRelativeLayout)findViewById(R.id.set_item_rl_locationshow);
        set_item_rl_rocket = (itemRelativeLayout)findViewById(R.id.set_item_rl_rocket);
        set_item_rl_clock = (itemRelativeLayout)findViewById(R.id.set_item_rl_clock);
    }

    private void initData() {
        boolean updata = SPUtils.getBoolean(this, Constant.SP_UOTO_UPDATA);
        set_rl.setimagerRes(updata);
    }

    private void initListener() {
        set_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean updata = SPUtils.getBoolean(SettingActivity.this, Constant.SP_UOTO_UPDATA);
                set_rl.setimagerRes(!updata);
                SPUtils.setBoolean(SettingActivity.this,Constant.SP_UOTO_UPDATA,!updata);
            }
        });

        set_item_rl_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state = ServiceStateUtils.getServiceState(SettingActivity.this, "com.m5266.mymobilebutler.activity.service.CallSmsService");
                if(state) {
                    Intent intent = new Intent(SettingActivity.this, CallSmsService.class);
                    stopService(intent);
                }else{
                    Intent intent = new Intent(SettingActivity.this, CallSmsService.class);
                    startService(intent);
                }
                set_item_rl_black.setimagerRes(!state);
            }
        });

        set_item_rl_locationshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state = ServiceStateUtils.getServiceState(SettingActivity.this, "com.m5266.mymobilebutler.activity.service.LocationShowService");
                if(!state) {
                    Intent intent = new Intent(SettingActivity.this, LocationShowService.class);
                    startService(intent);
                }else{
                    Intent intent = new Intent(SettingActivity.this,LocationShowService.class);
                    stopService(intent);
                }
                set_item_rl_locationshow.setimagerRes(!state);
            }
        });

        set_item_rl_rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        set_item_rl_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state = ServiceStateUtils.getServiceState(SettingActivity.this, "com.m5266.mymobilebutler.activity.service.ClockService");
                Log.e("TAG1", ""+state);
                if(!state) {
                    Intent intent = new Intent(SettingActivity.this, ClockService.class);
                    startService(intent);
                    Log.e("TAG1", "if");
                }else{
                    Log.e("TAG1", "else");
                    Intent intent = new Intent(SettingActivity.this,ClockService.class);
                    stopService(intent);
                }
                set_item_rl_clock.setimagerRes(!state);
            }
        });
    }

    @Override
    protected void onResume() {
        boolean state = ServiceStateUtils.getServiceState(SettingActivity.this, "com.m5266.mymobilebutler.activity.service.CallSmsService");
        set_item_rl_black.setimagerRes(state);

        boolean state2 = ServiceStateUtils.getServiceState(SettingActivity.this,"com.m5266.mymobilebutler.activity.service.LocationShowService");
        set_item_rl_locationshow.setimagerRes(state2);
        super.onResume();
    }
}
