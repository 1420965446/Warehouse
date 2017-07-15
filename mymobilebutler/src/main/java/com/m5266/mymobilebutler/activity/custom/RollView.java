package com.m5266.mymobilebutler.activity.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.m5266.mymobilebutler.R;

/**
 * Created by root on 2017/7/13.
 */

public class RollView extends ViewFlipper {

    private float mFontSize;
    private int mFontColor;
    public RollView(Context context) {
        super(context);
    }

    public RollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextFlipper);
        mFontSize = typedArray.getDimension(R.styleable.TextFlipper_fontSize, 10);
        mFontColor = typedArray.getColor(R.styleable.TextFlipper_fontColor, Color.BLACK);
        typedArray.recycle();
    }

    public void setData(int image[]){
        //ArrayList<String> strings
        for (int i = 0; i < image.length; i++) {
           // String s = strings.get(i);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(image[i]);
           // TextView textView = new TextView(getContext());
           // textView.setTextSize(mFontSize);
           // textView.setTextColor(mFontColor);
          //  textView.setText(s);
            addView(imageView);
        }
    }

}
