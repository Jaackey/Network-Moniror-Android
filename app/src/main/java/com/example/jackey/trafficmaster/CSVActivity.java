package com.example.jackey.trafficmaster;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jackey.trafficmaster.csvmaster.AlarmReceiver;
import com.example.jackey.trafficmaster.csvmaster.BootReceiver;
import com.example.jackey.trafficmaster.csvmaster.CSVService;

import java.util.Calendar;


public class CSVActivity extends ActionBarActivity {

    AlarmReceiver alarm = new AlarmReceiver();
    private MyDataBaseHelper dbHelper;

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv);
        getSupportActionBar().hide();

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CSVActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(CSVActivity.this, CSVService.class);

        startService(intent);
        alarm.setAlarm(this);
        dbHelper = new MyDataBaseHelper(this, "TrafficCheck.db", null, 2);
        final Traffic mtraffic = new Traffic();

        mtraffic.getTraffic();

        Button startTrafficCheck = (Button)findViewById(R.id.start_traffic_check);
        startTrafficCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dbHelper.getWritableDatabase();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                final int tracheck = Integer.parseInt(String.valueOf(mtraffic.total));

                values.put("tracheck",tracheck);
                values.put("hour",hour);
                values.put("day",day);
                values.put("month",month);
                values.put("year",year);
                db.insert("Traffic",null,values);
                values.clear();
            }
        });

        Button databaseTest = (Button)findViewById(R.id.database_test);
        databaseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Traffic",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        String count = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                        TextView databaseTest = (TextView) findViewById(R.id.database_count);
                        databaseTest.setText(count);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_csv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
