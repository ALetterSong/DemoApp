package cc.haoduoyu.demoapp.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/2/2.
 */
public class AsyncTaskActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ProgressDialog mDialog;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        mTextView = (TextView) findViewById(R.id.id_tv);

        mDialog = new ProgressDialog(this);
        mDialog.setMax(100);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(false);

// 3.0之后的AsyncTask同时只能有1个任务在执行。为什么升级之后可以同时执行的任务数反而变少了呢？
// 这是因为更新后的AsyncTask已变得更加灵活，如果不想使用默认的线程池，还可以自由地进行配置。比如使用如下的代码来启动任务：
// 这样就可以使用我们自定义的一个Executor来执行任务，而不是使用SerialExecutor。
// 下面代码的效果允许在同一时刻有15个任务正在执行，并且最多能够存储200个任务。
        Executor exec = new ThreadPoolExecutor(15, 200, 10,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
//        new MyAsyncTask().executeOnExecutor(exec);


        //测试直接打开注释，分别在不同版本系统中运行
        //4.4 #1-#2-#3-#4-#5-#5-#5---

        for (int i = 1; i <= 139; i++) {
//			new MyAsyncTask2().execute();
            new MyAsyncTask2().executeOnExecutor(exec);
        }


//        new MyAsyncTask().execute();

    }

    private class MyAsyncTask2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.e(TAG, Thread.currentThread().getName());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

    }


    private class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
            Log.e(TAG, Thread.currentThread().getName() + " onPreExecute ");
        }

        @Override
        protected Void doInBackground(Void... params) {

            // 模拟数据的加载,耗时的任务
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            Log.e(TAG, Thread.currentThread().getName() + " doInBackground ");
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mDialog.setProgress(values[0]);
            Log.e(TAG, Thread.currentThread().getName() + " onProgressUpdate ");
        }

        @Override
        protected void onPostExecute(Void result) {
            //进行数据加载完成后的UI操作
            mDialog.dismiss();
            mTextView.setText("LOAD DATA SUCCESS ");
            Log.e(TAG, Thread.currentThread().getName() + " onPostExecute ");
        }

    }

}
