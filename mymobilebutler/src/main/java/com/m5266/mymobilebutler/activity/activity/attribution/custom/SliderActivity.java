package com.m5266.mymobilebutler.activity.activity.attribution.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.m5266.mymobilebutler.R;


public class SliderActivity extends AppCompatActivity {

//    float mStartX;
//    float mStartY;
//    float mSumDy = 0;
//    private ImageView slider_imager;
//    private SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

      /*  sliderView = new SliderView(this);
       slider_imager = (ImageView) findViewById(R.id.slider_imager);

         slider_imager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("asd","到这了！");
                int action = event.getAction();
                switch (action){

                    case MotionEvent.ACTION_DOWN:
                        mStartX = event.getX();
                        mStartY = event.getY();
                        int width = slider_imager.getMeasuredWidth();
                        int height = slider_imager.getMeasuredHeight();
                        RectF rectF = new RectF(0, 0 + (int) mSumDy, android.R.attr.width, height + (int) mSumDy);

                        if(!rectF.contains(mStartX,mStartY)) {//如果不在里面
                            return false;
                        }
                        slider_imager.setImageResource(R.mipmap.jd);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float newX = event.getX();
                        float newY = event.getY();

                        float dx = newX - mStartX;
                        float dy = newY - mStartY;

                        mSumDy+=dy;

                        mStartX = newX;
                        mStartY = newY;

                        sliderView.requestLayout();
                       // requestLayout();//请求重新布局
                        break;

                    case MotionEvent.ACTION_UP:
                        slider_imager.setImageResource(R.mipmap.jc);

                        break;
                }

                return true;
            }
        });*/



    }
}
