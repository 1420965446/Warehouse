package com.m5266.mymobilebutler.activity.activity.attribution;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.db.dao.LocationDao;

public class ShowLocationActivity extends Activity {

    private EditText showlocation_et_phone;
    private TextView showlocation_tv_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);
        initView();
    }

    private void initView() {
        showlocation_et_phone = (EditText)findViewById(R.id.showlocation_et_phone);
        showlocation_tv_location = (TextView)findViewById(R.id.showlocation_tv_location);
    }

    public void selectLocation(View view) {
        String phone = showlocation_et_phone.getText().toString();
        if(TextUtils.isEmpty(phone)) {
            showlocation_et_phone.setError("请输入号码");
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            showlocation_et_phone.startAnimation(shake);
            return;
        }
        String location = LocationDao.getLocation(ShowLocationActivity.this,phone);
        showlocation_tv_location.setText("号码归属地:"+location);
        
    }
}
