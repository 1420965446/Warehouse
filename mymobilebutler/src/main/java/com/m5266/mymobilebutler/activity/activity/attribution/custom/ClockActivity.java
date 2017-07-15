package com.m5266.mymobilebutler.activity.activity.attribution.custom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.service.ClockService;

import java.util.Calendar;

public class ClockActivity extends AppCompatActivity {

    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        calendar = Calendar.getInstance();calendar.setTimeInMillis(System.currentTimeMillis());
        int hourT = calendar.get(Calendar.HOUR);
        int minuteT = calendar.get(Calendar.MINUTE);
        int secondT = calendar.get(Calendar.SECOND);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(ClockActivity.this,ClockService.class);
        stopService(intent);
        super.onResume();
    }
}
