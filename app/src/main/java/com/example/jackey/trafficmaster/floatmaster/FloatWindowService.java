package com.example.jackey.trafficmaster.floatmaster;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.jackey.trafficmaster.FloatActivity;
import com.example.jackey.trafficmaster.R;

public class FloatWindowService extends Service {
    private Handler handler = new Handler();
    private ActivityManager am = null;
    private Timer timer;

    private static final int MSG_BLUE = 0;
    private static final int MSG_RED = 1;

//    private Handler mHandler = new Handler() {
//        public void handleMessage (Message msg) {
//            switch (msg.what) {
//                case MSG_BLUE:
//                    //MAKE UI BLUE
//                    break;
//
//                case MSG_RED:
//                    //MAKE UI RED
//                    break;
//            }
//        }
//    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        // 开启定时器，每隔0.5秒刷新一次
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Service被终止的同时也停止定时器继续运行
        timer.cancel();
        timer = null;
    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {

            if (isDesktop()&& !MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.createSmallWindow(getApplicationContext());
                    }
                });
            }

            else if (!isDesktop() && MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.removeSmallWindow(getApplicationContext());
                        MyWindowManager.removeBigWindow(getApplicationContext());
                    }
                });
            }

            else if (isDesktop() && MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.updateUsedPercent(getApplicationContext());

                        try {
                            FloatActivity.trafficNow = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
                            FloatActivity.trafficChange = FloatActivity.trafficNow - FloatActivity.trafficLast;
                            if (FloatActivity.trafficChange == 0) {
                                FloatActivity.trafficLast = FloatActivity.trafficNow;
                                MyWindowManager.smallWindow.mHandler.obtainMessage(MSG_BLUE).sendToTarget();
                            } else {
                                FloatActivity.trafficLast = FloatActivity.trafficNow;
                                MyWindowManager.smallWindow.mHandler.obtainMessage(MSG_RED).sendToTarget();
                            }
                        }catch (Exception e){e.printStackTrace();}
                    }
                });
            }
        }

    }

    public boolean isDesktop() {
        Log.d("KOST", "Check");
        String[] activePackages;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            activePackages = getActivePackages();
        } else {
            activePackages = getActivePackagesCompat();
        }
        Log.d("KOST", Arrays.toString(activePackages));
        if (activePackages != null) {
            for (String activePackage : activePackages) {
                if (activePackage.equals("com.sec.android.app.launcher")) {
                    Log.d("isDesktop", "true");
                    return true;
                }
            }
        }
        Log.d("isDesktop", "false");
        return false;

    }

    String[] getActivePackagesCompat() {
        final List<ActivityManager.RunningTaskInfo> taskInfo = am
                .getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();
        return activePackages;
    }

    String[] getActivePackages() {
        final Set<String> activePackages = new HashSet<String>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = am
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}