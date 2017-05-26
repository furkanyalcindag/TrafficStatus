package com.example.furkan.trafficstatus;

/**
 * Created by furkan on 25.05.2017.
 */

public class Data {
    private long mStartRX = 0;
    private long mStartTX = 0;
    private long mobileRX =0;

    public long getMobileRX() {
        return mobileRX;
    }

    public void setMobileRX(long mobileRX) {
        this.mobileRX = mobileRX;
    }

    public long getmStartRX() {
        return mStartRX;
    }

    public void setmStartRX(long mStartRX) {
        this.mStartRX = mStartRX;
    }

    public long getmStartTX() {
        return mStartTX;
    }

    public void setmStartTX(long mStartTX) {
        this.mStartTX = mStartTX;
    }
}
