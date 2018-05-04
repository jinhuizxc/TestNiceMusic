package com.example.jh.testnicemusic.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.jh.testnicemusic.R;


/**
 * 带有蒙层的ImageView
 *
 * @author lzx
 * @date 2017/12/6
 */

public class OuterLayerImageView extends android.support.v7.widget.AppCompatImageView {

    private Paint mPaint;
    private int defaultColor = Color.parseColor("#4c000000");

    public OuterLayerImageView(Context context) {
        this(context, null, 0);
    }

    public OuterLayerImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OuterLayerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.OuterLayerImageView);
        int color = mTypedArray.getColor(R.styleable.OuterLayerImageView_outerLayerColor, defaultColor);
        mTypedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
    }

    public void setColor(int color) {
        this.defaultColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }
}
