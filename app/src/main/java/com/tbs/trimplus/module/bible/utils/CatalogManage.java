package com.tbs.trimplus.module.bible.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;

import java.util.ArrayList;

public class CatalogManage {

    //设置bible目录
    public static void setDefaultCatalog(Context context, ArrayList<Catalog> catalogs) {
        ArrayList<Catalog> arrayList = new ArrayList<>();
        Catalog catalog1 = new Catalog("99", "推荐", true);
        arrayList.add(catalog1);
        Catalog catalog2 = new Catalog("99", "本地", true);
        arrayList.add(catalog2);
        arrayList.addAll(catalogs);

        String string = JSON.toJSONString(arrayList);
        CacheUtil.putString(context, Constant.BIBLE_CATALOG, "mCatalog", string);
    }

    //获取勾选/未勾选的Catalog
    public static ArrayList<Catalog> getSelectedCatalog(Context context, boolean isSelected) {

        String catalogStr = CacheUtil.getString(context, Constant.BIBLE_CATALOG, "mCatalog");
        ArrayList<Catalog> catalogs = (ArrayList<Catalog>) JSONObject.parseArray(catalogStr, Catalog.class);

        if (catalogs == null || catalogs.size() <= 0) {
            return null;
        }

        ArrayList<Catalog> selectedCatalogs = new ArrayList<>();
        for (int i = 0; i < catalogs.size(); i++) {
            Catalog catalog = catalogs.get(i);
            String id = catalog.getId();
            String name = catalog.getName();
            boolean selected = catalog.isSelected();

            if (isSelected == selected) {
                Catalog newcatalog = new Catalog(id, name, isSelected);
                selectedCatalogs.add(newcatalog);
            }
        }
        return selectedCatalogs;
    }
}
