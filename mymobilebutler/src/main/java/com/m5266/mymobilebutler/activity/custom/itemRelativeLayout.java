package com.m5266.mymobilebutler.activity.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;

/**
 * Created by root on 2017/7/3.
 */

public class itemRelativeLayout extends RelativeLayout{


    private View view;
    private RelativeLayout set_rl;
    private TextView set_tv;
    private ImageView set_iv;


    public itemRelativeLayout(Context context) {
        this(context,null);
    }

    public itemRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview(context);
        initdata(attrs);

    }

    private void initdata(AttributeSet attrs) {
        String mtext = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "mtext");
        set_tv.setText(mtext);

    }

    private void initview(Context context) {
        view = View.inflate(context, R.layout.setting_item, this);
        set_rl = (RelativeLayout) view.findViewById(R.id.set_rl);
        set_iv = (ImageView) view.findViewById(R.id.set_iv);
        set_tv = (TextView) view.findViewById(R.id.set_tv);
    }


    public void setimagerRes(boolean updata) {
        set_iv.setImageResource(updata?R.drawable.i0:R.drawable.i2);
    }


}
