package com.tbs.trimplus.module.user.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.stetho.common.LogUtil;
import com.github.promeg.pinyinhelper.Pinyin;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.user.bean.City;
import com.tbs.trimplus.module.user.db.CityDbOpenHelper;
import com.tbs.trimplus.module.user.presenter.impl.GetCityPresenter;
import com.tbs.trimplus.module.user.view.IgetCityView;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.ToastUtil;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeCityActivity extends BaseActivity implements IgetCityView {

    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;

    GetCityPresenter getCityPresent;
    ArrayList<City> cityArrayList;
    List<HotCity> hotCities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);
        ButterKnife.bind(this);

        loadingLayout.setVisibility(View.VISIBLE);

        //获取城市信息
        getCityPresent = new GetCityPresenter(new Model(), this);
        getCityRequest();
    }

    private void getCityRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("gfd", AppUtil.getDateToken());
        getCityPresent.getCity(params);
    }

    @Override
    public void getCity(ResultList<City> cityResultList) {
        loadingLayout.setVisibility(View.GONE);
        if (cityResultList.getError_code().equals("0")) {
            cityArrayList = cityResultList.getData();

            setHotCity();
            modifyCityTable();
            showCityPicker();

        } else {
            ToastUtil.sToast(this, cityResultList.getMsg());
        }
    }

    private void setHotCity() {
        for (int i = 0; i < cityArrayList.size(); i++) {
            if (cityArrayList.get(i).getHot_flag().equals("1")) {
                hotCities.add(new HotCity(cityArrayList.get(i).getSimpname(), cityArrayList.get(i).toString(), cityArrayList.get(i).getCityid())); //code为城市代码
            }
        }
    }

    private void modifyCityTable() {

        /*
         * 获取城市数据，返回的参数     *   对应存在cities的字段
         * cityid : 324               *         c_code
         * simpname : 中卫            *         c_name
         * hot_flag : 0               *         c_province (cities表结构不改，)
         * isopen : 1                 *         c_province (整条记录信息存在c_province字段)
         * pinyin_sort : z            *         c_pinyin
         *
         *
         * CREATE TABLE "cities" (
         * [id] integer PRIMARY KEY AUTOINCREMENT,
         * [c_name] text,
         * [c_pinyin] text,
         * [c_code] varchar,
         * [c_province] text);
         * */

        SQLiteDatabase db = new CityDbOpenHelper(this).getWritableDatabase();
        String sql = "INSERT INTO cities (c_name, c_pinyin, c_code, c_province) VALUES (?,?,?,?)";
        db.beginTransaction();

        db.delete("cities", null, null);

        SQLiteStatement stmt = db.compileStatement(sql);
        for (int i = 0; i < cityArrayList.size(); i++) {
            stmt.bindString(1, cityArrayList.get(i).getSimpname());
            stmt.bindString(2, Pinyin.toPinyin(cityArrayList.get(i).getSimpname(), ""));
            stmt.bindString(3, cityArrayList.get(i).getCityid());
            stmt.bindString(4, cityArrayList.get(i).toString());
            stmt.execute();
            stmt.clearBindings();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void showCityPicker() {
        CityPicker.from(this) //activity或者fragment
                .enableAnimation(true)    //启用动画效果，默认无
                .setLocatedCity(null)  //APP自身已定位的城市，传null会自动定位（默认）
                .setHotCities(hotCities)    //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, com.zaaach.citypicker.model.City data) {
                        String cityString = data.getName();
                        if (cityString.contains("市") || cityString.contains("县")) {
                            cityString = cityString.substring(0, cityString.length() - 1);
                        }
                        LogUtil.e(cityString + "------------------");
                        goActivity(cityString);

                        finish();
                    }

                    @Override
                    public void onLocate() {

                        final String cityStr = getLocateCity();
                        Log.v("TAG", "cityStr:  " + cityStr);
                        //定位接口，需要APP自身实现，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.from(ChangeCityActivity.this)
                                        .locateComplete(new LocatedCity(cityStr, "", ""), LocateState.SUCCESS);
                            }
                        }, 100);
                    }

                    @Override
                    public void onCancel() {
                        ToastUtil.sToast(ChangeCityActivity.this, "取消选择");
                        finish();
                    }
                })
                .show();

    }

    /**
     * 跳转不同activity
     */
    private void goActivity(String city) {
        Intent intent = new Intent();
        intent.putExtra("city", city);
        LogUtil.e(city + "---------goActivity---------");
        setResult(Constant.CHANGE_USER_CITY_RESULTCODE, intent);
    }

    //定位获取城市信息
    private String getLocateCity() {
        String city = "";
        String locationProvider = null;
        int LOCATION_CODE = 301;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
            Log.v("TAG", "定位方式GPS");
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
            Log.v("TAG", "定位方式Network");
        } else {
            Log.v("TAG", "没有可用的位置提供器");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
            } else {
                //3.获取上次的位置，一般第一次运行，此值为null
                Location location = locationManager.getLastKnownLocation(locationProvider);
                if (location != null) {
                    city = getAddress(location);
                }
            }
        } else {
            Location location = locationManager.getLastKnownLocation(locationProvider);
            if (location != null) {
                city = getAddress(location);
            }
        }

        if (city.contains("市") || city.contains("县")) {
            city = city.substring(0, city.length() - 1);
        }
        if (TextUtils.isEmpty(city)) {
            city = "定位失败";
        }

        return city;
    }

    //获取地址信息:城市、街道等信息
    private String getAddress(Location location) {
        String city = "";
        List<Address> result;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(this, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (result.get(0).getLocality() != null && !TextUtils.isEmpty(result.get(0).getLocality())) {
                    city = result.get(0).getLocality();
                    Log.v("TAG", result.get(0).getLocality());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }


}
