package com.baidu.duer.dcs.sample.sdk.wakeup;

import android.util.Log;

public class LogUtil {
    public static final String TAG = "WakeUp";

    public static void i(String tag, String msg) {
        Log.i(TAG, tag + " - " + msg);
    }

    public static void d(String tag, String msg) {
        Log.d(TAG, tag + " - " + msg);
    }
}
