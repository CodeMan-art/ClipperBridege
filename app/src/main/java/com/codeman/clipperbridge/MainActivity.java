package com.codeman.clipperbridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.codeman.clipperbridge.utils.ClipperUtils;

public class MainActivity extends AppCompatActivity {

    private TextView clipperBoard;
    public static final String ACTION_CHANGE="action_change";
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //版本大于8.0 通过前台服务解决
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, ClipperService.class));
        }
        //版本小于8.0 通过静态receiver来解决
        initView();
        freshClipper();
        initBroadcast();
    }

    private void initBroadcast() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(ACTION_CHANGE)){
                    freshClipper();
                }
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,new IntentFilter(ACTION_CHANGE));
    }

    @Override
    protected void onResume() {
        super.onResume();
        freshClipper();
    }

    private void initView() {
        clipperBoard = (TextView) findViewById(R.id.clipper_bridge_clipper_broad);
    }
    private void freshClipper() {
        clipperBoard.post(new Runnable() {
            @Override
            public void run() {
                String clipboardContent = ClipperUtils.getClipboardContent(MainActivity.this);
                if (!TextUtils.isEmpty(clipboardContent)) {
                    clipperBoard.setText(clipboardContent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
}
