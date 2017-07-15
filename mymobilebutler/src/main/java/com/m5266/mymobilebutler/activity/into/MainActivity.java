package com.m5266.mymobilebutler.activity.into;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.m5266.mymobilebutler.activity.SplashActivity;
import com.m5266.mymobilebutler.activity.always.Constant;
import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.utils.SPUtils;
import com.m5266.mymobilebutler.activity.bean.UpDataBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    private RelativeLayout rl_root;
    private TextView splash_code;
    private TextView splash_name;
    private AnimationSet as;
    private long startTimer;
    private Message msg;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch ((int) msg.what) {
                case 1001:
                    Log.i("taggg","3.1");
                    showDialogUpdate(msg.obj);
                    break;
                case 1002:
                    Log.i("taggg","3.2");
                    startMainActivity();
                    break;
                default:
                    Log.i("taggg","3.3");
                    Toast.makeText(MainActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                    startMainActivity();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("taggg","aaaaaa1111111");

        //SPUtils.setBoolean(this, Constant.SP_UOTO_UPDATA, true);
        Log.i("taggg","a");

        initView();
        Log.i("taggg","b");

        initData();
        Log.i("taggg","c");

        initAnimation();
        Log.i("taggg","d");

        initListener();


    }

    private void initListener() {
        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (SPUtils.getBoolean(MainActivity.this, Constant.SP_UOTO_UPDATA)) {
                    //需要检查版本更新
                    Log.i("taggg","1");
                    checkVersionCode();

                } else {
                    //不需要检查版本更新
                    //啥事都不要做

                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //SPUtils.getBoolean(MainActivity.this, Constant.SP_NAME)
                if (SPUtils.getBoolean(MainActivity.this, Constant.SP_UOTO_UPDATA)) {
                    //需要检查版本更新


                } else {
                    //不需要检查版本更新
                    //啥事都不要做
                    startMainActivity();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void checkVersionCode() {
        Log.i("taggg","2");
        new Thread() {
            public void run() {
                //网络请求获得服务器 中的数据
                try {
                    startTimer = SystemClock.currentThreadTimeMillis();
                    msg = Message.obtain();
                    readDataFromNet();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.i("taggg","1003");
                    msg.what = 1003;
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("taggg","1004");
                    msg.what = 1004;
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("taggg","1005");
                    msg.what = 1005;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    Log.i("taggg","1006");
                    msg.what = 1006;
                } finally {
                    //比对时间
                    long endTimer = SystemClock.currentThreadTimeMillis();
                    handler.sendMessageDelayed(msg, 3000 - (endTimer - startTimer));


                }
            }


        }.start();
    }

    private void readDataFromNet() throws IOException, JSONException, PackageManager.NameNotFoundException  {
        URL url = new URL(Constant.URL.UPDATE);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方法
        conn.setRequestMethod("GET");
        //设置请求时长
        conn.setConnectTimeout(5000);
        if (conn.getResponseCode() == 200) {
            //请求成功
            //得到服务器传递过来的流
            InputStream in = conn.getInputStream();
            //将字节流转换为字符流
            String JsonStr = readStream(in);
            //解析这个Json字符串

            UpDataBean upDataBean = paramsJson(JsonStr);
            //比对版本号,如果服务器的版本号大于本地版本号,就提示更新
            //获取到本地版本后服务器的版本
            PackageInfo info = getPackageInfo();
            if (Integer.valueOf(upDataBean.version) > info.versionCode) {
                //需要更新
                Log.i("taggg","1001");
                msg.what = 1001;
                msg.obj = upDataBean;
            } else {
                Log.i("taggg","1002");
                //不需要更新
                // 进入主界面
                msg.what = 1002;


            }

        }

    }

    private UpDataBean paramsJson(String jsonStr) throws JSONException {
        UpDataBean upDataBean = new UpDataBean();
        JSONObject jsonObject = new JSONObject(jsonStr);
        upDataBean.version = jsonObject.getString("version");
        upDataBean.downloadurl = jsonObject.getString("downloadurl");
        upDataBean.desc = jsonObject.getString("desc");

        return upDataBean;
    }

    private String readStream(InputStream in) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        return sb.toString();
    }

    private void showDialogUpdate(Object obj) {
        final UpDataBean bean = (UpDataBean) obj;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("升级提醒");
        builder.setMessage(bean.desc);

        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downLoadApp(bean);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //关闭对话框
                dialog.dismiss();
                //关闭当前页面
                //进入主界面
                startMainActivity();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void initAnimation() {
        ScaleAnimation sa = new ScaleAnimation(
                0.0f, 1.0f,
                0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(3000);

        RotateAnimation ra = new RotateAnimation(
                0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(3000);

        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(3000);

        as = new AnimationSet(false);
        as.addAnimation(sa);
        as.addAnimation(ra);
        as.addAnimation(aa);
        rl_root.startAnimation(as);

    }


    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
        splash_code = (TextView) findViewById(R.id.splash_code);
        splash_name = (TextView) findViewById(R.id.splash_name);

    }

    private void initData() {
        try {
            PackageInfo info = getPackageInfo();
            String versionName = info.versionName;
            int versionCode = info.versionCode;
            splash_name.setText(versionName);



        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void downLoadApp(UpDataBean bean) {
        HttpUtils httpUtils=new HttpUtils();
        final File file=new File(Environment.getExternalStorageDirectory(),"xx.apk");
        System.out.println("bean="+bean.downloadurl);
        /**
         * 参数1 下载的路劲
         * 参数二,文件下载完毕后的路劲
         */
        httpUtils.download(bean.downloadurl, file.getAbsolutePath(), false, new RequestCallBack<File>() {
            //下载成功的回调
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                // 安装
                Toast.makeText(MainActivity.this, "下载成功!", Toast.LENGTH_SHORT).show();
                //  <action android:name="android.intent.action.VIEW" />
                // <category android:name="android.intent.category.DEFAULT" />
                //  <data android:scheme="content" />
                //  <data android:scheme="file" />
                //
                //  <data android:mimeType="application/vnd.android.package-archive" />
                Intent intent=new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
                startActivityForResult(intent,1);


            }
            //下载失败的回调
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(MainActivity.this, "下载失败!", Toast.LENGTH_SHORT).show();
                startMainActivity();
            }
        });
    }


    public PackageInfo getPackageInfo() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        return pm.getPackageInfo(getPackageName(), 0);
    }

    private void startMainActivity() {
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

}
