package com.tbs.trimplus.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class NetUtil {

    /**
     * 判断网络是否可用
     * <p>
     * NetworkInfo在Api29中过时
     * NetworkCapabilities在23后才可以正常使用
     * 为了兼容到最低Api21，两种分情况进行判断
     */
    public static boolean isNetAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true;
                    }
                }
            } else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        // 当前网络是连接的
                        if (activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                            // 当前所连接的网络可用
                            return true;
                        }
                    }
                } catch (Exception e) {
                    Log.i("isNetAvailable", "" + e.getMessage());
                }
            }

        }

        return false;
    }
}
