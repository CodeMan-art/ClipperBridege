package com.codeman.clipperbridge.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

public class ClipperUtils {
    /**
     * 获取剪贴板内容
     **/
    public static String getClipboardContent(Context context) {
        if (context == null) {
            return "";
        }
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) {
            return "";
        }
        ClipData data = clipboardManager.getPrimaryClip();
        if (data == null || data.getItemCount() == 0) {
            return "";
        }
        ClipData.Item item = data.getItemAt(0);
        if (item == null) {
            return "";
        }
        CharSequence copyText = item.getText();
        if (TextUtils.isEmpty(copyText)) {
            return "";
        }
        return copyText.toString();
    }


    /**
     * 设置剪贴板内容
     * @return true: 设置成功  false: 设置失败
     */
    public static boolean setClipboardContent(Context context,CharSequence text){
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            ClipData data = ClipData.newPlainText("ClipperBridge", text);
            clipboardManager.setPrimaryClip(data);
            return  true;
        }
        return  false;
    }
}
