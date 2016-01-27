package cc.haoduoyu.demoapp.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observer;

/**
 * http://gank.io/post/560e15be2dca930e00da1083
 * a library for composing asynchronous and event-based programs using observable sequences for the Java VM"
 * （一个在 Java VM 上使用可观测的序列来组成异步的、基于事件的程序的库）
 * Created by XP on 2015/12/14.
 */
public class RxJavaActivity extends AppCompatActivity {
    private static String TAG = "RxJavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {
            Log.d(TAG, "Completed!");
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d(TAG, "Error!");
        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item:" + s);
        }
    };
}
