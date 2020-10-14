package com.tbs.trimplus.module.bible.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tbs.trimplus.utils.CacheUtil;

import java.util.ArrayList;
import java.util.List;

public class ReadMessageManage {

    public static final String READ_MESSAGE = "read_message";
    public static final String ARTICLE_IDS = "article_ids";

    //添加一条数据
    public static void setHaveRead(Context context, String string) {
        List<String> list = getHaveRead(context);
        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.contains(string)) {
            return;
        }

        list.add(0, string);

        String saveStr = JSON.toJSONString(list);
        CacheUtil.putString(context, READ_MESSAGE, ARTICLE_IDS, saveStr);
    }

    //删除所有已读
    public static void deleteHaveRead(Context context) {
        CacheUtil.putString(context, READ_MESSAGE, ARTICLE_IDS, "");
    }

    //获取所有已读
    public static List<String> getHaveRead(Context context) {
        String dataStr = CacheUtil.getString(context, READ_MESSAGE, ARTICLE_IDS);
        return JSONObject.parseArray(dataStr, String.class);
    }
}
