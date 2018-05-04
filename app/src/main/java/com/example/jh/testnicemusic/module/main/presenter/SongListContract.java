package com.example.jh.testnicemusic.module.main.presenter;

import com.example.jh.testnicemusic.base.mvp.BaseContract;
import com.lzx.musiclibrary.aidl.model.SongInfo;


import java.util.List;

/**
 * @author lzx
 * @date 2018/2/5
 */

public interface SongListContract {

    interface View extends BaseContract.BaseView {
        void onGetSongListSuccess(List<SongInfo> list, String title);

        void onGetLiveSongSuccess(List<SongInfo> list);

        void loadMoreSongListSuccess(List<SongInfo> list, String title);

        void loadFinishAllData();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void requestSongList(String title);

        void requestLiveList(String title);

        void loadMoreSongList(String title);

        int getAlbumCover(String title);
    }

}
