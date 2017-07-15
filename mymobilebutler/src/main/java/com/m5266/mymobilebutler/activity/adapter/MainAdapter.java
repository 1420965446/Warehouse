package com.m5266.mymobilebutler.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class MainAdapter extends BaseAdapter {
    private int[] icons;
    private String[] titles;
    private String[] desc;
    private Context mContext;

    public MainAdapter(int[] icons, String[] titles, String[] desc, Context context) {
        this.icons = icons;
        this.titles = titles;
        this.desc = desc;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return icons != null ? icons.length : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_main_gv, null);
        ImageView icon = (ImageView) view.findViewById(R.id.main_item_logo);
        TextView tv_title = (TextView) view.findViewById(R.id.main_item_tv_title);
        TextView tv_desc = (TextView) view.findViewById(R.id.main_item_tv_desc);
        icon.setImageResource(icons[position]);
        tv_title.setText(titles[position]);
        tv_desc.setText(desc[position]);
        return view;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
