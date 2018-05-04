package com.example.jh.testnicemusic.base.mvp.factory;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.example.jh.testnicemusic.base.mvp.BaseContract;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * 基础Presenter
 *
 * @author lzx
 * @date 2017/12/5
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;
    protected Context mContext;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    private void unSubscribe() {
        mDisposable.dispose();
    }

    protected boolean remove(Disposable disposable) {
        return mDisposable.remove(disposable);
    }

    protected void addSubscribe(Disposable disposable) {
        mDisposable.add(disposable);
    }

    protected void clearSubscribe() {
        mDisposable.clear();
    }

    @Override
    public void attachView(T view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void onCreatePresenter(@Nullable Bundle savedState) {

    }

    @Override
    public void onDestroyPresenter() {
        mContext = null;
        clearSubscribe();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
