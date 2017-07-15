package com.m5266.mymobilebutler.activity.activity.guide;

import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.m5266.mymobilebutler.activity.always.Constant;
import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.utils.SPUtils;

public class Setup2Activity extends BaseActivity {

    private TelephonyManager tm;
    private ImageView setup2_iv_lock;
    @Override
    protected void initData() {
        String sim = SPUtils.getString(this, Constant.SIM);
        setup2_iv_lock.setImageResource(TextUtils.isEmpty(sim)?R.drawable.unlock:R.drawable.lock);
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_setup2);
        setup2_iv_lock = (ImageView)findViewById(R.id.setup2_iv_lock);


    }

    @Override
    public void showNext() {
        String sim = SPUtils.getString(this, Constant.SIM);
        if(TextUtils.isEmpty(sim)) {
            Toast.makeText(Setup2Activity.this, "需要绑定sim卡", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent(this, Setup3Activity.class);
        startActivity(intent);
    }

    @Override
    public void showPre() {
        Intent intent=new Intent(this, Setup1Activity.class);
        startActivity(intent);
    }

    public void bindSim(View view) {
        String sim = SPUtils.getString(this, Constant.SIM);
        /**
         * 判断是否邦定过Sim.
         */
        Log.i("newlog","1");
        if(TextUtils.isEmpty(sim)) {//没有绑定

            String simNumber = tm.getSimSerialNumber();
            SPUtils.setString(this,Constant.SIM,simNumber);
            setup2_iv_lock.setImageResource(R.drawable.lock);

        }else {//绑定过
            SPUtils.setString(this,Constant.SIM,"");
            setup2_iv_lock.setImageResource(R.drawable.unlock);
        }
    }
}
