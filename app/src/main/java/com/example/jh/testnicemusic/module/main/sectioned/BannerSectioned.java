package com.example.jh.testnicemusic.module.main.sectioned;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.example.jh.testnicemusic.R;
import com.example.jh.testnicemusic.widget.banner.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * @author lzx
 * @date 2018/2/14
 */

public class BannerSectioned extends StatelessSection {

    public BannerSectioned(Context context) {
        super(new SectionParameters.Builder(R.layout.home_section_banner).build());
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new BannerHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        BannerHolder holder = (BannerHolder) viewHolder;
        List<String> list = new ArrayList<>();
        list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2295583836,996934370&fm=27&gp=0.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518587106405&di=8fee364fdd8871d7153fbfed61d236cc&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2011%2F12%2F20111215m_2%2F07.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518586528124&di=e72fda209f90417ff1d97943bc9b975c&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fe4dde71190ef76c63d4029ff9716fdfaae516750.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518586548211&di=02a1d29c756e05e3f66f99b53c7a944c&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F908fa0ec08fa513da483f08a3b6d55fbb2fbd9bd.jpg");
        holder.mBannerLayout.initBannerImageView(list);
    }

    class BannerHolder extends RecyclerView.ViewHolder {
        private BannerLayout mBannerLayout;

        BannerHolder(View itemView) {
            super(itemView);
            mBannerLayout = itemView.findViewById(R.id.banner_layout);
        }
    }
}
