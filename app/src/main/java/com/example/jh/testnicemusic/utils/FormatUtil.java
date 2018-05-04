package com.example.jh.testnicemusic.utils;

import android.text.TextUtils;

/**
 * Created by xian on 2018/1/14.
 */

public class FormatUtil {


    public static String formatNum(String num) {
        if (TextUtils.isEmpty(num)) {
            num = "0";
        }
        long number = Long.parseLong(num);
        long result = 0;
        if (number > 10000) {
            result = number / 10000;
            return String.valueOf(result) + "万";
        } else if (number > 1000) {
            result = number / 1000;
            return String.valueOf(result) + "千";
        } else {
            return String.valueOf(number);
        }
    }

    /**
     * time的单位是秒
     *
     * @param durationString
     * @return
     */
    public static String formatMusicTime(String durationString) {
        try {
            final long duration = Long.parseLong(durationString) * 1000;
            return formatMusicTime(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "00:00";
    }
    /**
     * duration单位毫秒
     *
     * @param duration
     * @return
     */
    public static String formatMusicTime(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((int) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

}
