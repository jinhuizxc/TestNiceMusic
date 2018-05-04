package com.example.jh.testnicemusic.module.play.presenter;


import com.example.jh.testnicemusic.base.mvp.BaseContract;
import com.example.jh.testnicemusic.bean.LrcInfo;

/**
 * @author lzx
 * @date 2018/1/22
 */

public interface PlayContract {
    interface View extends BaseContract.BaseView {
        void onLrcInfoSuccess(LrcInfo info);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getLrcInfo(String musicId);
    }
}
