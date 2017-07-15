package com.m5266.mymobilebutler.activity.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.bean.ContactBean;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/5.
 */

public class ContactAdapter extends BaseAdapter{
    private ArrayList<ContactBean> list;
    public ContactAdapter(ArrayList<ContactBean> list) {
        this.list=list;
    }

    @Override
    public int getCount() {
        Log.i("newlog2",""+list.size());
        return list!=null?list.size():0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i("newlog","list="+list.get(position).name);
        Log.i("newlog","list="+list.get(position).phone);
        ViewHolder holder;
        if(convertView==null) {
            holder=new ViewHolder();
            convertView=View.inflate(parent.getContext(), R.layout.item_contact_lv,null);
            holder.tv_name= (TextView) convertView.findViewById(R.id.contact_item_name);
            holder.tv_phone= (TextView) convertView.findViewById(R.id.contact_item_phone);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(list.get(position).name);
        holder.tv_phone.setText(list.get(position).phone);

        return convertView;
    }

    class ViewHolder{
        TextView tv_phone;
        TextView tv_name;

    }

    @Override
    public ContactBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
