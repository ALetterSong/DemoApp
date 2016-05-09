package cc.haoduoyu.demoapp.rxjava.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.IOException;

import cc.haoduoyu.demoapp.App;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by XP on 2016/5/9.
 */
public enum RetrofitSingleton {
    i;

    private MovieService movieService;


    public MovieService getMovieService() {
        return movieService;
    }

    public void init() {

        //设置缓存路径
        File httpCacheDirectory0 = new File(App.getContext().getCacheDir(), "responses_0");
        File httpCacheDirectory1 = new File(App.getContext().getCacheDir(), "responses_1");
        //设置缓存 10M
        Cache cache0 = new Cache(httpCacheDirectory0, 10 * 1024 * 1024);
        Cache cache1 = new Cache(httpCacheDirectory1, 15 * 1024 * 1024);
        //选择拦截器
//        String interceptor = SettingUtils.getInstance(App.getContext()).getCache();
//        LogUtils.d("interceptor" + interceptor);

        //创建OkHttpClient，并添加拦截器和缓存代码
        OkHttpClient client0 = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor0)
                .addInterceptor(new LoggingInterceptor())
                .cache(cache0)
                .build();

        OkHttpClient client1 = new OkHttpClient.Builder()
                .addInterceptor(interceptor1)
                .addInterceptor(new LoggingInterceptor())
                .cache(cache1)
                .build();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(MovieService.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
//                .client(isEnableCache ? ("0".equals(interceptor) ? client0 : client1) : new OkHttpClient())
                .build();

        movieService = retrofit.create(MovieService.class);
    }

    /**
     * 拦截器一(有网和没有网都是先读缓存)
     * 设置max-age为60s之后，这60s之内无论有没有网,都读缓存。
     */
    Interceptor interceptor0 = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LogUtils.i(request);
            Response response = chain.proceed(request);

            int maxAge = 60;
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
            return response;
        }
    };
    /**
     * 拦截器二(离线可以缓存，在线就获取最新数据)
     * only-if-cached:(仅为请求标头)请求:告知缓存者,我希望内容来自缓存，我并不关心被缓存响应,是否是新鲜的.
     * public:(仅为响应标头)响应:告知任何途径的缓存者,可以无条件的缓存该响应.
     */
    Interceptor interceptor1 = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //拦截reqeust
            Request request = chain.request();
            //判断网络连接状况
            if (isNetworkReachable(App.getContext())) {
                //无网络时只从缓存中读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtils.d("暂无网络");
            }

            Response response = chain.proceed(request);
            if (isNetworkReachable(App.getContext())) {
                // 有网络时 设置缓存超时时间1分钟
                int maxAge = 60; // read from cache for 1 minute
                response.newBuilder()
                        //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        //设置缓存超时时间
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        //设置缓存策略，及超时策略
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };

    /**
     * 日志拦截器
     */
    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            LogUtils.i(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            LogUtils.i(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

    public Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
