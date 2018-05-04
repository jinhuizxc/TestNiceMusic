package com.example.jh.testnicemusic.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.jh.testnicemusic.base.mvp.BaseContract;
import com.example.jh.testnicemusic.base.mvp.BaseMvpProxy;
import com.example.jh.testnicemusic.base.mvp.factory.PresenterMvpFactory;
import com.example.jh.testnicemusic.base.mvp.factory.PresenterMvpFactoryImpl;
import com.example.jh.testnicemusic.base.mvp.factory.PresenterProxyInterface;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * 基础fragment
 *
 * @author lzx
 * @date 2017/12/5
 */

public abstract class BaseMvpFragment<V extends BaseContract.BaseView, P extends BaseContract.BasePresenter<V>> extends RxFragment implements PresenterProxyInterface<V, P>, BaseContract.BaseView {

    protected View mRootView;
    protected LayoutInflater inflater;
    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;

    protected Context mContext;
    protected Activity mActivity;

    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";

    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null)
                parent.removeView(mRootView);
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            mActivity = getActivity();
            mContext = mActivity;
            this.inflater = inflater;
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()), getActivity());
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        mProxy.onResume((V) this);
        isPrepared = true;
        init();
        lazyLoad();
    }

    /**
     * 获取布局
     *
     * @return
     */
    public abstract
    @LayoutRes
    int getLayoutId();


    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        lazyLoadData();
        isPrepared = false;
    }

    /**
     * 懒加载
     */
    protected void lazyLoadData() {

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 获取ApplicationContext 信息
     *
     * @return Context
     */
    public Context getApplicationContext() {
        return this.mContext == null ? (getActivity() == null ? null : getActivity()
                .getApplicationContext()) : this.mContext.getApplicationContext();
    }

    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getPresenter() {
        return mProxy.getPresenter();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }


    /**
     * 请求完成
     */
    @Override
    public void complete() {

    }

    /**
     * 是否显示进度条
     *
     * @param isShow
     */
    @Override
    public void showProgressUI(boolean isShow) {

    }

    /**
     * 请求失败
     *
     * @param msg        错误信息
     * @param isLoadMore 是否加载更多，用来区分刷新出错还是加载更多出错
     */
    @Override
    public void showError(String msg, boolean isLoadMore) {

    }

    /**
     * 隐藏View
     *
     * @param views view数组
     */
    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 显示View
     *
     * @param views view数组
     */
    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    /**
     * 隐藏View
     *
     * @param id
     */
    protected void gone(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = findViewById(resId);
                if (view != null)
                    gone(view);
            }
        }

    }

    /**
     * 显示View
     *
     * @param id
     */
    protected void visible(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = findViewById(resId);
                if (view != null)
                    visible(view);
            }
        }
    }

    public View findViewById(@IdRes int id) {
        View view;
        if (mRootView != null) {
            view = mRootView.findViewById(id);
            return view;
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    /**
     * 分离
     */
    @Override
    public void onDetach() {
        this.mActivity = null;
        super.onDetach();
    }
}
