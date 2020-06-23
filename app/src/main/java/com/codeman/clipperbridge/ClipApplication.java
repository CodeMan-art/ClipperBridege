package com.codeman.clipperbridge;

import android.app.Application;

import com.codeman.clipperbridge.utils.LogUtils;

public class ClipApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        LogUtils.setPower(LogUtils.POWER_ON);
    }
}
