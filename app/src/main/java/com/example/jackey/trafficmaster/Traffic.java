package com.example.jackey.trafficmaster;

/**
 * Created by Jackey on 15/3/19.
 */

import android.net.TrafficStats;


public class Traffic {
    public static final String TAG = "Traffic";
    public long mobRe;
    public long mobSe;
    public long mobTo;
    public long recTo;
    public long wifRe;
    public long senTo;
    public long wifSe;
    public long wifTo;
    public long total;

    public void getTraffic() {

        this.mobRe = TrafficStats.getMobileRxBytes()/(1024*1024);
        this.mobSe = TrafficStats.getMobileTxBytes()/(1024*1024);
        this.mobTo = mobRe + mobSe;
        this.recTo = TrafficStats.getTotalRxBytes()/(1024*1024);
        this.wifRe = recTo - mobRe;
        this.senTo = TrafficStats.getTotalTxBytes()/(1024*1024);
        this.wifSe = senTo - mobSe;
        this.wifTo = wifRe + wifSe;
        this.total = mobTo + wifTo;

    }
}
