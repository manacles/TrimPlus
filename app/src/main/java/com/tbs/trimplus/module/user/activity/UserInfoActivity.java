package com.tbs.trimplus.module.user.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.facebook.stetho.common.LogUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.config.AppConfig;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.user.bean.UserInfo;
import com.tbs.trimplus.module.user.presenter.impl.GetUserInfoDataPresenter;
import com.tbs.trimplus.module.user.view.IgetUserInfoDataView;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.GlideUtil;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.utils.WriteUtil;
import com.tbs.trimplus.utils.picselector.GlideEngine;
import com.tbs.trimplus.view.BottomListPopupWindow;
import com.tbs.trimplus.view.CustomSelectDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInfoActivity extends BaseActivity implements IgetUserInfoDataView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_userinfo_name)
    TextView tvUserinfoName;
    @BindView(R.id.ll_userinfo_name)
    LinearLayout llUserinfoName;
    @BindView(R.id.iv_userinfo_icon)
    ImageView ivUserinfoIcon;
    @BindView(R.id.ll_userinfo_icon)
    LinearLayout llUserinfoIcon;
    @BindView(R.id.tv_userinfo_sex)
    TextView tvUserinfoSex;
    @BindView(R.id.ll_userinfo_sex)
    LinearLayout llUserinfoSex;
    @BindView(R.id.tv_userinfo_city)
    TextView tvUserinfoCity;
    @BindView(R.id.ll_userinfo_city)
    LinearLayout llUserinfoCity;
    @BindView(R.id.tv_userinfo_decorate_type)
    TextView tvUserinfoDecorateType;
    @BindView(R.id.ll_userinfo_decorate_type)
    LinearLayout llUserinfoDecorateType;
    @BindView(R.id.ll_userinfo_sign_out)
    LinearLayout llUserinfoSignOut;

    private GetUserInfoDataPresenter getUserInfoDataPresenter;
    private UserInfo userInfo;
    private String uid;

    private BottomListPopupWindow sexPopupWindow;
    private BottomListPopupWindow decorateTypePopupWindow;
    private BottomListPopupWindow iconPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        initToolbar(toolbar);
        toolbarTitle.setText("个人信息");

        uid = CacheUtil.getString(this, Constant.USER_INFO, "uid");

        initView();
        initListenter();

        getUserInfoDataPresenter = new GetUserInfoDataPresenter(new Model(), this);
        getUserInfoDataRequest();
    }

    private void initView() {
        String mark = CacheUtil.getString(this, Constant.USER_INFO, "mark");
        if (mark.equals("3")) {
            llUserinfoSex.setVisibility(View.GONE);
            llUserinfoDecorateType.setVisibility(View.GONE);
        }
    }

    private void initListenter() {
        //修改昵称
        llUserinfoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, ChangeUserNameActivity.class);
                intent.putExtra("username", tvUserinfoName.getText().toString().trim());
                startActivityForResult(intent, Constant.CHANGE_USERNAME_REQUESTCODE);
            }
        });

        //修改性别
        llUserinfoSex.setOnClickListener(new View.OnClickListener() {
            String[] data = Constant.SEX_DATA;

            @Override
            public void onClick(View v) {
                sexPopupWindow = new BottomListPopupWindow(UserInfoActivity.this, "选择性别", data);
                sexPopupWindow.showAtLocation(UserInfoActivity.this.findViewById(R.id.userinfo_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                sexPopupWindow.setOnItemClickListener(new BottomListPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (tvUserinfoSex.getText().toString().trim().equals(data[position])) {
                            return;
                        }
                        setGenderRequest(position);
                    }
                });
            }
        });

        //城市
        llUserinfoCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, ChangeCityActivity.class);
                startActivityForResult(intent, Constant.CHANGE_USER_CITY_RESULTCODE);
            }
        });

        //装修阶段
        llUserinfoDecorateType.setOnClickListener(new View.OnClickListener() {
            String[] data = Constant.DECORATETYPE_DATA;

            @Override
            public void onClick(View v) {
                decorateTypePopupWindow = new BottomListPopupWindow(UserInfoActivity.this,
                        "装修阶段", data);
                decorateTypePopupWindow.showAtLocation(UserInfoActivity.this.findViewById(R.id.userinfo_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                decorateTypePopupWindow.setOnItemClickListener(new BottomListPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (tvUserinfoDecorateType.getText().toString().trim().equals(data[position])) {
                            return;
                        }
                        setDecorateRequest(position);
                    }
                });

            }
        });

        //修改头像
        llUserinfoIcon.setOnClickListener(new View.OnClickListener() {
            String[] data = Constant.ICON_DATA;

            @Override
            public void onClick(View v) {
                iconPopupWindow = new BottomListPopupWindow(UserInfoActivity.this,
                        "", data);
                iconPopupWindow.showAtLocation(UserInfoActivity.this.findViewById(R.id.userinfo_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                iconPopupWindow.setOnItemClickListener(new BottomListPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        setIcon(position);
                    }
                });
            }
        });

        //退出登录
        llUserinfoSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomSelectDialog signOutDialog = new CustomSelectDialog(UserInfoActivity.this,
                        "您确定退出吗？", 1, R.style.custom_dialog) {

                    @Override
                    public void onSureClick() {
                        SharedPreferences sharedPreferences = UserInfoActivity.this.getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
                        sharedPreferences.edit().clear().apply();
                        ToastUtil.sToast(UserInfoActivity.this, "退出成功");
                        finish();
                    }
                };
                signOutDialog.show();
            }
        });


    }

    private void getUserInfoDataRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("uid", uid);
        getUserInfoDataPresenter.getUserInfoData(params);
    }

    @Override
    public void getUserInfoData(BaseObject<UserInfo> userInfoBaseObject) {
        if (userInfoBaseObject.getStatus().equals("200")) {
            LogUtil.e(userInfoBaseObject.toString());

            userInfo = userInfoBaseObject.getData();
            showData();
        } else {
            ToastUtil.sToast(this, userInfoBaseObject.getMsg());
        }
    }

    @Override
    public void DialogStatus(int status, String error) {

    }

    private void showData() {
        String icon = CacheUtil.getString(this, Constant.USER_INFO, "icon");
        GlideUtil.glideLoader(this, icon, R.drawable.icon_head_default, R.drawable.icon_head_default,
                ivUserinfoIcon, GlideUtil.CIRCLE_IMAGE);
        tvUserinfoName.setText(userInfo.getName());
        //如果在其他客户端更改了用户名，在这里进行缓存更新
        CacheUtil.putString(this, Constant.USER_INFO, "name", userInfo.getName());
        tvUserinfoSex.setText(userInfo.getGender());
        tvUserinfoCity.setText(userInfo.getCity());
        tvUserinfoDecorateType.setText(userInfo.getDecorate_type());
    }

    //设置性别
    private void setGenderRequest(int sex) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("token", AppUtil.getDateToken());
        param.put("uid", uid);
        param.put("gender", sex);
        getUserInfoDataPresenter.setGender(param, sex);
    }

    @Override
    public void setGender(BaseObject baseObject, int sex) {
        if (baseObject.getStatus().equals("200")) {
            tvUserinfoSex.setText(Constant.SEX_DATA[sex]);
            CacheUtil.putString(this, Constant.USER_INFO, "gender", Constant.SEX_DATA[sex]);
        }
        LogUtil.e(baseObject.toString());
    }

    //设置装修阶段
    private void setDecorateRequest(int decorateType) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("decorate_type", decorateType + 1);
        param.put("token", AppUtil.getDateToken());
        param.put("uid", uid);
        getUserInfoDataPresenter.setDecorate(param, decorateType);
    }

    @Override
    public void setDecorate(BaseObject baseObject, int decorateType) {
        if (baseObject.getStatus().equals("200")) {
            tvUserinfoDecorateType.setText(Constant.DECORATETYPE_DATA[decorateType]);
            CacheUtil.putString(this, Constant.USER_INFO, "decorate_type", Constant.DECORATETYPE_DATA[decorateType]);
        }
        LogUtil.e(baseObject.toString());
    }

    //设置头像position: 0拍照;1从相册选择;
    private void setIcon(int position) {
        if (position == 0) {

            PictureSelector.create(this)
                    .openCamera(PictureMimeType.ofImage())
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .selectionMode(PictureConfig.SINGLE)
                    .isSingleDirectReturn(true)
                    .isEnableCrop(true)// 是否裁剪
                    .withAspectRatio(1, 1)
                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            // 结果回调
                            System.out.println("--------上传来到这里1-------");

                            urlpath = result.get(0).getCutPath();
                            LogUtil.e(urlpath);
                            setPicToView();

                        }

                        @Override
                        public void onCancel() {
                            // 取消
                        }
                    });
        } else if (position == 1) {

            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .selectionMode(PictureConfig.SINGLE)
                    .isSingleDirectReturn(true)
                    .isEnableCrop(true)// 是否裁剪
                    .withAspectRatio(1, 1)
                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            // 结果回调
                            System.out.println("--------上传来到这里1-------");

                            urlpath = result.get(0).getCutPath();
                            LogUtil.e(urlpath);
                            setPicToView();

                        }

                        @Override
                        public void onCancel() {
                            // 取消
                        }
                    });
        }

    }

    private String urlpath;     //PictureSelector裁剪后图片的本地路径
    private ProgressDialog pd;
    private String resultStr = "";
    private String pic_url;

    /***
     * 上传用户头像至服务器
     */
    private void setPicToView() {
        if (urlpath != null) {
            pd = ProgressDialog.show(UserInfoActivity.this, null, "正在上传图片，请稍候...");
            System.out.println("--------上传来到这里2-------");
            // 开启线程上传
            new Thread(uploadImageRunnable).start();
        }
    }

    Runnable uploadImageRunnable = new Runnable() {
        @Override
        public void run() {
            Looper.prepare();

            Map<String, String> textParams;
            Map<String, File> fileparams;
            try {
                URL url = new URL(AppConfig.UPLOAD_ICON);
                textParams = new HashMap<>();
                fileparams = new HashMap<>();
                File file = new File(urlpath);
                fileparams.put("filedata", file);
                textParams.put("s_code", "head_file");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setUseCaches(false);
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("ser-Agent", "Fiddler");
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + WriteUtil.BOUNDARY);
                OutputStream os = conn.getOutputStream();
                DataOutputStream ds = new DataOutputStream(os);
                WriteUtil.writeStringParams(textParams, ds);
                WriteUtil.writeFileParams(fileparams, ds);
                WriteUtil.paramsEnd(ds);
                // 对文件流操作完,要记得及时关闭
                os.close();
                // 服务器返回的响应吗
                int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
                // 对响应码进行判断
                if (code == 200) {// 返回的响应码200,是成功
                    // 得到网络返回的输入流
                    InputStream is = conn.getInputStream();
                    resultStr = WriteUtil.readString(is);
                    System.out.println("--------上传来到这里3-------resultStr  " + resultStr);
                } else {
                    handlerModifyIcon.sendEmptyMessage(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                handlerModifyIcon.sendEmptyMessage(1);
            }

            handlerUpload.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler

        }
    };

    private Handler handlerUpload = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pd.dismiss();
                    System.out.println("--------上传来到这里4-------");
                    try {
                        final JSONObject jsonObject = new JSONObject(resultStr);
                        System.out.println("--------上传来到这里5-------" + jsonObject.optString("error"));

//                                            {
//                                                "error":0,
//                                                    "url":"http://cdn01.tobosu.net/head_file/2016-11-18/582eb8e4b75c1.jpg"
//                                            }

                        if (jsonObject.optString("error").equals("0")) {
                            BitmapFactory.Options option = new BitmapFactory.Options();
                            option.inSampleSize = 1;
                            pic_url = jsonObject.optString("url");

                            RequestBody requestBody = new FormBody.Builder()
                                    .add("field", "5")
                                    .add("new", pic_url)
                                    .add("token", CacheUtil.getString(UserInfoActivity.this, Constant.USER_INFO, "token"))
                                    .build();
                            OkHttpClient okHttpClient = new OkHttpClient();
                            final Request request = new Request.Builder()
                                    .url(AppConfig.UPLOADHEADPICTUREURL)
                                    .post(requestBody)
                                    .build();
                            Call call = okHttpClient.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    System.out.println("-------上传失败了------");
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String json = response.body().string();
                                    System.out.println("-------json------" + json);
                                    JSONObject obj;
                                    try {
                                        obj = new JSONObject(json);

                                        if ("操作成功！".equals(obj.getString("msg"))) {

                                            System.out.println("-------pic_url------" + pic_url);

                                            CacheUtil.putString(UserInfoActivity.this, Constant.USER_INFO, "icon", pic_url);

                                            handlerModifyIcon.sendEmptyMessage(0);

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.optString("url"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

                default:
                    break;
            }
            return false;
        }
    });

    private Handler handlerModifyIcon = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                ToastUtil.sToast(UserInfoActivity.this, "操作成功！");
                GlideUtil.glideLoader(UserInfoActivity.this, pic_url, R.drawable.icon_head_default, R.drawable.icon_head_default,
                        ivUserinfoIcon, GlideUtil.CIRCLE_IMAGE);
            } else {
                ToastUtil.sToast(UserInfoActivity.this, "上传图片失败！");
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constant.CHANGE_USERNAME_REQUESTCODE:
                if (data != null) {
                    tvUserinfoName.setText(data.getStringExtra("newname"));
                }
                break;
            default:
                break;

        }
    }
}
