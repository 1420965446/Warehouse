package com.m5266.mymobilebutler.activity.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;
import com.m5266.mymobilebutler.activity.db.dao.BlacklitsOpenHelperDao;

import java.lang.reflect.Method;

public class CallSmsService extends Service {


    private BlacklitsOpenHelperDao dao;
    private SmsReceiver receiver;
    private TelephonyManager tm;
    private MyPhoneState state;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        dao = new BlacklitsOpenHelperDao(CallSmsService.this);
        receiver = new SmsReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(1000);
        registerReceiver(receiver, intentFilter);
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        state = new MyPhoneState();
        tm.listen(state, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        tm.listen(state, PhoneStateListener.LISTEN_NONE);
    }

    class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Object[] obj = (Object[]) intent.getExtras().get("pdus");
            for (Object o : obj) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) o);
                String address = sms.getOriginatingAddress();
                String body = sms.getMessageBody();
                //得到拦截模式
                String mode = dao.select(address);
                if ("2".equals(mode) || "1".equals(mode)) {
                    abortBroadcast();
                }


            }

        }
    }

    class MyPhoneState extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, final String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://空闲

                break;

                case TelephonyManager.CALL_STATE_OFFHOOK://接通

                break;

                case TelephonyManager.CALL_STATE_RINGING://响铃
                    String mode = dao.select(incomingNumber);
                    if("0".equals(mode)||"2".equals(mode)){

                        endCall();
                    } Uri uri=Uri.parse("content://call_log/calls");
                    getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) {
                        @Override
                        public void onChange(boolean selfChange) {
                            deleteCallLog(incomingNumber);
                            super.onChange(selfChange);
                        }
                    });


                        break;
            }

        }
    }

    private void deleteCallLog(String incomingNumber) {
        ContentResolver resolver = getContentResolver();
        Uri uri=Uri.parse("content://call_log/calls");
        resolver.delete(uri,"number=?",new String[]{incomingNumber});
    }


    private void endCall() {
        try {
            Class<?> loadClass = CallSmsService.this.getClassLoader().loadClass("android.os.ServiceManager");
            Method method = loadClass.getDeclaredMethod("getService", String.class);
            IBinder iBinder = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
            ITelephony iTelephony = ITelephony.Stub.asInterface(iBinder);
            iTelephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
