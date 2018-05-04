package com.example.jh.testnicemusic.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 基础契约类
 * Created by lzx on 2017/8/10.
 */

public interface BaseContract {
    interface BaseView {
        /**
         * 请求出错
         *
         * @param msg        错误信息
         * @param isLoadMore 是否加载更多，用来区分刷新出错还是加载更多出错
         */
        void showError(String msg, boolean isLoadMore);

        /**
         * 请求完成
         */
        void complete();

        /**
         * 是否显示进度条
         *
         * @param isShow
         */
        void showProgressUI(boolean isShow);
    }

    interface BasePresenter<T> {
        /**
         * 绑定
         *
         * @param view view
         */
        void attachView(T view, Context context);

        /**
         * 解绑
         */
        void detachView();

        /**
         * Presenter被创建后调用
         *
         * @param savedState
         */
        void onCreatePresenter(@Nullable Bundle savedState);

        /**
         * Presenter被销毁时调用
         */
        void onDestroyPresenter();

        /**
         * 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
         * 时机相同
         * @param outState
         */
        void onSaveInstanceState(Bundle outState);
    }
}
