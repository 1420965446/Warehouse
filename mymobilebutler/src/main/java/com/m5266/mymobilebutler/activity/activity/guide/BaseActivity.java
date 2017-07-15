package com.m5266.mymobilebutler.activity.activity.guide;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.m5266.mymobilebutler.R;

/**
 * Created by root on 2017/7/3.
 */

public abstract class BaseActivity extends Activity {

    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(e1.getRawX()-e2.getRawX()<20) {
                    showPre();
                    overridePendingTransition(R.anim.pre_int,R.anim.pre_out);
                }
                if(e1.getRawX()-e2.getRawX()>20) {
                    showNext();
                    overridePendingTransition(R.anim.next_int,R.anim.next_out);
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    protected abstract void initData();
    protected void initListener(){};

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public  abstract  void initView() ;

    public abstract void showNext();

    public abstract void showPre();

    public  void next(View view){
        showNext();
        overridePendingTransition(R.anim.next_int,R.anim.next_out);

    }



    public void pre(View view){
        showPre();
        overridePendingTransition(R.anim.pre_int,R.anim.pre_out);
    }

}
