package com.example.jh.testnicemusic.base.mvp.factory;


import com.example.jh.testnicemusic.base.mvp.BaseContract;

/**
 * 代理接口
 * @author lzx
 * @date 2017/12/7
 */

public interface PresenterProxyInterface<V extends BaseContract.BaseView,P extends BaseContract.BasePresenter<V>> {
    /**
     * 设置创建Presenter的工厂
     * @param presenterFactory PresenterFactory类型
     */
    void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory);
    /**
     * 获取Presenter的工厂类
     * @return 返回PresenterMvpFactory类型
     */
    PresenterMvpFactory<V,P> getPresenterFactory();
    /**
     * 获取创建的Presenter
     * @return 指定类型的Presenter
     */
    P getPresenter();
}