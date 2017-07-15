package com.m5266.mymobilebutler.activity.activity.guide;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.m5266.mymobilebutler.activity.always.Constant;
import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.utils.SPUtils;
import com.m5266.mymobilebutler.activity.custom.itemRelativeLayout;

public class Setup4Activity extends BaseActivity {

    private itemRelativeLayout icr_protect;
    private TextView setup4_tv_state;
    private DevicePolicyManager dpm;
    private ComponentName componentName;

    @Override
    public void initView() {
        Log.i("newlog3","跑了吗？initView");
        setContentView(R.layout.activity_setup4);
        icr_protect = (itemRelativeLayout)findViewById(R.id.icr_protect);
        setup4_tv_state = (TextView)findViewById(R.id.setup4_tv_state);
    }
    @Override
    protected void initData() {
        Log.i("newlog3","跑了吗？initData");
        dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, DeviceReceiver.class);
        boolean protect = SPUtils.getBoolean(Setup4Activity.this, Constant.PROTECT);
        //修改状态
        //修改图片
        icr_protect.setimagerRes(protect);
        //保存值
        setup4_tv_state.setText(!protect?"防盗保护已近关闭":"防盗保护已近开启");

    }

    @Override
    protected void initListener() {
        Log.i("newlog3","跑了吗？initListener");
        icr_protect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("newlog3","跑了吗？");
                boolean protect = SPUtils.getBoolean(Setup4Activity.this, Constant.PROTECT);
                icr_protect.setimagerRes(!protect);
                SPUtils.setBoolean(Setup4Activity.this,Constant.PROTECT,!protect);
                setup4_tv_state.setText(protect?"防盗保护已近关闭":"防盗保护已近开启");
                if(protect) {
                    //反注册
                    dpm.removeActiveAdmin(componentName);

                }else {
                    //激活
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "我是文本");

                    startActivity(intent);

                }
            }
        });
    }

    @Override
    public void showNext() {
        Intent intent=new Intent(this, FinishSetupActivity.class);
        startActivity(intent);
    }

    @Override
    public void showPre() {
        Intent intent=new Intent(this, Setup3Activity.class);
        startActivity(intent);
    }
}
