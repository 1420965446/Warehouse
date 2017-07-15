package com.m5266.mymobilebutler.activity.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.bean.BlacklistBean;
import com.m5266.mymobilebutler.activity.db.dao.BlacklitsOpenHelperDao;
import java.util.ArrayList;

/**
 * Created by root on 2017/7/7.
 */

public class BlackNumberAdapter extends BaseAdapter {

    private ArrayList<BlacklistBean> lists;

    public BlackNumberAdapter(ArrayList<BlacklistBean> lists) {
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_blacknumber_lv, null);
            holder.tv_mode = (TextView) convertView.findViewById(R.id.black_item_tv_mode);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.black_item_tv_phone);
            holder.iv_delete = (ImageView) convertView.findViewById(R.id.black_item_iv_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_phone.setText(lists.get(position).phone);
        switch (lists.get(position).mode) {
            case "0":
                holder.tv_mode.setText("电话拦截");
                break;
            case "1":
                holder.tv_mode.setText("短信拦截");
                break;
            case "2":
                holder.tv_mode.setText("全部拦截");
                break;
        }

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {

            private AlertDialog show;

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("警告!");
                builder.setMessage("确定删除该号码?");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phone = lists.get(position).phone;
                        BlacklitsOpenHelperDao dao = new BlacklitsOpenHelperDao(parent.getContext());
                        boolean delete = dao.delete(phone);
                        if(delete) {
                            lists.remove(position);
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(parent.getContext(), "删除失败！", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                show = builder.show();
            }
        });


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

    public void addData(BlacklistBean bean) {
        lists.add(0,bean);
    }


    class ViewHolder {
        TextView tv_phone;
        TextView tv_mode;
        ImageView iv_delete;
    }
}
