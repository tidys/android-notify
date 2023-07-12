package com.example.administrator.debugnative;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private String action;

    MyBroadcastReceiver(String action) {
        this.action = action;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(this.action)) {
            if (intent.hasExtra("imageCount")) {
                setResultData("10");
            } else if (intent.hasExtra("imageSave")) {
                setResultData("0");
            }
            abortBroadcast();
        }
    }
}
