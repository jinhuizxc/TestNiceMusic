package com.example.jh.testnicemusic.module.artist.presenter;

import com.example.jh.testnicemusic.base.mvp.factory.BasePresenter;
import com.example.jh.testnicemusic.bean.SingerInfo;
import com.example.jh.testnicemusic.helper.DataHelper;
import com.example.jh.testnicemusic.network.RetrofitHelper;
import com.google.gson.Gson;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.utils.LogUtil;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lzx
 * @date 2018/2/14
 */

public class ArtistPresenter extends BasePresenter<ArtistContract.View> implements ArtistContract.Presenter<ArtistContract.View> {
    @Override
    public void getArtistSongs(String artistId) {
        Disposable subscriber = getSongList(artistId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> mView.onArtistSongsSuccess(list),
                        throwable -> LogUtil.i(throwable.getMessage()));
        addSubscribe(subscriber);
    }

    private Observable<List<SongInfo>> getSongList(String artistId) {
        return RetrofitHelper.getMusicApi().requestArtistSongList(artistId, 20)
                .map(responseBody -> {
                    List<SongInfo> list = DataHelper.fetchArtistJSONFromUrl(responseBody);
                    List<SongInfo> newList = new ArrayList<>();
                    for (SongInfo info : list) {
                        RetrofitHelper.getMusicApi().playMusic(info.getSongId())
                                .map(responseUrlBody -> {
                                    String json = responseUrlBody.string();
                                    json = json.substring(1, json.length() - 2);
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONObject bitrate = jsonObject.getJSONObject("bitrate");
                                    return bitrate.getString("file_link");
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(url -> {
                                    info.setSongUrl(url);
                                    newList.add(info);
                                }, throwable -> {
                                    LogUtil.i("1error = " + throwable.getMessage());
                                });
                    }
                    return newList;
                });
    }

    @Override
    public void getArtistInfo(String artistId) {
        Disposable subscriber = RetrofitHelper.getMusicApi().requestArtistInfo(artistId)
                .map(responseBody -> {
                    String jsonString = responseBody.string();
                    jsonString = jsonString.substring(1, jsonString.length() - 1);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    return new Gson().fromJson(jsonObject.toString(), SingerInfo.class);
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(info -> mView.onArtistInfoSuccess(info),
                        throwable -> LogUtil.i(throwable.getMessage()));

        addSubscribe(subscriber);
    }
}
