package com.m5266.mymobilebutler.activity.activity.attribution.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.custom.OffNoView;

public class OffNoActivity extends AppCompatActivity {

    private TextView offno_textview;
    private OffNoView offno_offnoview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_no);
        offno_textview = (TextView)findViewById(R.id.offno_textview);
        offno_offnoview = (OffNoView)findViewById(R.id.offno_offnoview);

        offno_offnoview.setOnSlideListener(new OffNoView.OnSlideListener() {
            @Override
            public void onSlide(boolean isOpen) {
                offno_textview.setText("自动升级已"+(isOpen?"开启":"关闭"));
            }
        });

    }
}
