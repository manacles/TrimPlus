package com.tbs.trimplus.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void sToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void sToast(Context context, Integer message) {
        Toast.makeText(context, String.valueOf(message), Toast.LENGTH_SHORT).show();
    }

    public static void lToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void lToast(Context context, Integer message) {
        Toast.makeText(context, String.valueOf(message), Toast.LENGTH_LONG).show();
    }
}
