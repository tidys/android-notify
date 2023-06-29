package com.example.administrator.debugnative;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class LeakNotify {
    public static String ACTION_START = "start";
    public static String ACTION_END = "end";
    private static LeakNotify instance = null;

    public static LeakNotify getInstance() {
        if (null == instance) {
            instance = new LeakNotify();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show(Context context) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notify);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        String channel = "notify channel";
        final int notifyID = 1;
        final Notification.Builder builder = new Notification.Builder(context, channel);

        Runnable ff=()->{
            System.out.println("11");
        };

        BroadcastReceiver buttonReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(LeakNotify.ACTION_START)) {
                    Toast.makeText(context, "111", Toast.LENGTH_LONG).show();
                    views.setTextViewText(R.id.text, "start");

                    views.setViewVisibility(R.id.start, View.GONE);
                    views.setViewVisibility(R.id.end, View.VISIBLE);

                } else if (action.equals(LeakNotify.ACTION_END)) {
                    Toast.makeText(context, "end", Toast.LENGTH_LONG).show();
                    views.setTextViewText(R.id.text, "end");


                    views.setViewVisibility(R.id.start, View.VISIBLE);
                    views.setViewVisibility(R.id.end, View.GONE);

                }
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(notifyID, builder.build());
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LeakNotify.ACTION_START);
        intentFilter.addAction(LeakNotify.ACTION_END);
        context.registerReceiver(buttonReceiver, intentFilter);


        views.setOnClickPendingIntent(R.id.start, PendingIntent.getBroadcast(context, 0, new Intent(LeakNotify.ACTION_START), 0));
        views.setOnClickPendingIntent(R.id.end, PendingIntent.getBroadcast(context, 0, new Intent(LeakNotify.ACTION_END), 0));//为啥这个无法响应


        builder.setOngoing(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(views);

        NotificationChannel notificationChannel = new NotificationChannel(channel, "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(notifyID, builder.build());
    }
}
