package com.example.jh.testnicemusic.module.play.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jh.testnicemusic.R;

import com.example.jh.testnicemusic.widget.adapter.BaseViewHolder;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author lzx
 * @date 2018/2/9
 */

public class DialogMusicListAdapter extends RecyclerView.Adapter<DialogMusicListAdapter.ItemHolder> implements Observer {

    private Context mContext;
//    private DbManager mDbManager;
    private List<SongInfo> mMusicInfos;


    public DialogMusicListAdapter(Context context) {
        mContext = context;
//        mDbManager = new DbManager(context);
//        mDbManager.asyQueryPlayList().subscribe(list -> mMusicInfos = list);
    }

//    public List<SongInfo> getMusicInfos() {
//        return mMusicInfos == null ? new ArrayList<>() : mMusicInfos;
//    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_dialog_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        SongInfo info = mMusicInfos.get(position);
        holder.mMusicTitle.setText(info.getSongName() + "(" + info.getAlbumInfo().getAlbumName() + ")" + " - " + info.getArtist());
        AnimationDrawable animationDrawable = (AnimationDrawable) holder.mImageAnim.getDrawable();
        if (MusicManager.isCurrMusicIsPlayingMusic(info)) {
            holder.mMusicTitle.setTextColor(Color.parseColor("#BE0D36"));
            holder.mImageAnim.setVisibility(View.VISIBLE);
            if (MusicManager.isPlaying()) {
                animationDrawable.start();
            } else {
                animationDrawable.stop();
            }
        } else {
            holder.mMusicTitle.setTextColor(ContextCompat.getColor(mContext, R.color.font_color));
            animationDrawable.stop();
            holder.mImageAnim.setVisibility(View.INVISIBLE);
        }
//        holder.mBtnDelete.setOnClickListener(v -> {
//            mDbManager.asyDeleteInfoInPlayList(info)
//                    .subscribe(aBoolean -> {
//                        boolean isPlayNext = MusicManager.isCurrMusicIsPlayingMusic(info);
//                        MusicManager.get().deleteSongInfoOnPlayList(info, isPlayNext);
//                        mMusicInfos.remove(info);
//                        notifyItemRemoved(position);
//                        if (position != mMusicInfos.size()) {
//                            notifyItemRangeChanged(position, mMusicInfos.size() - position);
//                        }
//                        if (mMusicInfos.size() == 0) {
//                            mContext.sendBroadcast(new Intent(Constans.ACTION_STOP_MUSIC));
//                        }
//                    }, throwable -> {
//                        Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
//                    });
//        });
//        holder.itemView.setOnClickListener(v -> {
//            SpUtil.getInstance().putString(Constans.LAST_PLAYING_MUSIC, info.getSongId());
//            MusicManager.get().playMusicByInfo(info);
//        });
    }

    @Override
    public void update(Observable o, Object arg) {
        int msg = (int) arg;
        if (msg == MusicManager.MSG_PLAYER_START || msg == MusicManager.MSG_PLAYER_PAUSE) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mMusicInfos == null ? 0 : mMusicInfos.size();
    }

    class ItemHolder extends BaseViewHolder {
        TextView mMusicTitle;
        ImageView mBtnDelete, mImageAnim;

        ItemHolder(View itemView) {
            super(itemView, mContext, false);
            mMusicTitle = $(R.id.item_music_name);
            mBtnDelete = $(R.id.btn_delete);
            mImageAnim = $(R.id.image_anim);
        }
    }
}
