package com.m5266.mymobilebutler.activity.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.bean.AppInfoBean;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/11.
 */

public class SoftwareManagerAdapter extends BaseAdapter{

    private ArrayList<AppInfoBean> lists;
    public SoftwareManagerAdapter(ArrayList<AppInfoBean> lists) {
        this.lists = lists ;
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
            convertView = View.inflate(parent.getContext(), R.layout.software_manager_list_ltem, null);
            holder.manager_lv_im = (ImageView) convertView.findViewById(R.id.manager_lv_im);
            holder.manager_lv_space = (TextView) convertView.findViewById(R.id.manager_lv_space);
            holder.manager_lv_type = (TextView) convertView.findViewById(R.id.manager_lv_type);
            holder.manager_lv_title = (TextView) convertView.findViewById(R.id.manager_lv_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.manager_lv_im.setImageDrawable(lists.get(position).getIcon());
        holder.manager_lv_space.setText(String.valueOf(lists.get(position).getApksize()));
        holder.manager_lv_title.setText(lists.get(position).getAppname());
        if(lists.get(position).isUserApp()) {
            holder.manager_lv_type.setText("用户程序");
        }else{
            holder.manager_lv_type.setText("系统程序");
        }






        return convertView;
    }

    @Override
    public AppInfoBean getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView manager_lv_space;
        TextView manager_lv_type;
        TextView manager_lv_title;
        ImageView manager_lv_im;
    }


}
