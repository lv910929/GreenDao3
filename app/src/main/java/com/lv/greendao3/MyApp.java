package com.lv.greendao3;

import android.app.Application;

import com.lv.greendao3.data.DbManager;
import com.lv.greendao3.utils.CharacterParser;
import com.lv.greendao3.utils.PinyinComparator;

/**
 * Created by Lv on 2017/2/27.
 */

public class MyApp extends Application {

    private static MyApp INSTANCE;

    public static MyApp getInstance() {
        return INSTANCE;
    }

    public static CharacterParser characterParser;
    public static PinyinComparator pinyinComparator;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        DbManager.initDatabase(this);
    }
}
