package com.example.jh.testnicemusic.app;

import android.app.Application;
import android.content.Context;

import com.lzx.musiclibrary.manager.MusicManager;
import com.lzx.musiclibrary.utils.BaseUtil;

/**
 * Created by jinhui on 2018/5/5.
 * Email:1004260403@qq.com
 */
public class NiceMusicApplication extends Application {

    private static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        if (!BaseUtil.getCurProcessName(this).contains(":musicLibrary")) {
            MusicManager.get().setContext(this).init();
        }
    }

    public static Context getContext() {
        return sContext;
    }
}
