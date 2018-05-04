package com.example.jh.testnicemusic.widget.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jh.testnicemusic.R;


/**
 * Created by xian on 2018/2/5.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public View itemView;

    public BaseViewHolder(View itemView, Context context, boolean addBG) {
        super(itemView);
        this.itemView = itemView;
        //添加水波纹背景
        if (addBG) {
            int[] attrs = new int[]{R.attr.selectableItemBackground};
            TypedArray typedArray = context.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            this.itemView.setBackgroundResource(backgroundResource);
        }
    }

    public <T extends View> T $(@IdRes int id) {
        return itemView.findViewById(id);
    }

}