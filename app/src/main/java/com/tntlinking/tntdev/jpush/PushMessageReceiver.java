package com.tntlinking.tntdev.jpush;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tntlinking.tntdev.ui.activity.AuditionDetailActivity;
import com.tntlinking.tntdev.ui.activity.JobDetailsActivity;


import org.json.JSONException;
import org.json.JSONObject;


import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";
    private String MessageType;
    private String TypeId;

    @SuppressLint("LogNotTimber")
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG, "[onMessage] " + customMessage);
        Intent intent = new Intent("com.jiguang.demo.message");
        intent.putExtra("msg", customMessage.message);
        context.sendBroadcast(intent);
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageOpened] " + message);
        //**************解析推送过来的json数据******************
        JSONObject jsonObject;
        try {

            jsonObject = new JSONObject(message.notificationExtras);

            String extra = jsonObject.getString("extra");
            Gson gson = new Gson();
            // 直接转换
            ExtraBean extraBean = new Gson().fromJson(extra, new TypeToken<ExtraBean>() {
            }.getType());
            Log.e("extra", ">>" + extra);
            Log.e("extra", ">>" + extraBean.getMessageType());

            MessageType = extraBean.getMessageType();
            TypeId = extraBean.getTypeId();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (MessageType) {
            case "0":
            case "1":
                //打开职位推荐详情页面（目前统一跳转职位推荐详情页面）
                //打开职位上新页面（目前统一跳转职位推荐详情页面）
                Intent i = new Intent(context, JobDetailsActivity.class);
                i.putExtra("positionId", TypeId);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
                break;
            case "2":
            case "3":
            case "4":
                //打开面试开始前提醒页面（目前没有这个前面需要统一跳转面试详情页面）
                //打开面试创建提示页面（目前没有这个前面需要统一跳转面试详情页面）
                //打开面试邀约详情页面（目前没有这个前面需要统一跳转面试详情页面）
                Intent intent2 = new Intent(context, AuditionDetailActivity.class);
                intent2.putExtra("interviewId", TypeId);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent2);
                break;
        }
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if (nActionExtra == null) {
            Log.d(TAG, "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        switch (nActionExtra) {
            case "my_extra1":
                Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一");
                break;
            case "my_extra2":
                Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二");
                break;
            case "my_extra3":
                Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三");
                break;
            default:
                Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义");
                break;
        }
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived] " + message);
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e("QWER", ">>>" + registrationId);

        Intent intent = new Intent("com.jiguang.demo.message");
        intent.putExtra("rid", registrationId);
        context.sendBroadcast(intent);
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        super.onNotificationSettingsCheck(context, isOn, source);
        Log.e(TAG, "[onNotificationSettingsCheck] isOn:" + isOn + ",source:" + source);
    }

}
