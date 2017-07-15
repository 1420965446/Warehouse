package com.m5266.mymobilebutler.activity.activity.guide;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.m5266.mymobilebutler.R;

public class FinishSetupActivity extends Activity {

    private Button finish_setting_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_setup);
        initview();
        initlistener();


    }

    private void initview() {
        finish_setting_btn = (Button)findViewById(R.id.finish_setting_btn);
    }

    private void initlistener() { finish_setting_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(finishs)
        }
    });

    }


}
