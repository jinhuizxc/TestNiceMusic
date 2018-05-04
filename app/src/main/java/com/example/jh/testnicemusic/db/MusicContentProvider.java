package com.example.jh.testnicemusic.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by xian on 2018/1/22.
 */

public class MusicContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.lzx.nicemusic.db.provider";
    public static final Uri SONG_LIST_URI = Uri.parse("content://" + AUTHORITY + "/songs_list");
    public static final Uri FAVORITES_URI = Uri.parse("content://" + AUTHORITY + "/favorites");
    public static final int SONG_LIST_CODE = 0;
    public static final int FAVORITES_CODE = 1;

    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;
    private UriMatcher mUriMatcher;
    private Context mContext;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDbHelper = new DbHelper(mContext);
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, "songs_list", SONG_LIST_CODE);
        mUriMatcher.addURI(AUTHORITY, "favorites", FAVORITES_CODE);
        mDb = mDbHelper.getWritableDatabase();
        return true;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mUriMatcher.match(uri)) {
            case SONG_LIST_CODE:
                tableName = DbConstants.TABLE_SONG_LIST;
                break;
            case FAVORITES_CODE:
                tableName = DbConstants.TABLE_FAVORITES;
                break;
            default:
                break;
        }
        return tableName;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        String tableName = getTableName(uri);
        mDb.insert(tableName, null, contentValues);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        int count = mDb.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        int row = mDb.update(tableName, contentValues, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
