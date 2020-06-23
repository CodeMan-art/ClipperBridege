package com.codeman.clipperbridge;

import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.text.TextUtils;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.codeman.clipperbridge.utils.ClipperUtils;
import com.codeman.clipperbridge.utils.LogUtils;

public class ClipperReceiver extends BroadcastReceiver {

    public static String ACTION_GET = "clipper.get";
    public static String ACTION_GET_SHORT = "get";
    public static String ACTION_SET = "clipper.set";
    public static String ACTION_SET_SHORT = "set";
    public static String EXTRA_TEXT = "text";

    public static boolean isActionGet(final String action) {
        return ACTION_GET.equals(action) || ACTION_GET_SHORT.equals(action);
    }

    public static boolean isActionSet(final String action) {
        return ACTION_SET.equals(action) || ACTION_SET_SHORT.equals(action);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        ClipboardManager cb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (isActionSet(intent.getAction())) {
            LogUtils.d("process:"+ Process.myPid());
            LogUtils.d("======= set ======");
            String text = intent.getStringExtra(EXTRA_TEXT);
            if (text != null) {
                ClipperUtils.setClipboardContent(context, text);
                setResultCode(Activity.RESULT_OK);
                //通知界面刷新
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(MainActivity.ACTION_CHANGE));
                setResultData("Text is copied into clipboard.");
                LogUtils.d("======= ok ======");
            } else {
                setResultCode(Activity.RESULT_CANCELED);
                setResultData("No text is provided. Use -e text \"text to be pasted\"");
                LogUtils.e("No text is provided. Use -e text \"text to be pasted\"");
            }
        } else if (isActionGet(intent.getAction())) {
            LogUtils.d("======= get ======");
            String clip = ClipperUtils.getClipboardContent(context);
            if (!TextUtils.isEmpty(clip)) {
                LogUtils.d("======= res:" + clip + "=====");
                setResultCode(Activity.RESULT_OK);
                setResultData(clip);
            } else {
                setResultCode(Activity.RESULT_CANCELED);
                setResultData("");
                LogUtils.e("get error");
            }
        }
    }
}
