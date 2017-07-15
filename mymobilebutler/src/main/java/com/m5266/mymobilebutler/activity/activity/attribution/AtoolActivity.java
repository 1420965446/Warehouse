package com.m5266.mymobilebutler.activity.activity.attribution;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.m5266.mymobilebutler.R;

public class AtoolActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atool);
    }

    public void selectLocation(View view) {
        Intent intent=new Intent(this,ShowLocationActivity.class);
        startActivity(intent);
        finish();
    }

    public void custom(View view) {
        Intent intent=new Intent(this,ShowCustomActivity.class);
        startActivity(intent);
        finish();
    }
}
