package com.example.jh.testnicemusic.module.play;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jh.testnicemusic.R;
import com.example.jh.testnicemusic.base.BaseMvpActivity;
import com.example.jh.testnicemusic.base.mvp.factory.CreatePresenter;
import com.example.jh.testnicemusic.bean.LrcAnalysisInfo;
import com.example.jh.testnicemusic.bean.LrcInfo;
import com.example.jh.testnicemusic.constans.Constans;
import com.example.jh.testnicemusic.listener.SimpleSeekBarChangeListener;
import com.example.jh.testnicemusic.module.play.presenter.PlayContract;
import com.example.jh.testnicemusic.module.play.presenter.PlayPresenter;
import com.example.jh.testnicemusic.utils.FormatUtil;
import com.example.jh.testnicemusic.utils.GlideUtil;
import com.example.jh.testnicemusic.widget.CircleImageView;
import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzx.musiclibrary.manager.TimerTaskManager;
import com.plattysoft.leonids.ParticleSystem;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by xian on 2018/1/21.
 */
@CreatePresenter(PlayPresenter.class)
public class PlayingDetailActivity extends BaseMvpActivity<PlayContract.View, PlayPresenter> implements PlayContract.View, OnPlayerEventListener, View.OnClickListener {

    private int position;
    private List<SongInfo> songInfos;
    private SongInfo mSongInfo;
    private ParticleSystem ps;
    private ObjectAnimator mCoverAnim;
    private long currentPlayTime = 0;
    private TimerTaskManager mTimerTaskManager;
    private List<LrcAnalysisInfo> lrcList;

    private TextView mSongName, mStartTime, mTotalTime, mTextLyrics;
    private CircleImageView mMusicCover;
    private ImageView mBlueBg, mBtnPlayPause, mBtnPre, mBtnNext;
    private SeekBar mSeekBar;


    public static void launch(Context context, List<SongInfo> songInfos, int position) {
        Intent intent = new Intent(context, PlayingDetailActivity.class);
        intent.putParcelableArrayListExtra("SongInfos", (ArrayList<? extends Parcelable>) songInfos);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_playing_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        songInfos = getIntent().getParcelableArrayListExtra("SongInfos");
        position = getIntent().getIntExtra("position", position);
        mSongInfo = songInfos.get(position);

        mSongName = findViewById(R.id.song_name);
        mMusicCover = findViewById(R.id.music_cover);
        mBlueBg = findViewById(R.id.blue_bg);
        mBtnPlayPause = findViewById(R.id.btn_play_pause);
        mBtnPre = findViewById(R.id.btn_pre);
        mBtnNext = findViewById(R.id.btn_next);
        mSeekBar = findViewById(R.id.seekBar);
        mStartTime = findViewById(R.id.start_time);
        mTotalTime = findViewById(R.id.total_time);
        mTextLyrics = findViewById(R.id.text_lyrics);

        mBtnPlayPause.setOnClickListener(this);
        mBtnPre.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        // seekbar
        mSeekBar.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                MusicManager.get().seekTo(seekBar.getProgress());
            }
        });
        MusicManager.get().addPlayerEventListener(this);

        mTimerTaskManager = new TimerTaskManager();
        mTimerTaskManager.setUpdateProgressTask(this::updateProgress);

        if (mSongInfo != null) {
            getPresenter().getLrcInfo(mSongInfo.getSongId());
        }
        updateUI(mSongInfo);
        initMusicCoverAnim();
        if (MusicManager.isPaused()) {
            MusicManager.get().resumeMusic();
        }
    }

    private void updateUI(SongInfo music) {
        if (music == null) {
            return;
        }
        mSeekBar.setMax((int) music.getDuration());
        mSongName.setText(music.getSongName());
        mTotalTime.setText(FormatUtil.formatMusicTime(music.getDuration()));
        GlideUtil.loadImageByUrl(this, music.getSongCover(), mMusicCover);
        GlideUtil.loadImageByUrl(this, music.getSongCover(), mBlueBg);
    }

    /**
     * 更新进度
     */
    private void updateProgress() {
        long progress = MusicManager.get().getProgress();
        long bufferProgress = MusicManager.get().getBufferedPosition();
        mSeekBar.setProgress((int) progress);
        mSeekBar.setSecondaryProgress((int) bufferProgress);

  //     LogUtil.i("bufferProgress = " + bufferProgress);

        mStartTime.setText(FormatUtil.formatMusicTime(progress));
        if (lrcList != null && lrcList.size() > 0) {
            mTextLyrics.setText(getLrc(progress));
        }
    }

    private String getLrc(long progress) {
        String lrc = "";
        for (int i = 0; i < lrcList.size(); i++) {
            int index = i + 1;
            if (index >= lrcList.size() - 1) {
                index = lrcList.size() - 1;
            }
            if (progress >= lrcList.get(i).getTime() && progress < lrcList.get(index).getTime()) {
                lrc = lrcList.get(i).getText();
                break;
            }
        }
        return lrc;
    }

    @Override
    public void onMusicSwitch(SongInfo music) {
        mSongInfo = music;
        getPresenter().getLrcInfo(music.getSongId());
        mBtnPlayPause.setImageResource(R.drawable.ic_play);
        updateUI(music);
    }

    @Override
    public void onPlayerStart() {
        mBtnPlayPause.setImageResource(R.drawable.ic_pause);
        mTimerTaskManager.scheduleSeekBarUpdate();
        startCoverAnim();

    }

    @Override
    public void onPlayerPause() {
        mBtnPlayPause.setImageResource(R.drawable.ic_play);
        mTimerTaskManager.stopSeekBarUpdate();
        pauseCoverAnim();
    }

    @Override
    public void onPlayCompletion() {
        mBtnPlayPause.setImageResource(R.drawable.ic_play);
        mSeekBar.setProgress(0);
        mStartTime.setText("00:00");
        resetCoverAnim();
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(mContext, "播放失败", Toast.LENGTH_SHORT).show();
        resetCoverAnim();
    }

    @Override
    public void onAsyncLoading(boolean isFinishLoading) {

    }

    @Override
    public void onLrcInfoSuccess(LrcInfo info) {
        lrcList = LrcAnalysisInfo.parseLrcString(info.getLrcContent());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play_pause:
                if (MusicManager.isPlaying()) {
                    MusicManager.get().pauseMusic();
                } else {
                    MusicManager.get().resumeMusic();
                }
                break;
            case R.id.btn_pre:
                if (MusicManager.get().hasPre()) {
                    MusicManager.get().playPre();
                } else {
                    Toast.makeText(mContext, "没有上一首了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_next:
                if (MusicManager.get().hasNext()) {
                    MusicManager.get().playNext();
                } else {
                    Toast.makeText(mContext, "没有下一首了", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 转圈动画
     */
    private void initMusicCoverAnim() {
        mCoverAnim = ObjectAnimator.ofFloat(mMusicCover, "rotation", 0, 359);
        mCoverAnim.setDuration(20000);
        mCoverAnim.setInterpolator(new LinearInterpolator());
        mCoverAnim.setRepeatCount(Integer.MAX_VALUE);
    }

    /**
     * 开始转圈
     */
    private void startCoverAnim() {
        mCoverAnim.start();
        mCoverAnim.setCurrentPlayTime(currentPlayTime);
    }

    /**
     * 停止转圈
     */
    private void pauseCoverAnim() {
        currentPlayTime = mCoverAnim.getCurrentPlayTime();
        mCoverAnim.cancel();
    }

    private void resetCoverAnim() {
        pauseCoverAnim();
        mMusicCover.setRotation(0);
    }


    private class SongListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equals(Constans.ACTION_STOP_MUSIC)) {
                MusicManager.get().stopMusic();
                PlayingDetailActivity.this.finish();
            }
        }
    }

    private Integer[] starArray = new Integer[]{
            R.drawable.pl_blue,
            R.drawable.pl_red,
            R.drawable.pl_yellow
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int random = (int) (Math.random() * starArray.length);
                ps = new ParticleSystem(this, 100, starArray[random], 800);
                ps.setScaleRange(0.7f, 1.3f);
                ps.setSpeedRange(0.05f, 0.1f);
                ps.setRotationSpeedRange(90, 180);
                ps.setFadeOut(200, new AccelerateInterpolator());
                ps.emit((int) event.getX(), (int) event.getY(), 40);
                break;
            case MotionEvent.ACTION_MOVE:
                ps.updateEmitPoint((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                ps.stopEmitting();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetCoverAnim();
        mCoverAnim = null;
        mTimerTaskManager.onRemoveUpdateProgressTask();
        MusicManager.get().removePlayerEventListener(this);
    }


}
