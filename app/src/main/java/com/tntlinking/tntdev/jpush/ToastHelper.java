package com.tntlinking.tntdev.jpush;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.hjq.toast.ToastUtils;
import com.tntlinking.tntdev.R;


public class ToastHelper {
    private ToastHelper() {
    }


    public static void showOk(Context context, String s) {
        View v = LayoutInflater.from(context).inflate(R.layout.d_toast_customs, null);
        ImageView imageView = v.findViewById(R.id.iv_icon);
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d_ic_toast_ok));
//        ToastUtils.setView(v);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.show(s);
    }

    public static void showOther(Context context, String s) {
        View v = LayoutInflater.from(context).inflate(R.layout.d_toast_customs, null);
        ImageView imageView = v.findViewById(R.id.iv_icon);
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d_ic_toast_other));
//        ToastUtils.setView(v);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.show(s);
    }
}
