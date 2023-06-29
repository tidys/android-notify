package com.example.administrator.debugnative;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


public class FloatView {
    private static FloatView instance = null;

    public static FloatView getInstance(Context context) {
        if (instance == null) {
            synchronized (FloatView.class) {

                if (instance == null) {
                    instance = new FloatView(context);
                }
            }
        }
        return instance;
    }

    private Context context;
    private View popView;
    private WindowManager windowManager;

    private FloatView(Context context) {
        this.context = context;

        LayoutInflater layoutInflater = (LayoutInflater) ((Activity) context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.popView = layoutInflater.inflate(R.layout.notify, null);
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public void show() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(300, 300);
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        this.windowManager.addView(this.popView, params);
    }
}
