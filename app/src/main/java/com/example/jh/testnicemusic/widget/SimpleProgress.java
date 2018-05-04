package com.example.jh.testnicemusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author lzx
 * @date 2018/2/11
 */

public class SimpleProgress extends View {
    public SimpleProgress(Context context) {
        this(context, null, 0);
    }

    public SimpleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;
    private long max = -1;
    private long progress = 0;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#BE0D36"));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getMax() {
        return max;
    }

    public void setProgress(long progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (max == -1) {
            return;
        }
        float width = ((float) progress / (float) max) * getWidth();
        canvas.drawRect(0, 0, width, getHeight(), mPaint);
    }
}
