package com.tbs.trimplus.utils;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.tbs.trimplus.common.fastjson.FastJsonConvertFactory;
import com.tbs.trimplus.config.AppConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ApiUtil {
    private static ApiUtil instance = new ApiUtil();

    public ApiUtil() {
    }

    public static ApiUtil getInstance() {
        return instance;
    }

    public OkHttpClient InterceptClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //这里可以builder(). 添加更多的内容 具体看需求
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return okHttpClient;
    }

    public <T> T createRetrofitApi(Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.TOBOSU_URL)
                .addConverterFactory(new FastJsonConvertFactory<T>())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(this.InterceptClient())
                .build();
        return retrofit.create(service);
    }
}
