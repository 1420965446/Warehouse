package com.m5266.mymobilebutler.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.activity.process.ProcessActivity;
import com.m5266.mymobilebutler.activity.adapter.MainAdapter;
import com.m5266.mymobilebutler.activity.always.Constant;
import com.m5266.mymobilebutler.activity.activity.attribution.AtoolActivity;
import com.m5266.mymobilebutler.activity.activity.blacklist.ShowBlackNumberActivity;
import com.m5266.mymobilebutler.activity.activity.guide.FinishSetupActivity;
import com.m5266.mymobilebutler.activity.activity.guide.Setup1Activity;
import com.m5266.mymobilebutler.activity.activity.setup.SettingActivity;
import com.m5266.mymobilebutler.activity.activity.softwareManager.SoftwareManagerActivity;
import com.m5266.mymobilebutler.activity.utils.SPUtils;
import com.m5266.mymobilebutler.activity.utils.statusBarUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends Activity {

    public static final int GV_SAFE = 0;//手机管家
    public static final int GV_BLACK = 1;//通讯卫士
    public static final int GV_MANAG = 2;//软件管家
    public static final int GV_PROCE = 3;//进程管理
    public static final int GV_FLOWS = 4;//流量统计
    public static final int GV_VIRUS = 5;//病毒查杀
    public static final int GV_CACHE = 6;//缓存清理
    private static final int GV_ATOOL = 7;//高级工具

    private  ImageView sp_set_iv;
    private ImageView main_logo;
    private GridView main_gv;
    public int[] icons={R.mipmap.kp,R.mipmap.im,
            R.mipmap.je,R.mipmap.ad,
            R.mipmap.jf,R.mipmap.af
            ,R.mipmap.l5,R.mipmap.i8};
    public String[] title={"手机管家","通信卫士","软件管家","进程管理",
            "流量统计","病毒查杀","缓存清理","高级工具"};
    public String[] desc={"手机丢失好找","防骚扰反监听","方便管理软件","保持手机通畅",
            "注意流量超标","手机安全保障","手机快步如飞","特性处理更好"};
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private View view;
    private EditText et_password;
    private EditText et_password_conform;
    private Button btn_cancel;
    private Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarUtils.statusBar(SplashActivity.this);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
        initAnimaito();
        initlistener();
        copyDB();

    }



    private void initlistener() {
        sp_set_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });

        main_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case GV_SAFE:
                        String password = SPUtils.getString(SplashActivity.this, Constant.SAFEPASSWORD);
                        if (TextUtils.isEmpty(password.trim())) {
                            showSetPassword();
                        } else {
                            showEnterpassword();
                            
                        }
                    break;
                    case GV_BLACK:
                        Intent showBlackNumber=new Intent(SplashActivity.this,ShowBlackNumberActivity.class);
                        startActivity(showBlackNumber);
                    break;

                    case GV_MANAG:
                        Intent softwareManager=new Intent(SplashActivity.this,SoftwareManagerActivity.class);
                        startActivity(softwareManager);
                    break;
                    case GV_PROCE:
                        Intent process = new Intent(SplashActivity.this,ProcessActivity.class);
                        startActivity(process);
                        break;
                    case GV_FLOWS:

                        break;
                    case GV_VIRUS:

                        break;
                    case GV_CACHE:

                        break;

                    case GV_ATOOL:
                        Intent atoolIntent=new Intent(SplashActivity.this,AtoolActivity.class);
                        startActivity(atoolIntent);
                    break;

                }
            }
        });
    }

    /**
     * 设置安全密码的方法
     */
    private void showSetPassword() {
        builder = new AlertDialog.Builder(this);
        view = View.inflate(getApplicationContext(), R.layout.set_password, null);
        et_password = (EditText) view.findViewById(R.id.dialog_et_password);
        et_password_conform = (EditText) view.findViewById(R.id.dialog_et_password_confirm);
        btn_cancel = (Button) view.findViewById(R.id.dialog_btn_cancel);
        btn_ok = (Button) view.findViewById(R.id.dialog_btn_ok);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();
                String password_confirm = et_password_conform.getText().toString();
                if(TextUtils.isEmpty(password)||TextUtils.isEmpty(password_confirm)) {
                    Toast.makeText(SplashActivity.this, "请按要求输入", Toast.LENGTH_SHORT).show();
                    return;
                }if(!password.equals(password_confirm)) {
                    Toast.makeText(SplashActivity.this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                SPUtils.setString(SplashActivity.this,Constant.SAFEPASSWORD,password);
                dialog.dismiss();

            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    /**
     * 输入密码的方法
     */
    private void showEnterpassword() {
        builder = new AlertDialog.Builder(this);
        view = View.inflate(getApplicationContext(), R.layout.set_password, null);
        et_password = (EditText) view.findViewById(R.id.dialog_et_password);
        et_password_conform = (EditText) view.findViewById(R.id.dialog_et_password_confirm);
        et_password_conform.setVisibility(View.GONE);
        btn_cancel = (Button) view.findViewById(R.id.dialog_btn_cancel);
        btn_ok = (Button) view.findViewById(R.id.dialog_btn_ok);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpassword = et_password.getText().toString();
                String oldpassword = SPUtils.getString(SplashActivity.this, Constant.SAFEPASSWORD);
                if(TextUtils.isEmpty(newpassword)) {
                    Toast.makeText(SplashActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!newpassword.equals(oldpassword)) {
                    Toast.makeText(SplashActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean setup = SPUtils.getBoolean(SplashActivity.this, Constant.FINISHSETUP);
                if(setup) {
                    Intent intent=new Intent(SplashActivity.this,FinishSetupActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(SplashActivity.this,Setup1Activity.class);
                    startActivity(intent);
                }

                dialog.dismiss();

            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

    }



    private void initAnimaito() {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(main_logo, "rotationY", 0, 30, 60, 120, 160, 180, 210, 360);
        rotation.setDuration(1000);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.start();
    }

    private void initData() {
        MainAdapter adapter=new MainAdapter(icons ,title,desc,this);
        main_gv.setAdapter(adapter);
    }

    private void initView() {
        main_logo = (ImageView)findViewById(R.id.main_logo);
        main_gv = (GridView)findViewById(R.id.main_gv);
        sp_set_iv = (ImageView)findViewById(R.id.sp_set_iv);
    }

    private void copyDB() {
        //1 获得assets里面的数据流
        // 复制过一次就已经存在,存在没有必要再次复制
        new Thread(){
            public void run(){
                try {
                    File file = new File(getFilesDir(), "address.db");
                    if(file.exists()&&file.length()>0) {
                        return;
                    }
                    //需要优化的地方 必须将那些时间不确定的操作放在子线程
                    InputStream open = getAssets().open("address.db");
                    // 流转换为.db的文件,写到下面的文件中

                    FileOutputStream fos=new FileOutputStream(file);
                    byte[] buffer=new byte[1024];
                    int len=-1;
                    while ((len=open.read(buffer))!=-1)  {
                        fos.write(buffer,0,len);
                    }
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();



    }
}