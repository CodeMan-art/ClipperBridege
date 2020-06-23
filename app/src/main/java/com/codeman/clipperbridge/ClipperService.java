package com.codeman.clipperbridge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.codeman.clipperbridge.utils.LogUtils;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ClipperService extends Service {
    public static final String CHANNEL_ID = "*Clipper*";
    public static final String CHANNEL_NAME = "Clipper_Channel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(new ClipperReceiver(), createIntentFilter());
        return Service.START_STICKY;
    }

    private void createNotification() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID).build();
        startForeground(1, notification);
    }

    IntentFilter createIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ClipperReceiver.ACTION_GET);
        intentFilter.addAction(ClipperReceiver.ACTION_GET_SHORT);
        intentFilter.addAction(ClipperReceiver.ACTION_SET);
        intentFilter.addAction(ClipperReceiver.ACTION_SET_SHORT);
        return intentFilter;
    }
}
