package cc.haoduoyu.demoapp.rxjava.retrofit;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by XP on 2016/5/9.
 */
public interface MovieService2 {
    @GET("top250")
    Observable<Movie> getTopMovie(@Query("start") int start, @Query("count") int count);
}
