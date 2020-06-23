package com.codeman.clipperbridge.utils;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "ClipperBridge";
    public static int POWER_ON = 1;
    public static int POWER_OFF = 0;

    private static int POWER = POWER_ON;

    public static void setPower(int p) {
        POWER = p;
    }

    /**
     * debug
     */
    public static void d(String info) {
        if (check()) {
            Log.d(TAG, info);
        }
    }

    /**
     * error
     */
    public static void e(String error) {
        if (check()) {
            Log.e(TAG, error);
        }
    }

    private static boolean check() {
        return (POWER & POWER_ON) == POWER_ON;
    }
}
