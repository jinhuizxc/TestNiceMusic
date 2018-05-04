package com.example.jh.testnicemusic.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.lzx.musiclibrary.aidl.model.AlbumInfo;
import com.lzx.musiclibrary.aidl.model.SongInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lzx
 * @date 2018/2/6
 */

public class DbManager {

    private Context mContext;
    private ContentResolver mResolver;


    public DbManager(Context context) {
        mContext = context;
        mResolver = mContext.getContentResolver();

    }

    /**
     * 获取播放列表
     *
     * @return
     */
    private List<SongInfo> queryPlayList() {
        Uri uri = MusicContentProvider.SONG_LIST_URI;
        Cursor cursor = mResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            return new ArrayList<>();
        }
        List<SongInfo> musicInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            String musicId = cursor.getString(cursor.getColumnIndex(DbConstants.MUSIC_ID));
            String artist = cursor.getString(cursor.getColumnIndex(DbConstants.ARTIST));
            String musicTitle = cursor.getString(cursor.getColumnIndex(DbConstants.MUSIC_TITLE));
            String albumTitle = cursor.getString(cursor.getColumnIndex(DbConstants.ALBUM_TITLE));
            long duration = cursor.getLong(cursor.getColumnIndex(DbConstants.DURATION));
            String cover = cursor.getString(cursor.getColumnIndex(DbConstants.COVER));
            String url = cursor.getString(cursor.getColumnIndex(DbConstants.URL));
            SongInfo info = new SongInfo();
            info.setSongId(musicId);
            info.setArtist(artist);
            info.setSongName(musicTitle);
            info.setDuration(duration);
            info.setSongCover(cover);
            info.setSongUrl(url);
            AlbumInfo albumInfo = new AlbumInfo();
            albumInfo.setAlbumName(albumTitle);
            info.setAlbumInfo(albumInfo);
            musicInfos.add(info);
        }
        cursor.close();
        return musicInfos;
    }

    /**
     * 保存播放列表
     *
     * @param list
     */
    private void savePlayList(List<SongInfo> list) {
        clearPlayList();
        Uri uri = MusicContentProvider.SONG_LIST_URI;
        for (SongInfo info : list) {
            if (info.getAlbumInfo() == null) {
                throw new RuntimeException("albumInfo must not be null.");
            }
            ContentValues values = new ContentValues();
            values.put(DbConstants.MUSIC_ID, info.getSongId());
            values.put(DbConstants.ARTIST, info.getArtist());
            values.put(DbConstants.MUSIC_TITLE, info.getSongName());
            values.put(DbConstants.ALBUM_TITLE, info.getAlbumInfo().getAlbumName());
            values.put(DbConstants.DURATION, info.getDuration());
            values.put(DbConstants.COVER, info.getSongCover());
            values.put(DbConstants.URL, info.getSongUrl());
            mResolver.insert(uri, values);
        }
    }

    /**
     * 删除一条播放列表
     *
     * @return The number of rows deleted.
     */
    private int deleteInfoInPlayList(SongInfo info) {
        Uri uri = MusicContentProvider.SONG_LIST_URI;
        return mResolver.delete(uri, DbConstants.MUSIC_ID + " = ?", new String[]{info.getSongId()});
    }

    /**
     * 清空播放列表
     *
     * @return The number of rows deleted.
     */
    private int clearPlayList() {
        Uri uri = MusicContentProvider.SONG_LIST_URI;
        return mResolver.delete(uri, null, null);
    }

    /**
     * 获取一条音乐信息
     */
    private SongInfo querySongInfoById(String songId) {
        Uri uri = MusicContentProvider.SONG_LIST_URI;
        Cursor cursor = mResolver.query(uri, null, DbConstants.MUSIC_ID + " = ?", new String[]{songId}, null);
        if (cursor == null) {
            return null;
        }
        SongInfo info = new SongInfo();
        while (cursor.moveToNext()) {
            String musicId = cursor.getString(cursor.getColumnIndex(DbConstants.MUSIC_ID));
            String artist = cursor.getString(cursor.getColumnIndex(DbConstants.ARTIST));
            String musicTitle = cursor.getString(cursor.getColumnIndex(DbConstants.MUSIC_TITLE));
            String albumTitle = cursor.getString(cursor.getColumnIndex(DbConstants.ALBUM_TITLE));
            long duration = cursor.getLong(cursor.getColumnIndex(DbConstants.DURATION));
            String cover = cursor.getString(cursor.getColumnIndex(DbConstants.COVER));
            String url = cursor.getString(cursor.getColumnIndex(DbConstants.URL));
            info.setSongId(musicId);
            info.setArtist(artist);
            info.setSongName(musicTitle);
            info.setDuration(duration);
            info.setSongCover(cover);
            info.setSongUrl(url);
            AlbumInfo albumInfo = new AlbumInfo();
            albumInfo.setAlbumName(albumTitle);
            info.setAlbumInfo(albumInfo);
        }
        cursor.close();
        return info;
    }

    /**
     * 获取我的歌单
     *
     * @return
     */
    private List<SongInfo> queryFavoriteList() {
        Uri uri = MusicContentProvider.FAVORITES_URI;
        Cursor cursor = mResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            return new ArrayList<>();
        }
        List<SongInfo> musicInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            String musicId = cursor.getString(cursor.getColumnIndex(DbConstants.MUSIC_ID));
            String artist = cursor.getString(cursor.getColumnIndex(DbConstants.ARTIST));
            String musicTitle = cursor.getString(cursor.getColumnIndex(DbConstants.MUSIC_TITLE));
            String albumTitle = cursor.getString(cursor.getColumnIndex(DbConstants.ALBUM_TITLE));
            long duration = cursor.getLong(cursor.getColumnIndex(DbConstants.DURATION));
            String cover = cursor.getString(cursor.getColumnIndex(DbConstants.COVER));
            String url = cursor.getString(cursor.getColumnIndex(DbConstants.URL));
            SongInfo info = new SongInfo();
            info.setSongId(musicId);
            info.setArtist(artist);
            info.setSongName(musicTitle);
            info.setDuration(duration);
            info.setSongCover(cover);
            info.setSongUrl(url);
            AlbumInfo albumInfo = new AlbumInfo();
            albumInfo.setAlbumName(albumTitle);
            info.setAlbumInfo(albumInfo);
            musicInfos.add(info);
        }
        cursor.close();
        return musicInfos;
    }

    /**
     * 删除一条我的歌单
     *
     * @param info
     */
    private int deleteInfoInFavoriteList(SongInfo info) {
        Uri uri = MusicContentProvider.FAVORITES_URI;
        return mResolver.delete(uri, DbConstants.MUSIC_ID + " = ?", new String[]{info.getSongId()});
    }

    /**
     * 添加一条我的歌单
     *
     * @param info
     */
    private void addInfoInFavoriteList(SongInfo info) {
        if (info.getAlbumInfo() == null) {
            throw new RuntimeException("albumInfo must not be null.");
        }
        Uri uri = MusicContentProvider.FAVORITES_URI;
        ContentValues values = new ContentValues();
        values.put(DbConstants.MUSIC_ID, info.getSongId());
        values.put(DbConstants.ARTIST, info.getArtist());
        values.put(DbConstants.MUSIC_TITLE, info.getSongName());
        values.put(DbConstants.ALBUM_TITLE, info.getAlbumInfo().getAlbumName());
        values.put(DbConstants.DURATION, info.getDuration());
        values.put(DbConstants.COVER, info.getSongCover());
        values.put(DbConstants.URL, info.getSongUrl());
        mResolver.insert(uri, values);
    }

    /**
     * 清空我的歌单
     */
    private int clearFavoriteList() {
        Uri uri = MusicContentProvider.FAVORITES_URI;
        return mResolver.delete(uri, null, null);
    }

    /**
     * 下面是异步方法
     */
//    public Observable<List<SongInfo>> asyQueryPlayList() {
//        return Observable.create((ObservableOnSubscribe<List<SongInfo>>) emitter -> {
//            List<SongInfo> list = queryPlayList();
//            emitter.onNext(list);
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
//    }

//    public Observable<Boolean> asySavePlayList(List<SongInfo> list) {
//        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
//            savePlayList(list);
//            emitter.onNext(true);
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
//    }

//    public Observable<Boolean> asyDeleteInfoInPlayList(SongInfo info) {
//        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
//            int rows = deleteInfoInPlayList(info);
//            emitter.onNext(rows > 0);
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
//    }

//    public Observable<List<SongInfo>> asyQueryFavoriteList() {
//        return Observable.create((ObservableOnSubscribe<List<SongInfo>>) emitter -> {
//            List<SongInfo> list = queryFavoriteList();
//            emitter.onNext(list);
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
//    }

//    public Observable<Boolean> asyDeleteInfoInFavoriteList(SongInfo info) {
//        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
//            int rows = deleteInfoInFavoriteList(info);
//            emitter.onNext(rows > 0);
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
//    }

//    public Observable<Boolean> asyAddInfoInFavoriteList(SongInfo info) {
//        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
//            addInfoInFavoriteList(info);
//            emitter.onNext(true);
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
//    }

//    public Observable<Boolean> asyClearFavoriteList() {
//        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
//            int rows = clearFavoriteList();
//            emitter.onNext(rows > 0);
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
//    }

//    public Observable<SongInfo> asyGetSongInfoById(String songId) {
//        return Observable.create((ObservableOnSubscribe<SongInfo>) emitter -> emitter.onNext(querySongInfoById(songId)))
//                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
//    }
}
