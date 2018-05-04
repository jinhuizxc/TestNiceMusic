package com.example.jh.testnicemusic.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xian on 2018/1/13.
 */

public interface NewsApi {

    @GET("819-1?showapi_appid=22640&showapi_sign=0676cf5617eb46f1a6da7bcf7853f423&type=34&num=5")
    Observable<ResponseBody> requestBanner();

    @GET("819-1?showapi_appid=22640&showapi_sign=0676cf5617eb46f1a6da7bcf7853f423")
    Observable<ResponseBody> requestWelfare(@Query("type") int type, @Query("num") int num);
}
