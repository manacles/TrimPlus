package com.tbs.trimplus.module.bible.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tbs.trimplus.utils.CacheUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryManage {

    public static final String SEARCH_HISTORY = "search_history";
    public static final String HISTORY_DATA = "history_data";

    //添加一条数据
    public static void setHistory(Context context, String string) {
        List<String> list = getHistory(context);
        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.contains(string)) {
            return;
        }

        list.add(0, string);

        String saveStr;
        if (list.size() > 14) {
            List<String> list2 = list.subList(0, 14);
            saveStr = JSON.toJSONString(list2);
        } else {
            saveStr = JSON.toJSONString(list);
        }
        CacheUtil.putString(context, SEARCH_HISTORY, HISTORY_DATA, saveStr);
    }

    //删除所有记录
    public static void deleteHistory(Context context) {
        CacheUtil.putString(context, SEARCH_HISTORY, HISTORY_DATA, "");
    }

    //获取所有记录
    public static List<String> getHistory(Context context) {
        String dataStr = CacheUtil.getString(context, SEARCH_HISTORY, HISTORY_DATA);
        return JSONObject.parseArray(dataStr, String.class);
    }
}
