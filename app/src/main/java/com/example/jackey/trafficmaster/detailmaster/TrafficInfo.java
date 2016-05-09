package com.example.jackey.trafficmaster.detailmaster;

/**
 * Created by Jackey on 15/3/19.
 */
import android.graphics.drawable.Drawable;
public class TrafficInfo {
    private static final String TAG = "TrafficInfo";


    private String packname;

    private String appname;

    private long tx;

    private long rx;

    private Drawable icon;
    public String getPackname() {
        return packname;
    }
    public void setPackname(String packname) {
        this.packname = packname;
    }
    public String getAppname() {
        return appname;
    }
    public void setAppname(String appname) {
        this.appname = appname;
    }
    public long getTx() {
        return tx;
    }
    public void setTx(long tx) {
        this.tx = tx;
    }
    public long getRx() {
        return rx;
    }
    public void setRx(long rx) {
        this.rx = rx;
    }
    public Drawable getIcon() {
        return icon;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
