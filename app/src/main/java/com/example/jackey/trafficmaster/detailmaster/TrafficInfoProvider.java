package com.example.jackey.trafficmaster.detailmaster;

/**
 * Created by Jackey on 15/3/19.
 */

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Context;
import android.net.TrafficStats;
import java.util.ArrayList;
import java.util.List;
public class TrafficInfoProvider {
    private static final String TAG = "TrafficInfoProvider";

    private PackageManager pm;
    private Context context;

    public TrafficInfoProvider(Context context) {
        this.context = context;
        pm = context.getPackageManager();
    }

    public List<TrafficInfo> getTrafficInfos() {

        List<PackageInfo> packinfos = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);

        List<TrafficInfo> trafficInfos = new ArrayList<TrafficInfo>();
        for (PackageInfo packinfo : packinfos) {

            String[] permissions = packinfo.requestedPermissions;
            if (permissions != null && permissions.length > 0) {
                for (String permission : permissions) {

                    if ("android.permission.INTERNET".equals(permission)) {

                        TrafficInfo trafficInfo = new TrafficInfo();

                        trafficInfo.setPackname(packinfo.packageName);
                        trafficInfo.setIcon(packinfo.applicationInfo.loadIcon(pm));
                        trafficInfo.setAppname(packinfo.applicationInfo.loadLabel(pm).toString());

                        int uid = packinfo.applicationInfo.uid;

                        trafficInfo.setRx(TrafficStats.getUidRxBytes(uid));
                        trafficInfo.setTx(TrafficStats.getUidTxBytes(uid));
                        trafficInfos.add(trafficInfo);
                        trafficInfo = null;
                        break;
                    }
                }
            }
        }
        return trafficInfos;
    }
}
