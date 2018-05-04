package com.example.jh.testnicemusic.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xian on 2018/1/13.
 */

public interface MusicApi {

    /**
     * 获取列表
     *
     * @param type   1、新歌榜，2、热歌榜，11、摇滚榜，12、爵士，16、流行，
     *               21、欧美金曲榜，22、经典老歌榜，23、情歌对唱榜，
     *               24、影视金曲榜，25、网络歌曲榜
     * @param size   返回条目数量
     * @param offset 获取偏移
     * @return
     */
    @GET("ting?method=baidu.ting.billboard.billList")
    Observable<ResponseBody> requestMusicList(@Query("type") int type,
                                              @Query("size") int size,
                                              @Query("offset") int offset);


    /**
     * 搜索
     *
     * @param keyword 关键字
     * @return
     */
    @GET("ting?method=baidu.ting.search.catalogSug")
    Observable<ResponseBody> searchMusic(@Query("Query") String keyword);


    /**
     * 播放 音乐id
     *
     * @param songid
     * @return
     */
    @GET("ting?method=baidu.ting.song.play")
    Observable<ResponseBody> playMusic(@Query("songid") String songid);

    /**
     * 获取歌词
     *
     * @param songid
     * @return
     */
    @GET("ting?method=baidu.ting.song.lry")
    Observable<ResponseBody> requestMusicLry(@Query("songid") String songid);

    /**
     * 推荐列表
     *
     * @param num 返回条目数量
     * @return
     */
    @GET("ting?method=baidu.ting.song.getRecommandSongList")
    Observable<ResponseBody> requestRecommandSongList(@Query("num") String num);

    /**
     * 下载
     *
     * @param songid
     * @param bit    码率  "24, 64, 128, 192, 256, 320, flac"
     * @param time   时间戳
     * @return
     */
    @GET("ting?method=baidu.ting.song.downWeb")
    Observable<ResponseBody> downloadMusic(@Query("songid") String songid,
                                           @Query("bit") String bit,
                                           @Query("_t") String time);

    /**
     * 获取歌手信息
     *
     * @param tinguid 歌手id
     * @return
     */
    @GET("ting?method=baidu.ting.artist.getInfo")
    Observable<ResponseBody> requestArtistInfo(@Query("tinguid") String tinguid);


    /**
     * 获取歌手曲目列表
     *
     * @param tinguid 歌手id
     * @param limits  返回条目数量
     * @return
     */
    @GET("ting?method=baidu.ting.artist.getSongList")
    Observable<ResponseBody> requestArtistSongList(@Query("tinguid") String tinguid,
                                                   @Query(" limits") int limits);
}
