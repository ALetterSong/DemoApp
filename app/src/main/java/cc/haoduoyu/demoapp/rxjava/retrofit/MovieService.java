package cc.haoduoyu.demoapp.rxjava.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by XP on 2016/5/9.
 */
public interface MovieService {

    String BASE_URL = "https://api.douban.com/v2/movie/";

    @GET("top250")
    Call<Movie> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<Movie> getTopMovie2(@Query("start") int start, @Query("count") int count);
}
