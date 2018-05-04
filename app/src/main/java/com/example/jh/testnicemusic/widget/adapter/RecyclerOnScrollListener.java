package com.example.jh.testnicemusic.widget.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by xian on 2018/2/5.
 */

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {


    private int previousTotal = 0;

    private boolean loading = true;

    int lastCompletelyVisiableItemPosition, visibleItemCount, totalItemCount;

    private int currentPage = 1;
    private boolean shouldLoading = true;

    private RecyclerViewPositionHelper mHelper;

    public RecyclerOnScrollListener(RecyclerView recyclerView) {
        mHelper = new RecyclerViewPositionHelper(recyclerView);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (shouldLoading) {
            if (isSlideToBottom(recyclerView)) {
                currentPage++;
                onLoadMore(currentPage);
                loading = true;
            }
        }
    }

    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = manager.getItemCount();
            int lastCompletelyVisiableItemPosition = manager.findLastCompletelyVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            return !loading && (visibleItemCount > 0) && (lastCompletelyVisiableItemPosition >= totalItemCount - 1);
        } else {
            return false;
        }
    }

    public void setCanLoading(boolean loading) {
        shouldLoading = loading;
        if (shouldLoading)
            previousTotal = mHelper.getItemCount();
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        if (loading)
            previousTotal = mHelper.getItemCount();
    }

    public abstract void onLoadMore(int currentPage);


}