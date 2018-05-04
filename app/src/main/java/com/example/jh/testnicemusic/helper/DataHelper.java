package com.example.jh.testnicemusic.helper;

import com.lzx.musiclibrary.aidl.model.AlbumInfo;
import com.lzx.musiclibrary.aidl.model.SongInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by xian on 2018/1/20.
 */

public class DataHelper {
    /**
     * 获取流派
     *
     * @param topid
     * @return
     */
    public static String getMusicGenreByTopid(String topid) {
        switch (topid) {
            case "16":
                return "韩国";
            case "5":
                return "内地";
            case "6":
                return "港台";
            case "3":
                return "欧美";
            case "17":
                return "日本";
            default:
                return "Genre";
        }
    }


    /**
     * 得到一个随机列表
     *
     * @param list
     * @param size
     * @return
     */
    public static List<SongInfo> getShuffleMusicList(List<SongInfo> list, int size) {
        Collections.shuffle(list);
        List<SongInfo> results = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            results.add(list.get(i));
        }
        return results;
    }

    /**
     * 解析json
     *
     * @param responseBody
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static List<SongInfo> fetchJSONFromUrl(ResponseBody responseBody) throws IOException, JSONException {
        List<SongInfo> musicInfos = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(responseBody.string());
        JSONArray array = jsonObject.getJSONArray("song_list");
        JSONObject billboard = jsonObject.optJSONObject("billboard");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            SongInfo info = getSongInfo(billboard, object, i);
            musicInfos.add(info);
        }
        return musicInfos;
    }

    public static List<SongInfo> fetchArtistJSONFromUrl(ResponseBody responseBody) throws IOException, JSONException {
        List<SongInfo> musicInfos = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(responseBody.string());
        JSONArray array = jsonObject.getJSONArray("songlist");

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            SongInfo info = getSongInfo(null, object, i);
            musicInfos.add(info);
        }
        return musicInfos;
    }

    private static SongInfo getSongInfo(JSONObject billboard, JSONObject object, int i) throws IOException, JSONException {
        SongInfo info = new SongInfo();
        info.setSongId(object.getString("song_id")); //音乐id
        info.setSongName(object.getString("title")); //音乐标题
        info.setSongCover(object.getString("pic_s500"));  //音乐封面
        info.setSongUrl(""); //音乐播放地址
        if (billboard != null) {
            info.setGenre(billboard.getString("name"));  //类型（流派）
            info.setType(billboard.getString("billboard_type"));  //类型
        }
        info.setSize("");   //音乐大小
        info.setDuration(object.getInt("file_duration") * 1000);   //音乐长度
        info.setArtist(object.getString("author"));  //音乐艺术家
        info.setArtistId(object.getString("ting_uid"));   //音乐艺术家id
        info.setDownloadUrl("");  //音乐下载地址
        info.setSite(object.getString("country"));  //地点
        info.setFavorites(Integer.parseInt(object.optString("hot", "0")));  //喜欢数
        info.setPlayCount(info.getFavorites() * 2);  //播放数
        info.setTrackNumber(i);   //媒体的曲目号码（序号：1234567……）

        info.setLanguage(object.getString("language")); //语言
        info.setCountry(object.getString("country")); //地区
        info.setProxyCompany(object.getString("si_proxycompany")); //代理公司
        info.setPublishTime(object.getString("publishtime"));  //发布时间
        info.setDescription(object.getString("info"));  //音乐描述
        info.setVersions(object.getString("versions"));  //版本

        AlbumInfo albumInfo = new AlbumInfo();
        albumInfo.setAlbumId(object.getString("album_id")); //专辑id
        albumInfo.setAlbumName(object.getString("album_title"));   //专辑名称
        albumInfo.setAlbumCover(object.getString("album_500_500"));  //专辑封面
        albumInfo.setAlbumRectCover(object.getString("pic_huge"));   //长方形的封面
        albumInfo.setAlbumHDCover(object.getString("pic_premium"));   //高清的封面
        albumInfo.setArtist(object.optString("artist_name"));   //专辑艺术家
        albumInfo.setSongCount(0);   //专辑音乐数
        albumInfo.setPlayCount(0);   //专辑播放数

        info.setAlbumInfo(albumInfo);
        return info;
    }

    public static List<SongInfo> subList(List<SongInfo> list, int index, int size) {
        List<SongInfo> musicInfos = new ArrayList<>();
        for (int i = index; i < size; i++) {
            musicInfos.add(list.get(i));
            size = size + i * size;
        }
        return musicInfos;
    }

    public static List<SongInfo> getMusicByType(List<SongInfo> list, String type) {
        List<SongInfo> musicInfos = new ArrayList<>();
        for (SongInfo info : list) {
            if (info.getType().equals(type)) {
                musicInfos.add(info);
            }
        }
        return musicInfos;
    }


}
