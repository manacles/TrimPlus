package com.tbs.trimplus.module.user.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CityDbOpenHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "china_cities_v2.db";
    private static final int VERSION = 1;

    public CityDbOpenHelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //支持的数据类型：整型数据、字符串类型、日期类型、二进制的数据类型
        db.execSQL("CREATE TABLE cities (\n" +
                "                [id] integer PRIMARY KEY AUTOINCREMENT,\n" +
                "  [c_name] text,\n" +
                "  [c_pinyin] text,\n" +
                "  [c_code] varchar,\n" +
                "  [c_province] text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
