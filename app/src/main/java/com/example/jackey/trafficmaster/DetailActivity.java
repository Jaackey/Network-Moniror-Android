package com.example.jackey.trafficmaster;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;

import com.example.jackey.trafficmaster.detailmaster.TrafficInfo;
import com.example.jackey.trafficmaster.detailmaster.TrafficInfoProvider;


public class DetailActivity extends ActionBarActivity {
    private ListView lv;

    private TrafficInfoProvider provider;

    private ProgressBar ll_loading;

    private List<TrafficInfo> trafficInfos;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            try{
                ll_loading.setVisibility(View.INVISIBLE);
                lv.setAdapter(new TrafficAdapter());

            }catch (Exception e){
                Log.d("Handler","DetailActivity handler error");
                throw e;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        lv = (ListView) findViewById(R.id.lv_traffic);
        provider = new TrafficInfoProvider(this);
        ll_loading = (ProgressBar)findViewById(R.id.ll_loading);
        ll_loading.setVisibility(View.VISIBLE);

        new Thread(){
            public void run() {
                trafficInfos = provider.getTrafficInfos();

                handler.sendEmptyMessage(0);
            }
        }.start();
    }
    private class TrafficAdapter extends BaseAdapter {

        public int getCount() {
            return trafficInfos.size();
        }

        public Object getItem(int position) {
            return trafficInfos.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder = new ViewHolder();
            TrafficInfo info = trafficInfos.get(position);

            if(convertView==null){
                view = View.inflate(getApplicationContext(), R.layout.ll_taskmanager, null);
                holder.iv_icon = (ImageView) view.findViewById(R.id.iv_traffic_icon);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_traffic_name);
                holder.tv_rx = (TextView) view.findViewById(R.id.tv_traffic_rx);
                holder.tv_tx = (TextView) view.findViewById(R.id.tv_traffic_tx);
                holder.tv_total = (TextView) view.findViewById(R.id.tv_traffic_total);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.iv_icon.setImageDrawable(info.getIcon());
            holder.tv_name.setText(info.getAppname());

            long rx = info.getRx();

            long tx = info.getTx();

            if(rx<0){
                rx = 0;
            }
            if(tx<0){
                tx = 0;
            }
            holder.tv_rx.setText(Formatter.formatFileSize(getApplicationContext(), rx));
            holder.tv_tx.setText(Formatter.formatFileSize(getApplicationContext(), tx));

            long total = rx + tx;

            holder.tv_total.setText(Formatter.formatFileSize(getApplicationContext(), total));
            return view;
        }
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_tx;
        TextView tv_rx;
        TextView tv_total;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
