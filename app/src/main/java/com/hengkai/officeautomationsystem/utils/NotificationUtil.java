package com.hengkai.officeautomationsystem.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.hengkai.officeautomationsystem.R;

/**
 * Created by Harry on 2018/7/20.
 * 通知栏工具类, 用于显示下载进度
 */
public class NotificationUtil {

    private final NotificationManager mNotificationManager;
    private final NotificationCompat.Builder mBuilder;
    String channelID = "hk_channel_id";

    public NotificationUtil(Context mContext) {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelID = "hk_channel_id";
            NotificationChannel notificationChannel = new NotificationChannel(channelID, "下载", NotificationManager.IMPORTANCE_DEFAULT);
//            // 配置通知渠道的属性
//            mChannel.setDescription(description);
//            // 设置通知出现时的闪灯（如果 android 设备支持的话）
//            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
//            // 设置通知出现时的震动（如果 android 设备支持的话）
//            mChannel.enableVibration(true);
//            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            mBuilder = new NotificationCompat.Builder(mContext, channelID);
        } else {
            mBuilder = new NotificationCompat.Builder(mContext);
        }
    }

    public void prepare() {
        mBuilder.setContentTitle("正在下载新版恒凯OA")
                .setContentText("下载中")
                .setSmallIcon(R.mipmap.ic_launcher);
    }

    public void start(int progress) {
        mBuilder.setProgress(100, progress, false);
        mNotificationManager.notify(100, mBuilder.build());
    }

    public void complete() {
        mBuilder.setContentText("下载完成")
                // 移除进度条
                .setProgress(0, 0, false);
        mNotificationManager.notify(100, mBuilder.build());
    }

    public void cancel() {
        mNotificationManager.cancel(100);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mNotificationManager.deleteNotificationChannel(channelID);
        }
    }
}
