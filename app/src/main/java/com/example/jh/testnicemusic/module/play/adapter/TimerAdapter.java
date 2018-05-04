package com.example.jh.testnicemusic.module.play.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jh.testnicemusic.R;
import com.example.jh.testnicemusic.widget.adapter.BaseViewHolder;
import com.lzx.musiclibrary.manager.MusicManager;


import java.util.Arrays;
import java.util.List;

/**
 * @author lzx
 * @date 2018/2/13
 */

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ItemHolder> {

    private Context mContext;
    private List<String> mTimerList;

    public TimerAdapter(Context context) {
        mContext = context;
        mTimerList = Arrays.asList(mContext.getResources().getStringArray(R.array.alarms_title_array));
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timer, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        String timer = mTimerList.get(position);
        holder.mTimerText.setText(timer);
        holder.line.setVisibility(position == mTimerList.size() - 1 ? View.GONE : View.VISIBLE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        MusicManager.get().pausePlayInMillis(-1);
                        break;
                    case 1:
                        MusicManager.get().pausePlayInMillis(-1);
                        break;
                    case 2:
                        MusicManager.get().pausePlayInMillis(-1);
                        break;
                    case 3:
                        MusicManager.get().pausePlayInMillis(-1);
                        break;
                    case 4:
                        MusicManager.get().pausePlayInMillis(-1);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTimerList.size();
    }

    class ItemHolder extends BaseViewHolder {
        TextView mTimerText;
        CheckBox mCheckBox;
        View line;

        ItemHolder(View itemView) {
            super(itemView, mContext, false);
            mTimerText = $(R.id.text_time);
            mCheckBox = $(R.id.checkbox);
            line = $(R.id.line);
        }
    }
}
