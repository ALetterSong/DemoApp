package cc.haoduoyu.demoapp.downloadservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/1/30.
 */
public class DownloadActivity extends AppCompatActivity {


    @Bind(R.id.textView)
    TextView fileName;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    FileInfo fileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        progressBar.setMax(100);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
        fileInfo = new FileInfo(0,
                "http://www.zealer.com/assets/ZEALER.apk", "ZEALER.apk", 0, 0);
        fileName.setText(fileInfo.getFileName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @OnClick(R.id.start)
    void start() {
        Intent intent = new Intent(DownloadActivity.this, DownloadService.class);
        intent.setAction(DownloadService.ACTION_START);
        intent.putExtra("fileInfo", fileInfo);
        startService(intent);
    }

    @OnClick(R.id.stop)
    void stop() {
        Intent intent = new Intent(DownloadActivity.this, DownloadService.class);
        intent.setAction(DownloadService.ACTION_STOP);
        intent.putExtra("fileInfo", fileInfo);
        startService(intent);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = intent.getIntExtra("finished", 0);
                progressBar.setProgress(finished);
                if (finished == 100) {
                    fileName.setText(fileInfo.getFileName() + " 下载完成");
                }
            }
        }
    };
}
