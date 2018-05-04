package com.example.jh.testnicemusic.module.main.sectioned;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.jh.testnicemusic.R;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * @author lzx
 * @date 2018/2/14
 */

public class TitleSectioned extends StatelessSection {

    private String title;

    public TitleSectioned(Context context, String title) {
        super(new SectionParameters.Builder(R.layout.home_secton_title).build());
        this.title = title;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new TitleHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TitleHolder holder = (TitleHolder) viewHolder;
        holder.mTitle.setText(title);
    }

    class TitleHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;

        TitleHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
        }
    }
}
