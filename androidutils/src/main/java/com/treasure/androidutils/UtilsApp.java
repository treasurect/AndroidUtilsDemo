package com.treasure.androidutils;

import android.app.Application;
import android.content.Context;

/**
 * Created by treasure on 2018/5/2.
 * <p>
 * ------->   treasure <-------
 */

public class UtilsApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
