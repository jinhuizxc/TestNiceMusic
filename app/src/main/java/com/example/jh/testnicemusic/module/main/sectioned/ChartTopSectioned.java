package com.example.jh.testnicemusic.module.main.sectioned;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jh.testnicemusic.R;
import com.example.jh.testnicemusic.module.main.adapter.ChartTopAdapter;
import com.lzx.musiclibrary.aidl.model.SongInfo;


import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * @author lzx
 * @date 2018/2/14
 */

public class ChartTopSectioned extends StatelessSection {

    private List<SongInfo> mSongInfos;
    private Context mContext;

    public ChartTopSectioned(Context context, List<SongInfo> mSongInfos) {
        super(new SectionParameters.Builder(R.layout.home_section_charttop).build());
        this.mSongInfos = mSongInfos;
        mContext = context;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ChartTopHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ChartTopHolder holder = (ChartTopHolder) viewHolder;
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecycleChartTop.setLayoutManager(manager);
        ChartTopAdapter mChartTopAdapter = new ChartTopAdapter(mSongInfos, mContext);
        holder.mRecycleChartTop.setAdapter(mChartTopAdapter);
    }

    class ChartTopHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecycleChartTop;

        ChartTopHolder(View itemView) {
            super(itemView);
            mRecycleChartTop = itemView.findViewById(R.id.recycle_chart_top);
        }
    }
}
