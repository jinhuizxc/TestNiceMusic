package com.example.jh.testnicemusic.module.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jh.testnicemusic.R;
import com.example.jh.testnicemusic.module.artist.ArtistDetailActivity;
import com.example.jh.testnicemusic.utils.GlideUtil;
import com.lzx.musiclibrary.aidl.model.SongInfo;


import java.util.List;

/**
 * @author lzx
 * @date 2018/2/14
 */

public class ChartTopAdapter extends RecyclerView.Adapter<ChartTopAdapter.ChartHolder> {

    private List<SongInfo> mSongInfos;
    private Context mContext;

    public ChartTopAdapter(List<SongInfo> songInfos, Context context) {
        mSongInfos = songInfos;
        mContext = context;
    }

    @Override
    public ChartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_chart_top, parent, false);
        return new ChartHolder(view);
    }

    @Override
    public void onBindViewHolder(ChartHolder holder, int position) {
        SongInfo info = mSongInfos.get(position);
        GlideUtil.loadImageByUrl(mContext, info.getSongCover(), holder.mMusicCover);
        holder.mMusicName.setText(info.getSongName());
        holder.mSongerName.setText(info.getArtist());
        holder.mMusicCover.setOnClickListener(v -> {
            ArtistDetailActivity.launch(mContext, info);
            Toast.makeText(mContext, "info = " + info, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mSongInfos.size();
    }

    class ChartHolder extends RecyclerView.ViewHolder {

        ImageView mMusicCover;
        TextView mMusicName, mSongerName;

        ChartHolder(View itemView) {
            super(itemView);
            mMusicCover = itemView.findViewById(R.id.music_cover);
            mMusicName = itemView.findViewById(R.id.music_name);
            mSongerName = itemView.findViewById(R.id.songer_name);
        }
    }
}
