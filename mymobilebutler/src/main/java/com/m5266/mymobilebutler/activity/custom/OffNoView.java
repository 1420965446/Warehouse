package com.m5266.mymobilebutler.activity.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.m5266.mymobilebutler.R;

/**
 * Created by root on 2017/7/14.
 */

public class OffNoView extends View {

    private Bitmap bg;
    private Bitmap btn;
    private int bgH;
    private int bgW;
    private Paint paint;
    private float startX;
    private float startY;
    private int sumDx = 0;

    public OffNoView(Context context) {
        super(context);
    }

    public OffNoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bg = BitmapFactory.decodeResource(getResources(), R.mipmap.background);
        btn = BitmapFactory.decodeResource(getResources(), R.mipmap.slide_button);
        bgW = bg.getWidth();
        bgH = bg.getHeight();
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);
        if (modeW == MeasureSpec.AT_MOST) {
            sizeW = bgW;
        }
        if (modeH == MeasureSpec.AT_MOST) {
            sizeH = bgH;
        }
        setMeasuredDimension(sizeW, sizeH);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bg, 0, 0, paint);
        if (sumDx < 0) {
            sumDx = 0;
        }
        if (sumDx > bgW - btn.getWidth()) {
            sumDx = bgW - btn.getWidth();
        }
        canvas.drawBitmap(btn, 0 + sumDx, 0, paint);

    }

    boolean isTrue = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动时的新坐标
                float newX = event.getX();
                float newY = event.getY();
                //与之前坐标的偏移量
                float dx = newX - startX;
                float dy = newY - startY;
                //计算X轴偏移量总和
                sumDx += dx;
                //将移动后的位置作为新的起点
                startX = event.getX();
                startY = event.getY();

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                int a = bgW - btn.getWidth();
                Log.i("asdd", a + "  " + bgW + "   " + btn.getWidth());
                Log.i("asdd", sumDx + " ");
                if (sumDx > a / 2) {
                    sumDx = a;
                    Log.i("asdd", "进来了if");
                    if (!isTrue) {

                        isTrue = true;
                        if (mSlideListener != null) {
                            Log.i("asdd", "进来了false");
                            mSlideListener.onSlide(isTrue);
                        }
                    }
                } else {
                    sumDx = 0;
                    Log.i("asdd", "进来了else");
                    if (isTrue) {
                        isTrue = false;
                        Log.i("asdd", "进来了true");
                        if (mSlideListener != null) {
                            mSlideListener.onSlide(isTrue);
                        }
                    }
                }
                Log.i("asdd", "break了");
                invalidate();
                break;
        }


        return true;
    }

    public interface OnSlideListener {
        void onSlide(boolean isOpen);
    }

    private OnSlideListener mSlideListener;

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        mSlideListener = onSlideListener;
    }
}
