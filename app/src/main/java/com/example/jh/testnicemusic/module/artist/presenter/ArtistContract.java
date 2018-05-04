package com.example.jh.testnicemusic.module.artist.presenter;

import com.example.jh.testnicemusic.base.mvp.BaseContract;
import com.example.jh.testnicemusic.bean.SingerInfo;
import com.lzx.musiclibrary.aidl.model.SongInfo;


import java.util.List;

/**
 * @author lzx
 * @date 2018/2/14
 */

public interface ArtistContract {
    interface View extends BaseContract.BaseView {
        void onArtistSongsSuccess(List<SongInfo> list);
        void onArtistInfoSuccess(SingerInfo info);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getArtistSongs(String artistId);
        void getArtistInfo(String artistId);
    }
}
