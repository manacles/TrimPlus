package com.tbs.trimplus.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.module.login.bean.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {
    public static boolean isLogin(Context context) {
        String token = CacheUtil.getString(context, Constant.USER_INFO, "token");
        if ("".equals(token)) {
            return false;//未登录
        } else {
            return true;//已登陆
        }
    }

    public static void saveUserInfo(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        User.DataBean dataBean = user.getData();

        editor.putString("id", dataBean.getId());
        editor.putString("mark", dataBean.getMark());
        editor.putString("name", dataBean.getName());
        editor.putString("realname", dataBean.getRealname());
        editor.putString("cityid", dataBean.getCityid());
        editor.putString("icon", dataBean.getIcon());
        editor.putString("mobile", dataBean.getMobile());
        editor.putString("newuid", dataBean.getNewuid());
        editor.putString("nickname", dataBean.getNickname());
        editor.putString("cityname", dataBean.getCityname());
        editor.putString("province", dataBean.getProvince());
        editor.putString("uid", dataBean.getUid());
        editor.putString("cellphone", dataBean.getCellphone());
        editor.putString("type_id", dataBean.getType_id());
        editor.putString("is_modify_channel", dataBean.getIs_modify_channel());
        editor.putString("expected_cost", dataBean.getExpected_cost());
        editor.putString("login_time", dataBean.getLogin_time());
        editor.putString("token", dataBean.getToken());
        editor.apply();
    }

    /**
     * 获取当天的加密token
     * 加密规则：
     * 1.先将密码盐：zhj 进行md5加密；
     * 2.获取当天日期 如：2017-05-23；
     * 3.再对加密后的密码盐和当天日期进行md5加密；
     * 4.然后再对整体进行base64加密
     */
    public static String getDateToken() {
        String md5ZHJ = MD5Util.md5("zhj");//加密后的zhj
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String mTime = format.format(date);
        String s = MD5Util.md5(md5ZHJ + mTime);
        String dataToken = Base64Util.getBase64(s);
        return dataToken;
    }
}