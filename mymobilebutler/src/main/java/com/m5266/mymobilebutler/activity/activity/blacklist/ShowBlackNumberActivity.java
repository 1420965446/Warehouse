package com.m5266.mymobilebutler.activity.activity.blacklist;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.adapter.BlackNumberAdapter;
import com.m5266.mymobilebutler.activity.bean.BlacklistBean;
import com.m5266.mymobilebutler.activity.db.dao.BlacklitsOpenHelperDao;

import java.util.ArrayList;
import java.util.List;

public class ShowBlackNumberActivity extends Activity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(blackAdapter==null) {
                ArrayList<BlacklistBean> lists = (ArrayList<BlacklistBean>) msg.obj;
                blackAdapter = new BlackNumberAdapter(lists);

                sbn_lv.setAdapter(blackAdapter);
                blackAdapter.notifyDataSetChanged();
            }else {
                blackAdapter.notifyDataSetChanged();
            }
        }
    };
    private List<BlacklistBean> list;
    private BlackNumberAdapter blackAdapter;
    private ListView sbn_lv;
    private int maxCount=20;//每次查的个数
    private int startIndex=0;//从0开始查
    private BlacklitsOpenHelperDao dao;
    private AlertDialog dialog;
    private EditText black_dialog_et_phone;
    private RadioGroup black_dialog_rg;
    private Button black_dialog_btn_cancel;
    private Button black_dialog_btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_black_number);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        sbn_lv = (ListView)findViewById(R.id.sbn_lv);

    }

    private void initData() {
        fillData(maxCount,startIndex);


    }

    private void fillData(final int maxCount, final int startIndex) {
        new Thread(){

            public void run(){
                dao = new BlacklitsOpenHelperDao(ShowBlackNumberActivity.this);
                if(list==null) {
                    list = dao.getPartBalckPhone(maxCount, startIndex);
                }else{
                    list.addAll(dao.getPartBalckPhone(maxCount, startIndex));
                }
                Message msg = new Message();
                msg.obj = list;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private void initListener() {
        sbn_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            /**
             * 该方法在状态改变的时候被调用(开始，)
             * @param view
             * @param scrollState
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case SCROLL_STATE_FLING:

                        
                        break;
                    case SCROLL_STATE_IDLE:

                        int position = view.getLastVisiblePosition();
                        int count = view.getCount();
                        if(position==(count-1)) {

                            startIndex+=maxCount;
                            Log.i("newlogg","我被执行了 startIndex="+startIndex);
                            fillData(maxCount,startIndex);

                        }

                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:


                        break;

                }
            }

            /**
             * 该方法滚动时被调用
             * @param view
             * @param firstVisibleItem
             * @param visibleItemCount
             * @param totalItemCount
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 黑名单里的添加按钮的方法
     * @param view
     */
    public void addBlackNumber(View view) {
        View dailgoView = View.inflate(this, R.layout.dialog_add_black, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        black_dialog_et_phone = (EditText) dailgoView.findViewById(R.id.black_dialog_et_phone);
        black_dialog_rg = (RadioGroup) dailgoView.findViewById(R.id.black_dialog_rg);
        black_dialog_btn_cancel = (Button)dailgoView.findViewById(R.id.black_dialog_btn_cancel);
        black_dialog_btn_ok = (Button)dailgoView.findViewById(R.id.black_dialog_btn_ok);
        black_dialog_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        black_dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = black_dialog_et_phone.getText().toString();
                if(!TextUtils.isEmpty(phone)) {
                    int id = black_dialog_rg.getCheckedRadioButtonId();
                    String mode="0";
                    switch (id){
                        case R.id.black_dialog_rb_phone:
                            mode="0";
                            break;
                        case R.id.black_dialog_rb_sms:
                            mode="1";
                            break;
                        case R.id.black_dialog_rb_all:
                            mode="2";
                            break;
                    }
                    boolean add = dao.add(phone, mode);
                    if(add) {
                        BlacklistBean bean = new BlacklistBean();
                        bean.mode=mode;
                        bean.phone=phone;
                        blackAdapter.addData(bean);
                        blackAdapter.notifyDataSetChanged();

                    }else{
                        Toast.makeText(ShowBlackNumberActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }else{
                    Toast.makeText(ShowBlackNumberActivity.this, "添加需要号码！", Toast.LENGTH_SHORT).show();
                }
            }
        });



        dialog = builder.create();
        dialog.setView(dailgoView);
        dialog.show();

    }
}
