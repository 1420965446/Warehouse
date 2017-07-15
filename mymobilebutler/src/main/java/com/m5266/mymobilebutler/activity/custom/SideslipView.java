package com.m5266.mymobilebutler.activity.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by root on 2017/7/14.
 */

public class SideslipView extends ViewGroup {

    private int contH;
    private int contW;
    private int menuH;
    private int menuW;
    private View cont;
    private View menu;
    private float staX;
    private float staY;
    private Scroller scroller;
    private float itcX;
    private float itcY;

    public SideslipView(Context context) {
        super(context);
    }

    public SideslipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menu.layout(-menuW, 0, 0, menuH);
        cont.layout(0, 0, contW, contH);
    }

    int mScrollX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                staX = event.getX();
                staY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float newX = event.getX();
                float newY = event.getY();
                float dx = newX - staX;
                float dy = newY - staY;
                scrollBy((int) -dx, 0);
                mScrollX = getScrollX();
                if (mScrollX > 0) {
                    scrollTo(0, 0);
                }
                if (mScrollX < -menuW) {
                    scrollTo(-menuW, 0);
                }

                staX = event.getX();
                staY = event.getY();

                break;
            case MotionEvent.ACTION_UP:
                mScrollX = getScrollX();
                if (mScrollX < -menuW / 2) {
                    scroller.startScroll(mScrollX, 0, -menuW - mScrollX, 0, 500);
                } else {
                    scroller.startScroll(mScrollX, 0, -mScrollX, 0, 500);
                }
                invalidate();
                break;
        }
        return true;
    }
    private int sumitcX ;
    private int sumitcY ;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                itcX = ev.getX();
                itcY = ev.getY();
                staX = ev.getX();
                staY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();

                float dx = moveX - itcX;
                float dy = moveY - itcY;

                sumitcX += dx;
                sumitcY += dy;

//                ViewConfiguration.getTouchSlop();
                if(Math.abs(sumitcX)>10 && Math.abs(sumitcX)> Math.abs(sumitcY)) {
                    return true;
                }

                itcX = ev.getX();
                itcY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                sumitcX = 0 ;
                sumitcY = 0 ;
                break;
        }



        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int currX = scroller.getCurrX();
            int currY = scroller.getCurrY();
            scrollTo(currX, currY);
            //申请重绘
            invalidate();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        menu = getChildAt(0);
        cont = getChildAt(1);
        menuW = menu.getMeasuredWidth();
        menuH = menu.getMeasuredHeight();
        contW = cont.getMeasuredWidth();
        contH = cont.getMeasuredHeight();
    }
}
