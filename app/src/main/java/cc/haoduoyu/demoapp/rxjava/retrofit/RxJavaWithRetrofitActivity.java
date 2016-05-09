package cc.haoduoyu.demoapp.rxjava.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cc.haoduoyu.demoapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by XP on 2016/5/9.
 */
public class RxJavaWithRetrofitActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private Button btn2;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_retrofit);

        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        tv = (TextView) findViewById(R.id.tv);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn:
                getMovie();
                break;

            case R.id.btn2:
                getMovie2();
                break;
        }
    }


    //just use retrofit
    //A（观察者）对 B（被观察者）的某种变化高度敏感，需要在 B 变化的一瞬间做出反应。
    private void getMovie() {
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<Movie> call = movieService.getTopMovie(0, 10);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                tv.setText("retrofit " + response.body().toString());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                tv.setText(t.getMessage());
            }
        });
    }

    //retrofit with rxjava
    private void getMovie2() {
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MovieService2 movieService = retrofit.create(MovieService2.class);

        movieService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(RxJavaWithRetrofitActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        tv.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(Movie movieEntity) {
                        tv.setText("retrofit with rxjava " + movieEntity.toString());
                    }
                });
    }

}
