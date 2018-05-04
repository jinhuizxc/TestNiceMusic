package com.example.jh.testnicemusic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by xian on 2018/1/13.
 */

public class GlideUtil {

    public static void loadImageByUrl(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImageByUrl(Context context, int drawable, ImageView imageView) {
        Glide.with(context).load(drawable).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadBlurImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap blurBitmap = FastBlurUtil.doBlur(resource, 20, true);
                        imageView.setImageBitmap(blurBitmap);
                    }
                });
    }

}
