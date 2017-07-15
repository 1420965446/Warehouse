package com.m5266.mymobilebutler.activity.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.m5266.mymobilebutler.R;

/**
 * Created by root on 2017/7/13.
 */

public class SliderView extends ViewGroup {

    private ImageView slider_imager;
    public SliderView(Context context) {
        super(context);
    }

    public SliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        slider_imager = (ImageView) getChildAt(0);
        initListener();
    }

    float mStartX;
    float mStartY;
    int mSumDy = 0;
    private void initListener() {
        slider_imager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){

                    case MotionEvent.ACTION_DOWN:
                        mStartX = event.getX();
                        mStartY = event.getY();
                        //int width = slider_imager.getMeasuredWidth();
                        //int height = slider_imager.getMeasuredHeight();

                        slider_imager.setImageResource(R.mipmap.jd);
                        break;

                    case MotionEvent.ACTION_MOVE:
                      //  float newX = event.getX();
                        float newY = event.getY();

                        float dy = newY - mStartY;

                        mSumDy += (dy+0.5f);

                   //     mStartX = newX;
                     //   mStartY = newY;
                        mStartX = event.getX();
                        mStartY = event.getY();
                        slider_imager.scrollBy(0,(int)-dy);
                     //   requestLayout();//请求重新布局
                        break;

                    case MotionEvent.ACTION_UP:
                        slider_imager.setImageResource(R.mipmap.jc);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int measuredHeight = slider_imager.getMeasuredHeight();
        int measuredWidth = slider_imager.getMeasuredWidth();

//        if(mSumDy<0){
//            mSumDy = 0;
//        }
//        int i = getMeasuredHeight() - slider_imager.getMeasuredHeight();
//        if(mSumDy>i){
//            mSumDy = i;
//        }
        Log.i("qwer","mSumDy="+ mSumDy);

        slider_imager.layout(0,0+mSumDy,measuredWidth,measuredHeight+ mSumDy);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        if(wMode==MeasureSpec.AT_MOST) {
            wSize=getMaxWidth();
        }
        setMeasuredDimension(wSize,hSize);

    }

    private int getMaxWidth() {
        int maxWidth = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int measuredWidth = view.getMeasuredWidth();
            if(measuredWidth>maxWidth) {
                maxWidth=measuredWidth;
            }
        }
        return maxWidth;
    }
}
