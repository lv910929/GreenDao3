package com.lv.greendao3;

import android.app.Application;

import com.lv.greendao3.data.DbManager;

/**
 * Created by Lv on 2017/2/27.
 */

public class MyApp extends Application {

    private static MyApp INSTANCE;

    public static MyApp getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        DbManager.initDatabase(this);
    }
}
