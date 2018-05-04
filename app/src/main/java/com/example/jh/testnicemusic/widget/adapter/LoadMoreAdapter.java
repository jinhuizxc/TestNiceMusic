package com.example.jh.testnicemusic.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.jh.testnicemusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xian on 2018/2/5.
 */

public abstract class LoadMoreAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int TYPE_FOOTVIEW = 100;
    protected Context mContext;
    protected List<T> mDataList;
    private RecyclerView mRecyclerView;
    private NotifyObserver mNotifyObserver;
    private OnFootViewClickListener mOnFootViewClickListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    public FootViewHolder mFootViewHolder;
    private RecyclerOnScrollListener onScrollListener;

    /**
     * 是否显示加载更多
     */
    public boolean showLoadMore = true;

    public LoadMoreAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<T>();
    }

    public void setDataList(List<T> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
        if (mDataList.size() <= 3) {
            setShowLoadMore(false);
        } else {
            setShowLoadMore(true);
        }
        notifyDataSetChanged();
    }

    public void addDataList(List<T> dataList){
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mDataList;
    }


    public void clearAllData() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void setOnFootViewClickListener(OnFootViewClickListener onFootViewClickListener) {
        mOnFootViewClickListener = onFootViewClickListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    /**
     * RecyclerView在开始观察该适配器时调用。
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        onScrollListener = new RecyclerOnScrollListener(mRecyclerView) {
            @Override
            public void onLoadMore(int currentPage) {
                if (mOnLoadMoreListener != null)
                    mOnLoadMoreListener.onLoadMore();
            }
        };
        mRecyclerView.addOnScrollListener(onScrollListener);
        onScrollListener.setLoading(false);
        mNotifyObserver = new NotifyObserver();
        registerAdapterDataObserver(mNotifyObserver);

        if (mDataList.size() <= 3) {
            setShowLoadMore(false);
        } else {
            setShowLoadMore(true);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (onScrollListener != null) {
            mRecyclerView.removeOnScrollListener(onScrollListener);
        }
        if (mNotifyObserver != null) {
            unregisterAdapterDataObserver(mNotifyObserver);
        }
        onScrollListener = null;
        mNotifyObserver = null;
        mRecyclerView = null;
    }



    /**
     * 是否需要加载更多
     */
    public void setCanLoading(boolean loading) {
        if (onScrollListener != null) {
            onScrollListener.setCanLoading(loading);
            onScrollListener.setLoading(!loading);
        }
    }

    /**
     * 设置是否需要显示加载更多
     */
    public void setShowLoadMore(boolean showLoadMore) {
        this.showLoadMore = showLoadMore;
        setCanLoading(showLoadMore);
    }

    /**
     * 显示-已加载全部数据
     */
    public void showLoadAllDataUI() {
        if (mFootViewHolder != null) {
            mFootViewHolder.setClickable(false);
            mFootViewHolder.showTextOnly("已全部加载");
            setCanLoading(false);
        }
    }

    public void showProgress() {
        if (mFootViewHolder != null) {
            mFootViewHolder.setClickable(true);
            mFootViewHolder.showProgress();
            setCanLoading(true);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTVIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_footview, parent, false);
            mFootViewHolder = new FootViewHolder(view, mContext);
            return mFootViewHolder;
        } else {
            return onCreateBaseViewHolder(parent, viewType);
        }
    }

    public abstract BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTVIEW) {
            mFootViewHolder.mRootLayout.setOnClickListener(v -> {
                if (mOnFootViewClickListener != null) {
                    mOnFootViewClickListener.onFootViewClick();
                }
            });
        } else {
            BindViewHolder(holder, position);
        }
    }

    protected abstract void BindViewHolder(BaseViewHolder holder, int position);

    @Override
    public int getItemViewType(int position) {
        if (showLoadMore) {
            if (position == getItemCount() - 1) {
                return TYPE_FOOTVIEW;
            } else {
                return getViewType(position);
            }
        }
        return getViewType(position);
    }

    protected abstract int getViewType(int position);

    @Override
    public int getItemCount() {
        return showLoadMore ? (mDataList != null ? mDataList.size() + 1 : 0) : (mDataList != null ? mDataList.size() : 0);
    }

    private class FootViewHolder extends BaseViewHolder {
        LinearLayout mRootLayout;
        ProgressBar mProgressView;
        TextView mLoadText;

        public FootViewHolder(View itemView, Context context) {
            super(itemView, context, false);
            mRootLayout = $(R.id.root_layout);
            mProgressView = $(R.id.load_pro);
            mLoadText = $(R.id.loading_text);
        }

        public void setClickable(boolean clickable) {
            mRootLayout.setClickable(clickable);
        }

        public void setLoadText(String text) {
            mLoadText.setText(text);
        }

        public void showProgress() {
            mProgressView.setVisibility(View.VISIBLE);
            mLoadText.setText("正在加载...");
        }

        public void showClickText() {
            mProgressView.setVisibility(View.GONE);
            mLoadText.setText("点击重新加载");
        }

        public void showTextOnly(String text) {
            mProgressView.setVisibility(View.GONE);
            setLoadText(text);
        }
    }

    public interface OnFootViewClickListener {
        void onFootViewClick();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    /**
     * 数据变化时回调
     */
    private class NotifyObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            if (onScrollListener != null) {
                onScrollListener.setLoading(false);
            }
            if (mDataList != null) {
                if (mDataList.size() <= 3) {
                    setShowLoadMore(false);
                } else {
                    setShowLoadMore(true);
                }
            }
        }
    }
}