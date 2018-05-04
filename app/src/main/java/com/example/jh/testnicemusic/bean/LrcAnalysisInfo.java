package com.example.jh.testnicemusic.bean;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 歌词解析
 *
 * @author lzx
 * @date 2018/2/9
 */

public class LrcAnalysisInfo implements Comparable<LrcAnalysisInfo> {
    private long time;
    private String text;

    public LrcAnalysisInfo(long time, String text) {
        this.time = time;
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(@NonNull LrcAnalysisInfo info) {
        return (int) (time - info.getTime());
    }

    public static List<LrcAnalysisInfo> parseLrcString(String lrcString) {
        if (TextUtils.isEmpty(lrcString)) {
            return null;
        }
        List<LrcAnalysisInfo> lrcAnalysisInfos = new ArrayList<>();
        String[] array = lrcString.split("\\n");
        for (String line : array) {
            List<LrcAnalysisInfo> list = parseLine(line);
            if (list != null && !list.isEmpty()) {
                lrcAnalysisInfos.addAll(list);
            }
        }
        Collections.sort(lrcAnalysisInfos);
        return lrcAnalysisInfos;
    }

    private static List<LrcAnalysisInfo> parseLine(String line) {
        if (TextUtils.isEmpty(line)) {
            return null;
        }
        line = line.trim();
        Matcher lineMatcher = Pattern.compile("((\\[\\d\\d:\\d\\d\\.\\d\\d\\])+)(.+)").matcher(line);
        if (!lineMatcher.matches()) {
            return null;
        }

        String times = lineMatcher.group(1);
        String text = lineMatcher.group(3);
        List<LrcAnalysisInfo> entryList = new ArrayList<>();

        Matcher timeMatcher = Pattern.compile("\\[(\\d\\d):(\\d\\d)\\.(\\d\\d)\\]").matcher(times);
        while (timeMatcher.find()) {
            long min = Long.parseLong(timeMatcher.group(1));
            long sec = Long.parseLong(timeMatcher.group(2));
            long mil = Long.parseLong(timeMatcher.group(3));
            long time = min * DateUtils.MINUTE_IN_MILLIS + sec * DateUtils.SECOND_IN_MILLIS + mil * 10;
            entryList.add(new LrcAnalysisInfo(time, text));
        }
        return entryList;
    }
}
