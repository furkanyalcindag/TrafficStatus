package com.example.furkan.trafficstatus;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by furkan on 23.05.2017.
 */

public class DataUsageTracker extends Service {

    private ProgressDialog pDialog;
    private JSONObject json;
    private int success = 0;
    private HTTPURLConnection service;
    private String strname = "", strMobile = "", strAddress = "";
    //Initialize webservice URL
    private String path = "http://www.sri-ako.com/insert2.php";

    private TelephonyManager telephonyManager;
    private String imeiNumber ;

    Handler handler = new Handler() {

        public void publish(LogRecord record) {

        }


        public void flush() {

        }


        public void close() throws SecurityException {

        }
    };

    private Context mContext;
    private String jsonResponse;
    Data gpsTracker;
    public long x;
    public long y;
    public long z;

    public DataUsageTracker() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        gpsTracker = new Data();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                service = new HTTPURLConnection();
                // new PostDataTOServer.execute();
                track(gpsTracker);

                handler.postDelayed(this, 30000);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service destroyed", Toast.LENGTH_LONG).show();
    }

    public DataUsageTracker(Context context) {
        this.mContext = context;
       // getLocation();
    }
    public void track(Data data) {
        System.out.println("Merhaba Buradayım");
        data = new Data();

        // check if GPS enabled

        data.setmStartRX(TrafficStats.getTotalRxBytes());
        data.setmStartTX(TrafficStats.getTotalTxBytes());
             x = data.getmStartRX();
             y = data.getmStartTX();
            z =data.getMobileRX();
            System.out.println(x+"asas "+y+"sds");
            // new PostDataTOServer().execute();


            telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
             imeiNumber = telephonyManager.getDeviceId().toString();
        System.out.println(imeiNumber);
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningProcesses = manager.getRunningAppProcesses();
        for(int i =0;i<runningProcesses.size();i++){
            RunningAppProcessInfo a=runningProcesses.get(0);
            System.out.println(a.uid);
        }
int a= runningProcesses.get(0).uid;
            new PostDataTOServer().execute();
            // \n is for new line

            Toast.makeText(getApplicationContext(), "Veri kullanımı -  \nAlınan: " + x + "\nGonderilen: " + y, Toast.LENGTH_LONG).show();

         //++   insert();



    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class PostDataTOServer extends AsyncTask<Void, Void, Void> {

        String response = "";
        //Create hashmap Object to send parameters to web service
        HashMap<String, String> postDataParams;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //    pDialog.setMessage("Please wait...");
            //  pDialog.setCancelable(false);
            // pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            postDataParams=new HashMap<String, String>();
            postDataParams.put("RX", String.valueOf(x));
            postDataParams.put("TX", String.valueOf(y));
            postDataParams.put("imei",imeiNumber);
            postDataParams.put("mobil",String.valueOf(z));
            //  postDataParams.put("address", strAddress);
            //Call ServerData() method to call webservice and store result in response
            response= service.ServerData(path,postDataParams);
            try {
                json = new JSONObject(response);
                //Get Values from JSONobject
                System.out.println("success=" + json.get("success"));
                success = json.getInt("success");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           /* if (pDialog.isShowing())
                pDialog.dismiss();*/
            if(success==1) {
                Toast.makeText(getApplicationContext(), "Employee Added successfully..!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
