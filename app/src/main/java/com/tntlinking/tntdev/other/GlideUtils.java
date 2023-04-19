package com.tntlinking.tntdev.other;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.glide.GlideApp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by Dan on 2022/8/4.
 */

public class GlideUtils {

    /**
     * 加载图片
     *
     * @param activity
     * @param url
     * @param view
     */
    public static void loadImage(@NonNull Activity activity, String url, ImageView view) {
        GlideApp.with(activity).load(url).into(view);
    }

    public static void loadImage(@NonNull Activity activity, int res, ImageView view) {
        GlideApp.with(activity).load(res).into(view);
    }

    public static void loadImage(@NonNull Context context, String url, ImageView view) {
        GlideApp.with(context).load(url).into(view);
    }

    public static void loadImage(@NonNull Context context, int res, ImageView view) {
        GlideApp.with(context).load(res).into(view);
    }

    public static void loadImage(@NonNull FragmentActivity activity, String url, ImageView view) {
        GlideApp.with(activity).load(url).into(view);
    }

    public static void loadImage(@NonNull FragmentActivity activity, int res, ImageView view) {
        GlideApp.with(activity).load(res).into(view);
    }

    public static void loadImage(@NonNull Fragment fragment, String url, ImageView view) {
        GlideApp.with(fragment).load(url).into(view);
    }


    /**
     * 圆形图片
     *
     * @param activity
     * @param url
     * @param view
     */
    public static void loadCircle(@NonNull Activity activity, String url, ImageView view) {
        // 显示圆形的 ImageView
        GlideApp.with(activity)
                .load(url)
                .error(R.drawable.icon_default_avatar)
                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(view);
    }

    public static void loadCircle(@NonNull Context activity, String url, ImageView view) {
        // 显示圆形的 ImageView
        GlideApp.with(activity)
                .load(url)
                .error(R.drawable.icon_default_avatar)
                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(view);
    }

    public static void loadCircle(@NonNull Fragment activity, String url, ImageView view) {
        // 显示圆形的 ImageView
        GlideApp.with(activity)
                .load(url)
                .error(R.drawable.icon_default_avatar)
                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(view);
    }

    public static void loadCircle(@NonNull FragmentActivity activity, String url, ImageView view) {
        // 显示圆形的 ImageView
        GlideApp.with(activity)
                .load(url)
                .error(R.drawable.icon_default_avatar)
                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(view);
    }


    public static void loadCircle(@NonNull Activity activity, int res, ImageView view) {
        // 显示圆形的 ImageView
        GlideApp.with(activity)
                .load(res)
                .error(R.drawable.icon_default_avatar)
                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(view);
    }

    public static void loadCircle(@NonNull Context activity, int res, ImageView view) {
        // 显示圆形的 ImageView
        GlideApp.with(activity)
                .load(res)
                .error(R.drawable.icon_default_avatar)
                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(view);
    }

    public static void loadCircle(@NonNull Fragment activity, int res, ImageView view) {
        // 显示圆形的 ImageView
        GlideApp.with(activity)
                .load(res)
                .error(R.drawable.icon_default_avatar)
                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(view);
    }

    public static void loadCircle(@NonNull FragmentActivity activity, int res, ImageView view) {
        // 显示圆形的 ImageView
        GlideApp.with(activity)
                .load(res)
                .error(R.drawable.icon_default_avatar)
                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(view);
    }

    /**
     * 圆角 的图片
     *
     * @param activity
     * @param res
     * @param view
     * @param roundedCorners
     */
    public static void loadRoundCorners(@NonNull Activity activity, int res, ImageView view, int roundedCorners) {
        // 显示圆角的 ImageView
        GlideApp.with(activity)
                .load(res)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) roundedCorners)))
                .into(view);
    }

    public static void loadRoundCorners(@NonNull Context activity, int res, ImageView view, int roundedCorners) {
        // 显示圆角的 ImageView
        GlideApp.with(activity)
                .load(res)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) roundedCorners)))
                .into(view);
    }

    public static void loadRoundCorners(@NonNull Fragment activity, int res, ImageView view, int roundedCorners) {
        // 显示圆角的 ImageView
        GlideApp.with(activity)
                .load(res)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) roundedCorners)))
                .into(view);
    }

    public static void loadRoundCorners(@NonNull FragmentActivity activity, int res, ImageView view, int roundedCorners) {
        // 显示圆角的 ImageView
        GlideApp.with(activity)
                .load(res)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) roundedCorners)))
                .into(view);
    }

    public static void loadRoundCorners(@NonNull Activity activity, String res, ImageView view, int roundedCorners) {
        // 显示圆角的 ImageView
        GlideApp.with(activity)
                .load(res)
                .error(R.drawable.app_logo)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) roundedCorners)))
                .into(view);
    }

    public static void loadRoundCorners(@NonNull Context activity, String res, ImageView view, int roundedCorners) {
        // 显示圆角的 ImageView
        GlideApp.with(activity)
                .load(res)
                .error(R.drawable.app_logo)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) roundedCorners)))
                .into(view);
    }

    public static void loadRoundCorners(@NonNull Fragment activity, String res, ImageView view, int roundedCorners) {
        // 显示圆角的 ImageView
        GlideApp.with(activity)
                .load(res)
                .error(R.drawable.app_logo)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) roundedCorners)))
                .into(view);
    }

    public static void loadRoundCorners(@NonNull FragmentActivity activity, String res, ImageView view, int roundedCorners) {
        // 显示圆角的 ImageView
        GlideApp.with(activity)
                .load(res)
                .error(R.drawable.app_logo)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) roundedCorners)))
                .into(view);
    }

}
