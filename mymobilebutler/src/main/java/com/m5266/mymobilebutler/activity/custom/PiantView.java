package com.m5266.mymobilebutler.activity.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by root on 2017/7/13.
 */

public class PiantView extends ViewGroup {

    public PiantView(Context context) {
        super(context);
    }

    public PiantView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int sum = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();

            child.layout(0,0+sum,measuredWidth,measuredHeight+sum);

            sum+=measuredHeight;
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        if(wMode==MeasureSpec.AT_MOST) {
            wSize=getMaxWidth();
        }if(hMode==MeasureSpec.AT_MOST) {
            hSize=getSumHeight();
        }
        Log.i("asd"," ---- "+wSize+"  "+hSize);
        setMeasuredDimension(wSize,hSize);
    }

    private int getSumHeight() {
        int sumHeight = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int measuredHeight = view.getMeasuredHeight();

            sumHeight+=measuredHeight;

        }
        return sumHeight;
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
