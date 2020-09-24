package com.tbs.trimplus.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.user.bean.City;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    /***
     * 判断电话号码是否合法
     * @param mContext
     * @param phoneNum
     * @return
     */
    public static boolean judgePhone(Context mContext, String phoneNum) {
        phoneNum = phoneNum.replaceAll("-", "");

        if ("".equals(phoneNum)) {
            ToastUtil.sToast(mContext, "请输入手机号");
            return false;
        } else {
            String MOBILE = "^1[3-9]\\d{9}$";
            Pattern pattern = Pattern.compile(MOBILE);
            Matcher matcher = pattern.matcher(phoneNum);
            boolean flag = matcher.matches();
            if (!flag) {
                Toast.makeText(mContext, "请输入合法电话号码", Toast.LENGTH_SHORT).show();
            }
            return matcher.matches();
        }
    }


    public static String getAppVersion(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {

        }
        return versionName;
    }


    public static void setCity(Context context, String city) {
        if (!TextUtils.isEmpty(city)) {
            if (city.contains("市") || city.contains("县")) {
                city = city.substring(0, city.length() - 1);
            }
        }
        CacheUtil.putString(context, Constant.CITY_PREFERENCE, Constant.CITY, city);
    }

    public static String getCity(Context context) {
        return CacheUtil.getString(context, Constant.CITY_PREFERENCE, Constant.CITY);
    }

    public static void setCityJson(Context context, String cityJson) {
        CacheUtil.putString(context, Constant.CITY_PREFERENCE, Constant.CITYJSON, cityJson);
    }

    public static String getCityJson(Context context) {
        return CacheUtil.getString(context, Constant.CITY_PREFERENCE, Constant.CITYJSON);
    }


}
