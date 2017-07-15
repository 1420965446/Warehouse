package com.m5266.mymobilebutler.activity.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.m5266.mymobilebutler.R;
import com.m5266.mymobilebutler.activity.db.dao.LocationDao;

import static android.telephony.TelephonyManager.CALL_STATE_IDLE;
import static android.telephony.TelephonyManager.CALL_STATE_OFFHOOK;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;
import static com.m5266.mymobilebutler.activity.utils.CustomToast.removeMyToast;
import static com.m5266.mymobilebutler.activity.utils.CustomToast.showMyToast;

public class LocationShowService extends Service {

    private TelephonyManager tm;
    private MyShowLoctionListener listener;
    private CallOutPhoneReceiver receiver;
    private View view;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyShowLoctionListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        receiver = new CallOutPhoneReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(receiver, filter);

    }

    @Override
    public void onDestroy() {

    }

    class MyShowLoctionListener extends PhoneStateListener {

        private TextView tv;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.i("log4", "state=" + state);
            switch (state) {
                case CALL_STATE_IDLE://空闲
                    removeMyToast(tv);
                    break;

                case CALL_STATE_OFFHOOK://接通
                    //if(mWM!=null) {
                    //   mWM.removeView(view);
                    //   mWM=null;
                    // }
                    break;

                case CALL_STATE_RINGING://响铃

                    String location = LocationDao.getLocation(LocationShowService.this, incomingNumber);
                    Log.i("log44","location="+location);
                    view = View.inflate(LocationShowService.this, R.layout.wm_view, null);
                    view.setAlpha(0.5f);
                    TextView tv = (TextView) view.findViewById(R.id.ww_tv_location);
                    tv.setText(location);

                    showMyToast(LocationShowService.this, location, view);
                    break;
            }
        }


    }


    class CallOutPhoneReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = getResultData();
            String location = LocationDao.getLocation(LocationShowService.this, data);
            showMyToast(LocationShowService.this, location,view);
        }
    }

}
