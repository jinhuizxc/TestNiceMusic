package com.example.jh.testnicemusic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xian on 2018/1/22.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String name = "music_db";
    private static final int version = 7;
    private static volatile DbHelper instance;

    public DbHelper(Context context) {
        super(context, name, null, version);
    }

    /**
     * 播放列表
     */
    private final String CREATE_TABLE_SONG_LIST = "create table "
            + DbConstants.TABLE_SONG_LIST + " ( "
            + DbConstants.MUSIC_ID + " text, "
            + DbConstants.MUSIC_TITLE + " text, "
            + DbConstants.ALBUM_TITLE + " text, "
            + DbConstants.DURATION + " text, "
            + DbConstants.URL + " text, "
            + DbConstants.COVER + " text, "
            + DbConstants.ARTIST + " text);";

    /**
     * 我的歌单
     */
    private final String CREATE_TABLE_FAVORITS = "create table "
            + DbConstants.TABLE_FAVORITES + " ( "
            + DbConstants.MUSIC_ID + " text, "
            + DbConstants.MUSIC_TITLE + " text, "
            + DbConstants.ALBUM_TITLE + " text, "
            + DbConstants.DURATION + " text, "
            + DbConstants.URL + " text, "
            + DbConstants.COVER + " text, "
            + DbConstants.ARTIST + " text);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SONG_LIST);
        db.execSQL(CREATE_TABLE_FAVORITS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    instance = new DbHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }
}