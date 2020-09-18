package com.tbs.trimplus.common.fastjson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.config.AppConfig;
import com.tbs.trimplus.utils.AppUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetData {

    private static String json = "";

    public static String smsCode(String phone) {
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile", phone)
                .add("debug", "1")
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(AppConfig.APP_SMS_CODE)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("发送短信验证码请求失败 " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                json = response.body().string();
                LogUtil.e("发送短信验证码请求成功 " + json);
            }
        });

        return json;

    }

    private static Bitmap bitmap;

    //获取图形验证码无法验证，该方法未使用
    public static Bitmap getPicCode() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(AppConfig.PIC_CODE_URL)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("获取图形验证码失败 " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e("获取图形验证码成功 ");
                InputStream inputStream = response.body().byteStream();//得到图片的流
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        });
        return bitmap;
    }


}
