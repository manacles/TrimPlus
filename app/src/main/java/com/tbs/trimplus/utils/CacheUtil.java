package com.tbs.trimplus.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.facebook.stetho.common.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
 * 缓存软件的一些参数和数据
 * */
public class CacheUtil {
    /**
     * 得到缓存值
     */
    public static boolean getBoolean(Context context, String root, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(root, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * 保存软件参数
     */
    public static void putBoolean(Context context, String root, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(root, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 缓存文本数据
     */
    public static void putString(Context context, String root, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(root, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * 得到缓存的文本数据
     */
    public static String getString(Context context, String root, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(root, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}