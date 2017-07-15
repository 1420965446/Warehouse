package com.m5266.mymobilebutler.activity.activity.guide;

import android.content.Intent;

import com.m5266.mymobilebutler.R;

public class Setup1Activity extends BaseActivity {


    @Override
    public void initView() {
        setContentView(R.layout.activity_setup1);
    }
    @Override
    protected void initData() {

    }
    @Override
    public void showNext() {
        Intent intent=new Intent(this, Setup2Activity.class);
        startActivity(intent);
    }

    @Override
    public void showPre() {

    }
}
