package com.m5266.mymobilebutler.activity.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.m5266.mymobilebutler.activity.activity.attribution.custom.ClockActivity;
import com.m5266.mymobilebutler.activity.always.Constant;

/**
 * Created by root on 2017/7/10.
 */

public class CustomToast {

    private static WindowManager mWM;

    public static void showMyToast (final Context context, String location, final View view) {

        mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
        params.x = SPUtils.getInt(context, Constant.X);
        params.y = SPUtils.getInt(context, Constant.Y);
        params.gravity = Gravity.LEFT + Gravity.TOP;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //

        view.setOnTouchListener(new View.OnTouchListener() {

            private float startX;
            private float startY;
            private long mLastTime = System.currentTimeMillis();
            private long mCurTime = System.currentTimeMillis();
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://0 按下
                        startX = event.getRawX();
                        startY = event.getRawY();

                        mLastTime  = mCurTime;
                        mCurTime = System.currentTimeMillis();
                        if (mCurTime - mLastTime < 500) {
                            Log.i("qaqaqqa","我被双击了");


                            Intent Clockintent = new Intent(context, ClockActivity.class);

                            //开启任务栈
                            Clockintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //FLAG_ACTIVITY_SINGLE_TOP


                            context.startActivity(Clockintent);


                            return true;
                        }
                        break;

                    case MotionEvent.ACTION_UP://1 抬起

                        SPUtils.setInt(context, Constant.X, params.x);
                        SPUtils.setInt(context, Constant.Y, params.y);
                        break;

                    case MotionEvent.ACTION_MOVE://2 移动

                        float newX = event.getRawX();
                        float newY = event.getRawY();

                        int dx = (int) (newX - startX + 0.5f);
                        int dy = (int) (newY - startY + 0.5f);
                        params.x += dx;
                        params.y += dy;

                        if (params.x < 0) {
                            params.x = 0;
                        }
                        if (params.y < 0) {
                            params.y = 0;
                        }
                        if (params.x > mWM.getDefaultDisplay().getWidth() - view.getWidth()) {
                            params.x = mWM.getDefaultDisplay().getWidth() - view.getWidth();
                        }
                        if (params.y > mWM.getDefaultDisplay().getHeight() - view.getHeight()) {
                            params.y = mWM.getDefaultDisplay().getHeight() - view.getHeight();
                        }

                        mWM.updateViewLayout(view, params);
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                }
                return false;
            }
        });
        mWM.addView(view, params);

    }

    public static void removeMyToast(View view){
        if (mWM != null) {
            mWM.removeView(view);
            mWM = null;
        }
    }

}
