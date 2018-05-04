package com.example.jh.testnicemusic.base.mvp.factory;


import com.example.jh.testnicemusic.base.mvp.BaseContract;

/**
 *  Presenter工厂接口
 * @author lzx
 * @date 2017/12/7
 */

public interface PresenterMvpFactory<V extends BaseContract.BaseView, P extends BaseContract.BasePresenter<V>> {
    P createPresenter();
}
