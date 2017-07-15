package com.m5266.mymobilebutler.activity.adapter;

import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.bean.AppInfoBean;
import com.m5266.mymobilebutler.activity.utils.SPUtils;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/11.
 */

public class processAdapter extends BaseAdapter {
    ArrayList<AppInfoBean> lists;

    public processAdapter(ArrayList<AppInfoBean> lists) {
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists!=null?lists.size():0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.process_listview_item, null);
            holder.process_lv_im = (ImageView) convertView.findViewById(R.id.process_lv_im);
            holder.process_lv_title = (TextView) convertView.findViewById(R.id.process_lv_title);
            holder.process_lv_dir = (TextView) convertView.findViewById(R.id.process_lv_dir);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.process_lv_im.setImageDrawable(lists.get(position).getIcon());
        holder.process_lv_title.setText(lists.get(position).getAppname());
        holder.process_lv_dir.setText(Formatter.formatFileSize(parent.getContext(),lists.get(position).getApksize()));
        boolean aBoolean = SPUtils.getBoolean(parent.getContext(), "isbianji");
        if(aBoolean) {
            holder.checkBox.setVisibility(View.GONE);
        }else
        {
            holder.checkBox.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        CheckBox checkBox;
        ImageView process_lv_im;
        TextView process_lv_title;
        TextView process_lv_dir;
    }

}
