package com.example.jackey.trafficmaster;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends ActionBarActivity {

    private TextView textViewMobRe;
    private TextView textViewMobSe;
    private TextView textViewRecTo;
    private TextView textViewSenTo;
    private TextView textViewMobTo;
    private TextView textViewWifRe;
    private TextView textViewWifSe;
    private TextView textViewWifTo;
    private TextView textViewTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                //intent.addCategory("android.intent.category.DETAIL_CATEGORY");
                startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FloatActivity.class);
                //intent.addCategory("android.intent.category.FLOAT_CATEGORY");
                startActivity(intent);
            }
        });

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CSVActivity.class);
                startActivity(intent);

            }
        });

        Traffic traffic = new Traffic();

        traffic.getTraffic();
        textViewMobRe = (TextView)findViewById(R.id.Mob_Re);
        textViewMobRe.setText(String.valueOf(traffic.mobRe)+"MB",TextView.BufferType.NORMAL );

        textViewMobSe = (TextView)findViewById(R.id.Mob_Se);
        textViewMobSe.setText(String.valueOf(traffic.mobSe)+"MB",TextView.BufferType.NORMAL );

        textViewRecTo = (TextView)findViewById(R.id.Rec_To);
        textViewRecTo.setText(String.valueOf(traffic.recTo)+"MB",TextView.BufferType.NORMAL );

        textViewSenTo = (TextView)findViewById(R.id.Sen_To);
        textViewSenTo.setText(String.valueOf(traffic.senTo)+"MB",TextView.BufferType.NORMAL );

        textViewMobTo = (TextView)findViewById(R.id.Mob_To);
        textViewMobTo.setText(String.valueOf(traffic.mobTo)+"MB",TextView.BufferType.NORMAL );

        textViewWifRe = (TextView)findViewById(R.id.Wif_Re);
        textViewWifRe.setText(String.valueOf(traffic.wifRe)+"MB",TextView.BufferType.NORMAL );

        textViewWifSe = (TextView)findViewById(R.id.Wif_Se);
        textViewWifSe.setText(String.valueOf(traffic.wifSe)+"MB",TextView.BufferType.NORMAL );

        textViewWifTo = (TextView)findViewById(R.id.Wif_To);
        textViewWifTo.setText(String.valueOf(traffic.wifTo)+"MB",TextView.BufferType.NORMAL );

        textViewTotal = (TextView)findViewById(R.id.Total);
        textViewTotal.setText(String.valueOf(traffic.total)+"MB",TextView.BufferType.NORMAL );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
