package com.example.jackey.trafficmaster.csvmaster;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.jackey.trafficmaster.*;

import java.util.Calendar;


public class CSVService extends IntentService {


    public CSVService() {
        super("CSVService");
    }

    public static final String TAG = "CSV Demo";

    private MyDataBaseHelper dbHelper;

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            //My work to do is HERE.
            final Traffic mtraffic = new Traffic();
            mtraffic.getTraffic();
            dbHelper.getWritableDatabase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            final int tracheck = Integer.parseInt(String.valueOf(mtraffic.total));
            Log.i(TAG,String.valueOf(mtraffic.total));
            values.put("tracheck",tracheck);
            values.put("hour",hour);
            values.put("day",day);
            values.put("month",month);
            values.put("year",year);
            db.insert("Traffic",null,values);
            values.clear();

        } catch (Exception e) {
            Log.i(TAG, getString(R.string.csv_error));
            e.printStackTrace();

        }

        AlarmReceiver.completeWakefulIntent(intent);
    }

}
