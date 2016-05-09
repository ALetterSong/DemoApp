package cc.haoduoyu.demoapp.rxjava.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by XP on 2016/5/9.
 */
public interface MovieService {
    @GET("top250")
    Call<Movie> getTopMovie(@Query("start") int start, @Query("count") int count);
}
