package com.example.jh.testnicemusic.module.play.presenter;

import android.widget.Toast;

import com.example.jh.testnicemusic.base.mvp.factory.BasePresenter;
import com.example.jh.testnicemusic.bean.LrcInfo;
import com.example.jh.testnicemusic.network.RetrofitHelper;
import com.google.gson.Gson;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lzx
 * @date 2018/1/22
 */

public class PlayPresenter extends BasePresenter<PlayContract.View> implements PlayContract.Presenter<PlayContract.View> {
    @Override
    public void getLrcInfo(String musicId) {
        Disposable subscriber = RetrofitHelper.getMusicApi().requestMusicLry(musicId)
                .map(responseBody -> new Gson().fromJson(responseBody.string(), LrcInfo.class))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(info -> {
                    mView.onLrcInfoSuccess(info);
                }, throwable -> {
                    Toast.makeText(mContext, "获取歌词失败", Toast.LENGTH_SHORT).show();
                });
        addSubscribe(subscriber);
    }
}
