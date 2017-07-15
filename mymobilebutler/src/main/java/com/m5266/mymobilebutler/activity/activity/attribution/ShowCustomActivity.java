package com.m5266.mymobilebutler.activity.activity.attribution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.activity.attribution.custom.ClockActivity;
import com.m5266.mymobilebutler.activity.activity.attribution.custom.OffNoActivity;
import com.m5266.mymobilebutler.activity.activity.attribution.custom.RGBActivity;
import com.m5266.mymobilebutler.activity.activity.attribution.custom.RollActivity;
import com.m5266.mymobilebutler.activity.activity.attribution.custom.SideslipActivity;
import com.m5266.mymobilebutler.activity.activity.attribution.custom.SliderActivity;

public class ShowCustomActivity extends AppCompatActivity {


    private Button show_custom_button_hll;
    private Button show_custom_button_slider;
    private Button show_custom_button_roll;
    private Button show_custom_button_offno;
    private Button show_custom_button_sideslip;
    private Button show_custom_button_clock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_custom);

        initView();
        initListener();
    }

    private void initListener() {
        show_custom_button_hll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RGBintent = new Intent(ShowCustomActivity.this, RGBActivity.class);
                startActivity(RGBintent);
                finish();
            }
        });
        show_custom_button_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Sliderintent = new Intent(ShowCustomActivity.this, SliderActivity.class);
                startActivity(Sliderintent);
                finish();
            }
        });
        show_custom_button_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Rollintent = new Intent(ShowCustomActivity.this, RollActivity.class);
                startActivity(Rollintent);
                finish();
            }
        });
        show_custom_button_offno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OffNointent = new Intent(ShowCustomActivity.this, OffNoActivity.class);
                startActivity(OffNointent);
                finish();
            }
        });
        show_custom_button_sideslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Sideslipintent = new Intent(ShowCustomActivity.this, SideslipActivity.class);
                startActivity(Sideslipintent);
                finish();
            }
        });
        show_custom_button_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Clockintent = new Intent(ShowCustomActivity.this, ClockActivity.class);
                startActivity(Clockintent);
                finish();
            }
        });
    }

    private void initView() {
        show_custom_button_hll = (Button)findViewById(R.id.show_custom_button_hll);
        show_custom_button_slider = (Button)findViewById(R.id.show_custom_button_slider);
        show_custom_button_roll = (Button)findViewById(R.id.show_custom_button_roll);
        show_custom_button_offno = (Button)findViewById(R.id.show_custom_button_offno);
        show_custom_button_sideslip = (Button)findViewById(R.id.show_custom_button_sideslip);
        show_custom_button_clock = (Button)findViewById(R.id.show_custom_button_clock);
    }
}
