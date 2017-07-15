package com.m5266.mymobilebutler.activity.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.m5266.mymobilebutler.R;

import java.util.Calendar;

/**
 * Created by root on 2017/7/15.
 */

public class ClockView extends View {

    private Bitmap dial;
    private Bitmap hour;
    private Bitmap minu;
    private Bitmap seco;
    private Bitmap cent;
    private Paint paint;
    private int dialH;
    private int dialW;
    private Calendar calendar;
    private boolean swing = false;
    private Thread thread;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBitmap();
        initData();
    }


    private void initData() {
        dialW = dial.getWidth();
        dialH = dial.getHeight();
        paint = new Paint();
        calendar = Calendar.getInstance();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (swing){
                    SystemClock.sleep(1000);
                    postInvalidate();//在子线程中重绘：postInvalidate()
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        swing = true;
        thread.start();
    }

    //onDetachedFromWindow 控件跟窗口接触绑定了
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        swing = false;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);
        if (modeW == MeasureSpec.AT_MOST) {
            sizeW = dialW;
        }
        if (modeH == MeasureSpec.AT_MOST) {
            sizeH = dialH;
        }
        setMeasuredDimension(sizeW, sizeH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hourT = calendar.get(Calendar.HOUR);
        int minuteT = calendar.get(Calendar.MINUTE);
        int secondT = calendar.get(Calendar.SECOND);

        int measuW = getMeasuredWidth();
        int measuH = getMeasuredHeight();

        if(measuW<dialW||measuH<dialH) {
            float xbl = measuW * 1f / dialW;
            float ybl = measuH * 1f / dialH;
            float min = Math.min(xbl, ybl);
            canvas.scale(min,min,measuW/2,measuH/2);
        }
        canvas.drawBitmap(dial, (measuW / 2) - (dialW / 2), (measuH / 2) - (dialH / 2), paint);

        canvas.save();
        float hourAnim = hourT / 12f * 360 + minuteT / 60f * 30;
        canvas.rotate(hourAnim, measuW / 2, measuH / 2);
        canvas.drawBitmap(hour, (measuW / 2) - (hour.getWidth() / 2), (measuH / 2) - (hour.getHeight() / 2) - (hour.getWidth() / 2), paint);
        canvas.restore();

        canvas.save();
        float minuteAnim = minuteT / 60f * 360;
        canvas.rotate(minuteAnim, measuW / 2, measuH / 2);
        canvas.drawBitmap(minu, (measuW / 2) - (minu.getWidth() / 2), (measuH / 2) - (minu.getHeight() / 2) - (minu.getWidth() / 2), paint);
        canvas.restore();

        canvas.save();
        float secondAnim = secondT / 60f * 360;
        canvas.rotate(secondAnim, measuW / 2, measuH / 2);
        canvas.drawBitmap(seco, (measuW / 2) - (seco.getWidth() / 2), (measuH / 2) - (seco.getHeight() / 2) - (seco.getWidth() / 2), paint);
        canvas.restore();

        canvas.drawBitmap(cent, (measuW / 2) - (cent.getWidth() / 2), (measuH / 2) - (cent.getHeight() / 2), paint);
    }

    private void initBitmap() {
        dial = BitmapFactory.decodeResource(getResources(), R.mipmap.clock_dial);
        hour = BitmapFactory.decodeResource(getResources(), R.mipmap.hour_hand);
        minu = BitmapFactory.decodeResource(getResources(), R.mipmap.minute_hand);
        seco = BitmapFactory.decodeResource(getResources(), R.mipmap.sec_hand);
        cent = BitmapFactory.decodeResource(getResources(), R.mipmap.hand_center);
    }
}
