package com.m5266.mymobilebutler.activity.activity.guide;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.m5266.mymobilebutler.activity.always.Constant;
import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.utils.SPUtils;

public class Setup3Activity extends BaseActivity {

    private EditText setup3_et_phone;
    @Override
    public void initView() {
        setContentView(R.layout.activity_setup3);
        setup3_et_phone = (EditText)findViewById(R.id.setup3_et_phone);

    }
    @Override
    protected void initData() {
        String phone = SPUtils.getString(this, Constant.SAFEPHONE);
        setup3_et_phone.setText(phone);

    }
    @Override
    public void showNext() {
        String phone = setup3_et_phone.getText().toString();
        if(TextUtils.isEmpty(phone)) {
            Toast.makeText(Setup3Activity.this, "必须输入号码", Toast.LENGTH_SHORT).show();
            return;
        }
        //数据是正常的把
        SPUtils.setString(this,Constant.SAFEPHONE,phone);
        Intent intent=new Intent(this, Setup4Activity.class);
        startActivity(intent);
    }

    @Override
    public void showPre() {
        Intent intent=new Intent(this, Setup2Activity.class);
        startActivity(intent);
    }

    public void selectContact(View view) {
        Log.i("newlog","list="+"selectContact");
        Intent intent=new Intent(this,ContactsActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String phone = data.getStringExtra(Constant.PHONE).replace("-","");
        setup3_et_phone.setText(phone);
    }
}
