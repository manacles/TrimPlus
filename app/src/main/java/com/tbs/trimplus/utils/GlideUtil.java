package com.tbs.trimplus.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class GlideUtil {
    public static final int CIRCLE_IMAGE = 0;//圆形
    public static final int ROUND_IMAGE = 1;//圆角

    /**
     * 处理原生的图片
     *
     * @param context
     * @param imgUrl
     * @param erroImg
     * @param emptyImg
     * @param imageView
     */
    public static void glideLoader(Context context, String imgUrl, int erroImg, int emptyImg, ImageView imageView) {
        if (!isDestory((Activity) context)){
            Glide.with(context).load(imgUrl).placeholder(emptyImg).error(erroImg).into(imageView);
        }
    }

    /**
     * 处理圆角图片或者圆形图片
     *
     * @param context
     * @param url
     * @param erroImg
     * @param emptyImg
     * @param iv
     * @param imageType 0==圆形图片   1==圆角图片
     */
    public static void glideLoader(final Context context, String url, int erroImg, int emptyImg, final ImageView iv, int imageType) {
        if (!isDestory((Activity) context)){
            if (CIRCLE_IMAGE == imageType) {
                Glide.with(context).asBitmap().load(url).centerCrop().into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        iv.setImageDrawable(circularBitmapDrawable);
                    }
                });
            } else if (ROUND_IMAGE == imageType) {
                Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).transform(new GlideRoundTransform(context, 10)).into(iv);
            }
        }
    }

    public static boolean isDestory(Activity activity) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return true;
        } else {
            return false;
        }
    }
}

