package com.ivanwooll.floatinglabelvalidator.lib;

import android.app.Application;
import android.content.res.Resources;

/**
 * Created by Krzysztof on 11/11/2016.
 */

public class MyApplication extends Application {
    public static Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();
        resources = getResources();
    }
}
