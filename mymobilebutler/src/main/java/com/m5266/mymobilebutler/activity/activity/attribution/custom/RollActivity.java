package com.m5266.mymobilebutler.activity.activity.attribution.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.custom.RollView;

import java.util.ArrayList;

public class RollActivity extends AppCompatActivity {

    private RollView roll_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);
        roll_text = (RollView)findViewById(R.id.roll_text);

        initData();
    }

    private void initData() {
        roll_text.setFlipInterval(1500);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(String.format("这是第%d个文本",i));

        }
        int []image = {R.mipmap.aa,R.mipmap.af,R.mipmap.ac,R.mipmap.ad};
        roll_text.setData(image);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setDuration(500);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        TranslateAnimation trans = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 1,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0);
        animationSet.addAnimation(alpha);
        animationSet.addAnimation(trans);
        roll_text.setInAnimation(animationSet);
        roll_text.setOutAnimation(this,R.anim.roll_out);
        roll_text.startFlipping();
    }
}
