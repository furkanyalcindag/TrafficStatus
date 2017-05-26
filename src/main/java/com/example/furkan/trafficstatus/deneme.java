package com.example.furkan.trafficstatus;

import android.app.ActivityManager;
import android.net.TrafficStats;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class deneme extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private long mStartRX = 0;
    private long mStartTX = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartRX = TrafficStats.getTotalRxBytes();//receive
        mStartTX = TrafficStats.getTotalTxBytes();//transmit
        if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX == TrafficStats.UNSUPPORTED) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Uh Oh!");
            alert.setMessage("Your device does not support traffic stat monitoring.");
            alert.show();
        } else {
            mHandler.postDelayed(mRunnable, 1000);
        }
    }

    private final Runnable mRunnable = new Runnable() {
        public void run() {
            TextView RX = (TextView)findViewById(R.id.textView);
            TextView TX = (TextView)findViewById(R.id.textView2);
            long rxBytes = TrafficStats.getTotalRxBytes()- mStartRX;
           // System.out.println(TrafficStats.getUidTxBytes(2));
            RX.setText(Long.toString(rxBytes));
            long txBytes = TrafficStats.getTotalTxBytes()- mStartTX;
            TX.setText(Long.toString(txBytes));
            mHandler.postDelayed(mRunnable, 1000);
        }
    };
}
